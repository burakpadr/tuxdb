package com.padr.tuxdb.engine.storage.element.stc;

import java.util.HashMap;

public class InternalNodeStaticStorageElement extends StaticStorageElement {

    public InternalNodeStaticStorageElement(String databaseName, String collectionName, String id) {
        super(String.format("/%s/%s/btree/internal", databaseName, collectionName), id + EXTENSION);

        super.startingContent = new HashMap<>();

        super.startingContent.put("_id", id);
        super.startingContent.put("data", new HashMap<>());
        super.startingContent.put("linkId", "0");
    }
}
