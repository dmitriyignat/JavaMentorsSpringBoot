package mentors.spring_boot.controller;



import mentors.spring_boot.model.User;
import mentors.spring_boot.request.UserRequest;
import mentors.spring_boot.response.UserResponse;
import mentors.spring_boot.service.RoleService;
import mentors.spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

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
    private UserResponse response;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping(value = "/getUser")
    public UserResponse getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.getUserByLogin(authentication.getName());
        response = new UserResponse();
        response.setUser(user);
        return response;
    }

    @GetMapping(value = {"/welcome", "/"})
    public ModelAndView readUser(ModelAndView model, Authentication authentication) {
        model.setViewName("userPage");
        return model;
    }

    @GetMapping(value = "/read")
    public ModelAndView readAllUsers(ModelAndView model) {
        model.setViewName("userList");
        return model;
    }

    @PostMapping(value = "/readUsers")
    public UserResponse readAllUsers() {
        List<User> users = userService.getAll();
        response.setUsers(users);
        response.setRoles(roleService.getAll());
        return response;
    }

    @PostMapping(value = "/updateUser")
    public void updateUser(@RequestBody UserRequest userRequest) {
        User user = userService.getById(userRequest.getId());
        if (user.getLogin().equals(userRequest.getLogin()) || userService.validate(userRequest.getLogin(), userRequest.getPassword()) <= 0) {
            user = new User(userRequest.getLogin(), userRequest.getName(), userRequest.getPassword());
            user.setId(userRequest.getId());
            userService.update(user, userRequest.getRoles());
        }
    }

    @PostMapping(value = "/addUser")
    public void addUser(@RequestBody UserRequest userRequest) {
        User user = new User(userRequest.getLogin(), userRequest.getName(), userRequest.getPassword());
        if (userService.validate(userRequest.getLogin(), userRequest.getPassword()) == 0) {
            userService.add(user, userRequest.getRoles());
        }

    }

    @PostMapping(value = "/delete")
    public void deleteUserById(@RequestParam("id") long id) {
        userService.delete(id);
    }

}
