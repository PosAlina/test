package com.db.edu.Messenger.client.clientProcessor;

import com.db.edu.Messenger.exceptions.ClientConnectionException;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ClientMessageHandler {
    private static UserCommands userCommands = new UserCommands();
    private static ServiceCommands serviceCommands = new ServiceCommands();
    private ClientConnector clientConnector;

    public ClientMessageHandler(ClientConnector clientConnector) {
        this.clientConnector = clientConnector;
    }

    public void authorize() throws ClientConnectionException {
        System.out.println("Please, input your name with command '" + userCommands.name() + "'");
        
        while (true) {
            String message = clientConnector.read();
            String[] commandAndMessage = message.split(" ");
            
            if (userCommands.isName(commandAndMessage[0])) {
                if (commandAndMessage.length > 1) {
                    StringBuilder userName = new StringBuilder(commandAndMessage[1]);
                    for (int wordNumber = 2; wordNumber <= commandAndMessage.length; wordNumber++) {
                        userName.append(commandAndMessage[wordNumber]);
                    }
                    checkCorrectName(userName.toString());
                    break;
                }
                System.out.println("Please, input other name with command '" + userCommands.name() + "'");
            }
        }
    }

    private void checkCorrectName(String userName) throws ClientConnectionException {
        clientConnector.send(serviceCommands.sender() + userName);
    }

    public String parseMessage(String message) throws ClientConnectionException {
        String[] commandAndMessage = message.split(" ");
        String command = commandAndMessage[0];
        
        if (userCommands.isSend(command)) {
            return message;
        }
        if (userCommands.isHistory(command)) {
            return userCommands.history();
        }
        if (userCommands.isClose(command)) {
            clientConnector.send(serviceCommands.close());
            return userCommands.close();
        }
        return userCommands.wrong();
    }

    public String parseDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        
        return formatter.format(date);
    }

    public boolean isCloseMessage(String message) {
        return userCommands.isClose(message);
    }

    public boolean isWrongMessage(String message) {
        return userCommands.isWrong(message);
    }
}
