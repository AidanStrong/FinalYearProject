
package cards;
//AIDAN
import java.util.Random;
import java.util.*;

public class Card {

    //Attributes
    private Rank cardRank;
    private Suit cardSuit;

    //Constructor
    public Card(){

    }
    public Card(Rank cardRank, Suit cardSuit){
        this.cardRank = cardRank;
        this.cardSuit = cardSuit;
    }

    //Enums
    public enum Rank{
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9),
        TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);

        int value;
        Rank(int x){
            value = x;
        }

        public Rank getNext(){
            if (this.ordinal() == 12){
                return Rank.values()[0];
            }
            return this.ordinal() < Rank.values().length - 1
                    ? Rank.values()[this.ordinal() + 1] : null;
        }

        public int getValue(){
            int currentValue = this.value;
            return currentValue;
        }
    }

    public enum Suit{
        CLUBS(1), DIAMONDS(2), HEARTS(3), SPADES(4);

        int value;
        Suit(int x){
            value = x;
        }

        public static Suit randomSuit(){
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }

        public Suit getNext(){
            if (this.ordinal() == 3){
                return Suit.values()[0];
            }
            return this.ordinal() < Suit.values().length - 1
                    ? Suit.values()[this.ordinal() + 1] : null;
        }
    }

    //Accessor methods
    public Rank getRank(){
        return cardRank;
    }

    public Suit getSuit(){
        return cardSuit;
    }

    //toString
    @Override
    public String toString(){
        return "Rank: " + cardRank + " Suit: " + cardSuit;
    }

    //test harness
    public static void main(String[] args){
        // make new card
        Rank testRank = Rank.ACE;
        Suit testSuit = Suit.SPADES;
        Card aceOfSpades =  new Card(testRank, testSuit);

        //toString
        System.out.println("TestCard: " + aceOfSpades.toString());

    }
}