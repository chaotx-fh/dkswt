package eu.zoho.chaotx.doppelkopf.server.game;

import eu.zoho.chaotx.doppelkopf.server.session.User;

public class Player{
    public static final int MAX_HAND = 48;

    private int nextplay;
    private User user;
    private Card[] hand;
    private Card[] pile; // Ablagestapel

    public Player(User someuser) {
        user = someuser;
        hand = new Card[MAX_HAND];
        pile = new Card[MAX_HAND];
    }

    public Card[] getHand() {
        return hand;
    }

    public Card[] getPile() {
        return pile;
    }

    public void addToHand(Card newcard) {
        for(int i = 0; i < hand.length; ++i) {
            if(hand[i] == null) {
                hand[i] = newcard;
                return;
            }
        }
        
        // TODO throw exception if hand is full
    }

    public void addToPile(Card somecard) {
        // TODO catch and hanlde ArrayOutOfBounds (something went really wrong)
        pile[pile.length] = somecard;
    }

    /**
     * @brief Removes card from hand and returns it
     * 
     * @param somecard - Card to remove
     * @return the removed card or null if somecard was not in hand
     */
    public Card removeCard(Card somecard) {
        for(int i = 0; i < hand.length; ++i) {
            if(somecard == hand[i]) {
                hand[i] = null;
                return somecard;
            }
        }

        return null;
    }

    public Card getNextPlay() {
        return hand[user.request()];
    }
}