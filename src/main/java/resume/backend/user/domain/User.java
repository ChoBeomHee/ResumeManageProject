package resume.backend.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column
    String userName;
    @Column
    String passWord;

    public User() {

    }
}
