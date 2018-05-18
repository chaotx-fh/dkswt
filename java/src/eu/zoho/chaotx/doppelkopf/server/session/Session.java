package eu.zoho.chaotx.doppelkopf.server.session;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import eu.zoho.chaotx.doppelkopf.server.game.Card;
import eu.zoho.chaotx.doppelkopf.server.game.Game;
import eu.zoho.chaotx.doppelkopf.server.game.Player;


public class Session {
    public enum State {SUSPENDED, RUNNING, TERMINATED};

    private Game game;
    private State state;
    private Thread thread;

    public Session(User[] user) {
        state = State.SUSPENDED;
        Player[] player = new Player[user.length];

        for(int i = 0; i < user.length; ++i)
            player[i] = new Player(user[i]);

        game = new Game(player, new Card[0]);
    }

    public void start() {
        System.out.println("Game ready, starting...");
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