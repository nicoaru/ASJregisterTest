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
public class BadRequestException extends CustomHttpException {
    //** CONSTRUCTORES **//
    public BadRequestException() {
        super("Bad Request");
    }
    public BadRequestException(Map<String, Object> data) {
        super("Bad Request", data);
    }
    public BadRequestException(String message) {
        super(message);
    }
    public BadRequestException(String message, Map<String, Object> data) {
        super(message, data);
    }

}
