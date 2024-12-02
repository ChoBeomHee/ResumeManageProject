package resume.backend.search;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SearchRepository extends ElasticsearchRepository<Resume, Long> {
    @Query("{\"bool\": {\"should\": [ {\"match\": {\"title\": \"?0\"}}, {\"match\": {\"contents\": \"?1\"}} ]}}")
    List<Resume> findByTitleContainingOrContentsContaining(String title, String contents);
}
