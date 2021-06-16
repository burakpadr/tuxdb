package com.padr.tuxdb.engine.storage.element.stc;

import java.util.ArrayList;
import java.util.HashMap;

public class ExternalNodeStaticStorageElement extends StaticStorageElement {

    public ExternalNodeStaticStorageElement(String databaseName, String collectionName, String id) {
        super(String.format("/%s/%s/btree/external", databaseName, collectionName), id + EXTENSION);

        super.startingContent = new HashMap<>();

        super.startingContent.put("_id", id);
        super.startingContent.put("internalNodesId", new ArrayList<String>());
        super.startingContent.put("isLeaf", true);
    }
}
