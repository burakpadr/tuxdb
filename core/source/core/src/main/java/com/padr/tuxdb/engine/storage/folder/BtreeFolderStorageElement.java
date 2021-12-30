package com.padr.tuxdb.engine.storage.folder;

import com.padr.tuxdb.engine.storage.StorageElement;
import com.padr.tuxdb.engine.storage.file.BtreeFileStorageElement;

import java.util.ArrayList;

public class BtreeFolderStorageElement extends FolderStorageElement {

    public static String signature(String databaseName, String collectionName) {
        return String.format("%s%c%s", CollectionFolderStorageElement.signature(databaseName, collectionName),
                StorageElement.PREFIX, "btree");
    }

    public BtreeFolderStorageElement(String databaseName, String collectionName) {
        super(signature(databaseName, collectionName));

        super.startingContent = new ArrayList<>();

        super.startingContent.add(new BtreeFileStorageElement(databaseName, collectionName));
        super.startingContent.add(new InternalNodeFolderStorageElement(databaseName, collectionName));
        super.startingContent.add(new ExternalNodeFolderStorageElement(databaseName, collectionName));
    }
}
