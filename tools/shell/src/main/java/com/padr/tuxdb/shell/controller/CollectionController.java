package com.padr.tuxdb.shell.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.padr.tuxdb.shell.Connection;
import com.padr.tuxdb.shell.Constant;
import com.padr.tuxdb.shell.EnviormentVariable;

public class CollectionController {

    private static final String SERVICE = "collection";
    private static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public static void getName() throws Exception {
        System.out.println(EnviormentVariable.collectionName);
    }

    @SuppressWarnings("unchecked")
    public static void setName() throws Exception {
        System.out.print(String.format("%s New Collection Name: ", Constant.COMMAND_ANSWER_PREFIX));
        String newCollectionName = input.readLine();

        Map<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put("newCollectionName", newCollectionName);

        Map<String, Object> response = new HashMap<>();

        response = new Gson().fromJson(Connection.send(SERVICE, "setName", requestScheme(additionalParameters)), response.getClass());

        if ((boolean) (response.get("success")))
            EnviormentVariable.collectionName = newCollectionName; 

        System.out.println(response);
    }

    public static void getAllObjects() throws Exception {
        System.out.println(Connection.send(SERVICE, "getAllObjects", requestScheme(null)));
    }

    public static void findFromObjectId() throws Exception {
        System.out.print(String.format("%s Object ID: ", Constant.COMMAND_ANSWER_PREFIX));
        String objectId = input.readLine();

        Map<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put("objectId", objectId);

        System.out.println(Connection.send(SERVICE, "findFromObjectId", requestScheme(additionalParameters)));
    }

    @SuppressWarnings("unchecked")
    public static void findOne() throws Exception {
        Map<String, Object> query = new HashMap<>();

        System.out.print(String.format("%s Query (JSON Format like {'key': 'value'}): ", Constant.COMMAND_ANSWER_PREFIX));
        query = new Gson().fromJson(input.readLine(), query.getClass());

        Map<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put("query", query);

        System.out.println(Connection.send(SERVICE, "findOne",  requestScheme(additionalParameters)));
    }

    @SuppressWarnings("unchecked")
    public static void find() throws Exception {
        Map<String, Object> query = new HashMap<>();

        System.out.print(String.format("%s Query (JSON Format like {'key': 'value'}): ", Constant.COMMAND_ANSWER_PREFIX));
        query = new Gson().fromJson(input.readLine(), query.getClass());

        Map<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put("query", query);

        System.out.println(Connection.send(SERVICE, "find",  requestScheme(additionalParameters)));
    }

    @SuppressWarnings("unchecked")
    public static void insert() throws Exception {
        Map<String, Object> data = new HashMap<>();

        System.out.print(String.format("%s Data (JSON Format like {'key': 'value'}): ", Constant.COMMAND_ANSWER_PREFIX));
        data = new Gson().fromJson(input.readLine(), data.getClass());

        Map<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put("data", data);

        System.out.println(Connection.send(SERVICE, "insert",  requestScheme(additionalParameters)));
    }

    @SuppressWarnings("unchecked")
    public static void updateFromObjectId() throws Exception {
        System.out.print(String.format("%s Object ID: ", Constant.COMMAND_ANSWER_PREFIX));
        String objectId = input.readLine();

        Map<String, Object> updateData = new HashMap<>();

        System.out.print(String.format("%s Update Data (JSON Format like {'key': 'value'}): ", Constant.COMMAND_ANSWER_PREFIX));
        updateData = new Gson().fromJson(input.readLine(), updateData.getClass());

        Map<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put("objectId", objectId);
        additionalParameters.put("updateData", updateData);

        System.out.println(Connection.send(SERVICE, "updateFromObjectId",  requestScheme(additionalParameters)));
    }

    @SuppressWarnings("unchecked")
    public static void update() throws Exception {
        Map<String, Object> query = new HashMap<>();

        System.out.print(String.format("%s Query (JSON Format like {'key': 'value'}): ", Constant.COMMAND_ANSWER_PREFIX));
        query = new Gson().fromJson(input.readLine(), query.getClass());

        Map<String, Object> updateData = new HashMap<>();

        System.out.print(String.format("%s Update Data (JSON Format like {'key': 'value'}): ", Constant.COMMAND_ANSWER_PREFIX));
        updateData = new Gson().fromJson(input.readLine(), updateData.getClass());

        Map<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put("query", query);
        additionalParameters.put("updateData", updateData);

        System.out.println(Connection.send(SERVICE, "update",  requestScheme(additionalParameters)));
    }

    public static void deleteFromObjectId() throws Exception {
        System.out.print(String.format("%s Object ID: ", Constant.COMMAND_ANSWER_PREFIX));
        String objectId = input.readLine();

        Map<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put("objectId", objectId);

        System.out.println(Connection.send(SERVICE, "deleteFromObjectId",  requestScheme(additionalParameters)));
    }

    @SuppressWarnings("unchecked")
    public static void delete() throws Exception {
        Map<String, Object> query = new HashMap<>();

        System.out.print(String.format("%s Query (JSON Format like {'key': 'value'}): ", Constant.COMMAND_ANSWER_PREFIX));
        query = new Gson().fromJson(input.readLine(), query.getClass());

        Map<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put("query", query);

        System.out.println(Connection.send(SERVICE, "delete",  requestScheme(additionalParameters)));
    }

    public static void drop() throws Exception {
        System.out.println(Connection.send(SERVICE, "drop", requestScheme(null)));
    }

    private static Map<String, Object> requestScheme(Map<String, Object> additionalParameters) {
        Map<String, Object> scheme = new HashMap<>();

        scheme.put("databaseName", EnviormentVariable.databaseName);
        scheme.put("collectionName", EnviormentVariable.collectionName);

        if (additionalParameters != null)
            scheme.putAll(additionalParameters);

        return scheme;
    }
}
