package com.padr.tuxdb;

import java.net.InetAddress;
import java.util.Scanner;

import com.padr.tuxdb.server.Server;

public final class App {

    public static void main(String[] args) throws Throwable {
        System.out.println("\n——— Tuxdb ———");

        Scanner scanner = new Scanner(System.in);

        System.out.println("\nWhere will tuxdb run?");
        System.out.println("\n1- Localhost\n2- Local Area Network (LAN)\n");

        System.out.print("Choice (1 or 2): ");

        int hostChoice = Integer.parseInt(scanner.nextLine());

        System.out.print(
                String.format("\nPort (if you agree default port %d, please press enter): ", Server.DEFAULT_PORT));

        String portChoice = scanner.nextLine();

        scanner.close();

        InetAddress hostAddress;
        int port;

        if (hostChoice == 1)
            hostAddress = Server.getLocalHostAddress();
        else if (hostChoice == 2)
            hostAddress = Server.getLocalAreaNetworkAddress();
        else
            throw new Exception("Your choice must be 1 or 2");

        if (portChoice.isEmpty())
            port = Server.DEFAULT_PORT;
        else
            port = Integer.parseInt(portChoice);

        Server.run(hostAddress.getHostAddress(), port);
    }
}
