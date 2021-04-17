package blackjack;

import java.util.HashMap;
import java.util.Map;

public enum Decision {

    HIT(1), STAND(2), SURRENDER(3), DOUBLEDOWN(4), SPLIT(5), INSURANCE(6);
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

    public int getValue() {
        return this.value;
    }

    public static Decision newDecision(Decision oldDecision, int newValue) {
        switch (newValue) {
            case 1:
                oldDecision = Decision.HIT;
                break;
            case 2:
                oldDecision = Decision.STAND;
                break;
            case 3:
                oldDecision = Decision.SURRENDER;
                break;
            case 4:
                oldDecision = Decision.DOUBLEDOWN;
                break;
            case 5:
                oldDecision = Decision.SPLIT;
                break;
            case 6:
                oldDecision = Decision.INSURANCE;
                break;
        }
        return oldDecision;
    }

    public static void main(String[] args) {
        Decision decision = Decision.HIT;
        System.out.println(decision);
        decision = Decision.newDecision(decision, 3);
        System.out.println(decision);


    }
}
