package com.profile.service.imageGenerator;

@FunctionalInterface
public interface ImageGenerator<U> {
    byte[] generateImage(U key) throws Exception;
}