package com.asj.register.services;

import com.asj.register.exceptions.custom_exceptions.AlreadyExistingResourceException;
import com.asj.register.exceptions.custom_exceptions.NotFoundException;
import com.asj.register.model.entities.User;
import com.asj.register.model.requests.UserCreateRequest;
import com.asj.register.model.requests.UserUpdateRequest;
import com.asj.register.model.responses.UserResponse;
import com.asj.register.repositories.UserRepository;
import com.asj.register.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Override
    public UserResponse registerUser(UserCreateRequest newUser) {
        if(userRepository.existsByEmail(newUser.getEmail())) throw new AlreadyExistingResourceException("Usuario ya registrado con ese email");

        return UserResponse.toResponse(userRepository.save(UserCreateRequest.toEntity(newUser)));
    }

    @Override
    public UserResponse getUserById(Integer userId) {

        return UserResponse.toResponse(userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Usuario no " +
                "encontrado")));
    }

    @Override
    public UserResponse updateUserById(UserUpdateRequest updateUser, Integer userId) {
       User foundUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Usuario no " +
               "encontrado"));
       foundUser.setFirstname(updateUser.getFirstname());
       foundUser.setLastname(updateUser.getLastname());
       foundUser.setEmail(updateUser.getEmail());
       return UserResponse.toResponse(userRepository.save(foundUser));
    }
}
