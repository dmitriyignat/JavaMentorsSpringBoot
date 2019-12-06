package mentors.spring_boot.controller;


import mentors.spring_boot.model.User;
import mentors.spring_boot.response.UserResponse;
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
    private UserResponse response;



    @Autowired
    public UserController(UserService userService, RoleService roleService) {
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

}
