package eu.zoho.chaotx.doppelkopf.game;

public class Player{
    private static final int MAX_HAND = 48;

    private int nextplay;
    private Card[] hand;

    public Player() {
        this(null);
    }

    public Player(Card[] inithand) {
        // TODO throw exception if inithand.length > MAX_HAND
        hand = inithand == null ? new Card[MAX_HAND] : inithand;
    }

    public Card[] getHand() {
        return hand;
    }

    public void addCard(Card newcard) {
        for(int i = 0; i < hand.length; ++i) {
            if(hand[i] == null) {
                hand[i] = newcard;
                return;
            }
        }
        
        // TODO throw exception if hand is full
    }

    public void setNextplay(int handindex) {
        // TODO throw exception if hand[handindex] == null
        // TODO throw exception if hand[handindex] unplayable
        nextplay = handindex;
    }

    public Card getNextplay() {
        Card nextcard = hand[nextplay];
        hand[nextplay] = null;
        return nextcard;
    }
}