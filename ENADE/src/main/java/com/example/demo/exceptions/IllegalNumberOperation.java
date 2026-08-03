package com.example.demo.exceptions;

import lombok.Getter;


public class IllegalNumberOperation extends Exception {
    public IllegalNumberOperation(String message) {
        super(message);
    }
}
