package az.iktlab.taskmanagement.security;

import az.iktlab.taskmanagement.dao.User;
import az.iktlab.taskmanagement.errors.exception.AuthenticationException;
import az.iktlab.taskmanagement.reposiroty.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new AuthenticationException("Bad credentials"));

        return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(), List.of());
    }
}
