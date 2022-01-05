package com.padr.tuxdb.engine.storage.file;

import com.padr.tuxdb.engine.storage.StorageElement;
import com.padr.tuxdb.engine.storage.folder.CollectionFolderStorageElement;

public class CollectionFileStorageElement extends FileStorageElement {

    public CollectionFileStorageElement(String databaseName, String collectionName) {
        super(String.format("%s%c%s.%s", CollectionFolderStorageElement.signature(databaseName, collectionName),
                StorageElement.PREFIX, "collection", FILE_EXTENSION));
    }
}
