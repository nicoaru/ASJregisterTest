package com.asj.register.controllers;

import com.asj.register.model.requests.UserCreateRequest;
import com.asj.register.model.requests.UserUpdateRequest;
import com.asj.register.model.responses.UserResponse;
import com.asj.register.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> createUser(@Validated @RequestBody UserCreateRequest newUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(newUser));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@Validated @RequestBody UserUpdateRequest updateUser,
                                                   @PathVariable Integer userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserById(updateUser, userId));
    }
}
