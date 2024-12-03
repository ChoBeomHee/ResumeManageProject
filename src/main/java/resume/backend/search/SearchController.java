package resume.backend.search;

import org.springframework.web.bind.annotation.*;
import resume.backend.resume.domain.Resume;

import java.util.List;

@RestController
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    /***
     * 엘라스틱서치 기반 검색 -> 보류
     *
     * @param keyword 원하는 키워드
     * @return 자소서 내용
     */
//    @GetMapping("/search/{keyword}")
//    public List<Resume> search(@PathVariable String keyword) {
//        long startTime = System.currentTimeMillis();
//        List<Resume> res = searchService.searchByKeyword(keyword);
//        long endTime = System.currentTimeMillis();
//        long elapsedTime = endTime - startTime;
//        System.out.println("Elasticsearch 검색 성능 측정: " + elapsedTime + "ms");
//
//        return res;
//    }

    @GetMapping("/search/{keyword}")
    public List<Resume> search(@PathVariable String keyword){
        return this.searchService.searchByKeywordQuery(keyword);
    }

    @GetMapping("/autocomplete/{keyword}")
    public List<String> autocomplete(@PathVariable String keyword){
        return searchService.autocompleteKeyword(keyword);
    }
}
