package it.academy.service.contollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static it.academy.service.utils.UIConstants.LOGIN_PAGE;

@Controller
@RequestMapping("/")
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return LOGIN_PAGE;
    }

}
