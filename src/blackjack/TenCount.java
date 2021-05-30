package blackjack;

import cards.Card;
import cards.Deck;

import java.util.ArrayList;

/**
 * A class that implements CardCount and uses ten count, a card counting method based on counting number of tens
 */
public class TenCount implements CardCount{

    int numDecks;

    /**
     *
     * @param numDecks the number of decks in the shoe
     */
    public TenCount(int numDecks){
        this.numDecks = numDecks;
    }

    /**
     *
     * @param dealtCards an arrayList of card objects that are the cards dealt since last reshuffle
     * @param shoe the shoe/deck for the game as a Deck object
     * @return
     */
    @Override
    public int countCards(ArrayList<Card> dealtCards, Deck shoe){
        int betUnits = 0;
        double tenCount = 16 * numDecks;
        double otherCount = 36 * numDecks;
        for(Card c : dealtCards){
            if(c.getValue() == 10){
                tenCount -= 1;
            }else{
                otherCount -= 1;
            }
        }
        double ratio = otherCount / tenCount;
        //Source: Thorp 1962 - Beat the Dealer - pg 106
        // ratio -- bet (in units)
        // >2.00 -- 1(minimum)
        // 2.00-1.75 -- 2
        // 1.75-1.65 -- 4
        // below 1.65 -- 5
        // --
        // ratio -- % advantage -- bet units
        // 3.00 -- -2% -- 1
        // 2.00 -- 1% -- 2
        // 1.75 -- 2% -- 4
        // 1.63 -- 3% -- ~5
        // 1.50 -- 4%

        // where the ratio is between 2 and 1.65, bet twice as much,
        // in units, as our advantage is in %
        if(ratio < 1.65){
            betUnits = 5;
        }
        else if(ratio >= 1.65 && ratio < 1.75){
            betUnits = 4;
        }
        else if(ratio >= 1.75 && ratio < 2.00){
            betUnits = 2;
        }
        else if(ratio >= 2.00){
            betUnits = 1;
        }
        System.out.println("BET UNITS " + betUnits);
        return betUnits;
    }


    //test harness
    public static void main(String[] args) {
        int numDecks = 1;
        CardCount countStrat = new TenCount(numDecks);
        HumanPlayer myPlayer = new HumanPlayer();

    }
}
