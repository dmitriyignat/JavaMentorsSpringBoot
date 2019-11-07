package mentors.spring_boot.controller;

import mentors.spring_boot.model.Role;
import mentors.spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import mentors.spring_boot.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private final UserService<User> service;

    @Autowired
    public AdminController(UserService<User> service) {
        this.service = service;
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public ModelAndView readUser(ModelAndView model, Authentication authentication) {
        List<User> users = service.getAll();
        model.addObject("listUser", users);
        model.setViewName("userList");
        return model;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView redirectToUser(@RequestParam("id") long id, ModelAndView mv){
        User user = service.getById(id);
        List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        mv.addObject("user", user);
        mv.addObject("roles", roles);
        mv.addObject("message", "Update user");
        mv.setViewName("user");
        return mv;
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(@RequestParam("id") long id, @RequestParam("name") String name, @RequestParam("role") String[] roles,
                             @RequestParam("login") String login, @RequestParam("password") String password) {
        User user = service.getById(id);
        if (user.getLogin().equals(login) || service.validate(login, password) <= 0) {
            user = new User(login, name, password);
            user.setId(id);
            service.update(user, roles);
        }
        return "redirect:/admin/read";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteUserById(@RequestParam("id") long id) {
        service.delete(id);
        return "redirect:/admin/read";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public ModelAndView redirectToUser(ModelAndView mv){
        mv.addObject("message", "Add user");
        mv.setViewName("user");
        return mv;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@RequestParam("name") String name, @RequestParam("login") String login,
                          @RequestParam("password") String password, @RequestParam("role") String[] roles) {

        User user = new User(login, name, password);
        if (service.validate(login, password) == 0) {
            service.add(user, roles);
        }
        return "redirect:/admin/read";
    }

    @RequestMapping(value="/error")
    public String error() {
        return "access-denied";
    }

}
