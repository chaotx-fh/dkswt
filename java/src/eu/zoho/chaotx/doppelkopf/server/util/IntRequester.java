package eu.zoho.chaotx.doppelkopf.server.util;

public interface IntRequester {
    /**
     * @brief Requests an int from a connection and returns it,
     * blocks current thread until value was received
     */
    public int requestInt();
}