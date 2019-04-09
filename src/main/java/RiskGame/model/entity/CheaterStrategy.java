package RiskGame.model.entity;

import RiskGame.model.service.RiskUtil;
import RiskGame.model.service.imp.GameManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CheaterStrategy implements Strategy {
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
                                boundryTerr.add(n);
                            }
                        }
                    }

                    for (Territory t : boundryTerr) {
                        for (Territory n : t.getNeighbors().values()) {
                            if (t.getBelongs() != n.getBelongs()) {
                                if (n.getArmies() <= 0) {
                                    GameManager.getInstance().getActivePlayer().captureTerritory(t, n, n.getCaptureDiceNum());
                                    Thread.sleep(movementTime);
                                } else {
                                    GameManager.getInstance().getActivePlayer().allInMode(t, n);
                                    if (n.getArmies() > 0) {
                                        GameManager.getInstance().getActivePlayer().captureTerritory(t, n, n.getCaptureDiceNum());
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
                }
            }
        };
        thread.start();

        return thread;
    }

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
