package com.padr.tuxdb.server.controller;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.padr.tuxdb.engine.process.pub.Collection;

import spark.Request;

public class CollectionController {

    @SuppressWarnings("unchecked")
    public static Object handler(String function, Request request) throws Throwable {
        try {
            String databaseName = request.queryParams("databaseName");
            String collectionName = request.queryParams("collectionName");

            Collection collection = new Collection(databaseName, collectionName);

            Map<String, Object> draft = new HashMap<>();

            switch (function) {
                case "createCollection":
                    return Collection.createCollection(databaseName, collectionName);
                case "setCollectionName":
                    return collection.setCollectionName(request.queryParams("newCollectionName"));
                case "getAllObjects":
                    return collection.getAllObjects();
                case "findFromObjectId":
                    return collection.findFromObjectId(request.queryParams("objectId"));
                case "find":
                    return collection.find(new Gson().fromJson(request.queryParams("query"), draft.getClass()));
                case "insert":
                    return collection.insert(new Gson().fromJson(request.queryParams("data"), draft.getClass()));
                case "updateFromObjectId":
                    return collection.updateFromObjectId(request.queryParams("objectId"), new Gson().fromJson(request.queryParams("updateData"), draft.getClass()));
                case "update":
                    return collection.update(new Gson().fromJson(request.queryParams("query"), draft.getClass()), new Gson().fromJson(request.queryParams("updateData"), draft.getClass()));
                case "deleteFromObjectId":
                    return collection.deleteFromObjectId(request.queryParams("objectId"));
                case "delete":
                    return collection.delete(new Gson().fromJson(request.queryParams("query"), draft.getClass()));
                case "drop":
                    return collection.drop();
                case "isExist":
                    return collection.isExist();
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

}
