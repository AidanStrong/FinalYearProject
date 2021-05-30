package blackjack;

import cards.Card;
import cards.Deck;
import cards.Hand;

public class DealerPlayer implements Player{

    private Hand dealerHand;
    private Decision decision;

    /**
     * constructor that creates a new blank hand
     */
    public DealerPlayer(){
        dealerHand = new Hand();
    }

    /**
     *
     * @param h a Hand object to deal to the player
     */
    public void giveHand(Hand h){
        dealerHand = h;
    }

    /**
     *
     * @return - a Card object that is the dealer's upcard to be observed by the player
     */
    public Card getUpCard(){
        return dealerHand.getFirstCard();
    }

    /**
     *
     * @param c - a Card object to deal to the player
     */
    @Override
    public void dealCard(Card c) {
        dealerHand.addCard(c);
    }

    /**
     *
     * @return
     */
    @Override
    public Hand getHand() {
        return dealerHand;
    }


    /**
     *
     * @return an enum Decision
     */
    public Decision makeDecision() {
        if (dealerHand.getValue() < 17){
            decision = Decision.HIT;
        }
        else if (dealerHand.getValue() == 17 && dealerHand.isSoft()){
            decision = Decision.HIT;
        }
        else{
            decision = Decision.STAND;
        }
        return decision;
    }

    public static void main(String[] args) {
        DealerPlayer dealer = new DealerPlayer();
        Card ace = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        Card six = new Card(Card.Rank.SEVEN, Card.Suit.SPADES);
        dealer.dealCard(ace);
        dealer.dealCard(six);

    }
}
