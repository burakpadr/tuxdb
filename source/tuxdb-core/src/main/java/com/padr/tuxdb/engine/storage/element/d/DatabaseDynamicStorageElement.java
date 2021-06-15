package com.padr.tuxdb.engine.storage.element.d;

import java.util.ArrayList;
import java.util.List;

import com.padr.tuxdb.engine.storage.element.StorageElement;
import com.padr.tuxdb.engine.storage.element.stc.DatabaseStaticStorageElement;

public class DatabaseDynamicStorageElement extends DynamicStorageElement{
    
    public DatabaseDynamicStorageElement(String databaseName){
        super("/", databaseName);

        List<StorageElement> startingContent = new ArrayList<>();

        startingContent.add(new DatabaseStaticStorageElement(databaseName));

        super.startingContent = startingContent;
    }
}
