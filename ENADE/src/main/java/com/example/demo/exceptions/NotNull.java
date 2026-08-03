package com.example.demo.exceptions;


import lombok.Getter;


public class NotNull extends Exception {
    public NotNull(String message) {
        super(message);
    }
}
