package resume.backend.search;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "resumes")
public class Resume {
    @Id
    private String id;

    private int companyId;

    private String title;

    @Field(type = FieldType.Text)
    private String contents;
}
