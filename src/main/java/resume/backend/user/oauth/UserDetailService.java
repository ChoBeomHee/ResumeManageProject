package resume.backend.user.oauth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import resume.backend.user.domain.User;
import resume.backend.user.repository.UserRepository;

@Service
public class UserDetailService implements UserDetailsService {
    final private UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user == null) {
            throw new UsernameNotFoundException("해당 유저는 존재하지 않습니다.");
        }

        return new UserDetail(user);
    }
}
