package com.juja.socet.client.server;

import com.juja.socet.client.greet.Greetable;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class SimpleServer extends Thread {
    private Socket client;
    private Map<String, Greetable> hanedlers;

    public SimpleServer(Socket client, Map<String, Greetable> hanedlers) {
        this.client = client;
        this.hanedlers = hanedlers;
    }

    @Override
    public void run() {
        handleRequest();
    }

    private void handleRequest() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        ) {

            String request = br.readLine();
            String[] lines = request.split("\\s+");

            String response = buildResponse(lines[0], lines[1]);

            bw.write(response);
            bw.newLine();
            bw.flush();

            client.close();
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    private String buildResponse(String command, String userName) {
        Greetable handler = hanedlers.get(command);
        if (handler != null) {
            return handler.buildResponse(userName);
        }
        return "Hello, " + userName;
    }
}
