package com.profile.http;

import static spark.Spark.*;

public class Interface {

    public static void start(int port) {
        port(port);

        path("/render", () -> {
            ProfileEndpoint.register();
            ProfileExceptions.register();
        });
    }
}