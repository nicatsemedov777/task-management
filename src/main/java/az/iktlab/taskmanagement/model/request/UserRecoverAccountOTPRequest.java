package az.iktlab.taskmanagement.model.request;

import az.iktlab.taskmanagement.validator.Email;
import az.iktlab.taskmanagement.validator.Password;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRecoverAccountOTPRequest {
    @NotEmpty(message = "Otp is required")
    private String otp;
    @Email
    private String email;

    @Password
    private String newPassword;
}
