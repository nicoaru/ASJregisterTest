package com.asj.register.exceptions.custom_exceptions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ValidationException extends CustomHttpException {

    //** CONSTRUCTORES **//
    public ValidationException() {
        super("Error de validación de datos");
    }
    public ValidationException(Map<String, Object> validationData) {
        super("Error de validación de datos", validationData);
    }
    public ValidationException(String message) {
        super(message);
    }
    public ValidationException(String message, Map<String, Object> validationData) {
        super(message, validationData);
    }
}
