package cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is a blueprint for the hand objects, including an array of card objects and value of hand
 */
public class Hand {

    private ArrayList<Card> theHand;
    private int value;
    private int highValue;
    private int aceCount;
    private boolean isSoft;
    //contructors

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

    /*
     * calculates value of hand, adjusting for aces if hand is bust
     */
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

    /**
     * calculates number of aces in hand
     */
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

    /**
     * calculates number of aces and hand value
     */
    public void calculateValues(){
        calculateAceCount();
        calculateCardValue();
    }

    /**
     *
     * @return - a boolean representing if the hand 's value is over 21 or not
     */
    public boolean isOver21(){
        calculateValues();
        boolean isOver21 = false;
        if (value > 21){
            isOver21 = true;
        }
        return isOver21;
    }

    /**
     *
     * @return - a boolean representing if the hand is a blackjack or not
     */
    public boolean isBlackjack() {
        calculateValues();
        if(value == 21 && this.getSize() == 2){
            return true;
        }
        return false;
    }

    /**
     *
     * @return - a boolean representing if the hand contains an ace or not
     */
    public boolean hasAce(){
        if(getAceCount() > 0){
            return true;
        }
        return false;
    }

    /**
     *
     * @return - a boolean representing if the hand can be split or not
     */
    public boolean isSplittable(){
        if(theHand.get(0).getValue() == theHand.get(1).getValue() && this.getSize() == 2){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     *
     * @return - the number of cards in the hand
     */
    public int getSize() {return theHand.size();}

    /**
     *
     * @return - the ArrayList holding the cards
     */
    public ArrayList<Card> getTheHand() {
        return theHand;
    }

    /**
     *
     * @return - the value of the cards in the hand
     */
    public int getValue(){
        calculateValues();
        return value;
    }

    /**
     *
     * @return - the number of aces in the hand
     */
    public int getAceCount(){
        calculateValues();
        return aceCount;
    }

    /**
     *
     * @return - the first card in the hand
     */
    public Card getFirstCard(){return theHand.get(0);}

    /**
     *
     * @param pos the index of the card in the hand to be returned
     * @return - A Card object in position pos
     */
    public Card getCardInPos(int pos){
        return theHand.get(pos);
    }

    /**
     *
     * @return - a boolean determining if the hand is soft or not
     */
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

    /**
     *
     * @param newCard Card object to add to the hand
     */
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
