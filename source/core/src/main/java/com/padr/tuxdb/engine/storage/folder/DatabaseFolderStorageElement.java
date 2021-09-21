package com.padr.tuxdb.engine.storage.folder;

import java.util.ArrayList;

import com.padr.tuxdb.engine.storage.StorageElement;
import com.padr.tuxdb.engine.storage.file.DatabaseFileStorageElement;

public class DatabaseFolderStorageElement extends FolderStorageElement {

    public static String signature(String databaseName) {
        return String.format("%c%s", StorageElement.PREFIX, databaseName);
    }

    public DatabaseFolderStorageElement(String databaseName) {
        super(signature(databaseName));

        super.startingContent = new ArrayList<>();

        super.startingContent.add(new DatabaseFileStorageElement(databaseName));
    }
}
