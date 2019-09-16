package graduation.raitrest.web;

import graduation.raitrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController {
    @Autowired
    private UserService service;



    @GetMapping()
    public String root() {
        return "index";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @PostMapping("/user")
    public String setUser(HttpServletRequest request) {
        String user = request.getParameter("email");
//        SecurityUtil.setAuthUserId(userId);
        service.loadUserByUsername(user);
        return "redirect:index";
    }
}
