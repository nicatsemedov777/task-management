package az.iktlab.taskmanagement.service.impl;

import az.iktlab.taskmanagement.constants.SecurityConstant;
import az.iktlab.taskmanagement.service.CustomMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomMailServiceImpl implements CustomMailService {
    private final JavaMailSender javaMailSender;
    private final SecurityConstant securityConstant;

    @Override
    public void sendRegistrationCompleteNotification(String message, String to) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("SUCCESSFUL REGISTRATION");
        mailMessage.setText(message);
        mailMessage.setFrom(securityConstant.getEmail());
        mailMessage.setTo(to);

        javaMailSender.send(mailMessage);
    }

    @Override
    public void sendOTP(String to, String otp) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("RECOVER ACCOUNT");
        mailMessage.setText("Don't share this code with anyone!!\n" +
                "OTP Code : "+otp);
        mailMessage.setTo(to);
        mailMessage.setFrom(securityConstant.getEmail());
        javaMailSender.send(mailMessage);
    }
}
