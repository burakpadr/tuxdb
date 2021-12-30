package com.padr.tuxdb.engine.storage.folder;

import com.padr.tuxdb.engine.storage.StorageElement;
import com.padr.tuxdb.engine.storage.file.FileStorageElement;

import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FolderStorageElement extends StorageElement {

    protected List<StorageElement> startingContent;

    public FolderStorageElement() {
        super(String.valueOf(StorageElement.PREFIX));
    }

    protected FolderStorageElement(String pathname) {
        super(pathname);
    }

    @SuppressWarnings("unchecked")
    public boolean contains(String domain) throws IOException {
        FileStorageElement fileStorageElement = new FileStorageElement(String.format("%s%c%s.%s", getAbosultePathname(),
                StorageElement.PREFIX, domain, FileStorageElement.PREFIX));
        FolderStorageElement folderStorageElement = new FolderStorageElement(
                String.format("%s%c%s", getAbosultePathname(), StorageElement.PREFIX, domain));

        List<StorageElement> read = (List<StorageElement>) read();

        return read.contains(fileStorageElement) || read.contains(folderStorageElement);
    }

    @Override
    public void create() throws IOException {
        if (isExist())
            return;

        file.mkdirs();

        if (startingContent != null)
            for (StorageElement storageElement : startingContent)
                storageElement.create();
    }

    @Override
    public Object read() {
        List<StorageElement> content = new ArrayList<>();

        File[] children = file.listFiles();

        if (children != null)
            for (File child : children) {
                if (child.isDirectory())
                    content.add(new FolderStorageElement(
                            String.format("%s%c%s", getAbosultePathname(), StorageElement.PREFIX, child.getName())));
                else
                    content.add(new FileStorageElement(
                            String.format("%s%c%s", getAbosultePathname(), StorageElement.PREFIX, child.getName())));
            }

        return content;
    }

    @Override
    public void update(Map<String, Object> updateData) {
        return;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void delete() {
        List<StorageElement> content = (List<StorageElement>) read();

        for (StorageElement contentElement : content)
            contentElement.delete();

        file.delete();
    }
}
