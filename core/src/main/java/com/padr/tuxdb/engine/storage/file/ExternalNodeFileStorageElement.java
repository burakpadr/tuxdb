package com.padr.tuxdb.engine.storage.file;

import java.util.HashMap;
import java.util.ArrayList;

import com.padr.tuxdb.engine.storage.StorageElement;
import com.padr.tuxdb.engine.storage.folder.ExternalNodeFolderStorageElement;

public class ExternalNodeFileStorageElement extends FileStorageElement {

    public ExternalNodeFileStorageElement(String databaseName, String collectionName, String id) {
        super(String.format("%s%c%s.%s", ExternalNodeFolderStorageElement.signature(databaseName, collectionName),
                StorageElement.PREFIX, id, FILE_EXTENSION));

        super.startingContent = new HashMap<>();

        super.startingContent.put("_id", id);
        super.startingContent.put("internalNodesId", new ArrayList<String>());
        super.startingContent.put("isLeaf", true);
    }
}
