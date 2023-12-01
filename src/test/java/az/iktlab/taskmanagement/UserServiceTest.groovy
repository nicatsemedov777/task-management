package az.iktlab.taskmanagement

import az.iktlab.taskmanagement.dao.OTPSession
import az.iktlab.taskmanagement.dao.User
import az.iktlab.taskmanagement.model.request.UserCreateRequest
import az.iktlab.taskmanagement.model.request.UserRecoverAccountOTPRequest
import az.iktlab.taskmanagement.model.request.UserRecoverAccountRequest
import az.iktlab.taskmanagement.model.request.UserSignInRequest
import az.iktlab.taskmanagement.reposiroty.OTPSessionRepository
import az.iktlab.taskmanagement.reposiroty.UserRepository
import az.iktlab.taskmanagement.service.UserService
import az.iktlab.taskmanagement.service.impl.UserServiceImpl
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import spock.lang.Specification
import spock.lang.Subject

@SpringBootTest
class UserServiceTest extends Specification {


    @Subject
    UserService userService

    @MockBean
    UserRepository userRepository

    @MockBean
    OTPSessionRepository otpSessionRepository

    def setup() {
        userService = new UserServiceImpl(userRepository, null, null, null, otpSessionRepository)
    }

    def "Test signUp method"() {
        given:
        def userCreateRequest = new UserCreateRequest(email: "test@example.com", username: "testuser", password: "password", isRememberMe: false)
        def user = userRepository.existsByEmailAndIsDeletedFalse(userCreateRequest.getEmail()) >> false
        userRepository.save(user) >> new User()

        when:
        def jwtToken = userService.signUp(userCreateRequest)

        then:
        jwtToken != null
    }

    def "Test signIn method"() {
        given:
        def userSignInRequest = new UserSignInRequest(email: "test@example.com", password: "password", isRememberMe: false)
        def existingUser = new User(email: "test@example.com", password: "password")
        userRepository.findByEmailAndIsDeletedFalse(_) >> existingUser
        userService.passwordEncoder = { rawPassword, encodedPassword -> rawPassword == encodedPassword }

        when:
        def jwtToken = userService.signIn(userSignInRequest)

        then:
        jwtToken != null
    }

    def "Test sendOTP method"() {
        given:
        def userRecoverAccountRequest = new UserRecoverAccountRequest(email: "test@example.com")
        def existingUser = new User(email: "test@example.com")
        def otpSession = new OTPSession("112233qq##dd112233qq##dd112233qq##dd"
                , 123456 as String,"qwe123@#!qwe123@#!qwe123@#!qwe123@#!",false,new Date());
        userRepository.findByEmailAndIsDeletedFalse(existingUser.getEmail()) >> existingUser
        otpSessionRepository.save(otpSession) >> new OTPSession()

        when:
        userService.sendOTP(userRecoverAccountRequest)

        then:
        1 * otpSessionRepository.save(otpSession)
    }

    def "Test recover method"() {
        given:
        def userRecoverAccountOTPRequest = new UserRecoverAccountOTPRequest(email: "test@example.com", otp: "123456", newPassword: "newPassword")
        def existingUser = new User(email: "test@example.com", password: "password")
        def otpSession = new OTPSession(otpCode: "123456", userId: 1, isUsed: false, createDate: new Date())
        userRepository.findByEmailAndIsDeletedFalse(existingUser.getEmail()) >> existingUser
        otpSessionRepository.findByOtpCodeAndUserIdAndIsUsedFalse(otpSession.getOtpCode(), existingUser.getId()) >> otpSession
        userService.passwordEncoder = { rawPassword, encodedPassword -> rawPassword == encodedPassword }
        userService.checkOtpSessionIsExpired(otpSession.getCreateDate()) >> {}

        when:
        userService.recover(userRecoverAccountOTPRequest)

        then:
        1 * userRepository.save(existingUser)
        1 * otpSessionRepository.save(otpSession)
    }

    def "Test recover method throws ResourceNotFoundException when OTP session is not found"() {
        given:
        def userRecoverAccountOTPRequest = new UserRecoverAccountOTPRequest(email: "test@example.com", otp: "123456", newPassword: "newPassword")
        def existingUser = new User(email: "test@example.com", password: "password")
        userRepository.findByEmailAndIsDeletedFalse(existingUser.getEmail()) >> existingUser
        otpSessionRepository.findByOtpCodeAndUserIdAndIsUsedFalse(userRecoverAccountOTPRequest.getOtp(), existingUser.getId()) >> null

        when:
        userService.recover(userRecoverAccountOTPRequest)

        then:
        thrown(ResourceNotFoundException::new as Class<Throwable>)
    }
}
