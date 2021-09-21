package com.padr.tuxdb.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import com.padr.tuxdb.server.controller.CollectionController;
import com.padr.tuxdb.server.controller.DatabaseController;

public class Server extends Thread {

    private ServerSocket serverSocket;

    public static InetAddress getLocalAreaNetworkAddress() throws SocketException, UnknownHostException {
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

    public static InetAddress getLocalHostAddress() throws UnknownHostException, SocketException {
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

    public static int DEFAULT_PORT = 6060;

    public Server(InetAddress address, int port) throws IOException {
        serverSocket = new ServerSocket(port, 253, address);
    }

    @Override
    public void run() {
        System.out.println(String.format("\nTuxdb is running -> %s:%s", serverSocket.getInetAddress().getHostAddress(), serverSocket.getLocalPort()));

        Socket client = null;

        while (!serverSocket.isClosed()) {
            try {
                client = serverSocket.accept();

                new ClientHandler(client).start();
            } catch (IOException e) {
                continue;
            }
        }
    }

    private class ClientHandler extends Thread {

        private Socket client;
        private DataOutputStream output;
        private DataInputStream input;

        public ClientHandler(Socket client) throws IOException {
            this.client = client;

            output = new DataOutputStream(client.getOutputStream());
            input = new DataInputStream(client.getInputStream());
        }

        @SuppressWarnings("unchecked")
        @Override
        public void run() {
            try {
                byte[] read = new byte[1024];

                while (input.read(read) > 0 && !client.isClosed()) {
                    Map<String, Object> request = new HashMap<>();

                    request = new ObjectMapper().readValue(read, request.getClass());

                    handler(request);
                }
            } catch (Throwable e1) {
                return;
            }
        }

        @SuppressWarnings("unchecked")
        private void handler(Map<String, Object> request) throws Throwable {
            String service = (String) request.get("service");
            String function = (String) request.get("function");
            List<Object> parameters = (List<Object>) request.get("parameters");

            switch (service) {
                case "database":
                    output.write(new Gson().toJson(DatabaseController.handler(function, parameters)).getBytes());

                    break;
                case "collection":
                    output.write(new Gson().toJson(CollectionController.handler(function, parameters)).getBytes());

                    break;
                default:
                    output.write(
                        String.format("%s -> %s", "There is no such process associated with this name", service)
                                .getBytes());
            }

            output.flush();
        }
    }
}