package RiskGame.model.service;

import RiskGame.model.entity.Player;
import RiskGame.model.entity.Territory;
import RiskGame.model.service.imp.GameManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * The tool class for the whole game application
 * @author Baiyu Huo
 * @version v1.0.0
 * @since v1.0.0
 */
public class RiskUtil {
    /**
     * use to get the strongest country for the active player
     * @return result the territory that has the largest number of armies
     */
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

    /**
     * use to get the weakest country for the active player
     * @return result the territory that has the smallest number of armies
     */
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

    /**
     * use to get all the territories for the player
     * @param p the player instance who want to get all the territories
     * @return result all the territory result for the player
     */
    public static Map<String,Territory> getAllTerritoryFromPlayer(Player p){
        Map<String,Territory> result = new HashMap<String,Territory>();
        for(Territory t: GameManager.getInstance().getMap().getTerritories().values()){
            if (t.getBelongs()==p) {
                result.put(t.getName(), t);
            }
        }
        return result;
    }

    /**
     * random get a key from a map instance
     * @param list the list of selection
     * @return key the random result key.
     */
    public static String randomGetATerrKey(Map<String,Territory> list){

        String[] keys = list.keySet().toArray(new String[0]);

        Random random = new Random();
        String key = keys[random.nextInt(keys.length)];
        return key;
    }


}
