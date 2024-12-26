package resume.backend.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @Value("${custom.prefix}")
    private String prefix;

    @GetMapping("/home")
    public String homePage(Model model){
        model.addAttribute("prefix", prefix);

        return "home";
    }
}
