package com.padr.tuxdb.server.controller;

import java.util.List;
import java.util.Map;

import com.padr.tuxdb.engine.process.pub.Collection;

public class CollectionController {

    @SuppressWarnings("unchecked")
    public static Object handler(String function, List<Object> parameters) throws Throwable {
        // Index 0 and 1 of parameters should be databaseName and collectionName respectively

        try {
            String databaseName = (String) parameters.get(0);
            String collectionName = (String) parameters.get(1);

            Collection collection = new Collection(databaseName, collectionName);

            switch (function) {
                case "createCollection":
                    return Collection.createCollection(databaseName, collectionName);
                case "setCollectionName":
                    return collection.setCollectionName((String) parameters.get(2));
                case "getAllObjects":
                    return collection.getAllObjects();
                case "findFromObjectId":
                    return collection.findFromObjectId((String) parameters.get(2));
                case "find":
                    return collection.find((Map<String, Object>) parameters.get(2));
                case "insert":
                    return collection.insert((Map<String, Object>) parameters.get(2));
                case "updateFromObjectId":
                    return collection.updateFromObjectId((String) parameters.get(2), (Map<String, Object>) parameters.get(3));
                case "update":
                    return collection.update((Map<String, Object>) parameters.get(2), (Map<String, Object>) parameters.get(3));
                case "deleteFromObjectId":
                    return collection.deleteFromObjectId((String) parameters.get(2));
                case "delete":
                    return collection.delete((Map<String, Object>) parameters.get(2));
                case "drop":
                    return collection.drop();
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

}
