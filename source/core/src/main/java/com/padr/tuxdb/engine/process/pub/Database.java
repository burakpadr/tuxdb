package com.padr.tuxdb.engine.process.pub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.padr.tuxdb.engine.process.Feedback;
import com.padr.tuxdb.engine.storage.StorageElement;
import com.padr.tuxdb.engine.storage.folder.DatabaseFolderStorageElement;
import com.padr.tuxdb.engine.storage.folder.FolderStorageElement;
import com.padr.tuxdb.engine.storage.file.DatabaseFileStorageElement;

public class Database {

    @SuppressWarnings("unchecked")
    public static List<String> getDatabaseNames() throws IOException {
        List<String> databaseNames = new ArrayList<>();

        List<StorageElement> content = (List<StorageElement>) new FolderStorageElement().read();

        if (content == null)
            return databaseNames;

        for (StorageElement database : content)
            databaseNames.add(database.getDomain());

        return databaseNames;
    }

    public static Integer getDatabaseSize() throws IOException {
        return getDatabaseNames().size();
    }

    public static Map<String, Object> createDatabase(String databaseName) throws IOException {
        if (!StorageElement.domainIsSuited(databaseName))
            return Feedback.feedback(0, "The database name contains only a-Z, 0-9, -_.");

        DatabaseFolderStorageElement database = new DatabaseFolderStorageElement(databaseName);

        if (database.isExist())
            return Feedback.feedback(0, "There is already such a database associated with this name");

        database.create();

        return Feedback.feedback(1, "The database was created");
    }

    private DatabaseFolderStorageElement database;
    private Map<String, Object> constructorError;

    public Database(String databaseName) {
        database = new DatabaseFolderStorageElement(databaseName);

        if (!StorageElement.domainIsSuited(databaseName))
            constructorError = Feedback.feedback(0, "The database name contains only a-Z, 0-9, -_.");
        else if (!database.isExist())
            constructorError = Feedback.feedback(0, String.format("There is no database named %s", databaseName));
    }

    @SuppressWarnings("unchecked")
    public Object getCollectionNames() throws IOException {
        if (constructorError != null){
            return constructorError;
        }
        List<String> collectionNames = new ArrayList<>();

        for (StorageElement collection : (List<StorageElement>) database.read()) {
            if ((collection instanceof FolderStorageElement))
                collectionNames.add(collection.getDomain());
        }

        return collectionNames;
    }

    @SuppressWarnings("unchecked")
    public Object getCollectionSize() throws IOException {
        if (constructorError != null)
            return constructorError;

        return ((List<String>) getCollectionNames()).size();
    }

    public Map<String, Object> setDatabaseName(String databaseName) throws IOException {
        if (constructorError != null)
            return constructorError;
        else if (!StorageElement.domainIsSuited(databaseName))
            return Feedback.feedback(0, "The database name contains only a-Z, 0-9, -_.");
        else if (new DatabaseFileStorageElement(databaseName).isExist())
            return Feedback.feedback(0, "There is already such a database associated with this name");

        database.setDomain(databaseName);

        return Feedback.feedback(1, "The database was renamed");
    }

    public Map<String, Object> drop() throws IOException {
        if (constructorError != null)
            return constructorError;

        database.delete();

        return Feedback.feedback(1, "The database was deleted");
    }

    public boolean isExist() {
        if (constructorError != null)
            return false;

        return database.isExist();
    }
}
