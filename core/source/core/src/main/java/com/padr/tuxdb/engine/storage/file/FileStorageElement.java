package com.padr.tuxdb.engine.storage.file;

import java.io.IOException;
import java.io.Writer;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import com.padr.tuxdb.engine.storage.StorageElement;

public class FileStorageElement extends StorageElement {

    public final static String FILE_EXTENSION = "json";

    protected Map<String, Object> startingContent;

    public FileStorageElement(String pathname) {
        super(pathname);
    }

    @Override
    public void create() throws IOException {
        file.createNewFile();

        Writer writer = new FileWriter(file.getAbsolutePath());

        if (startingContent != null)
            new Gson().toJson(startingContent, writer);

        writer.close();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object read() throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(file.getAbsolutePath()));

        Map<String, Object> content = new HashMap<>();
        content = new Gson().fromJson(reader, content.getClass());

        reader.close();

        return content;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(Map<String, Object> updateData) throws IOException {
        Map<String, Object> read = (Map<String, Object>) read();

        for (Map.Entry<String, Object> data : updateData.entrySet()) {
            String key = (String) data.getKey();
            Object value = data.getValue();

            if (value == null)
                continue;

            read.put(key, value);
        }

        Writer writer = new FileWriter(file.getAbsoluteFile());

        new Gson().toJson(read, writer);

        writer.close();

    }

    @Override
    public void delete() {
        file.delete();
    }

}
