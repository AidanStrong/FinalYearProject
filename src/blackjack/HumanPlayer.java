package blackjack;

import cards.Card;
import cards.Deck;
import cards.Hand;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class HumanPlayer implements Player {

    private Hand playerHand;
    private CardCount playerCardCount;
    private double bank = 10000;
    private double bet;
    private boolean isCardCounting;
    private CardCount countStrat;
    private Hand[] split;
    private boolean hasSplit = false;
    private int minimumBet = 25;

    // constructors
    public HumanPlayer() {
        playerHand = new Hand();
        isCardCounting = false;
    }

    /**
     *
     * @param countStrat the card counting strategy
     * @param minimumBet the minimum bet
     * @param bank the starting bankroll
     */
    public HumanPlayer(CardCount countStrat, int minimumBet, int bank){
        isCardCounting = true;
        this.countStrat = countStrat;
        this.minimumBet = minimumBet;
        this.bank = bank;
    }

    /**
     *
     * @return a Hand object that is the player's hand
     */
    public Hand getHand() {
        return playerHand;
    }

    /**
     *
     * @param h a Hand object to deal to the player
     */
    public void giveHand(Hand h) {
        playerHand = h;
    }

    /**
     *
     * @param difference an integer to increase the bankroll by
     */
    public void changeBank(int difference){
        bank += difference;
    }

    /**
     * resets bank to default £10,000
     */
    public void resetBank(){
        bank = 10000;
        bet = 0;
    }

    /**
     * gives the player their bet if they win
     */
    public void winBet(){
        bank = bank + bet;
        bet = 0;
    }

    /**
     * takes away the player's bet if they lose
     */
    public void loseBet(){
        bank = bank - bet;
        bet = 0;
    }

    /**
     * no changes if it is a tie
     */
    public void tieBet(){
        bet = 0;
    }

    /**
     * gives 1.5x the bet if they win with blackjack
     */
    public void winBlackjack(){
        bank = bank + (bet * 1.5);
        bet = 0;
    }

    /**
     * doubles the bet
     */
    public void doubleDown(){
        bet = bet * 2;
    }

    /**
     * surrenders the bet
     */
    public void surrender(){
        bank = bank - (bet/2);
        bet = 0;
    }

    /**
     * doubles the bet for splitting
     */
    public void splitBet(){
        bet = bet * 2;
    }

    /**
     * wins one of the splits
     */
    public void winSplitBet(){
        bet = bet / 2;
        bank = bank + bet;
    }

    /**
     * loses one of the splits
     */
    public void loseSplitBet(){
        bet = bet / 2;
        bank = bank - bet;
    }

    /**
     * ties one of the splits
     */
    public void tieSplitBet(){
        bet = bet / 2;
    }

    /**
     * wins a blackjack on a split
     */
    public void winBJsplitBet(){
        bet = bet / 2;
        bank = bank + (bet * 1.5);
    }

    /**
     * resets the bet after split
     */
    public void endSplitBet(){
        bet = 0;
    }

    /**
     *
     * @return a boolean dictating if the bank is empty or not
     */
    public boolean isBankEmpty(){
        if (bank <= 0){
            return true;
        }
        return false;
    }

    /**
     *
     * @return a double that is the bankroll
     */
    public double getBank(){
        return bank;
    }

    /**
     * @return the card counting strategy used
     */
    public CardCount getCountStrat(){
        return playerCardCount;
    }

    /**
     * uses card counting to determine how much to bet
     * @param dealtCards the cards dealt so far
     * @param shoe the current shoe
     */
    public void makeBet(ArrayList<Card> dealtCards, Deck shoe){

        if (isCardCounting == true){
            int betUnits = countStrat.countCards(dealtCards, shoe);
            bet = betUnits * minimumBet;
            if(betUnits == 0){
                bet = 5;
            }
        }
        else{
            bet = minimumBet * 1.2;
        }
       // System.out.println("Betting: " + bet);
        //System.out.println("player betting £ " + bet);
    }

    /**
     *
     * @param c - a Card object to deal to the player
     */
    @Override
    public void dealCard(Card c) {
        if (hasSplit == false){
            playerHand.addCard(c);
        }
        else{
            playerHand.addCard(c);
        }

    }

    /**
     *
     * @param hand the player's current hand
     * @param upCard the dealer's up card to observe
     * @param firstTurn a boolean informing the method if it is the player's first move of their turn or not
     * @param basicStrat the basic strategy object in use
     * @return an enum decision
     */
    public Decision makeDecision(Hand hand, Card upCard, boolean firstTurn, BasicStrategy basicStrat) {
        int[][] table;
        int result;
        Decision decision = Decision.HIT;
        int upCardValue = upCard.getValue();
        int handValue = hand.getValue();
        int index = 0;
        boolean isSoft = hand.isSoft();
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

        result = table[index + handValue - 2][upCardValue - 2];
        decision = Decision.newDecision(decision, result);
        return decision;
    }

    public static void main(String[] args) {
        HumanPlayer player = new HumanPlayer();
        //Deck deck = new Deck();
        //Game.dealHandToPlayer(player, deck);
        Card upCard = new Card(Card.Rank.TWO, Card.Suit.CLUBS);
        Card card1 = new Card(Card.Rank.SIX, Card.Suit.CLUBS);
        Card card2 = new Card(Card.Rank.SIX, Card.Suit.CLUBS);
        ArrayList<Card> theHand = new ArrayList();
        theHand.add(card1);
        theHand.add(card2);
        Hand hand = new Hand(theHand);
        BasicStrategy strat = new BasicStrategy(8);
        Decision decision = player.makeDecision(hand, upCard, true, strat);
        System.out.println("\n" + decision);
    }
}