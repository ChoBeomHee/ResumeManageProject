package resume.backend.resume.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import resume.backend.resume.dto.*;
import resume.backend.resume.resumeapiservice.ResumeApiService;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class ResumeApiController {
    final private ResumeApiService resumeApiService;
    public ResumeApiController(ResumeApiService resumeApiService) {
        this.resumeApiService = resumeApiService;
    }

    @PostMapping("/resume")
    private ResponseEntity<String> insertResume(@RequestBody InsertResumeDto insertResumeDto,  Authentication authentication){
        System.out.println(insertResumeDto.getCompanyName());
        for(TitleAndContent t : insertResumeDto.getResumes()){
            System.out.println(t.getTitle());
        }
        return ResponseEntity.status(HttpStatus.OK).body(resumeApiService.insertResume(insertResumeDto, authentication));
    }

    @DeleteMapping("/resume/{id}")
    private ResponseEntity<String> deleteResume(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.OK).body(this.resumeApiService.deleteResume(id));
    }

    @PostMapping("/company")
    private ResponseEntity<String> createCompany(@RequestBody CreateCompanyDto createCompanyDto, Authentication authentication){
        return ResponseEntity.status(HttpStatus.OK).body(resumeApiService.createCompany(createCompanyDto, authentication));
    }

    @PutMapping("/resume")
    private ResponseEntity<String> updateResume(@RequestBody UpdateResumeDto updateResumeDto){
        return ResponseEntity.status(HttpStatus.OK).body(resumeApiService.updateResume(updateResumeDto));
    }

    @GetMapping("/company")
    private ResponseEntity<List<String>> selectCompany(Authentication userInfo){
        return ResponseEntity.status(HttpStatus.OK).body(resumeApiService.selectCompany(userInfo));
    }

    @GetMapping("/resume/{companyName}")
    private ResponseEntity<List<CompanyResumeDto>> selectResume(@PathVariable String companyName, Authentication authentication){
        return ResponseEntity.status(HttpStatus.OK).body(resumeApiService.selectResume(companyName, authentication));
    }
}
