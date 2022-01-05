package com.padr.tuxdb.shell.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.padr.tuxdb.shell.Connection;
import com.padr.tuxdb.shell.Constant;
import com.padr.tuxdb.shell.EnviormentVariable;

public class DatabaseController {

    private static final String SERVICE = "database";
    
    public static void getDatabaseNames() throws JsonSyntaxException, Exception {
        System.out.println(Connection.send(SERVICE, "getDatabaseNames", requestScheme(null)));
    }

    public static void getName() throws Exception {
        System.out.println(EnviormentVariable.databaseName);
    }

    @SuppressWarnings("unchecked")
    public static void setName() throws Exception {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); 
        
        System.out.print(String.format("%s New Database Name: ", Constant.COMMAND_ANSWER_PREFIX));
        String newDatabaseName = input.readLine();

        Map<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put("newDatabaseName", newDatabaseName);

        Map<String, Object> response = new HashMap<>();

        response = new Gson().fromJson(Connection.send(SERVICE, "setName", requestScheme(additionalParameters)), response.getClass());

        if ((boolean) (response.get("success")))
            EnviormentVariable.databaseName = newDatabaseName; 

        System.out.println(response);
    }

    public static void getCollectionNames() throws Exception {
        System.out.println(Connection.send(SERVICE, "getCollectionNames", requestScheme(null)));
    }

    public static void drop() throws Exception {
        System.out.println(Connection.send(SERVICE, "drop", requestScheme(null)));
    }

    private static Map<String, Object> requestScheme(Map<String, Object> additionalParameters) {
        Map<String, Object> scheme = new HashMap<>();

        scheme.put("databaseName", EnviormentVariable.databaseName);

        if (additionalParameters != null)
            scheme.putAll(additionalParameters);

        return scheme;
    }
}
