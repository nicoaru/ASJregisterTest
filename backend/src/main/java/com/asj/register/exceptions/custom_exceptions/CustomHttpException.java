package com.asj.register.exceptions.custom_exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class CustomHttpException extends RuntimeException {
    private final transient Map<String, Object> data;

    // CONSTRUCTORES //
    protected CustomHttpException(String message) {
        super(message);
        this.data = null;
    }
    protected CustomHttpException(String message, Map<String, Object> data) {
        super(message);
        this.data = data;
        System.out.println(data);
    }

}