package mentors.spring_boot.controller;



import mentors.spring_boot.model.Role;
import mentors.spring_boot.model.User;
import mentors.spring_boot.service.RoleService;
import mentors.spring_boot.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/getUser")
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUserByLogin(authentication.getName());
    }

    @GetMapping(value = {"/welcome", "/"})
    public ModelAndView readUser(ModelAndView model) {
        model.setViewName("userPage");
        return model;
    }

    @GetMapping(value = "/read")
    public ModelAndView readAllUsers(ModelAndView model) {
        model.setViewName("userList");
        return model;
    }

    @GetMapping(value = "/roles")
    public List<Role> getAllRoles() {
        return roleService.getAll();
    }

    @PostMapping(value = "/readUsers")
    public List<User> readAllUsers() {
        List<User> users = userService.getAll();
        return users;
    }

    @PostMapping(value = "/updateUser")
    public void updateUser(@RequestBody User userNew) {
        User user = userService.getById(userNew.getId());
        if (user.getLogin().equals(userNew.getLogin()) || userService.validate(userNew.getLogin(), userNew.getPassword()) <= 0) {
            userService.update(userNew);
        }
    }

    @PostMapping(value = "/addUser")
    public void addUser(@RequestBody User user) {
        if (userService.validate(user.getLogin(), user.getPassword()) == 0) {
            userService.add(user);
        }
    }

    @PostMapping(value = "/delete")
    public void deleteUserById(@RequestParam("id") long id) {
        userService.delete(id);
    }

}
