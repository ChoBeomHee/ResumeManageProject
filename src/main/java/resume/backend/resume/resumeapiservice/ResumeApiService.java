package resume.backend.resume.resumeapiservice;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import resume.backend.resume.domain.Company;
import resume.backend.resume.domain.Resume;
import resume.backend.resume.dto.CreateCompanyDto;
import resume.backend.resume.dto.InsertResumeDto;
import resume.backend.resume.dto.CompanyResumeDto;
import resume.backend.resume.dto.TitleAndContent;
import resume.backend.resume.repository.CompanyRepository;
import resume.backend.resume.repository.ResumeRepository;
import resume.backend.search.AutoComplete;
import resume.backend.search.AutoRepository;
import resume.backend.search.SearchRepository;
import resume.backend.user.domain.User;
import resume.backend.user.repository.UserRepository;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ResumeApiService {
    final private AutoRepository autoRepository;
    final private UserRepository userRepository;
    final private CompanyRepository companyRepository;
    final private ResumeRepository resumeRepository;
    final private SearchRepository searchRepository;

    public ResumeApiService(CompanyRepository companyRepository, ResumeRepository repository, AutoRepository autoRepository, UserRepository userRepository, ResumeRepository resumeRepository, SearchRepository searchRepository) {
        this.companyRepository = companyRepository;
        this.autoRepository = autoRepository;
        this.userRepository = userRepository;
        this.resumeRepository = resumeRepository;
        this.searchRepository = searchRepository;
    }

    public String createCompany(CreateCompanyDto createCompanyDto, Authentication authentication) {
        if (this.companyRepository.findByNameAndUserName(createCompanyDto.getName(), authentication.getName()) != null) {
            throw new IllegalArgumentException("이미 존재하는 회사 이름입니다.");
        }
        User user = this.userRepository.findByUserName(authentication.getName());

        if (user == null) {
            throw new NoSuchElementException("해당 유저를 찾을 수 없습니다.");
        }

        String userName = user.getUserName();

        this.companyRepository.save(Company.builder()
                .name(createCompanyDto.getName())
                .userName(userName)
                .build());
        System.out.println("회사 생성 완료    ");
        return "회사 생성 완료 : " + createCompanyDto.getName();
    }

    public String insertResume(InsertResumeDto insertResumeDto, Authentication authentication) {
        Company company = companyRepository.findByNameAndUserName(insertResumeDto.getCompanyName(), authentication.getName());
        if (company == null) {
            throw new NoSuchElementException("해당 회사를 찾을 수 없습니다.");
        }

        int companyId = company.getId();

        for(TitleAndContent titleAndContent : insertResumeDto.getResumes()){
            this.resumeRepository.save(Resume.builder()
                    .companyId(companyId)
                    .contents(titleAndContent.getContents())
                    .title(titleAndContent.getTitle())
                    .build());

            this.searchRepository.save(resume.backend.search.Resume.builder()
                    .id(UUID.randomUUID().toString())
                    .companyId(companyId)
                    .contents(titleAndContent.getContents())
                    .title(titleAndContent.getTitle())
                    .build());

            this.autoRepository.save(AutoComplete.builder()
                    .id(UUID.randomUUID().toString())
                    .companyId(companyId)
                    .contents(titleAndContent.getContents())
                    .title(titleAndContent.getTitle())
                    .build());
        }

        return "자소서 작성 완료 " + insertResumeDto.getCompanyName();
    }

    public String deleteResume(int id){
        Resume resume = this.resumeRepository.findById((long) id)
                .orElseThrow(() -> new IllegalArgumentException("Resume not found with id: " + id));

        this.resumeRepository.delete(resume);

        return "삭제 성공";
    }

    public List<String> selectCompany(Authentication userInfo){
        List<String> companyList = new ArrayList<>();
        List<Company> companies = this.companyRepository.findByUserName(userInfo.getName());

        for(Company c : companies){
            companyList.add(c.getName());
        }

        return companyList;
    }

    public List<CompanyResumeDto> selectResume(String companyName, Authentication authentication) {
        int companyId = this.companyRepository.findByNameAndUserName(companyName, authentication.getName()).getId();

        List<Resume> resumeList = this.resumeRepository.findByCompanyId(companyId);
        if (resumeList == null) {
            throw new NoSuchElementException("자소서를 찾을 수 없습니다.");
        }

        List<CompanyResumeDto> results = new ArrayList<>();
        for (Resume r : resumeList) {
            results.add(new CompanyResumeDto(r.getId(), r.getTitle(), r.getContents()));
        }

        return results;
    }
}