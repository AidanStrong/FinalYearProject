package cards;

import java.io.Serializable;
import java.util.Random;
import java.util.*;

public class Deck implements Iterable<Card> {

    private int numDecks = 1;

    Card[] theDeck;
    private Iterator<Card> deckIt = iterator();
    private double penetration;
    private ArrayList<Card> dealtCards;

    public Deck() {
        newDeck();
        theDeck = new Card[numDecks * 52];
    }

    public Deck(int numDecks) {
        this.numDecks = numDecks;
        theDeck = new Card[numDecks * 52];
        newDeck();
    }

    //getters
    public Card[] getTheDeck() {
        return theDeck;
    }

    public ArrayList<Card> getDealtCards(){
        return dealtCards;
    }

    public int getSize() {
        return theDeck.length;
    }

    //create the deck
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
    //return next card, used for dealing cards
    public Card deal(){
        return deckIt.next();
    }

    public double getPenetration(){
        return penetration;
    }

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
            penetration = (currentPointer*100)/(numDecks * 52);
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