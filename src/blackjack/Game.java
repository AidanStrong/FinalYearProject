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

    //Constructor
    public Game(int numDecks, HumanPlayer player){
        shoe = new Deck(numDecks);
        this.player = player;
        dealer = new DealerPlayer();
        strat = new BasicStrategy(numDecks);

        this.numDecks = numDecks;
    }

    public static void dealHandToPlayer(Player player, Deck deck){
        ArrayList<Card> cardPair = new ArrayList();
        Hand hand = new Hand(cardPair);
        hand.addCard(deck.deal());
        hand.addCard(deck.deal());
        player.giveHand(hand);
    }

    public double playGame(int numRounds){
        int roundCount = 0;

        while(roundCount < numRounds && player.isBankEmpty() == false){
            playRound();
            roundCount += 1;
        }
        System.out.println("Player ends their game with £" + player.getBank() + " in their bank.");
        return player.getBank();
    }

    public void playRound(){
        if(shoe.getPenetration() > 50){
            shoe.reshuffleShoe();
            //player.getCountStrat().resetCount();
        }
        player.makeBet(5, shoe.getDealtCards(), shoe);

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
            System.out.println("BLACKJACK!");
            System.out.println(player.getHand().toString());
            if(dealerBJ == true){
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
        }
        if (playerDecision == Decision.SURRENDER){
            player.surrender();
            return;
        }
        if (playerDecision == Decision.DOUBLEDOWN){
            player.doubleDown();
            player.dealCard(shoe.deal());
            return;
        }
        if(playerDecision == Decision.SPLIT){
            //player.giveSplit(splitHand(player.getHand()));
            playSplit(splitHand(player.getHand()));
            return;
        }
        if(playerDecision == Decision.HIT){
            player.dealCard(shoe.deal());
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
            System.out.println("BUST!");
            player.loseBet();
        }
        System.out.println(player.getBank());
    }

    public int dealerTurn(){
        Decision dealerDecision = Decision.HIT;
        while (dealer.getHand().isOver21() == false && dealerDecision != Decision.STAND) {
            dealerDecision = dealer.makeDecision();
            if (dealerDecision == Decision.HIT) {
                dealer.dealCard(shoe.deal());
                System.out.println("dealer hand: " + dealer.getHand().toString());
            }
        }
        int value = dealer.getHand().getValue();
        return value;
    }

    public Decision playerFirstTurn(){
        Decision decision = player.makeDecision(player.getHand(), dealer.getUpCard(), true, strat);
        return decision;
    }

    public Hand playerTurn(Hand hand){
        Decision decision = Decision.HIT;
        while (hand.isOver21() == false && decision != Decision.STAND && hand.isBlackjack() == false){ //if hand < 21 and decision false
            decision = player.makeDecision(hand, dealer.getUpCard(), false, strat);
            if (decision == Decision.HIT){
                hand.addCard(shoe.deal());
                System.out.println("now hand: " + hand.toString());
            }
        }
        return hand;
    }

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
            System.out.println("PLAYING SPLIT FOR " + h.toString());
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
        int numDecks = 1;
        CardCount countStrat = new TenCount(numDecks);
        HumanPlayer myPlayer = new HumanPlayer();

        Game myGame = new Game(numDecks, myPlayer);
        double endBank = myGame.playGame(1000000);
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
//make next turns, if aplpicable