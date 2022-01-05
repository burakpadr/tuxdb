package com.padr.tuxdb.server.controller;

import java.util.Map;

import com.padr.tuxdb.engine.process.pub.Collection;

public class CollectionController {

    @SuppressWarnings("unchecked")
    public static Object handler(String function, Map<String, Object> request) throws Throwable {
        try {
            Collection collection = new Collection((String) request.get("databaseName"), (String) request.get("collectionName"));

            switch (function) {
                case "setName":
                    return collection.setName((String) request.get("newCollectionName"));
                case "getAllObjects":
                    return collection.getAllObjects();
                case "findFromObjectId":
                    return collection.findFromObjectId((String) request.get("objectId"));
                case "findOne":
                    return collection.findOne((Map<String, Object>) request.get("query"));
                case "find":
                    return collection.find((Map<String, Object>) request.get("query"));
                case "insert":
                    return collection.insert((Map<String, Object>) request.get("data"));
                case "updateFromObjectId":
                    return collection.updateFromObjectId((String) request.get("objectId"), (Map<String, Object>) request.get("updateData"));
                case "update":
                    return collection.update((Map<String, Object>) request.get("query"), (Map<String, Object>) request.get("updateData"));
                case "deleteFromObjectId":
                    return collection.deleteFromObjectId((String) request.get("objectId"));
                case "delete":
                    return collection.delete((Map<String, Object>) request.get("query"));
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
