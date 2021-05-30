package blackjack;

import cards.Card;
import cards.Deck;

import java.util.ArrayList;

/**
 * A class that implements CardCount and uses HiLo, a card counting method based on incrementing
 * and decrementing a counter by one depending on the cards dealt, then calculating a true count and betting accordingly
 */
public class HiLoCount implements CardCount{

    private int numDecks = 1;

    /**
     *
     * @param numDecks the number of decks in the shoe
     */
    public HiLoCount(int numDecks){
        this.numDecks = numDecks;
    }

    /**
     *
     * @param dealtCards an arrayList of card objects that are the cards dealt since last reshuffle
     * @param shoe the shoe/deck for the game as a Deck object
     * @return
     */
    @Override
    public int countCards(ArrayList<Card> dealtCards, Deck shoe) {
        int runningCount = 0;
        int betUnits = 1;
        for(Card c : dealtCards){
            if(c.getValue() >= 2 && c.getValue() < 7){
                runningCount += 1;
            }
            if(c.getValue() >= 10){
                runningCount -= 1;
            }
        }

        double remainingDecks;
        if(numDecks != 1){
            remainingDecks = (1 - shoe.getPenetration()) * numDecks;
            remainingDecks = Math.round(remainingDecks);

        }
        else{
            remainingDecks = 1;
        }
        double trueCount = runningCount / remainingDecks;
        System.out.println("trueCount: " + trueCount);




        // each count represents an ~ increase in advantage of 0.5% -> my workings from thorp's paper
        // according to thorp:
         // T/O ratio -- % advantage -- bet units
        // 3.00 -- -2% -- 1
        // 2.00 -- 1% -- 2
        // 1.75 -- 2% -- 4
        // 1.63 -- 3% -- ~5
        // 1.50 -- 4%
        double approximateAdvantage = trueCount * 0.5;
        System.out.println("advantage " + approximateAdvantage);
//        if(approximateAdvantage > 3){
//            betUnits = 5;
//        }
//        else if(approximateAdvantage <= 3 && approximateAdvantage > 2){
//            betUnits = 4;
//        }
//        else if(approximateAdvantage <= 2 && approximateAdvantage > 1){
//            betUnits = 2;
//        }
//        else if(approximateAdvantage <= 1){
//            betUnits = 1;
//
//        }
        if(trueCount >= 6){
            betUnits = 5;
        }
        else if(trueCount < 2){
            betUnits = 1;

        }else{
            betUnits = (int)(Math.round(trueCount));
        }
//        if(trueCount < 1){
//            //advantage is negative, bet minimum
//            betUnits = 1;
//        }
//        else if(trueCount >= 1 && trueCount < 2){
//            betUnits = 2;
//        }
//        else if(trueCount >= 2 && trueCount < 3){
//            betUnits = 4;
//        }
//        else if(trueCount >= 3){
//            betUnits = 6;
//
//        }
        System.out.println("BET UNITS " + betUnits);
        return betUnits;
    }

}

//        else if(trueCount < 6 && trueCount >= 4){
//        betUnits = 4;
//        }
//        else if(trueCount < 4 && trueCount >= 2){
//        betUnits = 2;
//        }


// ### option to divide by all remaining cards to find a true count index ###
//        double trueCount = (double)runningCount / (((numDecks*52) - dealtCards.size()));
//        trueCount = Math.round(trueCount * 100);
//        System.out.println("trueCount: " + trueCount);
//        if(trueCount <= 2){
//            betUnits = 1;
//        }else{
//            betUnits = (int)Math.round(trueCount/2);
//        }