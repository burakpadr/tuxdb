package com.padr.tuxdb.server.controller;
import com.padr.tuxdb.engine.process.pub.Database;

import spark.Request;

public class DatabaseController {

    public static Object handler(String function, Request request) {
        try {
            switch (function) {
                case "getDatabaseNames":
                    return Database.getDatabaseNames();
                case "createDatabase":
                    return Database.createDatabase(request.queryParams("databaseName"));
                case "getCollectionNames":
                    return new Database(request.queryParams("databaseName")).getCollectionNames();
                case "setDatabaseName":
                    return new Database(request.queryParams("databaseName")).setDatabaseName(request.queryParams("newDatabaseName"));
                case "isExist":
                    return new Database(request.queryParams("databaseName")).isExist();
                case "drop": 
                    return new Database(request.queryParams("databaseName")).drop();
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
    
}
