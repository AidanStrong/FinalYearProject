package blackjack;

import cards.Card;

public interface Player {

    //deal a card to the player's hand
    void dealCard(Card c);

    //set the strategy that the player will use
    void setStrategy(Strategy s);

    //make a decision to stick or twist
    boolean decision();

}
