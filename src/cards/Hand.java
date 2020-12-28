package cards;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    //attributes
    private Card[] theHand = new Card[2];
    private List<Card> cardsInHand;
    private List<Integer> values;

    //Constructors
    public Hand(){
        cardsInHand = new ArrayList();
        values = new ArrayList();
        values.add(0);
    }

    public Hand(Card[] dealtHand){
        this.theHand = dealtHand;
    }

    //getters
    public int getSize() {
        return theHand.length;
    }

    public Card[] getTheHand() {
        return theHand;
    }

    //setters
    public void addCard(Card newCard){
        cardsInHand.add(newCard);
        
    }

    //addCard

    //getValue

    //isOver21


}
