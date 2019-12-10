package mentors.spring_boot.controller;


import mentors.spring_boot.model.User;
import mentors.spring_boot.service.RoleService;
import mentors.spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;



    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/getUser")
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.getUserByLogin(authentication.getName());
        return user;
    }

    @GetMapping(value = {"/welcome", "/"})
    public ModelAndView readUser(ModelAndView model, Authentication authentication) {
        model.setViewName("userPage");
        return model;
    }

}
