package blackjack;

import cards.Card;
import cards.Deck;
import cards.Hand;

import java.sql.SQLOutput;

public class HumanPlayer implements Player {

    private int chips;
    private Hand playerHand;
    private CardCount playerCardCount;
    private Decision decision;
    double bank = 100;
    double bet;

    //Some code taken from https://introcs.cs.princeton.edu/java/14array/Blackjack.java.html

    //HIT(1), STAND(2), SURRENDER(3), DOUBLEDOWN(4), SPLIT(5), INSURANCE(6);

    //hard hand means no ace
    int[][] hardHand = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //2
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //3
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //4
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //5
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //6
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //7
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //8
        {1, 4, 4, 4, 1, 1, 1, 1, 1, 1}, //9
        {4, 4, 4, 4, 4, 4, 4, 4, 1, 1}, //10
        {4, 4, 4, 4, 4, 4, 4, 4, 4, 4}, //11
        {1, 1, 2, 2, 2, 1, 1, 1, 1, 1}, //12
        {2, 2, 2, 2, 2, 1, 1, 1, 1, 1}, //13
        {2, 2, 2, 2, 2, 1, 1, 1, 1, 1}, //14
        {2, 2, 2, 2, 2, 1, 1, 1, 3, 3}, //15
        {2, 2, 2, 2, 2, 1, 1, 3, 3, 3}, //16
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 3}, //17
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //18
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //19
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //20
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //21
    };

    //soft hand means ace is in hand
    int[][] softHand = {
        {1, 1, 1, 4, 4, 1, 1, 1, 1, 1}, //13
        {1, 1, 1, 4, 4, 1, 1, 1, 1, 1}, //14
        {1, 1, 4, 4, 4, 1, 1, 1, 1, 1}, //15
        {1, 1, 4, 4, 4, 1, 1, 1, 1, 1}, //16
        {1, 4, 4, 4, 4, 1, 1, 1, 1, 1}, //17
        {4, 4, 4, 4, 4, 2, 2, 1, 1, 1}, //18
        {2, 2, 2, 2, 2, 4, 2, 2, 2, 2}, //19
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //20
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //21
    };

    public HumanPlayer() {
        playerHand = new Hand();
    }

    public Hand getHand() {
        return playerHand;
    }

    public void dealHand(Hand h) {
        playerHand = h;
    }

    public void changeBank(int difference){
        bank += difference;
    }

    public void makeBet(){
        bet = bank * 0.1;
    }

    public void winBet(){
        bank = bank + bet;
        bet = 0;
    }

    public void loseBet(){
        bank = bank - bet;
        bet = 0;
    }

    public void tieBet(){
        bet = 0;
    }

    public void winBlackjack(){
        bank = bank + (bet * 1.5);
        bet = 0;
    }

    @Override
    public void dealCard(Card c) {
        playerHand.addCard(c);
    }

    @Override
    public void setStrategy(CardCount c) {
        playerCardCount = c;
    }

    public Decision makeDecision(Card upCard) {
        int[][] basicStrategy;
        int result;
        Decision decision = Decision.HIT;
        int upCardValue = upCard.getValue();
        int handValue = playerHand.getValue();

        //if hand is hard (no ace)
        if (playerHand.getAceCount() == 0) {
            basicStrategy = hardHand;
        } else {
            basicStrategy = softHand;
        }

        result = basicStrategy[handValue - 2][upCardValue - 2];
        decision = Decision.newDecision(decision, result);
        return decision;
    }

    public static void main(String[] args) {
        HumanPlayer player = new HumanPlayer();
        Deck deck = new Deck();
        Game.dealHandToPlayer(player, deck);
        Card upCard = new Card(Card.Rank.ACE, Card.Suit.CLUBS);
        System.out.println(upCard.toString());
        Decision decision = player.makeDecision(upCard);
        System.out.println("\n" + decision);
    }
}