package cards;

import java.io.Serializable;
import java.util.Random;
import java.util.*;

/**
 * This class describes a deck/shoe, including an array of cards, array of dealt cards, penetration, an iterator and a reshuffle
 */
public class Deck implements Iterable<Card> {

    private int numDecks = 1;

    private Card[] theDeck;
    private Iterator<Card> deckIt = iterator();
    private double penetration;
    private ArrayList<Card> dealtCards;

    //constructors
    public Deck() {
        newDeck();
        theDeck = new Card[numDecks * 52];
    }

    /**
     * @param numDecks the number of decks to be in the shoe
     */
    public Deck(int numDecks) {
        this.numDecks = numDecks;
        theDeck = new Card[numDecks * 52];
        newDeck();
    }

    /**
     *
     * @return - returns the deck/shoe, an array of card objects
     */
    public Card[] getTheDeck() {
        return theDeck;
    }

    /**
     *
     * @return - ArrayList of Card objects already dealt since last reshuffle
     */
    public ArrayList<Card> getDealtCards(){
        return dealtCards;
    }

    /**
     *
     * @return - number of cards in the shoe
     */
    public int getSize() {
        return theDeck.length;
    }

    /**
     * creates a new deck/shoe and shuffles
     */
    public void newDeck() {
        int n = 0;

        for(int i = 0; i < numDecks; i++) {
            for (Card.Suit suit : Card.Suit.values()) {
                for (Card.Rank rank : Card.Rank.values()) {
                    Card card = new Card(rank, suit);
                    theDeck[n] = card;
                    n++;
                }
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
        dealtCards = new ArrayList();
    }

    /**
     *
     * @return - the neck Card in the deck iterator
     */
    public Card deal(){
        return deckIt.next();
    }

    /**
     *
     * @return - a double reprenting shoe penetration, the proportion of cards already dealt since last reshuffle
     */
    public double getPenetration(){
        return penetration;
    }

    /**
     * reshuffles the deck/shoe
     */
    public void reshuffleShoe(){
        newDeck();
        deckIt = iterator();
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
            penetration = (currentPointer)/(double)(numDecks * 52);
            dealtCards.add(nextCard);
            //System.out.println("dealing " + nextCard.toString());
            return nextCard;
        }
    }

    @Override
    public String toString(){
        String returnString = "\n";
        for(int i = 0; i < theDeck.length; i++){
            System.out.println(i);
            returnString = returnString + theDeck[i].toString() + "\n";
        }
        return returnString;
    }

    //test harness
    public static void main(String[] args) {
        Deck myDeck = new Deck(2);
        for(int i = 0; i < 75; i++){
            System.out.println(i);
            if(myDeck.getPenetration() > 50){
                myDeck.reshuffleShoe();
            }
            myDeck.deal();
        }
        System.out.println(myDeck.getPenetration());
        System.out.println(myDeck.theDeck.length);
        System.out.println(myDeck.toString());
    }
}