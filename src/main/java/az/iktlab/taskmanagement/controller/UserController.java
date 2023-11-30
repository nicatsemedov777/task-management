package az.iktlab.taskmanagement.controller;

import az.iktlab.taskmanagement.model.JWTToken;
import az.iktlab.taskmanagement.model.request.UserCreateRequest;
import az.iktlab.taskmanagement.model.request.UserSignInRequest;
import az.iktlab.taskmanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public JWTToken signUp(@RequestBody @Valid UserCreateRequest userCreateRequest) {
       return userService.signUp(userCreateRequest);
    }
    @PostMapping("/sign-in")
    public JWTToken signIn(@RequestBody @Valid UserSignInRequest userSignInRequest) {
        return userService.signIn(userSignInRequest);
    }

}
