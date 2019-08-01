package com.db.edu.Messenger.client;

import com.db.edu.Messenger.client.clientProcessor.ClientConnector;
import com.db.edu.Messenger.exceptions.ClientConnectionException;

public class ClientReceiver {
    private final static int PORT = 80;

    public static void main(String[] args) {
        try {
            ClientConnector clientConnector = new ClientConnector(PORT);
            try {
            while (true) {
                String message = clientConnector.receive();
                clientConnector.print(message);
            }
            } catch (ClientConnectionException e) {
                clientConnector.closeConnection();
                System.out.println(e.getMessage());
            }
        } catch (ClientConnectionException e) {
            System.out.println(e.getMessage());
        }
    }
}
