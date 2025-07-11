package com.profile.http;

import static spark.Spark.*;

public class Interface {

    public static void start(int port) {
        ipAddress("0.0.0.0");
        port(port);

        before((req, res) -> {
            if (req.uri() == null || req.uri().isEmpty()) {
                halt(400, "Invalid request URI");
            }
            
            req.session(false);
        });

        get("/", (req, res) -> "OK");

        notFound((req, res) -> {                
            res.type("text/plain");
            return "Route not found";
        });

        path("/render", () -> {
            ProfileEndpoint.register();
            ProfileExceptions.register();
        });
    }
}