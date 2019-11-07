package mentors.spring_boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommonController {

//    @RequestMapping(value="/error")
//    public ModelAndView error(ModelAndView model) {
//        model.setViewName("access-denied");
//        return model;
//    }
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
