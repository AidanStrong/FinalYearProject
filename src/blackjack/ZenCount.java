package blackjack;

import cards.Card;
import cards.Deck;

import java.util.ArrayList;

/**
 * A class that implements CardCount and uses zen count, a card counting method based on increasing
 * or decreasing a counter by -2, -1, 1, or 2 depending onc ards dealt.
 * This method then attempts to work out the true edge from the count and bets accordingly
 */
public class ZenCount implements CardCount {
    private int numDecks = 1;

    /**
     *
     * @param numDecks the number of decks in the shoe
     */
    public ZenCount(int numDecks){
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
            if(c.getValue() == 2 || c.getValue() == 3 || c.getValue() == 7){
                runningCount += 1;
            }
            if(c.getValue() == 4 || c.getValue() == 5 || c.getValue() == 6){
                runningCount += 2;
            }
            if(c.getRank() == Card.Rank.ACE){
                runningCount =- 1;
            }
            if(c.getValue() == 10){
                runningCount =- 2;
            }
        }

        //divide by remaining deck quarters (13 cards)
        double remainingDecks;
        int cardsUsed = dealtCards.size();
        int remainingCards = (numDecks * 52) - cardsUsed;
        int reaminingQuarters = Math.round(remainingCards / 13);
      //  System.out.println("running count: " + runningCount + " ### quarters: " + reaminingQuarters);
        double trueEdge = (double)runningCount / (double)reaminingQuarters;
       // System.out.println("zen trueCount: " + trueEdge);

        // according to thorpe:
        // ratio -- % advantage -- bet units
        // 3.00 -- -2% -- 1
        // 2.00 -- 1% -- 2
        // 1.75 -- 2% -- 4
        // 1.63 -- 3% -- ~5
        // 1.50 -- 4%

//        if(trueEdge <= 0){
//            //advantage is negative, bet minimum
//            betUnits = 1;
//        }
//        else if(trueEdge > 0 && trueEdge <= 2){
//            betUnits = 2;
//        }
//        else if(trueEdge > 2 && trueEdge <= 4){
//            betUnits = 4;
//        }
//        else if(trueEdge > 4 && trueEdge <= 6){
//            betUnits = 5;
//        }
//        else if(trueEdge > 6){
//            betUnits = 5;
//
//        }

        if(trueEdge <= 0.5){
            betUnits = 0;
        }else{
            betUnits = (int)Math.round(trueEdge / 0.5);
        }
        if(betUnits == 0){
            betUnits = 1;
        }
     //   System.out.println("BET UNITS " + betUnits);
        return betUnits;
        // Divide bankroll by 400 to determine kelly unit,
        // bet one kelly unit for every 1/2% advantage


    }


}
