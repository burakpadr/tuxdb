package com.padr.tuxdb.shell;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

public class Connection {

    public static final String DEFAULT_HOST = "127.0.0.1";
    public static final int DEFAULT_PORT = 6060;
    
    public static String send(String service, String function, Map<String, Object> parameters) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(String.format("http://%s:%s/%s/%s", DEFAULT_HOST, DEFAULT_PORT, service, function)).openConnection();

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.connect();

        OutputStream dataOutputStream = connection.getOutputStream();

        dataOutputStream.write(new Gson().toJson(parameters).getBytes(StandardCharsets.UTF_8));
        dataOutputStream.flush();

        InputStream inputStream = new BufferedInputStream(connection.getInputStream());

        return IOUtils.toString(inputStream, "UTF-8");
    }
}
