package resume.backend.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import resume.backend.user.service.LoginService;

@Controller
public class KaKaoLoginController {
    final LoginService loginService;

    public KaKaoLoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallBack(String code, HttpServletRequest request) throws ParseException {
        String username = loginService.requestKakaoToken(code);
        System.out.println(loginService.kakaoUserCheck(username));
        loginService.forcedLogin(username, request);

        //return new ResponseEntity<>("카카오 로그인 성공",HttpStatus.OK);
        return "redirect:http://localhost:8080/home";
    }

    @GetMapping("/auth/loginPage")
    public String loginPage(){
        return "loginPage";
    }
}
