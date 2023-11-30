package az.iktlab.taskmanagement.event.listener;

import az.iktlab.taskmanagement.event.RegistrationCompleteNotificationEvent;
import az.iktlab.taskmanagement.service.CustomMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationCompleteNotificationEventListener {

    private final CustomMailService mailService;

    @Async
    @EventListener
    public void onEvent(RegistrationCompleteNotificationEvent event) {
        mailService.sendRegistrationCompleteNotification(event.getMessage(), event.getTo());
    }

}
