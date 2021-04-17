package blackjack;

import cards.Card;
import cards.Deck;
import cards.Hand;

public class DealerPlayer implements Player{

    private Hand dealerHand;
    private Decision decision;

    public DealerPlayer(){
        dealerHand = new Hand();
    }

    public void dealHand(Hand h){
        dealerHand = h;
    }

    public Card getUpCard(){
        return dealerHand.getFirstCard();
    }

    @Override
    public void dealCard(Card c) {
        dealerHand.addCard(c);
    }

    @Override
    public Hand getHand() {
        return dealerHand;
    }

    @Override
    public void setStrategy(CardCount c) {

    }

    //dealer hits soft 17
    public Decision makeDecision() {
        if (dealerHand.getValue() < 17){
            decision = Decision.HIT;
        }
        else if (dealerHand.getValue() == 17 && dealerHand.getAceCount() > 0){
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
