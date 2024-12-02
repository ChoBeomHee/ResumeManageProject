package resume.backend.search;

import co.elastic.clients.elasticsearch.core.SearchRequest;
import org.springframework.stereotype.Service;
import resume.backend.resume.repository.ResumeRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final AutoRepository autoRepository;
    private final SearchRepository searchRepository;
    private final ResumeRepository resumeRepository;


    public SearchService(AutoRepository autoRepository, SearchRepository searchRepository, ResumeRepository resumeRepository) {
        this.autoRepository = autoRepository;
        this.searchRepository = searchRepository;
        this.resumeRepository = resumeRepository;
    }

    // 키워드가 포함된 contents와 title 검색
    public List<Resume> searchByKeyword(String keyword) {
        return this.searchRepository.findByTitleContainingOrContentsContaining(keyword, keyword);
    }

    public List<String> autocompleteKeyword(String keyword){
        List<AutoComplete> list = this.autoRepository.findByTitleContaining(keyword);
        HashSet<String> res = new HashSet<>();
        for(int i = 0; i < list.size(); i++){
            res.add(list.get(i).getTitle());
        }

        return List.copyOf(res);
    }

    public void align() {
        // 1. ResumeRepository에서 모든 레쥬메 정보를 가져옵니다.
        List<resume.backend.resume.domain.Resume> resumes = resumeRepository.findAll();

        // 2. SearchRepository에서 모든 레쥬메 정보를 가져옵니다.
        List<resume.backend.search.Resume> searchResumes = (List<Resume>) searchRepository.findAll();
    }

}
