package com.hsbc.exceptions;

public class MaxProjectLimit extends RuntimeException {
    public MaxProjectLimit(String message) {
        super(message);
    }
}
