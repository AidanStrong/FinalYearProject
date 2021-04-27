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
            remainingDecks = ( 1 - shoe.getPenetration()) * numDecks;
        }
        else{
            remainingDecks = 1;
        }
        double TrueCount = runningCount / remainingDecks;

        // >2.00 -- 1(minimum)
        // 2.00-1.75 -- 2
        // 1.75-1.65 -- 4
        // below 1.65 -- 5
        if(TrueCount < 1.65){
            betUnits = 5;
        }
        else if(TrueCount >= 1.65 && TrueCount < 1.75){
            betUnits = 4;
        }
        else if(TrueCount >= 1.75 && TrueCount < 2.00){
            betUnits = 2;
        }
        else if(TrueCount >= 2.00){
            betUnits = 1;
        }

        return betUnits;
    }

    @Override
    public void resetCount() {

    }
}
