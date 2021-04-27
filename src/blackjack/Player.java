package blackjack;

import cards.Card;
import cards.Hand;

public interface Player {

    //give player a hand
    void giveHand(Hand h);

    //deal a card to the player's hand
    void dealCard(Card c);

    //return hand
    Hand getHand();

}
