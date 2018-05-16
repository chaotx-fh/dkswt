package eu.zoho.chaotx.doppelkopf.server;

import java.util.HashMap;
import java.util.Map;

import eu.zoho.chaotx.doppelkopf.server.game.Card;
import eu.zoho.chaotx.doppelkopf.server.game.Game;
import eu.zoho.chaotx.doppelkopf.server.game.Player;


public class DKSession {
    public static final int CLIENT_CNT = 4;
    public enum State{SUSPENDED, RUNNING, TERMINATED};

    private Game game;
    private State state;
    private Thread thread;


    public DKSession() {
        state = State.SUSPENDED;
        game = new Game(new Player[0], new Card[0]);
    }

    public void start() {
        state = State.RUNNING;
        thread = new Thread(game);
        thread.setDaemon(true);
        thread.start();
    }

    public void check() {
        state = thread.isAlive() ? State.TERMINATED : state;
    }

    public State getState() {
        return state;
    }
}