package com.ufape.finproservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionMessage {
    EMAIL_ALREADY_EXISTS(404, "Email already exists");

    private final int code;
    private final String description;
}
