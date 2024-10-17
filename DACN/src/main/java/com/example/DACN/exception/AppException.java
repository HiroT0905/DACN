package com.example.DACN.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException
{
    private ErrorCode errorCode;


    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
    public AppException() {
        super();
    }

    // Constructor có tham số ErrorCode
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // Hoặc bất kỳ thông điệp nào bạn muốn
        this.errorCode = errorCode;
    }
}
