package resume.backend.resume.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import resume.backend.resume.dto.CreateCompanyDto;
import resume.backend.resume.dto.InsertResumeDto;
import resume.backend.resume.dto.CompanyResumeDto;
import resume.backend.resume.dto.TitleAndContent;
import resume.backend.resume.resumeapiservice.ResumeApiService;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class ResumeApiController {
    final private ResumeApiService resumeApiService;

//    private static final String[] TITLES = {
//            "소프트웨어 엔지니어", "데이터 분석가", "프로젝트 매니저",
//            "UX/UI 디자이너", "시스템 관리자", "웹 개발자", "모바일 개발자",
//            "QA 엔지니어", "DevOps 엔지니어", "네트워크 관리자",
//            "인공지능 연구원", "게임 개발자", "클라우드 엔지니어",
//            "정보 보안 전문가", "BI 분석가", "제품 매니저",
//            "시스템 아키텍트", "기술 지원 엔지니어", "SEO 전문가",
//            "모바일 앱 개발자", "웹 디자이너", "데이터 엔지니어",
//            "인턴십 프로그램 지원자", "비즈니스 애널리스트", "전략 기획 담당자",
//            "소셜 미디어 매니저", "컨텐츠 마케팅 전문가", "시장 조사 분석가",
//            "이커머스 매니저", "풀스택 개발자", "IT 컨설턴트"
//    };

//    private static final String[] CONTENTS = {
//            "자신의 경험을 상세히 기술하세요. 이전 직장에서 맡았던 역할에 대한 구체적인 설명과 함께, 수행한 작업의 맥락과 결과를 명확히 하십시오. 예를 들어, 어떤 프로젝트에 참여했는지, 그 프로젝트에서 당신의 주요 책임이 무엇이었는지, 어떤 기술이나 도구를 사용했는지, 그리고 그 결과로 어떤 성과를 얻었는지를 서술하는 것이 중요합니다. 이 경험이 현재 지원하는 직무에 어떻게 연결되는지도 강조해 주시면 좋습니다.",
//            "해당 직무에 필요한 기술을 강조하세요. 특히 이 직무에서 요구하는 특정 기술이나 경험에 대해 자신의 강점을 부각시키고, 이를 통해 회사에 어떻게 기여할 수 있는지를 명확하게 하세요. 관련된 기술이나 자격증, 교육 이수 경험 등을 포함하여 당신이 직무를 수행할 준비가 되어 있다는 것을 강조하는 것이 좋습니다.",
//
//            "프로젝트 경험 및 성과를 구체적으로 작성하세요. 진행했던 프로젝트의 목표, 수행한 과정, 자신의 역할 및 기여도, 최종 결과에 대한 상세한 설명을 포함하세요. 예를 들어, 프로젝트가 어떤 문제를 해결하기 위해 시작되었는지, 당신이 어떤 방법을 사용하여 기여했는지, 그 결과로 무엇을 달성했는지를 서술하는 것이 좋습니다. 수치적인 결과나 피드백을 포함하면 더 효과적입니다.",
//
//            "팀워크 및 커뮤니케이션 능력을 부각시키세요. 팀 프로젝트에서의 역할, 팀원들과의 효과적인 소통 방식, 갈등 해결 경험 등을 포함해 당신의 커뮤니케이션 능력을 구체적으로 보여주세요. 팀원들과의 협력에서 얻은 교훈이나 경험을 통해 팀의 목표를 달성하는 데 어떻게 기여했는지를 강조하는 것이 중요합니다.",
//
//            "업계 최신 트렌드를 반영하여 작성하세요. 현재의 기술 트렌드나 업계의 변화에 대한 자신의 인식을 반영하고, 이러한 트렌드를 어떻게 업무에 적용했는지를 구체적으로 서술하세요. 업계의 동향이나 변화를 주의 깊게 살펴보며 그에 맞는 기술을 사용하거나 전략을 세운 경험을 공유하세요.",
//
//            "비즈니스 가치를 극대화하는 방안을 제시하세요. 자신의 업무나 프로젝트가 어떻게 기업의 목표와 연계되어 있는지를 명확히 하고, 어떻게 비즈니스 성과를 높였는지를 서술하세요. 예를 들어, 특정 프로젝트를 통해 비용을 절감하거나 매출을 증가시킨 경험을 구체적으로 설명하며, 그로 인해 회사에 미친 긍정적인 영향을 강조하세요.",
//
//            "문제 해결 능력을 강조하며 작성하세요. 과거에 직면했던 구체적인 문제와 그 문제를 어떻게 분석하고 해결했는지를 서술하세요. 이 과정에서 어떤 접근 방식을 사용했는지, 어떤 대안을 고려했는지, 그리고 그 결과가 어땠는지를 상세히 기술하여 문제 해결 능력을 입증하세요.",
//
//            "고객의 요구사항을 충족시키는 경험을 서술하세요. 고객과의 협업 과정에서 그들의 요구를 어떻게 이해하고 충족시켰는지에 대한 경험을 구체적으로 설명하세요. 고객 피드백을 반영하여 서비스를 개선한 사례나 고객의 기대를 초과 달성한 경험이 있다면 포함하세요.",
//
//            "자신의 강점을 부각시키세요. 직무와 관련된 자신의 강점과 이 강점이 과거의 경험에서 어떻게 발휘되었는지를 서술하는 것이 좋습니다. 예를 들어, 특정 기술에 대한 전문성, 문제 해결 능력, 빠른 학습 능력 등을 강조하며, 이를 통해 프로젝트나 팀에 기여한 사례를 들어 설명하세요.",
//
//            "업무 수행 중의 주요 성과를 구체적으로 적어주세요. 자신의 업무에서 이룬 구체적인 성과나 수치를 포함하여, 이러한 성과가 조직에 미친 긍정적인 영향을 강조하세요. 예를 들어, 특정 목표를 달성하거나 효율성을 개선한 수치적인 결과를 제시하며 성과를 명확히 보여주는 것이 중요합니다.",
//
//            "데이터 기반의 의사 결정을 강조하세요. 데이터 분석 결과를 바탕으로 의사 결정을 내린 경험과 이를 통해 얻은 성과에 대해 구체적으로 설명하세요. 어떤 데이터를 수집하고 분석했는지, 그 결과를 기반으로 어떤 결정을 내렸는지, 그리고 그 결정이 회사에 미친 영향에 대해 서술하세요.",
//
//            "프로젝트의 리더십 경험을 서술하세요. 팀을 이끌면서 맡았던 역할과 리더십을 발휘한 구체적인 사례를 통해 당신의 리더십 능력을 보여주세요. 팀의 목표를 설정하고, 팀원들을 어떻게 동기부여 했는지, 그리고 프로젝트를 성공적으로 이끌기 위해 어떤 전략을 사용했는지를 상세히 설명하세요.",
//
//            "다양한 이해관계자와의 협업 경험을 공유하세요. 프로젝트에서 여러 이해관계자와의 상호작용을 통해 어떻게 효과적으로 협력했는지에 대한 사례를 포함하세요. 이해관계자와의 갈등을 어떻게 조율했는지, 그로 인해 어떤 긍정적인 결과가 있었는지를 강조하는 것이 좋습니다.",
//
//            "기술적 도전 과제를 해결한 사례를 제시하세요. 기술적인 문제를 해결했던 경험을 설명하고, 그 과정에서 사용했던 방법론이나 기술을 명시하세요. 도전 과제를 해결하기 위해 어떤 접근 방식을 취했는지, 그리고 그 결과가 어땠는지를 구체적으로 설명하면 좋습니다.",
//
//            "실패 경험을 통해 배운 점을 설명하세요. 과거의 실패 경험과 그로부터 얻은 교훈을 통해 개인적인 성장이나 발전을 강조하는 것이 좋습니다. 실패의 원인을 분석하고, 이후 어떻게 대처했는지를 상세히 서술하며 이 경험이 향후 업무에 어떤 긍정적인 영향을 미쳤는지 강조하세요.",
//
//            "업무 프로세스를 개선한 경험을 기술하세요. 기존의 업무 프로세스를 어떻게 분석하고, 어떤 개선 방안을 제안했는지를 구체적으로 설명하세요. 이 과정에서 발생한 어려움과 그 해결책, 그리고 최종적으로 개선된 프로세스의 성과를 포함하는 것이 좋습니다.",
//
//            "다양한 기술 스택에 대한 이해도를 강조하세요. 자신이 사용해본 다양한 기술 스택과 그에 대한 경험을 바탕으로, 이를 어떻게 실제 업무에 적용했는지를 설명하세요. 각 기술의 장단점과 함께 이를 활용한 프로젝트 사례를 들어보면 더욱 설득력이 높아집니다.",
//
//            "특정 툴이나 프레임워크에 대한 전문성을 서술하세요. 사용했던 특정 툴이나 프레임워크의 기능과 활용 방법에 대해 깊이 있는 설명을 추가하고, 이를 통해 해결한 문제를 명확히 하세요. 특히 해당 툴을 선택한 이유와 그로 인해 얻은 이점을 강조하면 좋습니다.",
//
//            "전문적인 교육이나 인증 과정을 언급하세요. 이수한 교육 과정이나 인증이 직무에 어떻게 도움이 되었는지, 그리고 그 과정에서 어떤 지식을 습득했는지를 설명하세요. 이 경험이 자신을 어떻게 성장시켰는지를 구체적으로 서술하여, 지속적인 학습과 자기 발전의 의지를 보여주세요.",
//
//            "향후 경력 계획과 목표를 설명하세요. 향후 5년 또는 10년 후의 커리어 목표와 이를 달성하기 위한 구체적인 계획을 서술하세요. 이 목표가 어떻게 개인적인 가치관이나 직무의 요구와 일치하는지를 강조하며, 지속적인 성장에 대한 의지를 보여주면 좋습니다."
//    };



    private static final Random RANDOM = new Random();

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

    @GetMapping("/company")
    private ResponseEntity<List<String>> selectCompany(Authentication userInfo){
        return ResponseEntity.status(HttpStatus.OK).body(resumeApiService.selectCompany(userInfo));
    }

    @GetMapping("/resume/{companyName}")
    private ResponseEntity<List<CompanyResumeDto>> selectResume(@PathVariable String companyName, Authentication authentication){
        return ResponseEntity.status(HttpStatus.OK).body(resumeApiService.selectResume(companyName, authentication));
    }
}
