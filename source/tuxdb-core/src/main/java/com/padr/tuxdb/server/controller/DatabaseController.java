package com.padr.tuxdb.server.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.padr.tuxdb.engine.process.pub.Database;

public class DatabaseController {

    public static Object handler(Map<String, Object> env, String operationName, List<Object> body) throws IOException {
        String databaseName = (String) env.get("database");

        if (databaseName.contains("/"))
            return "The database name doesn't contain '/'";

        switch (operationName) {
            case "getDatabaseNames":
                return Database.getDatabaseNames();
            case "getDatabaseSize":
                return Database.getDatabaseSize();
            case "createDatabase":
                return new Database(databaseName).createDatabase();
            case "getDatabaseName":
                return new Database(databaseName).getDatabaseName();
            case "createCollection":
                return new Database(databaseName).createCollection((String) body.get(0));
            case "getCollectionNames":
                return new Database(databaseName).getCollectionNames();
            case "getCollectionSize":
                return new Database(databaseName).getCollectionSize();
            case "setDatabaseName":
                return new Database(databaseName).setDatabaseName((String) body.get(0));
            case "dropDatabase":
                return new Database(databaseName).dropDatabase();
        }

        return null;
    }

}
