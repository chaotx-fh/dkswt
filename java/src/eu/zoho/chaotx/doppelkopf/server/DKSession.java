package eu.zoho.chaotx.doppelkopf.server;

public class DKSession {
    private Game game;
    private DKClient[] clients;

    public DKSession(DKClient c1, DKClient c2, DKClient c3, DKClient c4) {
        clients = new DKClient[]{c1, c2, c3, c4};
        game = new Game(
            new Player(c1.getUsername()),
            new Player(c2.getUsername()),
            new Player(c3.getUsername()),
            new Player(c4.getUsername())
        );
    }
    
    public Client[] getClients() {
        return clients;
    }
}