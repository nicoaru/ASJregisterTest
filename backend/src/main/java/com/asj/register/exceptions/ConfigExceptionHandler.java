package com.asj.register.exceptions;

// Anotations

import com.asj.register.exceptions.custom_exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


@ControllerAdvice
public class ConfigExceptionHandler {

    /* Bad Request - 400 */
    @ExceptionHandler({
            BadRequestException.class,
            ValidationException.class
    })
    public ResponseEntity<HttpErrorResponseBody> handleCustomsBadRequestExceptions(HttpServletRequest request, CustomHttpException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new HttpErrorResponseBody(exception, request));
    }

    @ExceptionHandler({
            IllegalArgumentException.class
    })
    public ResponseEntity<HttpErrorResponseBody> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException exception) {

        ValidationException validationException = new ValidationException("Error de validación de datos - dato de tipo inválido");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new HttpErrorResponseBody(validationException, request));
    }




    @ExceptionHandler({
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<HttpErrorResponseBody> handleHttpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException exception) {

        //si la fecha es de formato inválido
        if (exception.getCause().getCause() instanceof InvalidDateException) {
            String message = exception.getCause().getCause().getMessage();
            String exceptionClassString = exception.getCause().getCause().getClass().getSimpleName();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new HttpErrorResponseBody(message, exceptionClassString, request));
        }


        ValidationException validationException = new ValidationException("Error de validación de datos - dato de tipo inválido");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new HttpErrorResponseBody(validationException, request));
    }

    @ExceptionHandler({
                        ServletException.class,
                        HttpClientErrorException.class,
                        MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<HttpErrorResponseBody> handleBadRequestExceptions(HttpServletRequest request, Exception exception){


        //si el dato que llega por param/query no es de tipo invalido
        if(exception instanceof MethodArgumentTypeMismatchException) {
            ValidationException validationException = new ValidationException("Error de validación de tipo de dato por parámetro/query");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new HttpErrorResponseBody(validationException, request));
        }


        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new HttpErrorResponseBody(exception, request));
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpErrorResponseBody> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException exception){

        HashMap<String, Object> validationResult = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(v -> validationResult.put(v.getField(), v.getDefaultMessage()));

        ValidationException validationException = new ValidationException(validationResult);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new HttpErrorResponseBody(validationException, request));
    }




    /* Unauthorized - 401 */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<HttpErrorResponseBody> handleUnauthorizedExceptions(HttpServletRequest request, Exception exception){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new HttpErrorResponseBody(exception, request));
    }


    /* Not Found - 404 */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HttpErrorResponseBody> handleNotFoundException(HttpServletRequest request, NotFoundException exception){

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new HttpErrorResponseBody(exception, request));
    }


    /* Conflict - 409 */
    @ExceptionHandler(AlreadyExistingResourceException.class)
    public ResponseEntity<HttpErrorResponseBody> handleAlreadyExistingResourceException(HttpServletRequest request, AlreadyExistingResourceException exception){

        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(new HttpErrorResponseBody(exception, request));
    }


    /* Internal Serer Error - 500 */

    @ExceptionHandler({ErrorProcessException.class,
            Exception.class,
            TransactionException.class})
    public ResponseEntity<HttpErrorResponseBody> handleErrorProcessException(HttpServletRequest request, Exception exception){
        exception.printStackTrace();
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new HttpErrorResponseBody(exception, request));
    }

}

