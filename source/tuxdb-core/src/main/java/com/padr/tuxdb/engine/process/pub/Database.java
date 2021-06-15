package com.padr.tuxdb.engine.process.pub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.padr.tuxdb.engine.feedback.Feedback;
import com.padr.tuxdb.engine.storage.element.StorageElement;
import com.padr.tuxdb.engine.storage.element.d.DatabaseDynamicStorageElement;
import com.padr.tuxdb.engine.storage.element.d.DynamicStorageElement;
import com.padr.tuxdb.engine.storage.element.stc.DatabaseStaticStorageElement;

public class Database {

    public static List<String> getDatabaseNames() throws IOException {
        List<String> databaseNames = new ArrayList<>();

        List<StorageElement> content = new DynamicStorageElement("/", "").read();

        if (content == null)
            return databaseNames;

        for (StorageElement database : content)
            databaseNames.add(database.getDomain());

        return databaseNames;
    }

    public static Integer getDatabaseSize() throws IOException {
        return getDatabaseNames().size();
    }

    private String databaseName;

    private DatabaseDynamicStorageElement database;

    public Database(String databaseName) {
        this.databaseName = databaseName; 
        database = new DatabaseDynamicStorageElement(databaseName);
    }

    public Map<String, Object> createDatabase() throws IOException {
        if (!StorageElement.domainIsSuited(databaseName))
            return Feedback.feedback(0, "The database name contains only a-Z, 0-9, -_.");
        else if (database.isExist())
            return Feedback.feedback(0, "There is already such a database associated with this name");

        database.create();

        return Feedback.feedback(1, "The database was created");
    }

    public Object getDatabaseName() throws IOException {
        if (!database.isExist())
            return Feedback.feedback(0, "There is no such database");

        return database.getDomain();
    }

    // public Object getCollection(String collectionName) throws IOException {
    //     if (!database.isExist())
    //         return Feedback.feedback(0, "There is no such database");
    //     else if (!database.contains(collectionName))
    //         return Feedback.feedback(0, "This database doesn't contain such collection");

    //     return new Collection((String) getDatabaseName(), collectionName);
    // }

    public Object getCollectionNames() throws IOException {
        if (!database.isExist())
            return Feedback.feedback(0, "There is no such database");

        List<String> collectionNames = new ArrayList<>();

        for (StorageElement collection : database.read()) {
            if ((collection instanceof DynamicStorageElement))
                collectionNames.add(collection.getDomain());
        }

        return collectionNames;
    }

    @SuppressWarnings("unchecked")
    public Object getCollectionSize() throws IOException {
        if (!database.isExist())
            return Feedback.feedback(0, "There is no such database");

        return ((List<String>) getCollectionNames()).size();
    }

    public Map<String, Object> setDatabaseName(String databaseName) throws IOException {
        if (!database.isExist())
            return Feedback.feedback(0, "There is no such database");
        else if (!StorageElement.domainIsSuited(databaseName))
            return Feedback.feedback(0, "The database name contains only a-Z, 0-9, -_.");
        else if (new DatabaseStaticStorageElement(databaseName).isExist())
            return Feedback.feedback(0, "There is already such a database associated with this name");

        database.setDomain(databaseName);

        return Feedback.feedback(1, "The database was renamed");
    }

    public Map<String, Object> createCollection(String collectionName) throws IOException {
        if (!StorageElement.domainIsSuited(collectionName))
            return Feedback.feedback(0, "The collection name contains only a-Z, 0-9, -_.");
        else if (!database.isExist())
            return Feedback.feedback(0, "There is no such database");
        else if (database.contains(collectionName))
            return Feedback.feedback(0, "This database already contains a collection associated with this name");

        return new Collection((String) getDatabaseName(), collectionName).createCollection();
    }

    public Map<String, Object> dropDatabase() throws IOException {
        if (!database.isExist())
            return Feedback.feedback(0, "There is no such database");

        database.delete();

        return Feedback.feedback(1, "The database was deleted");
    }

    public boolean isExist() {
        return database.isExist();
    }
}
