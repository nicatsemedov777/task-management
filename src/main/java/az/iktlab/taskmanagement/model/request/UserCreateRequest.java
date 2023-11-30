package az.iktlab.taskmanagement.model.request;

import az.iktlab.taskmanagement.validator.Email;
import az.iktlab.taskmanagement.validator.Password;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequest {
    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Surname is required")
    private String surname;

    @Min(message = "Contact number must be longer than 7",value = 7)
    @Max(message = "Contact number must be shorter than 20",value = 20)
    private String contactNumber;

    @NotEmpty(message = "Username is required")
    private String username;

    @Email(message = "Email is required",nullable = false)
    private String email;

    @Password
    private String password;

    private String address;
    private LocalDateTime birthAt;

}
