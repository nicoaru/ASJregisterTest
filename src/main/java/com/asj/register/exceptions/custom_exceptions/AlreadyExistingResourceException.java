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
public class AlreadyExistingResourceException extends CustomHttpException {

    //** CONSTRUCTORES **//
    public AlreadyExistingResourceException() {
        super("Ya existe el recurso");
    }
    public AlreadyExistingResourceException(Map<String, Object> data) {
        super("Ya existe el recurso", data);
    }
    public AlreadyExistingResourceException(String message) {
        super(message);
    }
    public AlreadyExistingResourceException(String message, Map<String, Object> data) {
        super(message, data);
    }

}
