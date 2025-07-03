package com.ufape.finproservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionMessage {
    EMAIL_ALREADY_EXISTS(400, "Email already exists"),
    USER_NOT_FOUND(404, "User not found"),
    AUTHENTICATION_FAILED(404, "Email or password is incorrect"),;

    private final int code;
    private final String description;
}
