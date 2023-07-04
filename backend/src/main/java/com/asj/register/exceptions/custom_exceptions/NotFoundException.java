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
public class NotFoundException extends CustomHttpException {
    //** CONSTRUCTORES **//
    public NotFoundException() {
        super("Not Found");
    }
    public NotFoundException(Map<String, Object> data) {
        super("Not Found", data);
    }
    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException(String message, Map<String, Object> data) {
        super(message, data);
    }

}
