package az.iktlab.taskmanagement.service.impl;

import az.iktlab.taskmanagement.config.CustomEventPublisher;
import az.iktlab.taskmanagement.dao.OTPSession;
import az.iktlab.taskmanagement.dao.User;
import az.iktlab.taskmanagement.errors.exception.AuthenticationException;
import az.iktlab.taskmanagement.errors.exception.OTPSessionExpiredException;
import az.iktlab.taskmanagement.errors.exception.ResourceAlreadyExistException;
import az.iktlab.taskmanagement.errors.exception.ResourceNotFoundException;
import az.iktlab.taskmanagement.event.OTPEvent;
import az.iktlab.taskmanagement.event.RegistrationCompleteNotificationEvent;
import az.iktlab.taskmanagement.mapper.UserMapper;
import az.iktlab.taskmanagement.model.JWTToken;
import az.iktlab.taskmanagement.model.request.UserCreateRequest;
import az.iktlab.taskmanagement.model.request.UserRecoverAccountOTPRequest;
import az.iktlab.taskmanagement.model.request.UserRecoverAccountRequest;
import az.iktlab.taskmanagement.model.request.UserSignInRequest;
import az.iktlab.taskmanagement.reposiroty.OTPSessionRepository;
import az.iktlab.taskmanagement.reposiroty.UserRepository;
import az.iktlab.taskmanagement.security.JWTProvider;
import az.iktlab.taskmanagement.service.UserService;
import az.iktlab.taskmanagement.util.EmailMatcher;
import az.iktlab.taskmanagement.util.OTPGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;
    private final CustomEventPublisher eventPublisher;
    private final OTPSessionRepository otpSessionRepository;


    @Override
    public JWTToken signUp(UserCreateRequest userCreateRequest) {
        var isExist = userRepository.existsByEmailAndIsDeletedFalse(userCreateRequest.getEmail());
        if (isExist) {
            throw new ResourceAlreadyExistException("User exist with this email");
        }
        User user = buildUser(userCreateRequest);
        userRepository.save(user);
        eventPublisher.publishEvent(getEvent(userCreateRequest, user));

        return jwtProvider.getJWTToken(user.getId());
    }

    private static RegistrationCompleteNotificationEvent getEvent(UserCreateRequest userCreateRequest, User user) {
        return RegistrationCompleteNotificationEvent.builder()
                .to(userCreateRequest.getEmail())
                .message(String.format("%s user has completed registration successfully!!!", user.getUsername()))
                .build();
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

    @Override
    public void sendOTP(UserRecoverAccountRequest userRecoverAccountRequest) {
        var user = userRepository.findByEmailAndIsDeletedFalse(userRecoverAccountRequest.getEmail())
                .orElseThrow(ResourceNotFoundException::new);
        OTPSession otpSession = buildOTPSession(user);
        eventPublisher.publishEvent(buildOTPEvent(userRecoverAccountRequest.getEmail(), otpSession));
        otpSessionRepository.save(otpSession);

    }

    @Override
    public void recover(UserRecoverAccountOTPRequest userRecoverAccountOTPRequest) {
        var user = userRepository.findByEmailAndIsDeletedFalse(userRecoverAccountOTPRequest.getEmail())
                .orElseThrow(ResourceNotFoundException::new);

        OTPSession otpSession = otpSessionRepository
                .findByOtpCodeAndUserIdAndIsUsedFalse(userRecoverAccountOTPRequest.getOtp(), user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("OTP session not found"));

        checkOtpSessionIsExpired(otpSession.getCreateDate());
        user.setPassword(passwordEncoder.encode(userRecoverAccountOTPRequest.getNewPassword()));
        userRepository.save(user);
        otpSession.setIsUsed(true);
        otpSessionRepository.save(otpSession);
    }

    private void checkOtpSessionIsExpired(Date createDate) {
        Calendar now = Calendar.getInstance();
        Calendar otpCreateDate = Calendar.getInstance();
        otpCreateDate.setTime(createDate);
        otpCreateDate.add(Calendar.MINUTE, 3);
        if (now.after(otpCreateDate)) {
            throw new OTPSessionExpiredException("Otp session is expired");
        }
    }

    private static OTPSession buildOTPSession(User user) {
        return OTPSession.builder()
                .userId(user.getId())
                .otpCode(OTPGenerator.generate())
                .build();
    }

    private static OTPEvent buildOTPEvent(String email, OTPSession otpSession) {
        return OTPEvent.builder()
                .email(email)
                .otpCode(otpSession.getOtpCode())
                .build();
    }

    private User buildUser(UserCreateRequest userCreateRequest) {
        User user = UserMapper.INSTANCE.toEntity(userCreateRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }
}
