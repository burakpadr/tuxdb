package com.padr.tuxdb.engine.storage.folder;

import java.util.*;

import com.padr.tuxdb.engine.storage.StorageElement;
import com.padr.tuxdb.engine.storage.file.CollectionFileStorageElement;

public class CollectionFolderStorageElement extends FolderStorageElement {
    
    public static String signature(String databaseName, String collectionName) {
        return String.format("%s%c%s", DatabaseFolderStorageElement.signature(databaseName), StorageElement.PREFIX, collectionName);
    }

    public CollectionFolderStorageElement(String databaseName, String collectionName) {
        super(signature(databaseName, collectionName));

        super.startingContent= new ArrayList<>();

        super.startingContent.add(new CollectionFileStorageElement(databaseName, collectionName));
        super.startingContent.add(new BtreeFolderStorageElement(databaseName, collectionName));
    }
}
