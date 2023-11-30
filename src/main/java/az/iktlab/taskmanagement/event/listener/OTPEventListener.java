package az.iktlab.taskmanagement.event.listener;

import az.iktlab.taskmanagement.event.OTPEvent;
import az.iktlab.taskmanagement.service.CustomMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OTPEventListener {

    private final CustomMailService mailService;

    @Async
    @EventListener
    public void onEvent(OTPEvent event){
        mailService.sendOTP(event.getEmail(), event.getOtpCode());
    }
}
