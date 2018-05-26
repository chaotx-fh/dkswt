package eu.zoho.chaotx.doppelkopf.server.game;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;


public class Game {
    private Player[] player; // zwischen den teams befindet sich ein null-Element
    private Card[] cards;
    private Stack<Card> board;

    private int[] table; // enthält Player indexe sortiert nach Sitzordnung
    private int nextplayer; // zeiger welcher auf den aktuellen Spieler im table-array zeigt

    public Game(Player[] someplayer, Card[] somecards) {
        player = someplayer;
        cards = somecards;
        table = new int [] {-1,-1,-1,-1};
        board = new Stack<>();

    }

    public Stack<Card> getBoard() {
        return board;
    }

    /**
     * Schritte:
     *  1. Karten werden (gemischt) verteilt (Player.addToHand(Card)) (aus Card array entfernen?)
     *  2. Player array wird umsortiert (team1 an index 0 und ggf. 1)
     *  3. Der erste Spieler wird bestimmt (siehe Regeln) -> zufall
     *      -> seinen index im player-array zwischenspeichern
     *      -> nextplayer = 0 setzen
     *  4. Sitzfolge wird festgelegt (nicht die Reihenfolge im player-array!)
     *      -> folgende Idee: der index des ersten Spielers landet im table an Stelle 0,
     *         die indexe der anderen Spieler werden daraufhin in zufälliger Reihenfolge dem table-array hinzugefügt
     * 
     * Ziel:
     *  - player-array mit den Spielern aus team1 an index 0 (und ggf. 1) gefolgt von null-Element und team2
     *  - table-array mit den player-indexen in zufälliger Reihenfolge (z.B. {0, 3, 1, 4})
     *  - alle Karten wurden zufällig aber gleichmäßig an alle Spieler ausgeteilt
     */
    public void init() {
        Player[] teamplayer = new Player[player.length+1];
        Random rng = new Random(System.nanoTime());     

        List<Integer> playerlist = new LinkedList<>();
        List<Card> cardlist = new LinkedList<>();

        for(int i = 0 ; i < player.length; ++i)
            playerlist.add(new Integer(i));

        for(Card c : cards)
            cardlist.add(c);

        for(int i = 0; i < player.length; ++i) {
            table[i] = playerlist.remove(rng.nextInt(playerlist.size())).intValue();

            // Karten austeilen
            boolean is_re = false; // Kreuzdame -> Re-partei
            for(int j = 0; j < cards.length/player.length; ++j) {
                Card card = cardlist.remove(rng.nextInt(cardlist.size()));
                player[table[i]].addToHand(card);

                if(!is_re)
                    is_re = card.getSymbol() == Card.Symbol.CLUB && card.getValue() == Card.Value.QUEEN;
            }

            // team verteilung -> r, r, null, k, k bzw. r, null, k, k, k
            int l = is_re ? 0 : (teamplayer.length-1);
            while(teamplayer[l] != null) l += is_re ? 1 : -1;
            teamplayer[l] = player[table[i]];
            table[i] = l; // "Spieler setzt sich an den Tisch"
        }

        player = teamplayer;
    }

    /**
     * TODO
     * so wie die Dinge gerade stehen befindet sich an index 0 die Karte die gespielt
     * werden soll und an index 1 die oberste Karte auf dem board (Reihenfolge und
     * Anzahl sollten jedoch egal sein)
     * 
     * @return boolean - true if play is valid, false otherwise
     */
    public boolean checkPlay(Card[] somecards) {
        return true;
    }

    public boolean checkPlay(Card somecard) {
        return somecard != null;
    }

    /**
     * TODO
     * Ziel: Spieler hat Karte auf's Board abgelegt, je nach Situation (und Regelwerk)
     * sind dementsprechend anzuwendende Aktionen durchgeführt worden (sprich Player.addToPile(Card))
     */
    public void play(Player curplayer, Card card) {
        board.push(curplayer.removeCard(card));

        if(board.size() == player.length-1) {
            // TODO // sieger ermitteln // karten zuweisen
            // ggf. true/false zurückgeben (so können wir von ausserhalb ermitteln ob ne runde beendet wurde)
        }

        nextplayer = (nextplayer+1)%table.length;
        // return false/true
    }

    public Player getNextPlayer() {
        return player[table[nextplayer]];
    }
}