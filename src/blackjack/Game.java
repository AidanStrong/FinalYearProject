package blackjack;

import cards.Card;
import cards.Deck;
import cards.Hand;

import java.util.ArrayList;

public class Game {

    private Deck shoe;
    private HumanPlayer player;
    private DealerPlayer dealer;
    private BasicStrategy strat;
    private int numDecks = 1;
    private double penertration = 0.5;

    //Constructor

    /**
     *
     * @param numDecks the number of decks in the game
     * @param player the player
     * @param penertration the amount of deck penetration before reshuffling
     */
    public Game(int numDecks, HumanPlayer player, Double penertration){
        shoe = new Deck(numDecks);
        this.player = player;
        dealer = new DealerPlayer();
        strat = new BasicStrategy(numDecks);
        this.penertration = penertration;
        this.numDecks = numDecks;
    }

    /**
     *
     * @param player the player to deal a hand to
     * @param deck the deck to deal from
     */
    public static void dealHandToPlayer(Player player, Deck deck){
        ArrayList<Card> cardPair = new ArrayList();
        Hand hand = new Hand(cardPair);
        hand.addCard(deck.deal());
        hand.addCard(deck.deal());
        player.giveHand(hand);
    }

    /**
     *
     * @param numRounds number of rounds to play in the game
     * @return returns the end bankroll for the player
     */
    public double playGame(int numRounds){
        int roundCount = 0;
        player.resetBank();
        while(roundCount < numRounds && player.isBankEmpty() == false){
            System.out.println("\n\n   --- NEW ROUND ---");
            playRound();
            roundCount += 1;
        }
        System.out.println("Player ends their game with £" + player.getBank() + " in their bank.");
        return player.getBank();
    }

    /**
     * plays a round and settles bets
     */
    public void playRound(){
        if(shoe.getPenetration() > penertration){
            shoe.reshuffleShoe();
            //player.getCountStrat().resetCount();
        }
        player.makeBet(shoe.getDealtCards(), shoe);

        dealHandToPlayer(player, shoe);
        dealHandToPlayer(dealer, shoe);
        Decision playerDecision;
        int dealerValue;
        int playerValue;
        boolean playerBJ = player.getHand().isBlackjack();
        boolean dealerBJ = dealer.getHand().isBlackjack();
        boolean dealerBust;
        boolean playerBust;

        //if player has blackjack
        if (playerBJ == true){
         //   System.out.println("BLACKJACK!");
            if(dealerBJ == true){
              //  System.out.println("dealer blackjack!");
                player.tieBet();
            }
            else{
                player.winBlackjack();
            }
            return;
        }
        //if no blackjack, play first turn
        else{
            playerDecision = playerFirstTurn();
          //  System.out.println("making first turn");
        }
        if (playerDecision == Decision.SURRENDER){
           // System.out.println("surrender");
            player.surrender();
            return;
        }
        if (playerDecision == Decision.DOUBLEDOWN){
            //System.out.println("double down");
            player.doubleDown();
            player.dealCard(shoe.deal());
        }
        if(playerDecision == Decision.SPLIT){
            //player.giveSplit(splitHand(player.getHand()));
            playSplit(splitHand(player.getHand()));
            //System.out.println("split");
            return;
        }
        if(playerDecision == Decision.HIT){
            //deal and extra card
            player.dealCard(shoe.deal());
            //play the rest of the turn and give them the hand returned
            player.giveHand(playerTurn(player.getHand()));
            playerValue = player.getHand().getValue();
        }
        //if decision is stand
        else{
            playerValue = player.getHand().getValue();
        }

        playerBust = player.getHand().isOver21();

        if (playerBust == false){
            if (dealerBJ == false){
                //dealer only has turn if player isn't bust and dealer has no blackjack
                dealerValue = dealerTurn();
                dealerBust = dealer.getHand().isOver21();
            }
            //dealer wins on blackjack
            else{
               // System.out.println("dealer blackjack!");
                player.loseBet();
                return;
            }
            //player wins on dealer bust
            if(dealerBust == true) {
                player.winBet();
                return;
            }
            //compare hand values
            if(playerValue < dealerValue){
                player.loseBet();
            }
            else if(playerValue > dealerValue){
                player.winBet();
            }
            else{
                player.tieBet();
            }
        }
        //if player did go bust
        else {
           // System.out.println("BUST!");
            player.loseBet();
        }
    }

    /**
     *
     * @return the value of the dealer's hand after their turn
     */
    public int dealerTurn(){
        Decision dealerDecision = Decision.HIT;
        while (dealer.getHand().isOver21() == false && dealerDecision != Decision.STAND) {
            dealerDecision = dealer.makeDecision();
            if (dealerDecision == Decision.HIT) {
                dealer.dealCard(shoe.deal());
            }
        }
        //System.out.println("dealer hand: " + dealer.getHand().toString());
        int value = dealer.getHand().getValue();
        return value;
    }

