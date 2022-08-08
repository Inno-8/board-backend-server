package com.sparta.springweb.exception;

import com.sparta.springweb.global.error.exception.ErrorCode;
import com.sparta.springweb.global.error.exception.InvalidValueException;

public class FailedConvertingException extends InvalidValueException {
    public FailedConvertingException(ErrorCode errorCode) {
        super(errorCode);
    }
}
