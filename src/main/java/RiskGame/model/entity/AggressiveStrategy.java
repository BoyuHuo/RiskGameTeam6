package RiskGame.model.entity;

import RiskGame.model.service.RiskUtil;
import RiskGame.model.service.imp.GameManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This is implement class for Aggressive Strategy !!
 * contains: attack , fortification, reinforcement
 *
 * @author Baiyu Huo
 * @version v1.0.0
 */
public class AggressiveStrategy implements Strategy {

    /**
     * attack behavior for aggressive strategies robot
     * @param movementTime the time for every movement
     * @return thread the thread that used to finished attack strategy
     */

    @Override
    public Thread attack(int movementTime) {


        Thread thread = new Thread() {
            public void run() {
                try {
                    Territory largestTer = RiskUtil.getActivePlayerStrongestCountry();
                    if (largestTer == null) {
                        if (GameManager.getInstance().getGamePhase().equals("Attack")) {
                            GameManager.getInstance().setMessage("[Aggressive Player]" + GameManager.getInstance().getActivePlayer().getName() + " has finished the attacking phase!\n" +
                                    "It will automatically move to next step in " + movementTime * 2 + "s");
                            Thread.sleep(movementTime * 2);
                            GameManager.getInstance().nextRound();
                            return;
                        }
                    }
                    Map<String, Territory> neibors = largestTer.getNeighbors();

                    for (Territory t : neibors.values()) {
                        if (t.getBelongs() != largestTer.getBelongs()) {
                            if (t.getArmies() <= 0) {
                                GameManager.getInstance().getActivePlayer().captureTerritory(largestTer, t, t.getCaptureDiceNum());
                            } else {
                                GameManager.getInstance().getActivePlayer().allInMode(largestTer, t);
                                if (t.getArmies() <= 0) {
                                    GameManager.getInstance().getActivePlayer().captureTerritory(largestTer, t, t.getCaptureDiceNum());
                                }
                            }
                            Thread.sleep(movementTime);
                        }
                    }

                    GameManager.getInstance().setMessage("[Aggressive Player]" + GameManager.getInstance().getActivePlayer().getName() + " has finished the attacking phase!\n" +
                            "It will automatically move to next step in " + movementTime * 2 + "s");

                    Thread.sleep(movementTime * 2);

                    GameManager.getInstance().nextRound();

                } catch (InterruptedException e) {
                }
            }
        };
        thread.start();
        return thread;
    }

    /**
     *  reinforce for aggressive strategies robot
     * @param movementTime the time for every movement
     * @return thread the thread that used to finished attack strategy
     */

    @Override
    public Thread reinforce(int movementTime) {

        Thread thread = new Thread() {
            public void run() {
                try {
                    Territory largestTer = RiskUtil.getActivePlayerStrongestCountry();
                    while (largestTer != null && GameManager.getInstance().getActivePlayer().getArmies() > 0) {
                        largestTer.increaseArmies(GameManager.getInstance().getActivePlayer());

                        Thread.sleep(movementTime / 2);
                    }
                    GameManager.getInstance().setMessage("[Aggressive Player]" + GameManager.getInstance().getActivePlayer().getName() + " has finished the Reinforcement phase!\n" +
                            "It will automatically move to next step in " + movementTime * 2 + "s");
                    Thread.sleep(movementTime * 2);

                    GameManager.getInstance().nextRound();
                } catch (InterruptedException e) {
                }
            }
        };
        thread.start();


        return thread;
    }

    /**
     *  fortify for aggressive strategies robot
     * @param movementTime the time for every movement
     * @return thread the thread that used to finished attack strategy
     */
    @Override
    public Thread fortify(int movementTime) {
        Thread thread = new Thread() {
            public void run() {
                try {

                    Boolean flag = false;
                    Territory largestTer = RiskUtil.getActivePlayerStrongestCountry();
                    for (Territory t : largestTer.getNeighbors().values()) {
                        if (t.getBelongs() != largestTer.getBelongs()) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        for (Territory t : GameManager.getInstance().getMap().getTerritories().values()) {
                            if (t != largestTer && t.getBelongs() == GameManager.getInstance().getActivePlayer() && t.getArmies() > 0) {
                                if (GameManager.getInstance().getActivePlayer().immigrantArimies(t.getArmies(), t, largestTer)) {
                                    break;
                                }
                            }
                        }
                    } else {
                        Map<String, Territory> territories = RiskUtil.getAllTerritoryFromPlayer(GameManager.getInstance().getActivePlayer());
                        Map<String, Territory> targets = new HashMap<>();
                        here:
                        for (Territory t : territories.values()) {
                            for (Territory n : t.getNeighbors().values()) {
                                if (n.getBelongs() != t.getBelongs()) {
                                    targets.put(t.getName(), t);
                                }

                            }
                        }
                        while (targets.size() > 0) {
                            String key = targets.keySet().toArray(new String[0])[new Random().nextInt(targets.size())];
                            Territory tempT = targets.get(key);
                            if (GameManager.getInstance().getActivePlayer().immigrantArimies(largestTer.getArmies(), largestTer, tempT)) {
                                System.out.println("moveeeeeeeeee" + largestTer.getName() + " to " + tempT.getName());
                                break;
                            } else {
                                targets.remove(targets.get(key));
                            }
                        }

                    }
                    if (GameManager.getInstance().getGamePhase().equals("Fortification")) {
                        GameManager.getInstance().nextRound();
                    }

                } catch (Exception e) {
                }
            }
        };
        thread.start();
        return thread;
    }

    /**
     * startup for aggressive strategies robot
     * @param movementTime the time for every movement
     * @return thread the thread that used to finished attack strategy
     */
    @Override
    public Thread startup(int movementTime) {
        Thread thread = new Thread() {
            public void run() {
                try {
                    Random random = new Random();
                    Map<String, Territory> territories = RiskUtil.getAllTerritoryFromPlayer(GameManager.getInstance().getActivePlayer());
                    String[] keys = territories.keySet().toArray(new String[0]);
                    while (GameManager.getInstance().getActivePlayer().getArmies() > 0) {
                        GameManager.getInstance().getMap().getTerritories().get(keys[random.nextInt(keys.length)]).increaseArmies(GameManager.getInstance().getActivePlayer());
                        Thread.sleep(movementTime / 2);
                    }
                    Thread.sleep(movementTime * 2);
                    GameManager.getInstance().nextRound();
                } catch (InterruptedException e) {
                }
            }
        };
        thread.start();
        return thread;
    }


}
