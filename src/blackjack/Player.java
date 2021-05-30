package blackjack;

import cards.Card;
import cards.Hand;

public interface Player {

    /**
     *
     * @param h a Hand object to deal to the player
     */
    void giveHand(Hand h);

    /**
     *
     * @param c - a Card object to deal to the player
     */
    void dealCard(Card c);

    /**
     *
     * @return - the Hand object held by the player
     */
    Hand getHand();

}
