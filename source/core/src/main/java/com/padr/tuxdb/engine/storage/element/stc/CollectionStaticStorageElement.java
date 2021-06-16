package com.padr.tuxdb.engine.storage.element.stc;

public class CollectionStaticStorageElement extends StaticStorageElement{

    public CollectionStaticStorageElement(String databaseName, String collectionName){
        super(String.format("/%s/%s", databaseName, collectionName), String.format("%s.%s", "collection", EXTENSION));
    }
}
