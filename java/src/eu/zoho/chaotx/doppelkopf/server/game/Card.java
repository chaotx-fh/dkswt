package eu.zoho.chaotx.doppelkopf.server.game;

// TODO
public class Card {
    
    private int rating,points;
;

    public enum Symbol{
        HEART = 1, DIAMOND = 0, SPADE = 2,CLUB = 3
    }//HEART = Herz, DIAMOND = Karo, SPADE = Pik ,CLUB = Kreuz
    
    public enum Value{
        NINE = 0, KING = 1 ,TEN = 2,ACE = 3,JACK = 4,QUEEN = ()
    }

    public Card(Symbol s, Value v) {
        if(s = TEN && v = HEART)
            rating = 20;
        
        if(s = CROSS || CLUB || HEART){
           rating = s + v;
        }else
            rating = v + s + 4;
    }
}