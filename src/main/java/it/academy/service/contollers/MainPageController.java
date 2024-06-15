package it.academy.service.contollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import static it.academy.service.utils.Constants.*;
import static it.academy.service.utils.UIConstants.MAIN_PAGE_PATH;

@Controller
@RequestMapping("/mainPage")
public class MainPageController {

    @GetMapping
    public String showMainPage(Model model) {
        model.addAttribute(PAGE_NUM, FIRST_PAGE);
        return MAIN_PAGE_PATH;
    }
}
