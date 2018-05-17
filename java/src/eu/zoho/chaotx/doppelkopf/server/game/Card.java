package eu.zoho.chaotx.doppelkopf.server.game;

// TODO
public class Card {
    public enum Symbol {
        DIAMOND(0), HEART(1), SPADE(2), CLUB(3);
        private int value;

        Symbol(int someval) {
            value = someval;
        }

        public int numval() {
            return value;
        }
    };

    public enum Value {
        NINE(0), KING(1), TEN(2), ACE(3), JACK(4), QUEEN(8);
        private int value;

        Value(int someval) {
            value = someval;
        }

        public int numval() {
            return value;
        }
    };

    private int rank, points;

    public Card(Symbol symbol, Value value) {
        if(symbol ==  Symbol.HEART && value == Value.TEN)
            rank = 16;
        
        if(symbol == Symbol.SPADE || symbol == Symbol.CLUB || symbol == Symbol.HEART)
            rank = symbol.numval() + value.numval();
        else
            rank = symbol.numval() + value.numval() + 4;
    }
}