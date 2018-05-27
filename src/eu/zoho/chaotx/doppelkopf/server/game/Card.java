package eu.zoho.chaotx.doppelkopf.server.game;

// TODO
public class Card {
    private static int _counter = 0;
    private int id;

    public enum Symbol {
        DIAMOND(0), HEART(1), CLUB(2), SPADE(3);
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
    private boolean is_trump;
    private Symbol symbol;
    private Value value;

    public Card (Symbol symbol, Value value){
        id = _counter++;
        this.symbol = symbol;
        this.value = value;

        if(symbol != Symbol.DIAMOND && value != (symbol == Symbol.HEART ? Value.TEN : null) && value != Value.QUEEN && value != Value.JACK) {
            rank = value.numval();
            is_trump = false;
        } else {
            rank = symbol == Symbol.HEART && value == Value.TEN ? 16 : value.numval()+symbol.numval()+4;
            is_trump = true;
        }

        points = value == Value.NINE ? 0
            : value == Value.JACK ? 2
            : value == Value.QUEEN ? 3
            : value == Value.KING ? 4
            : value == Value.TEN ? 10 : 11;
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

    public int getId() {
        return id;
    }

    /**
     * @brief returns wether the card is a trump card or not
     * 
     * @return boolean - true if card is a trump, false otherwise
     */
    public boolean isTrump() {
        return is_trump;
    }

    @Override
    public String toString() {
        return "["
            + (symbol == Symbol.DIAMOND ? "D"
            : symbol == Symbol.HEART ? "H"
            : symbol == Symbol.CLUB ? "C" : "S")
            + (value == Value.NINE ? "09"
            : value == Value.TEN ? "10"
            : value == Value.JACK ? "_J"
            : value == Value.QUEEN ? "_Q"
            : value == Value.KING ? "_K" : "_A")
        + "]";
    }
}