package com.padr.tuxdb.engine.storage.element.stc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.padr.tuxdb.engine.storage.element.StorageElement;

public class StaticStorageElement extends StorageElement {

    protected static String EXTENSION = ".json";

    protected Map<String, Object> startingContent;

    public StaticStorageElement(String superDirectory, String domain) {
        super(superDirectory, domain);
    }

    @Override
    public void create() throws IOException {
        if (isExist())
            return;

        getFile().createNewFile();

        Writer writer = new FileWriter(getFile().getAbsoluteFile());

        new Gson().toJson(startingContent, writer);

        writer.close();
    }

    @Override 
    @SuppressWarnings("unchecked")
    public Map<String, Object> read() throws IOException{
        if (!isExist())
            return null;

        Reader fileReader = Files.newBufferedReader(getFile().toPath());
        
        Map<String, Object> fileContent = new HashMap<>();
        fileContent = (Map<String, Object>) new Gson().fromJson(fileReader, fileContent.getClass());

        fileReader.close();

        return fileContent;
    }

    @Override
    public void update(Map<String, Object> updateData) throws IOException{
        if (!isExist())
            return;
        
        Map<String, Object> read = read();

        for (Map.Entry<String, Object> data: updateData.entrySet()){
            String key = data.getKey();
            Object value = data.getValue();

            if (value == null)
                continue;

            read.put(key, value);
        }

        Writer writer = new FileWriter(getFile().getAbsoluteFile());

        new Gson().toJson(read, writer);

        writer.close();
    }

    @Override
    public void delete() throws IOException{
        if (!isExist())
            return;
        
        getFile().delete();
    }
}
