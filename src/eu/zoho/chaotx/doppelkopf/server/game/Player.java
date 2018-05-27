package eu.zoho.chaotx.doppelkopf.server.game;

import eu.zoho.chaotx.doppelkopf.server.session.User;

public class Player{
    public static final int MAX_HAND = 48;

    private String name;
    private Card[] hand;
    private Card[] pile; // Ablagestapel

    public Player(String somename) {
        name = somename;
        hand = new Card[MAX_HAND];
        pile = new Card[MAX_HAND];
    }

    public Card[] getHand() {
        return hand;
    }

    public Card[] getPile() {
        return pile;
    }

    /**
     * @ Get card from player hand
     * 
     * @return card from hand or null if index is out of range
     */
    public Card getFromHand(int index) {
        try {
            return hand[index];
        } catch(IndexOutOfBoundsException e) {
            return null;
        }
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

    /**
     * @ Get card from player pile
     * 
     * @return card from pile or null if index is out of range
     */
    public Card getFromPile(int index) {
        try {
            return pile[index];
        } catch(IndexOutOfBoundsException e) {
            return null;
        }
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

    /* deprecated
    public Card getNextPlay() {
        return 43;//hand[user.request()];
    }
    //*/

    @Override
    public String toString() {
        return name;
    }
}