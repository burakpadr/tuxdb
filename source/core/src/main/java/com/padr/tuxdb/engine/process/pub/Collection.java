package com.padr.tuxdb.engine.process.pub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.padr.tuxdb.engine.process.Feedback;
import com.padr.tuxdb.engine.process.pvt.Btree;
import com.padr.tuxdb.engine.storage.StorageElement;
import com.padr.tuxdb.engine.storage.folder.CollectionFolderStorageElement;
import com.padr.tuxdb.engine.storage.file.CollectionFileStorageElement;

public class Collection {

    public static Object createCollection(String databaseName, String collectionName) throws IOException {
        if (!new Database(databaseName).isExist())
            return Feedback.feedback(0, String.format("There is no database named %s", databaseName));
        else if (!StorageElement.domainIsSuited(collectionName))
            return Feedback.feedback(0, "The collection name contains only a-Z, 0-9, -_.");
        else if (new Collection(databaseName, collectionName).isExist())
            return Feedback.feedback(0, String.format("There is already such a collection named %s", collectionName));

        new CollectionFolderStorageElement(databaseName, collectionName).create();
        Btree.creatBtree(databaseName, collectionName);

        return Feedback.feedback(1, "The collection was created");
    }

    private String databaseName;
    private CollectionFolderStorageElement collection;
    private Btree btree;

    private Map<String, Object> constructorError;

    public Collection(String databaseName, String collectionName) throws IOException {
        collection = new CollectionFolderStorageElement(databaseName, collectionName);

        if (!new Database(databaseName).isExist())
            constructorError = Feedback.feedback(0, String.format("There is no database named %s", databaseName));
        else if (!StorageElement.domainIsSuited(collectionName))
            constructorError = Feedback.feedback(0, "The collection name contains only a-Z, 0-9, -_.");
        else if (!isExist())
            constructorError = Feedback.feedback(0, String.format("There is no collection named %s", collectionName));
        else {
            this.databaseName = databaseName;
            btree = new Btree(databaseName, collectionName);
        }
    }

    public Object getCollectionName() throws IOException {
        if (constructorError != null)
            return constructorError;

        return collection.getDomain();
    }

    public Map<String, Object> setCollectionName(String collectionName) throws IOException {
        if (constructorError != null)
            return constructorError;
        else if (!StorageElement.domainIsSuited(collectionName))
            return Feedback.feedback(0, "The collection name contains only a-Z, 0-9, -_.");
        else if (new CollectionFileStorageElement(databaseName, collectionName).isExist())
            return Feedback.feedback(0, "There is already such a collection associated with this name");

        collection.setDomain(collectionName);

        return Feedback.feedback(1, "The collection was renamed");
    }

    public Map<String, Object> drop() throws IOException {
        if (constructorError != null)
            return constructorError;

        collection.delete();

        return Feedback.feedback(1, "The collection was deleted");
    }

    public Object getAllObjects() {
        if (constructorError != null)
            return constructorError;

        List<Map<String, Object>> allObjects = new ArrayList<>();

        for (Map<String, Object> object : btree)
            allObjects.add(object);

        return allObjects;
    }

    public Map<String, Object> findFromObjectId(String objectId) throws IOException {
        if (constructorError != null)
            return constructorError;

        return btree.get(objectId);
    }

    public Object find(Map<String, Object> query) throws IOException {
        if (constructorError != null){
            return constructorError;
        }

        return btree.get(query);
    }

    public Map<String, Object> insert(Map<String, Object> data) throws Throwable {
        if (constructorError != null)
            return constructorError;
        else if (data.containsKey("_id"))
            return Feedback.feedback(0, "The data does not contains _id key");

        btree.insert(data);

        return Feedback.feedback(1, "The object was inserted");
    }

    public Map<String, Object> updateFromObjectId(String id, Map<String, Object> updateData) throws IOException {
        if (constructorError != null)
            return constructorError;
        else if (updateData.containsKey("_id"))
            return Feedback.feedback(0, "The update data does not contains _id key");

        boolean updateStatus = btree.update(id, updateData);

        if (!updateStatus)
            return Feedback.feedback(0, "The object wasn not updated");

        return Feedback.feedback(1, "The object was updated");
    }

    public Map<String, Object> update(Map<String, Object> query, Map<String, Object> updateData) throws IOException {
        if (constructorError != null)
            return constructorError;
        else if (updateData.containsKey("_id"))
            return Feedback.feedback(0, "The update data does not contains _id key");

        boolean updateStatus = btree.update(query, updateData);

        if (!updateStatus)
            return Feedback.feedback(0, "The object was not updated");

        return Feedback.feedback(1, "The object was updated");
    }

    public Map<String, Object> deleteFromObjectId(String id) throws Throwable {
        if (constructorError != null)
            return constructorError;

        boolean deleteStatus = btree.delete(id);

        if (!deleteStatus)
            return Feedback.feedback(0, "The object was not deleted");

        return Feedback.feedback(1, "The object was deleted");
    }

    public Map<String, Object> delete(Map<String, Object> query) throws Throwable {
        if (constructorError != null)
            return constructorError;

        boolean deleteStatus = btree.delete(query);

        if (!deleteStatus)
            return Feedback.feedback(0, "The object was not deleted");

        return Feedback.feedback(1, "The object was deleted");
    }

    public boolean isExist() {
        if (constructorError != null)
            return false;

        return collection.isExist();
    }
}
