package com.profile.client;

public class ClashClientErrorCodes {

    public static String getProfileErrorMessage(int code) {
        switch (code) {
            case 403:
                return "Invalid API auth";
            case 404:
                return "Profile not found";
            case 429:
                return "API throttled";
            case 500:
                return "Unknown API error";
            case 503:
                return "Game maintenance";
            default:
                return "Could not parse status" + code;
        }
    }
}