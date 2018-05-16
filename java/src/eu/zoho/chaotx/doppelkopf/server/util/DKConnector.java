package eu.zoho.chaotx.doppelkopf.server.util;


public interface DKConnector {
    /**
     * @brief Requests an int from a connection and returns it,
     * blocks current thread until Object was received
     */
    public int requestInt();
}