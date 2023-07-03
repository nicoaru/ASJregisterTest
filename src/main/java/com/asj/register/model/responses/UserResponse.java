package com.asj.register.model.responses;

import com.asj.register.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Integer id;

    private String firstname;

    private String lastname;

    private String email;



    public static UserResponse toResponse(User userEntity){
        if(userEntity == null) return null;
        return UserResponse.builder()
                .id(userEntity.getId())
                .firstname(userEntity.getFirstname())
                .lastname(userEntity.getLastname())
                .email(userEntity.getEmail())
                .build();
    }

    public static List<UserResponse> toResponseList(List<User> userEntityList){

        List<UserResponse> userResponse = new ArrayList<>();

        userEntityList.forEach((user) -> {
            userResponse.add(UserResponse.toResponse(user));
        });

        return userResponse;
    }

}
