package resume.backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import resume.backend.user.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT u FROM User u where u.userName = :username")
    User findByUserName(@Param("username") String username);
}
