package com.profile.utils;

public class Cache<T> {
    private final T value;
    private final long expiresAt;

    public Cache(T value, long expiresAt) {
        this.value = value;
        this.expiresAt = expiresAt;
    }

    public T value() {
        return value;
    }

    public long expiresAt() {
        return expiresAt;
    }
}