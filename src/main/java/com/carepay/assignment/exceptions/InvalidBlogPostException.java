package com.carepay.assignment.exceptions;

public class InvalidBlogPostException extends RuntimeException{
    public InvalidBlogPostException(String message) {
        super(message);
    }
}
