package com.db.edu.Messenger.client.clientProcessor;

import com.db.edu.Messenger.exceptions.ClientConnectionException;

import java.io.*;
import java.net.Socket;

public class ClientConnector {
    private static final int PORT = 80;
    private Socket server;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader console;

    public ClientConnector() throws ClientConnectionException {
        try {
            server = new Socket("localhost", PORT);
            in = new BufferedReader(
                    new InputStreamReader(
                            new BufferedInputStream(
                                    server.getInputStream())));
            out = new BufferedWriter(
                    new OutputStreamWriter(
                            new BufferedOutputStream(
                                    server.getOutputStream())));
            console = new BufferedReader(
                            new InputStreamReader(
                                    new BufferedInputStream(
                                            System.in)));

        } catch (IOException e) {
            throw new ClientConnectionException("Connection don't established");
        }
    }

    public void closeConnection() throws ClientConnectionException {
        try {
            out.close();
            in.close();
            server.close();

        } catch (IOException e) {
            throw new ClientConnectionException("Connection was closed with error");
        }
    }

    public void checkConnection() throws ClientConnectionException {
        try {
            receive();

        } catch (ClientConnectionException e) {
            throw new ClientConnectionException("Connection was closed");
        }
    }

    public void send(String message) throws ClientConnectionException {
        try {
            out.write(message);
            out.newLine();
            out.flush();

        } catch (IOException e) {
            throw new ClientConnectionException("Message don`t sent");
        }
    }

    public String receive() throws ClientConnectionException {
        try {
            return in.readLine();

        } catch (IOException e) {
            throw new ClientConnectionException("Message don`t received");
        }
    }

    public void print(String message) {
        System.out.println(message);
    }

    public String read() throws ClientConnectionException {
        try {
            return console.readLine();

        } catch (IOException e) {
            throw new ClientConnectionException("Message don`t read");
        }
    }

    public BufferedReader getIn() {
        return in;
    }

    public BufferedWriter getOut() {
        return out;
    }

    public BufferedReader getConsole() {
        return console;
    }
}
