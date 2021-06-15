package com.padr.tuxdb.server.controller;

import java.util.List;
import java.util.Map;

import com.padr.tuxdb.engine.process.pub.Collection;
import com.padr.tuxdb.engine.process.pub.Database;

public class CollectionController {

    @SuppressWarnings("unchecked")
    public static Object handler(Map<String, Object> env, String operationName, List<Object> body)
            throws Throwable {
        
        if (!(new Database((String) env.get("database")).isExist()))
            return "There is no such a database";
        else if (((String) body.get(0)).contains("/"))
                return "The collection name doesn't contain '/'";

        Collection collection = new Collection((String) env.get("database"), (String) body.get(0));

        if (!collection.isExist() && !operationName.equals("createCollection"))
                return String.format("%s %s %s", "The database named", (String) env.get("database"), "doesn't contain such a collection");

        switch (operationName) {
            case "setCollectionName":
                return collection.setCollectionName((String) body.get(1));
            case "getAllObjects":
                return collection.getAllObjects();
            case "findFromObjectId":
                return collection.findFromObjectId((String) body.get(1));
            case "find":
                return collection.find((Map<String, Object>) body.get(1));
            case "insert":
                return collection.insert((Map<String, Object>) body.get(1));
            case "updateFromObjectId":
                return collection.updateFromObjectId((String) body.get(1), (Map<String, Object>) body.get(2));
            case "update":
                return collection.update((Map<String, Object>) body.get(1), (Map<String, Object>) body.get(2));
            case "deleteFromObjectId":
                return collection.deleteFromObjectId((String) body.get(1));
            case "delete":
                return collection.delete((Map<String, Object>) body.get(1));
            case "dropCollection":
                return collection.dropCollection();
        }

        return null;
    }

}
