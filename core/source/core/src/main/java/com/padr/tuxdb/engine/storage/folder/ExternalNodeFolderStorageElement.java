package com.padr.tuxdb.engine.storage.folder;

import com.padr.tuxdb.engine.storage.StorageElement;

public class ExternalNodeFolderStorageElement extends FolderStorageElement {

    public static String signature(String databaseName, String collectionName) {
        return String.format("%s%c%s", BtreeFolderStorageElement.signature(databaseName, collectionName),
                StorageElement.PREFIX, "external");
    }

    public ExternalNodeFolderStorageElement(String databaseName, String collectionName) {
        super(signature(databaseName, collectionName));
    }
}
