package resume.backend.user.service;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.hibernate.mapping.Join;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import resume.backend.user.domain.User;
import resume.backend.user.dto.JoinDto;
import resume.backend.user.oauth.UserDetail;
import resume.backend.user.repository.UserRepository;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class LoginService {
    final private RestClient restClient;
    final private UserRepository userRepository;
    final private BCryptPasswordEncoder bCryptPasswordEncoder;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.restClient = RestClient.create();
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public String requestKakaoToken(String code) throws ParseException {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "9fe6e0ee364495b0f1ec8dd4da3260d5");
        params.add("redirect_uri", "http://115.85.183.243/auth/kakao/callback");
        params.add("code", code);
        
        ResponseEntity<Map> response = restClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(params)
                .retrieve()
                .toEntity(Map.class);


        return requestUserInfo((String) Objects.requireNonNull(response.getBody()).get("access_token"));
    }

    private String requestUserInfo(String authorization) throws ParseException {
        String accessToken = "Bearer " + authorization;

        ResponseEntity<Map> userInfo = restClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", accessToken)
                .retrieve()
                .toEntity(Map.class);

        Map user_account = (Map) Objects.requireNonNull(userInfo.getBody()).get("kakao_account");

        return (String) user_account.get("email");
    }

    public String join(JoinDto JoinDto){
        User user = User.builder()
                .userName(JoinDto.getUsername())
                .passWord(this.bCryptPasswordEncoder.encode(JoinDto.getPassword()))
                .build();

        userRepository.save(user);

        return "회원가입 성공";
    }

    public String kakaoUserCheck(String username){
        String response = "회원 정보가 있기 때문에 회원가입 없이 진행";

        if(userFind(username) == null){
            String password = UUID.randomUUID().toString();
            join(new JoinDto(username, password));

            response = "회원정보가 없어 로그인 후 진행";
        }

        return response;
    }

    public void forcedLogin(String username, HttpServletRequest request){
        User user = this.userRepository.findByUserName(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(new UserDetail(user), null, Collections.emptyList());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    }

    private User userFind(String username){
        return this.userRepository.findByUserName(username);
    }
}
