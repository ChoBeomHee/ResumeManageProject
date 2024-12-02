package resume.backend.resume.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import resume.backend.resume.domain.Company;
import resume.backend.user.domain.User;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByNameAndUserName(String name, String username);
    List<Company> findByUserName(String username);
}
