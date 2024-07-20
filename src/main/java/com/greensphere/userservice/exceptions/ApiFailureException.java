package com.greensphere.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApiFailureException extends RuntimeException {

    public ApiFailureException(String message) {
        super(message);
    }

}