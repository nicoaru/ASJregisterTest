package com.asj.register.exceptions.custom_exceptions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


//@Data
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ErrorProcessException extends CustomHttpException {
    //** CONSTRUCTORES **//
    public ErrorProcessException() {
        super("Internal Server Error");
    }
    public ErrorProcessException(String message) {
        super(message);
    }
    public ErrorProcessException(Exception error) {
        super(error.getMessage());
    }

}
