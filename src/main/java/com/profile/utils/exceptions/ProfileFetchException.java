package com.profile.utils.exceptions;

public class ProfileFetchException extends RuntimeException {
    private final int statusCode;

    public ProfileFetchException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}