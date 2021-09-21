package com.padr.tuxdb.engine.storage.file;

import java.util.HashMap;

import com.padr.tuxdb.engine.storage.StorageElement;
import com.padr.tuxdb.engine.storage.folder.InternalNodeFolderStorageElement;

public class InternalNodeFileStorageElement extends FileStorageElement {

    public InternalNodeFileStorageElement(String databaseName, String collectionName, String id) {
        super(String.format("%s%c%s.%s", InternalNodeFolderStorageElement.signature(databaseName, collectionName),
                StorageElement.PREFIX, id, FILE_EXTENSION));

        super.startingContent = new HashMap<>();

        super.startingContent.put("_id", id);
        super.startingContent.put("data", new HashMap<>());
        super.startingContent.put("linkId", "0");
    }
}
