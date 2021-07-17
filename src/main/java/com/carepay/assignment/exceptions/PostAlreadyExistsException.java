package com.carepay.assignment.exceptions;

import com.carepay.assignment.helpers.APIConstants;

public class PostAlreadyExistsException extends RuntimeException{


    public PostAlreadyExistsException(String property, String value) {
        super(String.format(
                APIConstants.POST_ALREADY_EXISTS,
                property, value));
    }
}
