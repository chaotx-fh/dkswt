package eu.zoho.chaotx.doppelkopf.server.connect;

import java.net.Socket;

public interface ConnectionHandler {
    /**
     * @brief Functional interface, handles connection
     */
    public void handle(Socket client);
}