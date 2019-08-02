package com.db.edu.Messenger.client.clientProcessor;

public class UserCommands {
    private final String send = "/snd";
    private final String name = "/chid";
    private final String history = "/history";
    private final String close = "/close";
    private final String wrong = "/wrong";

    public boolean isName(String message) { return name.equals(message); }

    public boolean isSend(String message) { return send.equals(message); }

    public boolean isHistory(String message) { return history.equals(message); }

    public boolean isClose(String message) { return close.equals(message); }

    public boolean isWrong(String message) { return wrong.equals(message); }

    public String name() { return name; }

    public String send() { return send; }

    public String history() { return history; }

    public  String  close() { return close; }

    public String wrong() { return wrong; }
}
