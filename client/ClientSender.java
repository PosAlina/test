package com.db.edu.Messenger.client;

import com.db.edu.Messenger.client.clientProcessor.ClientConnector;
import com.db.edu.Messenger.client.clientProcessor.ClientMessageHandler;
import com.db.edu.Messenger.client.clientProcessor.UserCommands;
import com.db.edu.Messenger.exceptions.ClientConnectionException;

public class ClientSender {
    private final static int PORT = 80;

    public static void main(String[] args) {
        try {
            ClientConnector clientConnector = new ClientConnector(PORT);

            createCheckerConnection(clientConnector);

            createMessageReaderAndSender(clientConnector);

        } catch (ClientConnectionException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createCheckerConnection(ClientConnector clientConnector) {
        new Thread(() -> {
            try {
                while (true) {
                    clientConnector.checkConnection();
                }
            } catch (ClientConnectionException e) {
                try {
                    clientConnector.closeConnection();
                } catch (ClientConnectionException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }).start();
    }

    private static void createMessageReaderAndSender(ClientConnector clientConnector) throws ClientConnectionException {
        UserCommands userCommands = new UserCommands();
        ClientMessageHandler clientMessageHanlder = new ClientMessageHandler(clientConnector, userCommands);

        clientMessageHanlder.authorize();

        while (true) {
            String message = clientConnector.read();
            String date = clientMessageHanlder.parseDate();

            message = clientMessageHanlder.parseMessage(message);
            if (userCommands.close.equals(message)) {
                break;
            }
            if (userCommands.wrong.equals(message)) {
                continue;
            }

            clientConnector.send(date + " " + message);
        }
        clientConnector.closeConnection();
    }
}
