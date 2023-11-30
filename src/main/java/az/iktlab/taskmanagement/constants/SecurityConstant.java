package az.iktlab.taskmanagement.constants;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class SecurityConstant {

    @Value("${security.auth.whitelist}")
    private String[] whiteList;

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${spring.mail.username}")
    private String email;
}
