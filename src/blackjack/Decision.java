package blackjack;

import java.util.HashMap;
import java.util.Map;

/**
 * An enum class that represents the different playing decisions in the game
 */
public enum Decision {

    //insurance not implemented as not in generic basic strategy
    HIT(1), STAND(2), SURRENDER(3), DOUBLEDOWN(4), SPLIT(5);
    int value;

    Decision(int x) {
        value = x;
    }

//    private static Map map = new HashMap<>();
//
//    static {
//        for (Decision decision : Decision.values()){
//            map.put(decision.value, decision);
//        }
//    }

    /**
     *
     * @return - the int value of the enum
     */
    public int getValue() {
        return this.value;
    }

    /**
     *
     * @param decision the decision before changing
     * @param newValue the int value of the new decision
     * @return returns the new decision
     */
    public static Decision newDecision(Decision decision, int newValue) {
        switch (newValue) {
            case 1:
                decision = Decision.HIT;
                break;
            case 2:
                decision = Decision.STAND;
                break;
            case 3:
                decision = Decision.SURRENDER;
                break;
            case 4:
                decision = Decision.DOUBLEDOWN;
                break;
            case 5:
                decision = Decision.SPLIT;
                break;
        }
        return decision;
    }

    public static void main(String[] args) {
        Decision decision = Decision.HIT;
        System.out.println(decision);
        decision = Decision.newDecision(decision, 3);
        System.out.println(decision);

    }
}
