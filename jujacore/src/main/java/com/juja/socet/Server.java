package com.juja.socet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        
        try (Socket clientSocket = serverSocket.accept();
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            System.out.println("New connection from: " + clientSocket.getRemoteSocketAddress().toString());
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Have read from client: " + inputLine);
                out.println(Integer.valueOf(inputLine) + 1);
                System.out.println("Have wrote to client: " +(Integer.valueOf(inputLine) + 1));
            }
            System.out.println("Client has disconnected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
