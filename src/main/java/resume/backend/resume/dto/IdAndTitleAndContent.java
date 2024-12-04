package resume.backend.resume.dto;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class IdAndTitleAndContent {
    private int id;
    private String title;
    private String content;
}
