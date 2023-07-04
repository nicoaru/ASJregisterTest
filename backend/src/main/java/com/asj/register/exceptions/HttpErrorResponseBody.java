package com.asj.register.exceptions;

import com.asj.register.exceptions.custom_exceptions.CustomHttpException;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;


@Data
@NoArgsConstructor
//@JsonRootName(value = "error")
public class HttpErrorResponseBody {
    final boolean error = true;
    String exception;
    String message;
    Object data;
    String method;
    String path;



    public HttpErrorResponseBody(CustomHttpException exception, HttpServletRequest request) {
        this.message = exception.getMessage();
        this.data = exception.getData();
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.exception = exception.getClass().getSimpleName();

    }
    public HttpErrorResponseBody(Exception exception, HttpServletRequest request) {
        this.message = exception.getMessage();
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.exception = exception.getClass().getSimpleName();
    }

    public HttpErrorResponseBody(String errMessage, String excepClass,  HttpServletRequest request) {
        this.message = errMessage;
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.exception = excepClass;
    }



}
