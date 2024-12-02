package resume.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class JoinDto {
    String username;
    String password;
}
