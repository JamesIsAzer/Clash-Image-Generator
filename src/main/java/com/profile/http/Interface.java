package com.profile.http;

import static spark.Spark.*;

public class Interface {

    public static void start(int port) {
        port(port);

        before((req, res) -> {
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