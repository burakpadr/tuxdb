package com.padr.tuxdb.engine.storage.element.d;

import java.util.ArrayList;
import java.util.List;

import com.padr.tuxdb.engine.storage.element.StorageElement;
import com.padr.tuxdb.engine.storage.element.stc.BtreeStaticStorageElement;

public class BTreeDynamicStorageElement extends DynamicStorageElement{
    
    public BTreeDynamicStorageElement(String databaseName, String collectionName){
        super(String.format("/%s/%s", databaseName, collectionName), "btree");

        String superDirectory = String.format("/%s/%s/%s", databaseName, collectionName, "btree");

        List<StorageElement> startingContent = new ArrayList<>();

        startingContent.add(new BtreeStaticStorageElement(databaseName, collectionName));
        startingContent.add(new DynamicStorageElement(superDirectory, "external"));
        startingContent.add(new DynamicStorageElement(superDirectory, "internal"));

        super.startingContent = startingContent;
    }
}
