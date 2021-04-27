package blackjack;

import cards.Card;
import cards.Deck;

import java.util.ArrayList;

//Parent class to other card counting methods?
public interface CardCount {

    int countCards(ArrayList<Card> dealtCards, Deck shoe);

    void resetCount();
}
