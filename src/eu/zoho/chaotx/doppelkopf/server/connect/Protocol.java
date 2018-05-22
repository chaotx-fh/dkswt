package eu.zoho.chaotx.doppelkopf.server.connect;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Protocol implements Serializable {
    private static final long serialVersionUID = 42;
    private Map<String, ConnectionHandler> handlerMap;

    /**
     * @brief Protocol to determine behaviour of connection
     * with certain clients (@see ConnectionHandler).
     */
    public Protocol() {
        handlerMap = new HashMap<>();
    }
    
    public ConnectionHandler getHandler(String name) {
        return handlerMap.get(name);
    }

    public void setHandler(String name, ConnectionHandler somehandler) {
        handlerMap.put(name, somehandler);
    }
}