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
    private Symbol symbol;
    private Value value;

    public Card (Symbol symbol, Value value){
        this.symbol=symbol;
        this.value=value;
        if(symbol != Symbol.DIAMOND && (value == (symbol == Symbol.HEART ? value : Value.TEN) || value == Value.ACE || value == Value.KING || value == Value.NINE)) {
            rank = value.numval();
        } else {
            rank = symbol == Symbol.HEART && value == Value.TEN ? 16 : value.numval()+symbol.numval()+4;
        }

        points = value == Value.NINE ? 0 : value == Value.JACK ? 2 : value == Value.QUEEN ? 3 : value == Value.KING ? 4 : value == Value.TEN ? 10 : 11;
    }

    public Symbol getSymbol(){
        return symbol;
    }

    public Value getValue(){
        return value;
    }
    
    public int getRank(){
        return rank;
    }
    
    public int getPoints(){
        return points;
    }

}