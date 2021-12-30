package com.padr.tuxdb.engine.storage.file;

import com.padr.tuxdb.engine.storage.StorageElement;
import com.padr.tuxdb.engine.storage.folder.DatabaseFolderStorageElement;

public class DatabaseFileStorageElement extends FileStorageElement {

    public DatabaseFileStorageElement(String databaseName) {
        super(String.format("%s%c%s.%s", DatabaseFolderStorageElement.signature(databaseName), StorageElement.PREFIX,
                "database", FILE_EXTENSION));
    }
}
