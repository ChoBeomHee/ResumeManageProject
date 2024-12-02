package resume.backend.search;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search/{keyword}")
    public List<Resume> search(@PathVariable String keyword) {
        long startTime = System.currentTimeMillis();
        List<Resume> res = searchService.searchByKeyword(keyword);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Elasticsearch 검색 성능 측정: " + elapsedTime + "ms");

        return res;
    }

    @GetMapping("/autocomplete/{keyword}")
    public List<String> autocomplete(@PathVariable String keyword){
        return searchService.autocompleteKeyword(keyword);
    }
}
