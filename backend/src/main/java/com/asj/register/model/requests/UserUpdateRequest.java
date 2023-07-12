package com.asj.register.model.requests;

import com.asj.register.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    @NotBlank(message = "Nombre no debe estar vacío")
    @Pattern(regexp = "^([a-zA-Z][a-zA-Z ,.'`Ççã-]{1,30})$", message = "Nombre sólo puede contener letras o los siguientes caracteres: , . ' ` - Ç ç ã")
    private String firstname;
    @NotBlank(message = "Apellido no debe estar vacío")
    @Pattern(regexp = "^([a-zA-Z][a-zA-Z ,.'`Ççã-]{1,30})$", message = "Nombre sólo puede contener letras o los siguientes caracteres: , . ' ` - Ç ç ã")
    private String lastname;
    @NotBlank(message = "Email no debe estar vacío")
    @Email
    private String email;
    public static User toEntity(UserUpdateRequest userCreateRequest){
        return User.builder()
                .firstname(userCreateRequest.getFirstname())
                .lastname(userCreateRequest.getLastname())
                .email(userCreateRequest.getEmail())
                .build();
    }

}