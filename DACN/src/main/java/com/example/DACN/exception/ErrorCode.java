package com.example.DACN.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized"),
    USER_EXISTED(1002, "User already existed"),
    USER_NOTFOUND(1003, "User not found"),
    ;

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
