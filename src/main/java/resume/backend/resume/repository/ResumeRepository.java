package resume.backend.resume.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import resume.backend.resume.domain.Resume;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByTitleContainingOrContentsContaining(String titleKeyword, String contentsKeyword);
    List<Resume> findByCompanyId(int id);

}
