package resume.backend.search;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface AutoRepository extends ElasticsearchRepository<AutoComplete, Long> {
    @Query ("{\"match_phrase\": {\"title\": \"?0\"}}")
    List<AutoComplete> findByTitleContaining(String title);
}
