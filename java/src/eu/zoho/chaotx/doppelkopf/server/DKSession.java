package eu.zoho.chaotx.doppelkopf.server;

import java.util.HashMap;
import java.util.Map;

import eu.zoho.chaotx.doppelkopf.client.DKClient;
import eu.zoho.chaotx.doppelkopf.game.Game;
import eu.zoho.chaotx.doppelkopf.game.Player;


public class DKSession {
    public static final int CLIENT_CNT = 4;
    public enum State{SUSPENDED, RUNNING, TERMINATED};

    private Game game;
    private State state;
    private Thread thread;
    private Map<Player, DKClient> clientMap;


    public DKSession(DKClient[] someclients) {
        // TODO throw exception if someclients.length != CLIENT_CNT
        state = SUSPENDED;
        clientMap = new HashMap<>(CLIENT_CNT);

        for(DKClient client : someclients)
            clientMap.put(new Player(client.getUser(), client.getConnector()), client);

        game = new Game(clientMap.keySet().toArray(new Player[0]), new Board());
    }
    
    public Client[] getClients() {
        return clients;
    }

    public void start() {
        state = RUNNING;
        thread = new Thread(game);
        thread.setDaemon(true);
        thread.start();
    }

    public void check() {
        state = thread.isAlive() ? TERMINATED : state;
    }

    public State getState() {
        return state;
    }
}