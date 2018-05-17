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
        if(symbol!=DIMOND && (value==(symbol==HEART? value: TEN)||value==ACE || value==KING||value==NINE)) {
            
            rank =value.numval();
        }else{
            rank= symbol==HEARTS &&value ==TEN ? 16 : value.numval()+symbol.numval()+4;
        }
        points=value==NINE ? 0: value==JACK ? 2 : value==QUEEN ? 3 : value== KING ? 4 :value== TEN ? 10 : 11;
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