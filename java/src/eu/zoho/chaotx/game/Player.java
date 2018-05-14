// TODO
public class Player{

    private Card nextPlay;
    private Hand hand;

    public Player(Hand somehand){
        if(h != null)
            this.hand = somehand;
        else
            hand  = new Hand();    
    }

    public Card getNextPlay(){
        return nextPlay;
    }
}