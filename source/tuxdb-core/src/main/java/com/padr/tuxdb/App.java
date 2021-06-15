package com.padr.tuxdb;

import java.io.IOException;

import com.padr.tuxdb.server.Server;

public class App {

    public static void main(String[] args) throws IOException {
        Server server = new Server(6060);

        server.start();
    }
}
