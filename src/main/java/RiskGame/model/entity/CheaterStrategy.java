package RiskGame.model.entity;

import RiskGame.model.service.RiskUtil;
import RiskGame.model.service.imp.GameManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This is implement class for Cheater Strategy !!
 * contains: attack , fortification, reinforcement
 *
 * @author Baiyu Huo
 * @version v1.0.0
 */
public class CheaterStrategy implements Strategy {
    /**
     * attack behavior for CheaterStrategy robot
     * @param movementTime the time for every movement
     * @return thread the thread that used to finished attack strategy
     */
    @Override
    public Thread attack(int movementTime) {
        Thread thread = new Thread() {
            public void run() {
                try {
                    Map<String, Territory> territories = RiskUtil.getAllTerritoryFromPlayer(GameManager.getInstance().getActivePlayer());
                    List<Territory> boundryTerr = new ArrayList<>();

                    for (Territory t : territories.values()) {
                        for (Territory n : t.getNeighbors().values()) {
                            if (n.getBelongs() != t.getBelongs()) {
                                boundryTerr.add(t);
                            }
                        }
                    }

                    for (Territory t : boundryTerr) {
                        for (Territory n : t.getNeighbors().values()) {
                            if (t.getBelongs() != n.getBelongs()) {
                                if (n.getArmies() <= 0) {
                                    GameManager.getInstance().getActivePlayer().captureTerritory(t,n,n.getCaptureDiceNum());
                                    Thread.sleep(movementTime);
                                } else {
                                    if (n.getArmies() > 0) {
                                        GameManager.getInstance().getActivePlayer().allInMode(t,n);
                                        if (n.getArmies() <= 0) {
                                            GameManager.getInstance().getActivePlayer().captureTerritory(t,n,n.getCaptureDiceNum());
                                            Thread.sleep(movementTime);
                                        }
                                        Thread.sleep(movementTime);
                                    }
                                }

                            }
                        }
                    }
                    if (GameManager.getInstance().getGamePhase().equals("Attack")) {
                        Thread.sleep(movementTime * 2);
                        GameManager.getInstance().nextRound();
                    }
                } catch (InterruptedException e) {
                    GameManager.getInstance().nextRound();
                }
                if (GameManager.getInstance().getGamePhase().equals("Attack")) {
                    GameManager.getInstance().nextRound();
                }
            }
        };
        thread.start();

        return thread;
    }

    /**
     * reinforce behavior for CheaterStrategy robot
     * @param movementTime the time for every movement
     * @return thread the thread that used to finished attack strategy
     */
    @Override
    public Thread reinforce(int movementTime) {
        Thread thread = new Thread() {
            public void run() {
                try {
                    Map<String, Territory> territories = RiskUtil.getAllTerritoryFromPlayer(GameManager.getInstance().getActivePlayer());
                    for (Territory t : territories.values()) {
                        t.setArmies(t.getArmies() * 2);
                        GameManager.getInstance().setMessage("[Cheater]" + GameManager.getInstance().getActivePlayer() + " just double the armies in " + t.getName());
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

    /**
     * fortify behavior for CheaterStrategy robot
     * @param movementTime the time for every movement
     * @return thread the thread that used to finished attack strategy
     */
    @Override
    public Thread fortify(int movementTime) {

        Thread thread = new Thread() {
            public void run() {
                try {
                    Map<String, Territory> territories = RiskUtil.getAllTerritoryFromPlayer(GameManager.getInstance().getActivePlayer());
                    for (Territory t : territories.values()) {
                        for (Territory n : t.getNeighbors().values()) {
                            if (n.getBelongs() != t.getBelongs()) {
                                t.setArmies(t.getArmies() * 2);
                                break;
                            }
                        }
                    }

                    Territory strongest = RiskUtil.getActivePlayerStrongestCountry();
                    boolean moveFlag = true;
                    for(Territory t: strongest.getNeighbors().values()){
                        if(t.getBelongs()!=GameManager.getInstance().getActivePlayer()){
                            moveFlag = false;
                            break;
                        }
                    }
                    if(moveFlag){
                        for (Territory t : territories.values()) {
                            if(t==strongest){
                                continue;
                            }
                            for (Territory n : t.getNeighbors().values()) {
                                if (n.getBelongs() != t.getBelongs()) {
                                    if(GameManager.getInstance().getActivePlayer().immigrantArimies(strongest.getArmies(),strongest,t))
                                        break;
                                }
                            }
                        }
                    }


                    if (GameManager.getInstance().getGamePhase().equals("Fortification")) {
                        GameManager.getInstance().nextRound();
                    }
                } catch (Exception e) {
                }

                if (GameManager.getInstance().getGamePhase().equals("Fortification")) {
                    GameManager.getInstance().nextRound();
                    interrupt();
                }
            }
        };
        thread.start();


        return thread;
    }


    /**
     * startup behavior for CheaterStrategy robot
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
