package com.padr.tuxdb.engine.storage.element.stc;

public class DatabaseStaticStorageElement extends StaticStorageElement{

    public DatabaseStaticStorageElement(String databaseName){
        super(String.format("/%s", databaseName), String.format("%s.%s", "database", EXTENSION));
    }
}
