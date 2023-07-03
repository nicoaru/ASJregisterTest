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
public class UnauthorizedException extends CustomHttpException {
    //** CONSTRUCTORES **//
    public UnauthorizedException() {
        super("Unauthorized");
    }
    public UnauthorizedException(Map<String, Object> data) {
        super("Unauthorized", data);
    }
    public UnauthorizedException(String message) {
        super(message);
    }
    public UnauthorizedException(String message, Map<String, Object> data) {
        super(message, data);
    }

}
