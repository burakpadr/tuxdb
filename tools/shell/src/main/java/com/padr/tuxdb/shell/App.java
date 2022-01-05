package com.padr.tuxdb.shell;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.google.gson.JsonSyntaxException;
import com.padr.tuxdb.shell.controller.Controller;

public class App {
    public static void main( String[] args ) throws JsonSyntaxException, Exception {
        System.out.println("\n\033[1mNote:\033[0m If you want to see usable commands, you can say help\n");

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));  

        while (true) {
            System.out.print(String.format("%s ", Constant.COMMAND_PREFIX));

            Controller.handler(input.readLine());
        }

    }
}
