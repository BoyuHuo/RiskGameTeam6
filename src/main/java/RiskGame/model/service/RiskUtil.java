package RiskGame.model.service;

import RiskGame.model.entity.Player;
import RiskGame.model.entity.Territory;
import RiskGame.model.service.imp.GameManager;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

    public static Map<String,Territory> getAllTerritoryFromPlayer(Player p){
        Map<String,Territory> result = new HashMap<String,Territory>();
        for(Territory t: GameManager.getInstance().getMap().getTerritories().values()){
            if (t.getBelongs()==p) {
                result.put(t.getName(), t);
            }
        }
        return result;
    }

    public static String randomGetATerrKey(Map<String,Territory> list){

        String[] keys = list.keySet().toArray(new String[0]);

        Random random = new Random();
        String key = keys[random.nextInt(keys.length)];
        return key;
    }


}
