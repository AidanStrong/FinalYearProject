package blackjack;

import cards.Card;
import cards.Deck;

import java.util.ArrayList;

public class HiLoCount implements CardCount{

    private int numDecks = 1;

    public HiLoCount(int numDecks){
        this.numDecks = numDecks;
    }

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
            remainingDecks = shoe.getPenetration() * numDecks;
            System.out.println("remaining decks: " + remainingDecks);
        }
        else{
            remainingDecks = 1;
        }
        double trueCount = runningCount / remainingDecks;

        // >2.00 -- 1(minimum)
        // 2.00-1.75 -- 2
        // 1.75-1.65 -- 4
        // below 1.65 -- 5
        System.out.println("ratio: " + trueCount);
//        if(TrueCount < 1.65){
//            betUnits = 5;
//        }
//        else if(TrueCount >= 1.65 && TrueCount < 1.75){
//            betUnits = 4;
//        }
//        else if(TrueCount >= 1.75 && TrueCount < 2.00){
//            betUnits = 2;
//        }
//        else if(TrueCount >= 2.00){
//            betUnits = 1;
//        }

        // each count represents an ~ increase in advantage of 0.5%
        // according to thorpe:
         // ratio -- % advantage -- bet units
        // 3.00 -- -2% -- 1
        // 2.00 -- 1% -- 2
        // 1.75 -- 2% -- 4
        // 1.63 -- 3% -- ~5
        // 1.50 -- 4%
        double approximateAdvantage = -0.5 + (trueCount * 0.5);
        System.out.println("advantage " + approximateAdvantage);
//        if(trueCount <= 0.5){
//            //advantage is negative, bet minimum
//            betUnits = 1;
//        }
//        else if(approximateAdvantage > 1 && approximateAdvantage < 2){
//            betUnits = 2;
//        }
//        else if(approximateAdvantage >= 2 && approximateAdvantage < 3){
//            betUnits = 4;
//        }
//        else if(approximateAdvantage >= 3 && approximateAdvantage < 4){
//            betUnits = 5;
//        }
//        else if(approximateAdvantage >= 4 && approximateAdvantage < 5){
//            betUnits = 6;
//        }
//        else if(approximateAdvantage >= 5){
//            betUnits = 7;
//        }

        if(trueCount < 1){
            //advantage is negative, bet minimum
            betUnits = 1;
        }
        else if(trueCount >= 1 && trueCount < 2){
            betUnits = 2;
        }
        else if(trueCount >= 2 && trueCount < 3){
            betUnits = 4;
        }
        else if(trueCount >= 3 && trueCount < 4){
            betUnits = 6;
        }
        else if(trueCount >= 4 && trueCount < 5){
            betUnits = 8;
        }
        else if(trueCount >= 5){
            betUnits = 10;
        }
        System.out.println("BET UNITS " + betUnits);
        return betUnits;
    }

    @Override
    public void resetCount() {

    }
}
