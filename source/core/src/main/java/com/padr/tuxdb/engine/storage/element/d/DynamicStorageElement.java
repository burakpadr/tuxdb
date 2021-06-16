package com.padr.tuxdb.engine.storage.element.d;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;

import com.padr.tuxdb.engine.storage.element.StorageElement;
import com.padr.tuxdb.engine.storage.element.stc.StaticStorageElement;

public class DynamicStorageElement extends StorageElement {

    protected List<StorageElement> startingContent;

    public DynamicStorageElement(String superDirectory, String domain) {
        super(superDirectory, domain);
    }

    @Override
    public void create() throws IOException {
        if (isExist())
            return;

        getFile().mkdirs();

        if (startingContent != null)
            for (int i = 0; i < startingContent.size(); i++)
                startingContent.get(i).create();
    }

    @Override
    public List<StorageElement> read() throws IOException {
        if (!isExist())
            return null;

        List<StorageElement> content = new ArrayList<>();

        File[] children = getFile().listFiles();

        for (File child : children) {
            if (child.isDirectory())
                content.add(new DynamicStorageElement(getFile().getAbsolutePath(), child.getName()));
            else
                content.add(new StaticStorageElement(getFile().getAbsolutePath(), child.getName()));
        }

        return content;
    }

    @Override
    public void update(Map<String, Object> updateData) throws IOException {
        if (!isExist())
            return;

        for (Map.Entry<String, Object> entry : updateData.entrySet()) {
            String key = entry.getKey();
            String value = (String) entry.getValue();

            File file = new File(String.format("%s/%s", getFile().getAbsolutePath(), key));

            if (!file.exists())
                return;
            else {
                File tempFile = new File(String.format("%s/%s", getFile().getAbsolutePath(), value));

                if (tempFile.exists())
                    return;
                else
                    file.renameTo(tempFile);
            }
        }
    }

    @Override
    public void delete() throws IOException {
        if (!isExist())
            return;

        deleteRecursive(getFile());

        getFile().delete();
    }

    public boolean contains(String domain) throws IOException{
        StaticStorageElement staticStorageElement = new StaticStorageElement(getFile().getAbsolutePath(), domain);
        DynamicStorageElement dynamicStorageElement = new DynamicStorageElement(getFile().getAbsolutePath(), domain);

        List<StorageElement> read = read();

        return read.contains(staticStorageElement) || read.contains(dynamicStorageElement);
    }

    private void deleteRecursive(File file){
        File[] children = file.listFiles();

        if (children != null)
            for (File child : children)
                deleteRecursive(child);
        
        file.delete();
    }
}
