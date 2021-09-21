package com.padr.tuxdb.engine.storage.file;

import java.util.HashMap;

import com.padr.tuxdb.engine.storage.StorageElement;
import com.padr.tuxdb.engine.storage.folder.BtreeFolderStorageElement;

public class BtreeFileStorageElement extends FileStorageElement {
    
    public BtreeFileStorageElement(String databaseName, String collectionName) {
        super(String.format("%s%c%s.%s", BtreeFolderStorageElement.signature(databaseName, collectionName), StorageElement.PREFIX, "btree", FILE_EXTENSION));

        super.startingContent = new HashMap<>();
        
        super.startingContent.put("rootId", "0");
        super.startingContent.put("minL", 2);
        super.startingContent.put("maxL", 4);
    }
}
