package blackjack;

public class BasicStrategy {

    int[][][] firstTurnStrat;
    int[][][] strat; // [isSoft][row][column]
    int[][] splitStrat;
    int numDecks;

    BasicStrategy(int numDecks){
        this.numDecks = numDecks;
        makeStrat();
    };

    public void makeStrat(){
        firstTurnStrat = new int[][][]{hardHandFirstTurnSingleDeck, softHandFirstTurnSingleDeck};
        strat = new int[][][]{hardHandSingleDeck, softHandSingleDeck};
        splitStrat = splitHandFirstTurnSingleDeck;
    }

    /**
     *
     * @param hasAce
     * @param firstTurn
     * @return
     */
    public int[][] getStrat(boolean hasAce, boolean firstTurn){
        if (hasAce){
            if(firstTurn){
                return firstTurnStrat[1];
            }
            return strat[1];
        }
        else{
            if(firstTurn){
                return firstTurnStrat[0];
            }
            return strat[0];
        }
    }
//
//    public int[][] getFirstTurnStrat(boolean hasAce){
//        if (hasAce == true){
//            return firstTurnStrat[1];
//        }else{
//            return firstTurnStrat[0];
//        }
//    }

    public int[][] getSplitStrat(){
        return splitStrat;
    }

    //Some code taken from https://introcs.cs.princeton.edu/java/14array/Blackjack.java.html

    //basic strategy taken from https://wizardofodds.com/games/blackjack/strategy/4-decks/
    //HIT(1), STAND(2), SURRENDER(3), DOUBLEDOWN(4), SPLIT(5), INSURANCE(6);


    //hard hand means no ace
    //no ace  - first turn - single deck
    int[][] hardHandFirstTurnSingleDeck = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //2
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //3
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //4
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //5
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //6
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //7
            {1, 1, 1, 4, 4, 1, 1, 1, 1, 1}, //8
            {1, 4, 4, 4, 1, 1, 1, 1, 1, 1}, //9
            {4, 4, 4, 4, 4, 4, 4, 4, 1, 1}, //10
            {4, 4, 4, 4, 4, 4, 4, 4, 4, 4}, //11
            {1, 1, 2, 2, 2, 1, 1, 1, 1, 1}, //12
            {2, 2, 2, 2, 2, 1, 1, 1, 1, 1}, //13
            {2, 2, 2, 2, 2, 1, 1, 1, 1, 1}, //14
            {2, 2, 2, 2, 2, 1, 1, 1, 1, 3}, //15
            {2, 2, 2, 2, 2, 1, 1, 1, 3, 3}, //16
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 3}, //17
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //18
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //19
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //20
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //21
    };

    //soft hand means ace is in hand
    //ace - first turn - single deck
    int[][] softHandFirstTurnSingleDeck = {
            {1, 1, 4, 4, 4, 1, 1, 1, 1, 1}, //13 - 2
            {1, 1, 4, 4, 4, 1, 1, 1, 1, 1}, //13 - 2
            {1, 1, 4, 4, 4, 1, 1, 1, 1, 1}, //14 - 3
            {1, 1, 4, 4, 4, 1, 1, 1, 1, 1}, //15 - 4
            {1, 1, 4, 4, 4, 1, 1, 1, 1, 1}, //16 - 5
            {4, 4, 4, 4, 4, 1, 1, 1, 1, 1}, //17 - 6
            {2, 4, 4, 4, 4, 2, 2, 1, 1, 1}, //18 - 7
            {2, 2, 2, 2, 4, 2, 2, 2, 2, 2}, //19 - 8
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //20 - 9
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //21 - BJ
    };

    //hard hand means no ace
    //no ace  - not first turn - single deck
    int[][] hardHandSingleDeck = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //2
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //3
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //4
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //5
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //6
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //7
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //8
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //9
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //10
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //11
            {1, 1, 2, 2, 2, 1, 1, 1, 1, 1}, //12
            {2, 2, 2, 2, 2, 1, 1, 1, 1, 1}, //13
            {2, 2, 2, 2, 2, 1, 1, 1, 1, 1}, //14
            {2, 2, 2, 2, 2, 1, 1, 1, 1, 1}, //15
            {2, 2, 2, 2, 2, 1, 1, 1, 1, 1}, //16
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 1}, //17
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //18
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //19
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //20
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //21
    };

    //soft hand means ace is in hand
    //ace  - not first turn - single deck
    int[][] softHandSingleDeck = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //12 - 1
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //13 - 2
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //14 - 3
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //15 - 4
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //16 - 5
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //17 - 6
            {2, 2, 2, 2, 2, 2, 2, 1, 1, 1}, //18 - 7
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //19 - 8
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //20 - 9
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //21 - BJ
    };

    //split hand - first turn - single deck
    /**
     * a javadoc
     */
    int[][] splitHandFirstTurnSingleDeck = {
            {1, 5, 5, 5, 5, 5, 1, 1, 1, 1}, //2,2
            {1, 1, 5, 5, 5, 5, 1, 1, 1, 1}, //3,3
            {1, 1, 1, 4, 4, 1, 1, 1, 1, 1}, //4,4
            {4, 4, 4, 4, 4, 4, 4, 4, 1, 1}, //5,5
            {5, 5, 5, 5, 5, 1, 1, 1, 1, 1}, //6,6
            {5, 5, 5, 5, 5, 5, 1, 1, 3, 3}, //7,7
            {5, 5, 5, 5, 5, 5, 5, 5, 5, 5}, //8,8
            {5, 5, 5, 5, 5, 2, 5, 5, 2, 2}, //9,9
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //10,10
            {5, 5, 5, 5, 5, 5, 5, 5, 5, 5}, //A,A
    };
}
