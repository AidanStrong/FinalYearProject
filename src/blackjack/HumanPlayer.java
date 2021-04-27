package blackjack;

import cards.Card;
import cards.Deck;
import cards.Hand;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class HumanPlayer implements Player {

    private Hand playerHand;
    private CardCount playerCardCount;
    double bank = 10000;
    double bet;
    boolean isCardCounting;
    CardCount countStrat;
    Hand[] split;
    boolean hasSplit = false;

    //Source: Thorp 1962 - Beat the Dealer
    // [Ratio][Bet units]
    //ratio -- bet (in units)
    // >2.00 -- 1(minimum)
    // 2.00-1.75 -- 2
    // 1.75-1.65 -- 4
    // below 1.65 -- 5
    double[][] tenCountBet = {
            {2.01, 1},
            {2.00, 2},
            {1.75, 4},
            {1.65, 5},
    };

    public HumanPlayer() {
        playerHand = new Hand();
        isCardCounting = false;
    }

    public HumanPlayer(CardCount countStrat){
        isCardCounting = true;
        this.countStrat = countStrat;
    }

    public Hand getHand() {
        return playerHand;
    }

    public void giveSplit(Hand[] split){
        playerHand = null;
        this.split = split;
        hasSplit = true;
    }

    public void giveHand(Hand h) {
        playerHand = h;
    }

    public void changeBank(int difference){
        bank += difference;
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

    public void doubleDown(){
        bet = bet * 2;
    }

    public void surrender(){
        bank = bank - (bet/2);
        bet = 0;
    }

    public void splitBet(){
        bet = bet * 2;
    }

    public void winSplitBet(){
        bet = bet / 2;
        bank = bank + bet;
    }

    public void loseSplitBet(){
        bet = bet / 2;
        bank = bank - bet;
    }

    public void tieSplitBet(){
        bet = bet / 2;
    }

    public void winBJsplitBet(){
        bet = bet / 2;
        bank = bank + (bet * 1.5);
    }

    public void endSplitBet(){
        bet = 0;
    }

    public boolean isBankEmpty(){
        if (bank <= 0){
            return true;
        }
        return false;
    }

    public double getBank(){
        return bank;
    }

    public CardCount getCountStrat(){
        return playerCardCount;
    }

    public void makeBet(int minimumBet, ArrayList<Card> dealtCards, Deck shoe){

        if (isCardCounting == true){
            int betUnits = countStrat.countCards(dealtCards, shoe);
            bet = betUnits * minimumBet;
        }
        else{
            bet = minimumBet * 1.2;
        }

        //System.out.println("player betting £ " + bet);
    }

    @Override
    public void dealCard(Card c) {
        if (hasSplit == false){
            playerHand.addCard(c);
        }
        else{
            playerHand.addCard(c);
        }

    }

    public Decision makeDecision(Hand hand, Card upCard, boolean firstTurn, BasicStrategy basicStrat) {
        int[][] table;
        int result;
        Decision decision = Decision.HIT;
        int upCardValue = upCard.getValue();
        int handValue = hand.getValue();
        int index = 0;
        boolean isSoft = hand.isSoft();
        System.out.println("SPLITTABLE? " + hand.isSplittable());
        System.out.println(firstTurn);
        if(firstTurn == true && hand.isSplittable() == true) {
            table = basicStrat.getSplitStrat();
            index = -(hand.getFirstCard().getValue());
            if(hand.hasAce()){
                index = index + 10;
            }
        }
        else{
            table = basicStrat.getStrat(isSoft, firstTurn);
            if (hand.isSoft()) {
                index = - 10;
            }
        }
        if(firstTurn == false && hand.isSplittable() == true && hand.hasAce() == true){
            System.out.println("THIS ONE");
        }
        System.out.println(upCard.toString());
        System.out.println(hand.toString());
        result = table[index + handValue - 2][upCardValue - 2];
        System.out.println("[" + (index + handValue - 2) + "][" + (upCardValue - 2) + "]");
        decision = Decision.newDecision(decision, result);
        System.out.println(decision);
        System.out.println("--\n");
        return decision;
    }

    public static void main(String[] args) {
        HumanPlayer player = new HumanPlayer();
        //Deck deck = new Deck();
        //Game.dealHandToPlayer(player, deck);
        Card upCard = new Card(Card.Rank.SIX, Card.Suit.CLUBS);
        Card card1 = new Card(Card.Rank.ACE, Card.Suit.CLUBS);
        Card card2 = new Card(Card.Rank.SEVEN, Card.Suit.CLUBS);
        ArrayList<Card> theHand = new ArrayList();
        theHand.add(card1);
        theHand.add(card2);
        Hand hand = new Hand(theHand);
        player.giveHand(hand);
        BasicStrategy strat = new BasicStrategy(1);
        //Decision decision = player.makeDecision(upCard, true, strat);
        //System.out.println("\n" + decision);
    }
}

//
//    //hard hand means no ace
//    double[][] hardHand_48Deck = {
//            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //2
//            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //3
//            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //4
//            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //5
//            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //6
//            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //7
//            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //8
//            {1, 4, 4, 4, 4, 1, 1, 1, 1, 1}, //9
//            {4, 4, 4, 4, 4, 4, 4, 4, 1, 1}, //10
//            {4, 4, 4, 4, 4, 4, 4, 4, 4, 4}, //11
//            {1, 1, 2, 2, 2, 1, 1, 1, 1, 1}, //12
//            {2, 2, 2, 2, 2, 1, 1, 1, 1, 1}, //13
//            {2, 2, 2, 2, 2, 1, 1, 1, 1, 1}, //14
//            {2, 2, 2, 2, 2, 1, 1, 1, 3, 3}, //15
//            {2, 2, 2, 2, 2, 1, 1, 3, 3, 3}, //16
//            {2, 2, 2, 2, 2, 2, 2, 2, 2, 3}, //17
//            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //18
//            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //19
//            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //20
//            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //21
//    };
//
//    //soft hand means ace is in hand
//    int[][] softHand_48Deck = {
//            {1, 1, 1, 4, 4, 1, 1, 1, 1, 1}, //13 - 2
//            {1, 1, 1, 4, 4, 1, 1, 1, 1, 1}, //14 - 3
//            {1, 1, 4, 4, 4, 1, 1, 1, 1, 1}, //15 - 4
//            {1, 1, 4, 4, 4, 1, 1, 1, 1, 1}, //16 - 5
//            {1, 4, 4, 4, 4, 1, 1, 1, 1, 1}, //17 - 6
//            {4, 4, 4, 4, 4, 2, 2, 1, 1, 1}, //18 - 7
//            {2, 2, 2, 2, 2, 4, 2, 2, 2, 2}, //19 - 8
//            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //20 - 9
//            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //21 - BJ
//    };