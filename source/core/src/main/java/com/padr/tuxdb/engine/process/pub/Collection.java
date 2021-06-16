package com.padr.tuxdb.engine.process.pub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.padr.tuxdb.engine.feedback.Feedback;
import com.padr.tuxdb.engine.process.pvt.Btree;
import com.padr.tuxdb.engine.storage.element.StorageElement;
import com.padr.tuxdb.engine.storage.element.d.CollectionDynamicStorageElement;
import com.padr.tuxdb.engine.storage.element.stc.CollectionStaticStorageElement;

public class Collection {

    private String databaseName;
    private String collectionName;

    private CollectionDynamicStorageElement collectionDynamicStorageElement;
    private Btree btree;

    public Collection(String databaseName, String collectionName) throws IOException {
        this.databaseName = databaseName;
        this.collectionName = collectionName;

        collectionDynamicStorageElement = new CollectionDynamicStorageElement(databaseName, collectionName);

        if (collectionDynamicStorageElement.isExist())
            btree = new Btree(databaseName, collectionName);
    }

    public String getCollectionName() throws IOException {
        return collectionDynamicStorageElement.getDomain();
    }

    public Map<String, Object> setCollectionName(String collectionName) throws IOException {
        if (!StorageElement.domainIsSuited(collectionName))
            return Feedback.feedback(0, "The collection name contains only a-Z, 0-9, -_.");
        if (new CollectionStaticStorageElement(databaseName, collectionName).isExist())
            return Feedback.feedback(0, "There is already such a collection associated with this name");

        collectionDynamicStorageElement.setDomain(collectionName);

        return Feedback.feedback(1, "The collection was renamed");
    }

    public Map<String, Object> createCollection() throws IOException {
        if (!StorageElement.domainIsSuited(collectionName))
            return Feedback.feedback(0, "The collection name contains only a-Z, 0-9, -_.");
        if (collectionDynamicStorageElement.isExist())
            return Feedback.feedback(0, "There is already such a collection associated with this name");

        collectionDynamicStorageElement.create();
        Btree.creatBtree(databaseName, collectionName);

        return Feedback.feedback(1, "The collection was created");
    }

    public Map<String, Object> dropCollection() throws IOException {
        collectionDynamicStorageElement.delete();

        return Feedback.feedback(1, "The collection was deleted");
    }

    public List<Map<String, Object>> getAllObjects() {
        List<Map<String, Object>> allObjects = new ArrayList<>();

        for (Map<String, Object> object : btree)
            allObjects.add(object);

        return allObjects;
    }

    public Map<String, Object> findFromObjectId(String objectId) throws IOException {
        return btree.get(objectId);
    }

    public List<Map<String, Object>> find(Map<String, Object> query) throws IOException {
        return btree.get(query);
    }

    public Map<String, Object> insert(Map<String, Object> data) throws Throwable {
        if (data.containsKey("_id"))
            return Feedback.feedback(0, "The data doesn't contains '_id' key");

        btree.insert(data);

        return Feedback.feedback(1, "The object was inserted");
    }

    public Map<String, Object> updateFromObjectId(String id, Map<String, Object> updateData) throws IOException {
        if (updateData.containsKey("_id"))
            return Feedback.feedback(0, "The update data doesn't contains '_id' key");

        boolean updateStatus = btree.update(id, updateData);

        if (!updateStatus)
            return Feedback.feedback(0, "The object wasn't updated");

        return Feedback.feedback(1, "The object was updated");
    }

    public Map<String, Object> update(Map<String, Object> query, Map<String, Object> updateData) throws IOException {
        if (updateData.containsKey("_id"))
            return Feedback.feedback(0, "The update data doesn't contains '_id' key");

        boolean updateStatus = btree.update(query, updateData);

        if (!updateStatus)
            return Feedback.feedback(0, "The object wasn't updated");

        return Feedback.feedback(1, "The object was updated");
    }

    public Map<String, Object> deleteFromObjectId(String id) throws Throwable {
        boolean deleteStatus = btree.delete(id);

        if (!deleteStatus)
            return Feedback.feedback(0, "The object wasn't deleted");

        return Feedback.feedback(1, "The object was deleted");
    }

    public Map<String, Object> delete(Map<String, Object> query) throws Throwable {
        boolean deleteStatus = btree.delete(query);

        if (!deleteStatus)
            return Feedback.feedback(0, "The object wasn't deleted");

        return Feedback.feedback(1, "The object was deleted");
    }

    public boolean isExist() {
        return collectionDynamicStorageElement.isExist();
    }
}
