package com.greensphere.userservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class DefaultResponse<T> {
    private String code;
    private String title;
    private String message;
    private T data;

    public DefaultResponse(String code, String title, String message, T data) {
        this.code = code;
        this.title = title;
        this.message = message;
        this.data = data;
    }

    public DefaultResponse(String code, String title, String message) {
        this.code = code;
        this.title = title;
        this.message = message;
    }

    public DefaultResponse setMessage(String message) {
        this.message = message;
        return this;
    }

}
