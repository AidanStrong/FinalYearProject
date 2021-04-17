import cards.Card;
import cards.Deck;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Card myCard = new Card();
        String cardString = myCard.toString();
        System.out.println(cardString);

        Deck myDeck = new Deck();
        System.out.println(myDeck.toString());

        myCard = myDeck.deal();
        System.out.println(myCard.toString());


    }
}
