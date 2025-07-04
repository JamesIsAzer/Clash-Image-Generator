package com.profile.http;

import static spark.Spark.*;

import com.google.gson.Gson;
import com.profile.utils.exceptions.ProfileFetchException;
import java.util.Map;

public class ProfileExceptions {
    public static void register() {
        exception(ProfileFetchException.class, (ex, req, res) -> {
            res.type("application/json");
            res.status(ex.getStatusCode());
            res.body(new Gson().toJson(Map.of(
                "error", ex.getMessage(),
                "status", ex.getStatusCode()
            )));
        });

        exception(Exception.class, (ex, req, res) -> {
            res.type("application/json");
            res.status(500);
            res.body(new Gson().toJson(Map.of(
                "error", "Internal server error",
                "details", ex.getMessage()
            )));
        });
    }
}