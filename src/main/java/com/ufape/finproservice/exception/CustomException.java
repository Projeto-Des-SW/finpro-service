package com.ufape.finproservice.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomException extends RuntimeException {
    public final ExceptionMessage exceptionMessage;
}
