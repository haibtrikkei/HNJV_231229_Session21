package ra.demo_springwebmvc_hibernate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/home")
    public String home(){
        return "home-user";
    }
}
