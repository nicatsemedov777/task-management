package az.iktlab.taskmanagement.service.impl;

import az.iktlab.taskmanagement.dao.User;
import az.iktlab.taskmanagement.errors.exception.AuthenticationException;
import az.iktlab.taskmanagement.errors.exception.ResourceAlreadyExistException;
import az.iktlab.taskmanagement.errors.exception.ResourceNotFoundException;
import az.iktlab.taskmanagement.mapper.UserMapper;
import az.iktlab.taskmanagement.model.JWTToken;
import az.iktlab.taskmanagement.model.request.UserCreateRequest;
import az.iktlab.taskmanagement.model.request.UserSignInRequest;
import az.iktlab.taskmanagement.reposiroty.UserRepository;
import az.iktlab.taskmanagement.security.JWTProvider;
import az.iktlab.taskmanagement.service.UserService;
import az.iktlab.taskmanagement.util.EmailMatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;

    @Override
    public JWTToken signUp(UserCreateRequest userCreateRequest) {
        var isExist = userRepository.existsByEmailAndIsDeletedFalse(userCreateRequest.getEmail());
        if (isExist) {
            throw new ResourceAlreadyExistException("User exist with this email");
        }
        User user = buildUser(userCreateRequest);
        userRepository.save(user);
        return jwtProvider.getJWTToken(user.getId());
    }

    @Override
    public JWTToken signIn(UserSignInRequest userSignInRequest) {
        boolean isEmail = EmailMatcher.match(userSignInRequest.getUsernameOrEmail());
        User user;
        if (isEmail) {
            user = userRepository.findByEmailAndIsDeletedFalse(userSignInRequest.getUsernameOrEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("User is not found with this email"));
        } else {
            user = userRepository.findByUsernameAndIsDeletedFalse(userSignInRequest.getUsernameOrEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("User is not found with this username"));
        }
        boolean isTrue = passwordEncoder.matches(userSignInRequest.getPassword(), user.getPassword());
        if (isTrue)
            return jwtProvider.getJWTToken(user.getId());

        throw new AuthenticationException("Bad Credentials");
    }

    private User buildUser(UserCreateRequest userCreateRequest) {
        User user = UserMapper.INSTANCE.toEntity(userCreateRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }
}
