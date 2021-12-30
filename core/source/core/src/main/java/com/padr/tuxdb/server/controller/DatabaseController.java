package com.padr.tuxdb.server.controller;

import java.util.Map;

import com.padr.tuxdb.engine.process.pub.Database;

public class DatabaseController {

    public static Object handler(String function, Map<String, Object> request) {
        try {
            switch (function) {
                case "getDatabaseNames":
                    return Database.getDatabaseNames();
                case "createDatabase":
                    return Database.createDatabase((String) request.get("databaseName"));
                case "getCollectionNames":
                    return new Database((String) request.get("databaseName")).getCollectionNames();
                case "setDatabaseName":
                    return new Database((String) request.get("databaseName")).setDatabaseName((String) request.get("newDatabaseName"));
                case "isExist":
                    return new Database((String) request.get("databaseName")).isExist();
                case "drop": 
                    return new Database((String) request.get("databaseName")).drop();
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
    
}
