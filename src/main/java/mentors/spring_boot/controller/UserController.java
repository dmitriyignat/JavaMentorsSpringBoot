package mentors.spring_boot.controller;


import mentors.spring_boot.model.User;
import mentors.spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public ModelAndView readUser(ModelAndView model, Authentication authentication) {
        User user = (User)service.getUserByLogin(authentication.getName());
        model.addObject("user", user);
        model.setViewName("userPage");
        return model;
    }

    @RequestMapping(value="/error")
    public ModelAndView error(ModelAndView model) {
        model.setViewName("error");
        return model;
    }

}
