package blackjack;

import cards.Card;
import cards.Deck;
import cards.Hand;

import java.util.ArrayList;

public class Game {

    static Deck deck;
    private HumanPlayer player;
    private DealerPlayer dealer;

    //Constructor
    public Game(){
        deck = new Deck();
        player = new HumanPlayer();
        dealer = new DealerPlayer();
    }

    public static void dealHandToPlayer(Player player, Deck deck){
        ArrayList<Card> cardPair = new ArrayList();
        Hand hand = new Hand(cardPair);
        hand.addCard(deck.deal());
        hand.addCard(deck.deal());
        player.dealHand(hand);
    }


    public void playRound(){

    }

    public void playGame(){
        player.makeBet();
        dealHandToPlayer(player, deck);
        int playerValue = 0;
        int dealerValue = 0;
        boolean playerBJ = player.getHand().isBlackjack();
        boolean dealerBJ = dealer.getHand().isBlackjack();

        if (playerBJ == false){
            playerValue = playerTurn();
        }

        boolean playerBust = player.getHand().isOver21();
        if (playerBust == false){
            if (dealerBJ == false){
                dealerValue = dealerTurn();
            }
        }
        boolean dealerBust = dealer.getHand().isOver21();

        if(playerBJ == true && dealerBJ == true){
            System.out.println("Tie! - Both blackjacks!");
            player.tieBet();
        }
        else if (playerBJ == true && dealerBJ == false){
            System.out.println("Player win - BLACKJACK!");
            player.winBlackjack();
        }
        else if (playerBJ == false && dealerBJ == true){
            System.out.println("Dealer win - BLACKJACK!");
            player.loseBet();
        }
        else { //if no blackjacks
            if(playerBust == true){
                System.out.println("Dealer win - player gone BUST!");
                player.loseBet();
            }
            else if(dealerBust == true){
                System.out.println("Player win - dealer gone BUST!");
                player.winBet();
            }
            else {
                if (dealerValue > playerValue) {
                    System.out.println("dealer win");
                    player.loseBet();
                } else if (dealerValue == playerValue) {
                    System.out.println("tie!");
                    player.tieBet();
                } else {
                    System.out.println("player win");
                    player.winBet();
                }
            }
        }
    }

    public int dealerTurn(){
        Decision dealerDecision = Decision.HIT;
        while (dealer.getHand().isOver21() == false && dealerDecision != Decision.STAND) {
            dealerDecision = dealer.makeDecision();
            if (dealerDecision == Decision.HIT) {
                dealer.dealCard(deck.deal());
            }
        }
        int value = dealer.getHand().getValue();
        return value;
    }

    public int playerTurn(){
        Decision decision = Decision.HIT;
        while (player.getHand().isOver21() == false && decision != Decision.STAND){ //if hand < 21 and decision false
            System.out.println("test");
            decision = player.makeDecision(dealer.getUpCard());
            if (decision == Decision.HIT){
                player.dealCard(deck.deal());
            }

        }
        int value = player.getHand().getValue();
        return value;
    }


    //test harness
    public static void main(String[] args) {
        Game myGame = new Game();
        myGame.playGame();
        //System.out.println(myGame.player.getHand().toString());
        //System.out.println(myGame.dealer.getHand().toString());

    }
}

// read up on a simple basic strategy
// implement all decisions + rest of game
//implement one simple card counting + basic betting
//call elena
