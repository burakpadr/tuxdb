package io.github.burakpadr.tuxjava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Collection {

    private final static String SERVICE_NAME = "collection";

    private Client client;
    private String databaseName;
    private String collectionName;

    public Collection(Client client, String databaseName, String collectionName) throws Exception {
        this.client = client;
        this.databaseName = databaseName;
        this.collectionName = collectionName;

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("databaseName", databaseName);
        parameters.put("collectionName", collectionName);

        boolean collectionIsExist = (boolean) new ObjectMapper().readValue(
                client.send(SERVICE_NAME, "isExist", parameters), Boolean.class);

        if (!collectionIsExist)
            client.send(SERVICE_NAME, "createCollection", parameters);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> setCollectionName(String newCollectionName) throws Exception {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("databaseName", databaseName);
        parameters.put("collectionName", collectionName);
        parameters.put("newCollectionName", newCollectionName);

        Map<String, Object> response = new HashMap<>();

        response = (Map<String, Object>) new ObjectMapper().readValue(client.send(SERVICE_NAME, "setCollectionName",
                parameters), response.getClass());

        if ((int) response.get("success") == 1)
            collectionName = newCollectionName;

        return response;
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getAllObjects() throws Exception {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("databaseName", databaseName);
        parameters.put("collectionName", collectionName);

        List<Map<String, Object>> response = new ArrayList<>();

        response = (List<Map<String, Object>>) new ObjectMapper().readValue(
                client.send(SERVICE_NAME, "getAllObjects", parameters),
                response.getClass());

        return response;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> findFromObjectId(String objectId) throws Exception {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("databaseName", databaseName);
        parameters.put("collectionName", collectionName);
        parameters.put("objectId", objectId);

        Map<String, Object> response = new HashMap<>();

        response = (Map<String, Object>) new ObjectMapper().readValue(
                client.send(SERVICE_NAME, "findFromObjectId", parameters),
                response.getClass());

        return response;
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> find(Map<String, Object> query) throws Exception {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("databaseName", databaseName);
        parameters.put("collectionName", collectionName);
        parameters.put("query", query);

        List<Map<String, Object>> response = new ArrayList<>();

        response = (List<Map<String, Object>>) new ObjectMapper().readValue(
                client.send(SERVICE_NAME, "find", parameters),
                response.getClass());

        return response;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> insert(Map<String, Object> data) throws Exception {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("databaseName", databaseName);
        parameters.put("collectionName", collectionName);
        parameters.put("data", data);

        Map<String, Object> response = new HashMap<>();

        response = (Map<String, Object>) new ObjectMapper().readValue(
                client.send(SERVICE_NAME, "insert", parameters),
                response.getClass());

        return response;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> updateFromObjectId(String objectId, Map<String, Object> updateData) throws Exception {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("databaseName", databaseName);
        parameters.put("collectionName", collectionName);
        parameters.put("objectId", objectId);
        parameters.put("updateData", updateData);

        Map<String, Object> response = new HashMap<>();

        response = (Map<String, Object>) new ObjectMapper().readValue(client.send(SERVICE_NAME, "updateFromObjectId",
                parameters), response.getClass());

        return response;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> update(Map<String, Object> query, Map<String, Object> updateData) throws Exception {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("databaseName", databaseName);
        parameters.put("collectionName", collectionName);
        parameters.put("query", query);
        parameters.put("updateData", updateData);

        Map<String, Object> response = new HashMap<>();

        response = (Map<String, Object>) new ObjectMapper().readValue(
                client.send(SERVICE_NAME, "update", parameters),
                response.getClass());

        return response;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> deleteFromObjectId(String objectId) throws Exception {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("databaseName", databaseName);
        parameters.put("collectionName", collectionName);
        parameters.put("objectId", objectId);

        Map<String, Object> response = new HashMap<>();

        response = (Map<String, Object>) new ObjectMapper().readValue(
                client.send(SERVICE_NAME, "deleteFromObjectId", parameters),
                response.getClass());

        return response;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> delete(Map<String, Object> query) throws Exception {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("databaseName", databaseName);
        parameters.put("collectionName", collectionName);
        parameters.put("query", query);

        Map<String, Object> response = new HashMap<>();

        response = (Map<String, Object>) new ObjectMapper().readValue(
                client.send(SERVICE_NAME, "delete", parameters),
                response.getClass());

        return response;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> drop() throws Exception {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("databaseName", databaseName);
        parameters.put("collectionName", collectionName);

        Map<String, Object> response = new HashMap<>();

        response = (Map<String, Object>) new ObjectMapper().readValue(
                client.send(SERVICE_NAME, "drop", parameters), response.getClass());

        return response;
    }

    @Override
    public String toString() {
        return String.format("Database Name -> %s\nCollection Name -> %s", databaseName, collectionName);
    }
}
