package com.padr.tuxdb.engine.storage.element.d;

import java.util.ArrayList;
import java.util.List;

import com.padr.tuxdb.engine.storage.element.StorageElement;
import com.padr.tuxdb.engine.storage.element.stc.CollectionStaticStorageElement;

public class CollectionDynamicStorageElement extends DynamicStorageElement {
    
    public CollectionDynamicStorageElement(String databaseName, String collectionName){
        super(String.format("/%s", databaseName), collectionName);

        List<StorageElement> startingContent = new ArrayList<>();

        startingContent.add(new CollectionStaticStorageElement(databaseName, collectionName));

        super.startingContent = startingContent;
    }
}
