package com.padr.tuxdb.server.controller;

import java.util.Map;

import com.padr.tuxdb.engine.process.pub.Database;

public class DatabaseController {

    public static Object handler(String function, Map<String, Object> request) {
        try {
            Database database = new Database((String) request.get("databaseName"));

            switch (function) {
                case "getDatabaseNames":
                    return Database.getDatabaseNames();
                case "setName":
                    return database.setName((String) request.get("newDatabaseName"));
                case "getCollectionNames":
                    return database.getCollectionNames();
                case "drop": 
                    return database.drop();
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
    
}
