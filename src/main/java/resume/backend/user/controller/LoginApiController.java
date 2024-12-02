package resume.backend.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import resume.backend.user.dto.JoinDto;
import resume.backend.user.service.LoginService;

@RestController
public class LoginApiController {
    private final LoginService loginService;

    public LoginApiController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/test")
    public String test(){
        return "Hello World!!";
    }

    @PostMapping("/auth/join")
    public ResponseEntity<String> join(@RequestBody JoinDto joinDto){
        return new ResponseEntity<>(loginService.join(joinDto), HttpStatus.OK);
    }
}
