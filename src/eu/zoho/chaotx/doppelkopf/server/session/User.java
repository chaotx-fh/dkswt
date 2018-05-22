package eu.zoho.chaotx.doppelkopf.server.session;

import java.net.Socket;

public class User {
    private String username, password;
    private Socket client;

    public User(String somename, String somepassword, Socket someclient) {
        client = someclient;
        username = somename;
        password = somepassword;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Socket getClient() {
        return client;
    }
}