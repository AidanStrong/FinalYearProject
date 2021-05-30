package blackjack;

import cards.Card;
import cards.Deck;

import java.util.ArrayList;

/**
 * An interface for the basic layout of card counting classes
 */
public interface CardCount {

    /**
     *
     * @param dealtCards an arrayList of card objects that are the cards dealt since last reshuffle
     * @param shoe the shoe/deck for the game as a Deck object
     * @return - an integer dictating how many units to bet
     */
    int countCards(ArrayList<Card> dealtCards, Deck shoe);

}
