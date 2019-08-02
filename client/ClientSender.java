package com.db.edu.Messenger.client;

import com.db.edu.Messenger.client.clientProcessor.ClientConnector;
import com.db.edu.Messenger.client.clientProcessor.ClientMessageHandler;
import com.db.edu.Messenger.exceptions.ClientConnectionException;

public class ClientSender {
    private static ClientConnector clientConnector;

    public static void main(String[] args) {
        try {
            clientConnector = new ClientConnector();

            createCheckerConnection();
            createMessageReaderAndSender();

        } catch (ClientConnectionException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createCheckerConnection() {
        new Thread(() -> {
            try {
                while (true) {
                    clientConnector.checkConnection();
                }

            } catch (ClientConnectionException e) {
                System.out.println(e.getMessage());

                try {
                    clientConnector.closeConnection();

                } catch (ClientConnectionException ex) {
                    System.out.println(ex.getMessage());
                }

            }
        }).start();
    }

    private static void createMessageReaderAndSender() throws ClientConnectionException {
        ClientMessageHandler clientMessageHanlder = new ClientMessageHandler(clientConnector);
        clientMessageHanlder.authorize();

        while (true) {
            String message = clientConnector.read();

            String date = clientMessageHanlder.parseDate();
            message = clientMessageHanlder.parseMessage(message);

            if (clientMessageHanlder.isCloseMessage(message)) {
                break;
            }
            if (clientMessageHanlder.isWrongMessage(message)) {
                continue;
            }

            clientConnector.send(date + " " + message);
        }

        clientConnector.closeConnection();
    }
}
