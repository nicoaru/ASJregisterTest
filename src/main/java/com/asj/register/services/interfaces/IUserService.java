package com.asj.register.services.interfaces;

import com.asj.register.model.requests.UserCreateRequest;
import com.asj.register.model.responses.UserResponse;

public interface IUserService {

    UserResponse registerUser(UserCreateRequest newUser);
}
