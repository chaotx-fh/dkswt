package eu.zoho.chaotx.doppelkopf.client;

import eu.zoho.chaotx.doppelkopf.util.DKConnector;

public interface DKClient {
    public String getUser();
    public String getEmail();
    public String getPassword();
    public DKConnector getConnector();
}