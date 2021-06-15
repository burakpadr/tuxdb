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

    private static InetAddress getHost() throws SocketException, UnknownHostException {
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

        return InetAddress.getLocalHost();
    }

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port, 253, getHost());
    }

    @Override
    public void run() {
        Socket client = null;

        while (!serverSocket.isClosed()) {
            try {
                client = serverSocket.accept();

                new Handler(client).start();
            } catch (IOException e) {
                continue;
            }
        }
    }

    private class Handler extends Thread {

        private Socket client;
        private DataOutputStream output;
        private DataInputStream input;

        public Handler(Socket client) throws IOException {
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
            Map<String, Object> env = (Map<String, Object>) request.get("env");
            List<String> head = (List<String>) request.get("head");
            List<Object> body = (List<Object>) request.get("body");

            if (head.get(0).equals("database"))
                output.write(new Gson().toJson(DatabaseController.handler(env, head.get(1), body)).getBytes());
            else if (head.get(0).equals("collection"))
                output.write(new Gson().toJson(CollectionController.handler(env, head.get(1), body)).getBytes());
            else
                output.write(
                        String.format("%s -> %s", "There is no such process associated with this name", head.get(0))
                                .getBytes());

            output.flush();
        }
    }
}