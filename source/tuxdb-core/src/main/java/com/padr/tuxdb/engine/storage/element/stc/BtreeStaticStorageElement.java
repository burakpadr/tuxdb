package com.padr.tuxdb.engine.storage.element.stc;

import java.util.HashMap;

public class BtreeStaticStorageElement extends StaticStorageElement {

    public BtreeStaticStorageElement(String databaseName, String collectionName){
        super(String.format("/%s/%s/%s", databaseName, collectionName, "btree"), String.format("%s.%s", "btree", EXTENSION));

        super.startingContent = new HashMap<>();
        super.startingContent.put("rootId", "0");
        super.startingContent.put("minL", 2);
        super.startingContent.put("maxL", 4);
    }
}
