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
    private boolean isSoft;
    //Constructors
    public Hand(){
        theHand = new ArrayList();
        value = 0;
        aceCount = 0;
        calculateValues();
    }

    public Hand(ArrayList<Card> dealtHand){
        this.theHand = dealtHand;
        calculateValues();
    }

    public void calculateCardValue(){
        int value = 0;
        for(Card eachCard : theHand){
            value = value + eachCard.getValue();
        }
        if (value > 21 && aceCount > 0){
            int count = 0;
            while(value > 21 && count != aceCount){
                value = value - 10;
                count += 1;
            }
            if(count == aceCount){
                isSoft = false;
            }
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
        if(aceCount > 0){
            isSoft = true;
        }
        this.aceCount = aceCount;
    }

    public void calculateValues(){
        calculateAceCount();
        calculateCardValue();
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
        if(value == 21 && this.getSize() == 2){
            return true;
        }
        return false;
    }

    public boolean hasAce(){
        if(getAceCount() > 0){
            return true;
        }
        return false;
    }

    public boolean isSplittable(){
        if(theHand.get(0).getValue() == theHand.get(1).getValue() && this.getSize() == 2){
            return true;
        }
        else{
            return false;
        }
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

    public Card getCardInPos(int pos){
        return theHand.get(pos);
    }

    public boolean isSoft(){
        return isSoft;
    }

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
        System.out.println(myHand.getFirstCard().toString());
        int value = myHand.getValue();
        System.out.println(value);
        System.out.println(myHand.isOver21());
    }
}
