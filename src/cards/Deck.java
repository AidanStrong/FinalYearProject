package cards;

import java.io.Serializable;
import java.util.Random;
import java.util.*;

public class Deck implements Iterable<Card> {

    Card[] theDeck = new Card[52];
    private Iterator<Card> deckIt = iterator();

    public Deck() {
        newDeck();
    }

    //getters
    public Card[] getTheDeck() {
        return theDeck;
    }

    public int getSize() {
        return theDeck.length;
    }

    //create the deck
    public void newDeck() {
        int n = 0;
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                Card card = new Card(rank, suit);
                theDeck[n] = card;
                n++;
            }
        }

        //shuffle deck - Fisher–Yates shuffle
        Random rand = new Random();
        for (int i = theDeck.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            Card tempCard = theDeck[index];
            theDeck[index] = theDeck[i];
            theDeck[i] = tempCard;
        }
    }

    //return next card, used for dealing cards
    public Card deal(){
        return deckIt.next();
    }

    //iterator
    @Override
    public Iterator<Card> iterator() {
        return new deckIterator();
    }

    public class deckIterator implements Iterator<Card> {
        int currentPointer = 0;

        @Override
        public boolean hasNext() {
            if (theDeck[currentPointer] != null){
                return true;
            }
            return false;
        }

        @Override
        public Card next() {
            Card nextCard;
            nextCard = theDeck[currentPointer];
            currentPointer += 1;
            System.out.println("dealing " + nextCard.toString());
            return nextCard;
        }
    }

    @Override
    public String toString(){
        String returnString = "\n";
        for(int i = 0; i < theDeck.length; i++){
            returnString = returnString + theDeck[i].toString() + "\n";
        }
        return returnString;
    }

    //test harness
    public static void main(String[] args) {
        Deck myDeck = new Deck();
        System.out.println(myDeck.theDeck[2]);
    }
}