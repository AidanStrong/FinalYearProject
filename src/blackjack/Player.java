package blackjack;

import cards.Card;
import cards.Hand;

public interface Player {


    //give player a hand
    void dealHand(Hand h);

    //deal a card to the player's hand
    void dealCard(Card c);

    //return hand
    Hand getHand();

    //set the strategy that the player will use
    void setStrategy(CardCount c);


}
