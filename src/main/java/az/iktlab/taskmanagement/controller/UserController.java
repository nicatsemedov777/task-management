package az.iktlab.taskmanagement.controller;

import az.iktlab.taskmanagement.model.JWTToken;
import az.iktlab.taskmanagement.model.request.UserCreateRequest;
import az.iktlab.taskmanagement.model.request.UserRecoverAccountOTPRequest;
import az.iktlab.taskmanagement.model.request.UserRecoverAccountRequest;
import az.iktlab.taskmanagement.model.request.UserSignInRequest;
import az.iktlab.taskmanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    @Operation(summary = "This endpoint help us to register",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The request was successful"),
                    @ApiResponse(responseCode = "400",
                            description = "There is incoming request validation error"),
                    @ApiResponse(responseCode = "409",
                            description = "There is a conflict with the current state of the resource, " +
                                    "preventing the request from being completed."),
                    @ApiResponse(responseCode = "417",
                            description = "The server cannot meet the expectations specified in the request"),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred on the server.")
            })
    public JWTToken signUp(@RequestBody @Valid UserCreateRequest userCreateRequest) {
       return userService.signUp(userCreateRequest);
    }
    @PostMapping("/sign-in")
    @Operation(summary = "This endpoint help us to login",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The request was successful"),
                    @ApiResponse(responseCode = "400",
                            description = "There is incoming request validation error"),
                    @ApiResponse(responseCode = "409",
                            description = "There is a conflict with the current state of the resource, " +
                                    "preventing the request from being completed."),
                    @ApiResponse(responseCode = "417",
                            description = "The server cannot meet the expectations specified in the request"),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred on the server.")
            })
    public JWTToken signIn(@RequestBody @Valid UserSignInRequest userSignInRequest) {
        return userService.signIn(userSignInRequest);
    }

    @PostMapping("/recover")
    @Operation(summary = "This endpoint help us to recover",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The request was successful"),
                    @ApiResponse(responseCode = "400",
                            description = "There is incoming request validation error"),
                    @ApiResponse(responseCode = "409",
                            description = "There is a conflict with the current state of the resource, " +
                                    "preventing the request from being completed."),
                    @ApiResponse(responseCode = "417",
                            description = "The server cannot meet the expectations specified in the request"),
                    @ApiResponse(responseCode = "500", description = "An unexpected error occurred on the server.")
            })
    public void sendOTP(@RequestBody @Valid UserRecoverAccountRequest userRecoverAccountRequest) {
        userService.sendOTP(userRecoverAccountRequest);
    }

    @PostMapping("/recover/otp")
    public void recover(@RequestBody @Valid UserRecoverAccountOTPRequest userRecoverAccountOTPRequest) {
        userService.recover(userRecoverAccountOTPRequest);
    }

}
