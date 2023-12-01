package az.iktlab.taskmanagement.config;

import az.iktlab.taskmanagement.dao.User;
import az.iktlab.taskmanagement.model.UserInfo;
import az.iktlab.taskmanagement.reposiroty.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
@RequiredArgsConstructor
public class RequestScopeUserProvider {

    private final UserRepository userRepository;

    @Bean
    @RequestScope
    public UserInfo requestScopedUser(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return null;
        }
        String userId = authentication.getName();
        User user = userRepository.findById(userId).orElse(null);
        return new UserInfo(user);
    }
}
