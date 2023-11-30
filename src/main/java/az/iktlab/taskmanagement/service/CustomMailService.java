package az.iktlab.taskmanagement.service;

public interface CustomMailService {
    void sendRegistrationCompleteNotification(String message,String to);

    void sendOTP(String to,String otp);
}
