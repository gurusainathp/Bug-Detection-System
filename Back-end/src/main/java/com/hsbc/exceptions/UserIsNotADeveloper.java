package com.hsbc.exceptions;

public class UserIsNotADeveloper extends RuntimeException {
    public UserIsNotADeveloper(String message) {
        super(message);
    }
}

