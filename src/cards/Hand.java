package cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hand {

    //attributes
    private ArrayList<Card> theHand;
    private int value;
    private int highValue;
    private int aceCount;

    //Constructors
    public Hand(){
        theHand = new ArrayList();
        value = 0;
        aceCount = 0;
    }

    public Hand(ArrayList<Card> dealtHand){
        this.theHand = dealtHand;
        calculateCardValue();
        calculateAceCount();
    }

    public void calculateCardValue(){
        int value = 0;
        for(Card eachCard : theHand){
            value = value + eachCard.getValue();
        }
        this.value = value;
    }

    public void calculateAceCount(){
        int aceCount = 0;
        for(Card eachCard : theHand){
            if(eachCard.getRank() == Card.Rank.ACE){
                aceCount++;
            }
        }
        this.aceCount = aceCount;
    }

    public void calculateValues(){
        calculateCardValue();
        calculateAceCount();
    }

    public boolean isOver21(){
        boolean isOver21 = false;
        if (value > 21){
            isOver21 = true;
        }
        return isOver21;
    }

    public boolean isBlackjack() {
        calculateValues();
        if(value == 21){
            return true;
        }
        return false;
    }

    //getters
    public int getSize() {return theHand.size();}

    public ArrayList<Card> getTheHand() {
        return theHand;
    }

    public int getValue(){
        return value;
    }

    public int getAceCount(){
        return aceCount;
    }

    public Card getFirstCard(){return theHand.get(0);}

    @Override
    public String toString() {
        return "Hand{" +
                "theHand=" + theHand +
                ", value=" + value +
                '}';
    }

    //setters
    public void addCard(Card newCard){
        theHand.add(newCard);
        calculateValues();
    }


    public static void main(String[] args) {
        ArrayList<Card> hand = new ArrayList();
        Deck deck = new Deck();
        hand.add(deck.deal());
        hand.add(deck.deal());
        Hand myHand = new Hand(hand);
        int value = myHand.getValue();
        System.out.println(value);
        System.out.println(myHand.isOver21());
    }
}
