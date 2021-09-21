package com.padr.tuxdb.server.controller;

import java.io.IOException;
import java.util.List;

import com.padr.tuxdb.engine.process.pub.Database;

public class DatabaseController {

    public static Object handler(String function, List<Object> parameters) throws IOException {
        try {
            switch (function) {
                case "getDatabaseNames":
                    return Database.getDatabaseNames();
                case "getDatabaseSize":
                    return Database.getDatabaseSize();
                case "createDatabase":
                    return Database.createDatabase((String) parameters.get(0));
                case "getCollectionNames":
                    return new Database((String) parameters.get(0)).getCollectionNames();
                case "getCollectionSize":
                    return new Database((String) parameters.get(0)).getCollectionSize();
                case "setDatabaseName":
                    return new Database((String) parameters.get(0)).setDatabaseName((String) parameters.get(1));
                case "drop":
                    return new Database((String) parameters.get(0)).drop();
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

}
