package eu.zoho.chaotx.doppelkopf.server.session;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import eu.zoho.chaotx.doppelkopf.server.connect.ConnectionHandler;
import eu.zoho.chaotx.doppelkopf.server.connect.Protocol;
import eu.zoho.chaotx.doppelkopf.server.game.Card;
import eu.zoho.chaotx.doppelkopf.server.game.Game;
import eu.zoho.chaotx.doppelkopf.server.game.Player;


public class Session implements Runnable {
    private static long _counter = 0;
    private long id;

    private Game game;
    private Protocol protocol;

    private Map<Player, User> userMap;
    private Map<Socket, Player> playerMap;

    public Session(User[] someusers) {
        id = _counter++;
        userMap = new HashMap<>();
        playerMap = new HashMap<>();

        Player p;
        for(User u : someusers) {
            p = new Player(u.getUsername());
            userMap.put(p, u);
            playerMap.put(u.getClient(), p);
        }

        // temporary card construction (later with config file)
        Card[] cards = new Card[] {
            new Card(Card.Symbol.CLUB, Card.Value.NINE), new Card(Card.Symbol.CLUB, Card.Value.NINE),
            new Card(Card.Symbol.CLUB, Card.Value.TEN), new Card(Card.Symbol.CLUB, Card.Value.TEN),
            new Card(Card.Symbol.CLUB, Card.Value.JACK), new Card(Card.Symbol.CLUB, Card.Value.JACK),
            new Card(Card.Symbol.CLUB, Card.Value.QUEEN), new Card(Card.Symbol.CLUB, Card.Value.QUEEN),
            new Card(Card.Symbol.CLUB, Card.Value.KING), new Card(Card.Symbol.CLUB, Card.Value.KING),
            new Card(Card.Symbol.CLUB, Card.Value.ACE), new Card(Card.Symbol.CLUB, Card.Value.ACE),

            new Card(Card.Symbol.SPADE, Card.Value.NINE), new Card(Card.Symbol.SPADE, Card.Value.NINE),
            //new Card(Card.Symbol.SPADE, Card.Value.TEN), new Card(Card.Symbol.SPADE, Card.Value.TEN),
            new Card(Card.Symbol.SPADE, Card.Value.JACK), new Card(Card.Symbol.SPADE, Card.Value.JACK),
            new Card(Card.Symbol.SPADE, Card.Value.QUEEN), new Card(Card.Symbol.SPADE, Card.Value.QUEEN),
            new Card(Card.Symbol.SPADE, Card.Value.KING), new Card(Card.Symbol.SPADE, Card.Value.KING),
            new Card(Card.Symbol.SPADE, Card.Value.ACE), new Card(Card.Symbol.SPADE, Card.Value.ACE),

            new Card(Card.Symbol.HEART, Card.Value.NINE), new Card(Card.Symbol.HEART, Card.Value.NINE),
            new Card(Card.Symbol.HEART, Card.Value.TEN), new Card(Card.Symbol.HEART, Card.Value.TEN),
            new Card(Card.Symbol.HEART, Card.Value.JACK), new Card(Card.Symbol.HEART, Card.Value.JACK),
            new Card(Card.Symbol.HEART, Card.Value.QUEEN), new Card(Card.Symbol.HEART, Card.Value.QUEEN),
            new Card(Card.Symbol.HEART, Card.Value.KING), new Card(Card.Symbol.HEART, Card.Value.KING),
            new Card(Card.Symbol.HEART, Card.Value.ACE), new Card(Card.Symbol.HEART, Card.Value.ACE),

            new Card(Card.Symbol.DIAMOND, Card.Value.NINE), new Card(Card.Symbol.DIAMOND, Card.Value.NINE),
            new Card(Card.Symbol.DIAMOND, Card.Value.TEN), new Card(Card.Symbol.DIAMOND, Card.Value.TEN),
            new Card(Card.Symbol.DIAMOND, Card.Value.JACK), new Card(Card.Symbol.DIAMOND, Card.Value.JACK),
            new Card(Card.Symbol.DIAMOND, Card.Value.QUEEN), new Card(Card.Symbol.DIAMOND, Card.Value.QUEEN),
            new Card(Card.Symbol.DIAMOND, Card.Value.KING), new Card(Card.Symbol.DIAMOND, Card.Value.KING),
            new Card(Card.Symbol.DIAMOND, Card.Value.ACE), new Card(Card.Symbol.DIAMOND, Card.Value.ACE)
        };

        game = new Game(playerMap.values().toArray(new Player[0]), cards);
    }

    public void run() {
        game.init();

        Socket next;
        while(true) {
            printBoard();
            printHand(game.getNextPlayer());

            next = userMap.get(game.getNextPlayer()).getClient();
            protocol.getHandler("game_running").handle(next);
        }
    }

    // TODO these are meant to be serialized
    private void initProtocols() {
        protocol.setHandler("game_running", (client) -> {
            try {
                client.getOutputStream().write(1);
                Player player = playerMap.get(client);
                Card card = player.getFromHand(client.getInputStream().read());

                while(!game.checkPlay(card)) {
                    System.out.println(player + "[room " + id + "]: invalid play " + card);
                    client.getOutputStream().write(0);
                    card = player.getFromHand(client.getInputStream().read());
                }

                game.play(player, card);
                System.out.println(player + "[room " + id + "]: played " + card);
            } catch(IOException e) {
                e.printStackTrace();
            }
        });
    }

    // TEMP
    private void printBoard() {
        System.out.print("server[room " + id + "]: [Board]:");
        game.getBoard().forEach((c) -> System.out.print(" " + c));
        System.out.println();
    }

    private void printHand(Player player) {
        System.out.print(player + "[room " + id + "]: [Hand]:");
        for(Card c : player.getHand())
            if(c != null)
                System.out.print(" " + c);
        System.out.println();
    }
}