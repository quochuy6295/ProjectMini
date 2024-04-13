package org.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
@RestController
public class GlobalHandlerException extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException arguments, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        BindingResult bindingResult = arguments.getBindingResult();
        List<String> validationMessages = new ArrayList<>();
        List<ObjectError> objectErrors = bindingResult.getAllErrors();
        for (ObjectError objectError : objectErrors) {
            String defaultMethod = objectError.getDefaultMessage();
            validationMessages.add(defaultMethod);
        }
        return super.handleMethodArgumentNotValid(arguments, headers, status, request);
    }
}
