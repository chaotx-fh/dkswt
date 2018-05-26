package eu.zoho.chaotx.doppelkopf.server.connect;

import java.io.Serializable;
import java.net.Socket;

public interface ConnectionHandler extends Serializable {
    /**
     * @brief functional interface, handles connection
     */
    public void handle(Socket client);
}