    /**
     *
     * @return the enum decision for the player's first play move
     */
    public Decision playerFirstTurn(){
        Decision decision = player.makeDecision(player.getHand(), dealer.getUpCard(), true, strat);
        return decision;
    }

    /**
     *
     * @param hand the starting hand of the player
     * @return the hand that the player ends with after their turn
     */
    public Hand playerTurn(Hand hand){
        Decision decision = Decision.HIT;
        while (hand.isOver21() == false && decision != Decision.STAND && hand.isBlackjack() == false){ //if hand < 21 and decision false
            decision = player.makeDecision(hand, dealer.getUpCard(), false, strat);
            if (decision == Decision.HIT){
                hand.addCard(shoe.deal());
            }
        }
        return hand;
    }

    /**
     *
     * @param playerHand the starting hand that is to be split
     * @return an array of Hand objects that hav been split and dealt
     */
    public Hand[] splitHand(Hand playerHand){
        Hand split1 = new Hand();
        Hand split2 = new Hand();
        split1.addCard(playerHand.getCardInPos(0));
        split2.addCard(playerHand.getCardInPos(1));
        split1.addCard(shoe.deal());
        split2.addCard(shoe.deal());
        Hand[] split = {split1, split2};
        return split;
    }

    /**
     *
     * @param split Array of Hand objects to be played as splits
     * @return returns number of splits won
     */
    public int playSplit(Hand[] split){
        player.splitBet();
        int splitsWon = 0;
        split[0] = playerTurn(split[0]);
        split[1] = playerTurn(split[1]);
        //if either hand is not bust
        if (split[0].isOver21() == false || split[1].isOver21() == false){
            if(split[0].isBlackjack() == false || split[1].isBlackjack() == false)
                //dealer only has turn if at least one of the player's hands is not bust and not a blackjack
                dealerTurn();
        }
        for(Hand h : split) {
            //if blackjack
            if (h.isBlackjack()) {
                if (dealer.getHand().isBlackjack()) {
                    player.tieSplitBet();
                } else {
                    player.winBJsplitBet();
                }
            }

            if (h.isOver21() == false) {
                //no dealer blackjack
                if (dealer.getHand().isBlackjack() == false) {
                    //dealer went bust but not player
                    if (dealer.getHand().isOver21()) {
                        player.winSplitBet();
                    } else { //neither dealer or player went bust
                        //compare hand values
                        if (h.getValue() < dealer.getHand().getValue()) {
                            player.loseSplitBet();
                        } else if (h.getValue() > dealer.getHand().getValue()) {
                            player.winSplitBet();
                        } else {
                            player.tieSplitBet();
                        }
                    }
                } else {
                    //dealer wins on blackjack
                    player.loseSplitBet();
                }
            }
            //player hand went bust
            else {
                player.loseSplitBet();
            }
        }
        player.endSplitBet();
        return splitsWon;
    }

    //test harness
    public static void main(String[] args) {
        int numDecks = 8;
        int numRounds = 100;
        int numGames = 50000;
        CardCount countStrat = new HiLoCount(numDecks);
        double penertration = 0.25;
        int minimumBet = 25;
        int bankroll = 10000;
        HumanPlayer myPlayer = new HumanPlayer(countStrat, minimumBet, bankroll);
        Game myGame = new Game(numDecks, myPlayer, penertration);
        double[] bankArray = new double[numGames];
        for (int i = 0; i < numGames; i++) {
            double endBank = myGame.playGame(numRounds);
            bankArray[i] = endBank;
        }
        System.out.println("--");
        double mean = Calculate.mean(bankArray);
        double advantage = (mean - bankroll) * 100 / bankroll;
     //   System.out.println("mean: ");
        System.out.println(mean);
      //  System.out.println("mean % advantage: ");
        System.out.println(advantage);
       // System.out.println("standard deviation: ");
        System.out.println(Calculate.standardDeviation(bankArray));

      //  System.out.println("min value: ");
        System.out.println(Calculate.min(bankArray));
       // System.out.println("max value: ");
        System.out.println(Calculate.max(bankArray));

       // System.out.println("-- percentiles --");
        System.out.println(Calculate.percentile(bankArray, 0.05));
        System.out.println(Calculate.percentile(bankArray, 0.25));
        System.out.println(Calculate.percentile(bankArray, 0.50));
        System.out.println(Calculate.percentile(bankArray, 0.75));
        System.out.println(Calculate.percentile(bankArray, 0.95));

    }
}

// read up on a simple basic strategy - DONE
// implement all decisions + rest of game - DONE
// implement one simple card counting - not done
// basic betting - DONE
// deck saturation - DONE
// deck numbers, game initiation - card counting method
//call elena


//check player blackJack
// > check dealer blackJack
//make first turn
// > if surrender,
//make next turns, if applicable