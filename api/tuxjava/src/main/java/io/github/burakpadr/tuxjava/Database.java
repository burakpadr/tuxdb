package io.github.burakpadr.tuxjava;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Database {

    private final static String SERVICE_NAME = "database";

    private Client client;
    private String databaseName;

    public Database(Client client, String databaseName) throws Exception {
        this.client = client;
        this.databaseName = databaseName;
    }

    public Collection getCollection(String collectionName) throws Exception {
        return new Collection(client, databaseName, collectionName);
    }

    @SuppressWarnings("unchecked")
    public List<String> getDatabaseNames() throws IOException {
        List<String> response = new ArrayList<>();

        response = (List<String>) new ObjectMapper().readValue(client.send(SERVICE_NAME, "getDatabaseNames", new HashMap<>()),
                response.getClass());

        return response;
    }

    public String getName() {
        return databaseName;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> setName(String newDatabaseName) throws Exception {
        Map<String, Object> response = new HashMap<>();

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("databaseName", databaseName);
        parameters.put("newDatabaseName", newDatabaseName);

        response = (Map<String, Object>) new ObjectMapper().readValue(
                client.send(SERVICE_NAME, "setName", parameters),
                response.getClass());

        if ((boolean) response.get("success"))
            databaseName = newDatabaseName;

        return response;
    }

    @SuppressWarnings("unchecked")
    public List<String> getCollectionNames() throws Exception {
        List<String> collectionNames = new ArrayList<>();

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("databaseName", databaseName);

        collectionNames = (List<String>) new ObjectMapper().readValue(
                client.send(SERVICE_NAME, "getCollectionNames", parameters),
                collectionNames.getClass());

        return collectionNames;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> drop() throws Exception {
        Map<String, Object> response = new HashMap<>();

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("databaseName", databaseName);

        response = (Map<String, Object>) new ObjectMapper()
                .readValue(client.send(SERVICE_NAME, "drop", parameters), response.getClass());

        return response;
    }

    @Override
    public String toString() {
        return databaseName;
    }
}
