package com.asj.register.exceptions.custom_exceptions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class InvalidDateException extends CustomHttpException {
    //** CONSTRUCTORES **//
    public InvalidDateException() {
        super("Formato de fecha inválido. Formato correcto 'yyyy-MM-dd'");
    }
    public InvalidDateException(String message) {
        super(message);
    }


}
