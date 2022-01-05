package com.padr.tuxdb.server;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.padr.tuxdb.server.controller.CollectionController;
import com.padr.tuxdb.server.controller.DatabaseController;

import spark.Spark;

public class Server {

    public static final String DEFAULT_HOST = "127.0.0.1";
    public static final int DEFAULT_PORT = 6060;

    public static void run() throws Throwable {
        Spark.ipAddress(DEFAULT_HOST);
        Spark.port(DEFAULT_PORT);

        Spark.init();

        if (System.getProperty("os.name").contains("Windows"))
            Runtime.getRuntime().exec("cls");
        else
            System.out.print("\033\143");

        System.out.println(String.format("Tuxdb is working at http://%s:%d", DEFAULT_HOST, DEFAULT_PORT));

        loadRoutes();
    }

    @SuppressWarnings("unchecked")
    private static void loadRoutes() throws Throwable {
        Spark.post("/*/*", (request, response) -> {
            String service = request.splat()[0];
            String function = request.splat()[1];

            response.type("application/json");

            Map<String, Object> requestAsMap = new HashMap<>();
            requestAsMap = new Gson().fromJson(request.body(), requestAsMap.getClass());

            switch (service) {
                case "database":
                    return new Gson().toJson(DatabaseController.handler(function, requestAsMap));
                case "collection":
                    try {
                        return new Gson().toJson(CollectionController.handler(function, requestAsMap));
                    } catch (Throwable e) {
                        return null;
                    }
                default:
                    return null;
            }
        });
    }
}