package eu.zoho.chaotx.doppelkopf.server;

public class DKSession {
    public enum State{SUSPENDED, RUNNING, TERMINATED};

    private Game game;
    private Status state;
    private Thread thread;
    private DKClient[] clients;


    public DKSession(DKClient c1, DKClient c2, DKClient c3, DKClient c4) {
        clients = new DKClient[]{c1, c2, c3, c4};
        state = SUSPENDED;

        game = new Game(new Player[]{
                new Player(c1.getUsername()),
                new Player(c2.getUsername()),
                new Player(c3.getUsername()),
                new Player(c4.getUsername())
            }, new Board()
        );
    }
    
    public Client[] getClients() {
        return clients;
    }

    public void start() {
        state = RUNNING;
        thread = new Thread(game);
        thread.start();
    }

    public void check() {
        state = thread.isAlive() ? TERMINATED : RUNNING;
    }

    public Status getState() {

    }
}