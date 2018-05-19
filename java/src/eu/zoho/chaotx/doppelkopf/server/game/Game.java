package eu.zoho.chaotx.doppelkopf.server.game;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Stack;
import java.util.Arrays;


public class Game implements Runnable {
    private Player[] player; // 1d arrays sind schneller und flexibler -> zwischen den teams befindet sich ein null-Element
    private Card[] cards;
    //private Board board; // brauchen wa echt ne eigene Klasse fürs board?

    private Stack<Card> board;
    private int[] table; // enthält Player indexe sortiert nach Sitzordnung
    private int nextplayer; // zeiger welcher auf den aktuellen Spieler im table-array zeigt
    private boolean running;

    public Game(Player[] someplayer, Card[] somecards) {
        player = someplayer;
        cards = somecards;
        //board = someboard;
        board = new Stack<>();
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
     *  - table-array mit den player-indexen in zufälliger Reihenfolge (z.B. {0, 3, 1, 2})
     *  - alle Karten wurden zufällig aber gleichmäßig an alle Spieler ausgeteilt
     */
    private void init() {

    }

    private void validate(){     
        int count = 0;

        for(int i = 0; i < Player.MAX_HAND; i++ ){
            if((board.get(0).getSymbol == Symbol.HEART && board.get(0).getValue == Value.TEN) ? return : (board.get(0).getSymbol() == Value.ACE || Value.KING || Value.TEN)) { //Fehl
                if(player.hand[i].getSymbol == board.get(0).getSymbol)
                    valid_plays[i] = true;
                    return;
            }else if(player.hand[i].getSymbol == board.get(0).getSymbol){ //Trumpf
                    valid_plays[i] = true;
            }else
                count++;
        }

        if(count == Player.MAX_HAND){
            Arrays.fill(valid_plays,true);
        }

        return valid_plays[player[nextplayer].getNextPlay];
        
    }

    /**
     * TODO
     * so wie die Dinge gerade stehen befindet sich an index 0 die Karte die gespielt
     * werden soll und an index 1 die oberste Karte auf dem board (Reihenfolge und
     * Anzahl sollten jedoch egal sein)
     * 
     * @return boolean - true if play is valid, false otherwise
     */
    private boolean checkPlay() {
        /**
        *Schritt 1: Symbol der erst gespielten Karte merken
        *Schritt 2: Deck des zuspielenden Spielers überprüfen ob er dieses Symbol hat (Wenn ja spiel Sie, wenn nein Spiel beliebige Karte)
        */

        return valid_plays[player[nextplayer].getNextPlay];
        
        
        if(player.getNextPlay.getSymbol == board.get(0).getSymbol)
                return true;
            else
                return false;
    }

    /**
     * TODO
     * Ziel: Spieler hat Karte auf's Board abgelegt, je nach Situation (und Regelwerk)
     * sind dementsprechend anzuwendende Aktionen durchgeführt worden (sprich Player.addToPile(Card))
     */
    private void play(Player player, Card card) {
        board.push(player.removeCard(card));

        // TODO

        // wir könnten das cards-array nutzen um zu bestimmen ob alle karten ausgespielt wurden
        for(int i = 0; i < cards.length; ++i) {
            if(cards[i] == card) {
                cards[i] = null;
                break;
            }
        }

        if(cards.length == 0)
            running = false;
    }

    private void doTurn() {
        Player current_player = player[table[nextplayer]];
        Card play = current_player.getNextPlay(); // blockiert -> wartet auf Antwort vom Client -> yield (TODO!)

        if(checkPlay(new Card[]{play, board.peek()})) {
            play(current_player, play);
            nextplayer = (nextplayer+1)%table.length;
        } else {
            // sende Fehlermeldung an Client
        }
    }

    @Override
    public void run() {
        init();
        running = true;

        while(running)
            doTurn();

        System.out.println("Game finished");
    }
}