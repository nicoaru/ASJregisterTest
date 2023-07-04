package com.asj.register.model.requests;

import com.asj.register.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
    @NotBlank(message = "Nombre no debe estar vacío")
    @Pattern(regexp = "^([a-zA-Z][a-zA-Z ,.'`Ççã-]{1,30})$", message = "Nombre sólo puede contener letras o los siguientes caracteres: , . ' ` - Ç ç ã")
    private String firstname;
    @NotBlank(message = "Apellido no debe estar vacío")
    @Pattern(regexp = "^([a-zA-Z][a-zA-Z ,.'`Ççã-]{1,30})$", message = "Nombre sólo puede contener letras o los siguientes caracteres: , . ' ` - Ç ç ã")
    private String lastname;
    @NotBlank(message = "Email no debe estar vacío")
    @Email
    private String email;
    @NotBlank(message = "Contraseña no debe estar vacío")
    @Size(min = 8, max = 20,message = "La contraseña debe contener entre 8 y 20 caracteres")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$", message = "La contraseña debe contener al menos un número, una mayúscula, una minúscula")
    private String password;

    public static User toEntity(UserCreateRequest userCreateRequest){
        return User.builder()
                .firstname(userCreateRequest.getFirstname())
                .lastname(userCreateRequest.getLastname())
                .email(userCreateRequest.getEmail())
                .password(userCreateRequest.getPassword())
                .build();
    }

}