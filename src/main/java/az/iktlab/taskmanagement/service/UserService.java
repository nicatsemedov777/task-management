package az.iktlab.taskmanagement.service;

import az.iktlab.taskmanagement.model.JWTToken;
import az.iktlab.taskmanagement.model.request.UserCreateRequest;
import az.iktlab.taskmanagement.model.request.UserSignInRequest;

public interface UserService {
    JWTToken signUp(UserCreateRequest accountCreateRequest);

    JWTToken signIn(UserSignInRequest userSignInRequest);
}
