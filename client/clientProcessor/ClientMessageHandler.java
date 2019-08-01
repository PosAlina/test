package com.db.edu.Messenger.client.clientProcessor;

import com.db.edu.Messenger.exceptions.ClientConnectionException;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ClientMessageHandler {
    private ClientConnector clientConnector;
    private UserCommands userCommands;

    public ClientMessageHandler(ClientConnector clientConnector, UserCommands userCommands) {
        this.clientConnector = clientConnector;
        this.userCommands = userCommands;
    }

    public void authorize() throws ClientConnectionException {
        System.out.println("Please, input your name with command '/chid'.");
        while (true) {
            String message = clientConnector.read();
            String[] commandAndMessage = message.split(" ");
            if (userCommands.name.equals(commandAndMessage[0])) {
                if (commandAndMessage.length > 1) {
                    String userName = commandAndMessage[1];
                    checkCorrectName(userName);
                    break;
                }
                System.out.println("Please, input other name with command '/chid'.");
            }
        }
    }

    private void checkCorrectName(String userName) throws ClientConnectionException {
        clientConnector.send("#sender " + userName);
    }

    public String parseMessage(String message) {
        String[] commandAndMessage = message.split(" ");
        String command = commandAndMessage[0];
        if (userCommands.send.equals(command)) {
            return message;
        }
        if (userCommands.history.equals(command)) {
            return userCommands.history;
        }
        if (userCommands.close.equals(command)) {
            return userCommands.close;
        }
        return userCommands.wrong;
    }

    public String parseDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
}