package mentors.spring_boot.controller;


import mentors.spring_boot.model.User;
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

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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

}
