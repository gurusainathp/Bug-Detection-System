package com.hsbc.exceptions;

public class UserIsNotTester extends RuntimeException {
    public UserIsNotTester(String message) {
        super(message);
    }
}
