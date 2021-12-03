package com.padr.tuxdb.server;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;

import com.google.gson.Gson;
import com.padr.tuxdb.server.controller.CollectionController;
import com.padr.tuxdb.server.controller.DatabaseController;

import spark.Spark;

public class Server {

    public static final int DEFAULT_PORT = 6060;

    public static InetAddress getLocalAreaNetworkAddress() throws Exception {
        Enumeration<NetworkInterface> newtorkInterfaces = NetworkInterface.getNetworkInterfaces();

        for (NetworkInterface networkInterface : Collections.list(newtorkInterfaces)) {
            if (!networkInterface.isLoopback()) {
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();

                for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                    if (inetAddress instanceof Inet4Address)
                        return inetAddress;
                }
            }
        }

        return null;
    }

    public static InetAddress getLocalHostAddress() throws Exception {
        Enumeration<NetworkInterface> newtorkInterfaces = NetworkInterface.getNetworkInterfaces();

        for (NetworkInterface networkInterface : Collections.list(newtorkInterfaces)) {
            if (networkInterface.isLoopback()) {
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();

                for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                    if (inetAddress instanceof Inet4Address)
                        return inetAddress;
                }
            }
        }

        return null;
    }

    public static void run(String hostAddress, int port) throws Throwable {
        Spark.ipAddress(hostAddress);
        Spark.port(port);

        Spark.init();

        if (System.getProperty("os.name").contains("Windows"))
            Runtime.getRuntime().exec("cls");
        else
            System.out.print("\033\143");

        System.out.println(String.format("Tuxdb is working at http://%s:%d", hostAddress, port));
        System.out.println("\nIf you want to stop the tuxdb, you should press CTRL + C");

        loadRoutes();
    }

    private static void loadRoutes() throws Throwable {
        Spark.post("/*/*", (request, response) -> {
            String service = request.splat()[0];
            String function = request.splat()[1];

            response.type("application/json");

            switch (service) {
                case "database":
                    return new Gson().toJson(DatabaseController.handler(function, request));
                case "collection":
                    try {
                        return new Gson().toJson(CollectionController.handler(function, request));
                    } catch (Throwable e) {
                        return null;
                    }
                default:
                    return null;
            }
        });
    }

}
