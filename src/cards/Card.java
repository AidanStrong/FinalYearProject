
package cards;
//AIDAN Strong
import java.util.Random;
import java.util.*;

/**
 * This class is a blueprint for card objects, including rank, suit and value
 */
public class Card {

    private Rank cardRank;
    private Suit cardSuit;

    //constructors
    public Card(){

    }

    /**
     * @param cardRank enum rank of card
     * @param cardSuit enum suit of card
     */
    public Card(Rank cardRank, Suit cardSuit){
        this.cardRank = cardRank;
        this.cardSuit = cardSuit;
    }

    /**
     * Enum to hold rank and value of the card
     */
    public enum Rank{
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9),
        TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);

        int value;
        Rank(int x){
            value = x;
        }

        public int getValue(){
            int currentValue = this.value;
            return currentValue;
        }
    }

    /**
     * Enum to hold suit of the card
     */
    public enum Suit{
        CLUBS(1), DIAMONDS(2), HEARTS(3), SPADES(4);

        int value;
        Suit(int x){
            value = x;
        }
    }

    //Accessor methods
    public Rank getRank(){
        return cardRank;
    }

    public Suit getSuit(){
        return cardSuit;
    }

    /**
     *
     * @return - returns face vlue of the card
     */
    public int getValue(){ return cardRank.getValue(); }


    @Override
    public String toString(){
        return "Rank: " + cardRank + " Suit: " + cardSuit;
    }

    public static void main(String[] args){
        // make new card
        Rank testRank = Rank.ACE;
        Suit testSuit = Suit.SPADES;
        Card aceOfSpades =  new Card(testRank, testSuit);

        //toString
        System.out.println("TestCard: " + aceOfSpades.toString());
    }
}