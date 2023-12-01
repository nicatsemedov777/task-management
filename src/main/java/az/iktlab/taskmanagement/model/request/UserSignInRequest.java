package az.iktlab.taskmanagement.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignInRequest {
    private String email;
    private String password;
    private Boolean isRememberMe;
}
