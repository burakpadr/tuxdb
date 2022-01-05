package io.github.burakpadr.tuxjava;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Client {

    private final static String DEFAULT_HOST = "127.0.0.1";
    private final static int DEFAULT_PORT = 6060;

    private String host;
    private int port;

    public Client() throws Exception {
        host = DEFAULT_HOST;
        port = DEFAULT_PORT;
    }

    public Client(String host, int port) throws Exception {
        this.host = host;
        this.port = port;;
    }

    public byte[] send(String service, String function, Map<String, Object> parameters) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(String.format("http://%s:%s/%s/%s", host, port, service, function)).openConnection();

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.connect();

        OutputStream dataOutputStream = connection.getOutputStream();

        dataOutputStream.write(new Gson().toJson(parameters).getBytes(StandardCharsets.UTF_8));
        dataOutputStream.flush();

        InputStream inputStream = new BufferedInputStream(connection.getInputStream());

        return IOUtils.toString(inputStream, "UTF-8").getBytes();
    }

    public Database getDatabase(String databaseName) throws Exception {
        return new Database(this, databaseName);
    }
}
