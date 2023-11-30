package az.iktlab.taskmanagement.model.request;

import az.iktlab.taskmanagement.validator.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRecoverAccountRequest {
    @Email
    private String email;
}
