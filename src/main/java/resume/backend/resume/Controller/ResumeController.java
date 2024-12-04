package resume.backend.resume.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResumeController {
    @GetMapping("/company")
    private String company(){
        return "company";
    }

    @GetMapping("/resume")
    private String resume(){
        return "resume";
    }
    @GetMapping("/searchPage")
    private String search(){
        return "search";
    }

    @GetMapping("/resumeUpdate")
    private String update() {return "resumeUpdate"; }
}
