package eu.zoho.chaotx.doppelkopf.server.session;

import eu.zoho.chaotx.doppelkopf.server.util.IntRequester;

public class User {
    private String name;
    private IntRequester requester;

    public User(String somename, IntRequester somerequester) {
        name = somename;
        requester = somerequester;
    }

    public String getName() {
        return name;
    }

    public int request() {
        return requester.requestInt();
    }
}