package RiskGame.model.service;

import RiskGame.model.entity.Territory;
import RiskGame.model.service.imp.GameManager;

import java.awt.*;
import java.util.Date;
import java.util.Map;

public class RiskUtil {
    public static void delay(int time){
        Robot r   = null;
        try {
            r = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        r.delay(   time   );
    }

    public static Territory getActivePlayerStrongestCountry() {
        Map<String, Territory> terrs = GameManager.getInstance().getMap().getTerritories();
        Territory largestTer = null;
        for (Territory t : terrs.values()) {
            if (t.getBelongs() == GameManager.getInstance().getActivePlayer()) {
                if (largestTer == null) {
                    largestTer = t;
                } else {
                    if (largestTer.getArmies() < t.getArmies()) {
                        largestTer = t;
                    }
                }
            }
        }
        return largestTer;
    }

    public static Territory getActivePlayerWeakestCountry() {
        Map<String, Territory> terrs = GameManager.getInstance().getMap().getTerritories();
        Territory smallestTer = null;
        for (Territory t : terrs.values()) {
            if (t.getBelongs() == GameManager.getInstance().getActivePlayer()) {
                if (smallestTer == null) {
                    smallestTer = t;
                } else {
                    if (smallestTer.getArmies() > t.getArmies()) {
                        smallestTer = t;
                    }
                }
            }
        }
        return smallestTer;
    }
}
