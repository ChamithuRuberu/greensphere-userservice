package com.greensphere.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ApiUnavailableException extends RuntimeException {

    public ApiUnavailableException(String message) {
        super(message);
    }

    public ApiUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

}