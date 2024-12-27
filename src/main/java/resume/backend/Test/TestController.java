package resume.backend.Test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Value("${custom.prefix}")
    private String serverAddress;

    @GetMapping("/auth/port")
    public String test(){
        return this.serverAddress;
    }
}
