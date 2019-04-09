package RiskGame.model.entity;

import RiskGame.model.service.RiskUtil;
import RiskGame.model.service.imp.GameManager;

import java.util.Map;
import java.util.Random;

public class AggressiveStrategy implements Strategy {

    @Override
    public void attack(int movementTime) {

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(movementTime);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                test();
            }

            private void test() {
                // TODO Auto-generated method stub

                Territory largestTer = RiskUtil.getActivePlayerStrongestCountry();
                Map<String, Territory> neibors = largestTer.getNeighbors();
                if (largestTer == null) {
                    return;
                }
                for (Territory t : neibors.values()) {
                    if (t.getBelongs() != largestTer.getBelongs()) {
                        if (t.getArmies() <= 0) {
                            GameManager.getInstance().getActivePlayer().captureTerritory(largestTer, t, t.getCaptureDiceNum());
                        } else {
                            GameManager.getInstance().getActivePlayer().allInMode(largestTer, t);
                        }
                        RiskUtil.delay(movementTime);
                    }
                }

                GameManager.getInstance().setMessage("[Aggressive Player]" + GameManager.getInstance().getActivePlayer().getName() + " has finished the attacking phase!\n" +
                        "It will automatically move to next step in " + movementTime * 2 + "s");

                RiskUtil.delay(movementTime * 2);

                GameManager.getInstance().nextRound();
            }

            public Runnable start() {
                // TODO Auto-generated method stub
                return null;
            }
        }.start());

        return;
    }

    @Override
    public void reinforce(int movementTime) {

        new Thread(new Runnable() {
            public void run() {

                    try {
                        Thread.sleep(movementTime);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                test();
            }
            private void test() {
                // TODO Auto-generated method stub


                Territory largestTer = RiskUtil.getActivePlayerStrongestCountry();
                while (largestTer != null && GameManager.getInstance().getActivePlayer().getArmies() > 0) {
                    largestTer.increaseArmies(GameManager.getInstance().getActivePlayer());

                    RiskUtil.delay(movementTime / 2);
                }
                GameManager.getInstance().setMessage("[Aggressive Player]" + GameManager.getInstance().getActivePlayer().getName() + " has finished the Reinforcement phase!\n" +
                        "It will automatically move to next step in " + movementTime * 2 + "s");
                RiskUtil.delay(movementTime * 2);
                GameManager.getInstance().nextRound();
                return;
            }
            public Runnable start() {
                // TODO Auto-generated method stub
                return null;
            }
        }.start());


    }

    @Override
    public void fortify(int movementTime) {

        new Thread(new Runnable() {
            public void run() {

                    try {
                        Thread.sleep(movementTime);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                test();
            }
            private void test() {
                // TODO Auto-generated method stub
                Territory largestTer = RiskUtil.getActivePlayerStrongestCountry();
                for (Territory t : GameManager.getInstance().getMap().getTerritories().values()) {
                    if (t != largestTer && t.getBelongs() == GameManager.getInstance().getActivePlayer() && t.getArmies() > 0) {
                        if (GameManager.getInstance().getActivePlayer().immigrantArimies(t.getArmies(), t, largestTer)) {
                            break;
                        }
                    }
                }
                if (GameManager.getInstance().getGamePhase().equals("Fortification")) {
                    GameManager.getInstance().nextRound();
                }
                return;
            }
            public Runnable start() {
                // TODO Auto-generated method stub
                return null;
            }
        }.start());


    }

    @Override
    public void startup(int movementTime) {

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    test();
                    try {
                        Thread.sleep(movementTime);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            private void test() {
                // TODO Auto-generated method stub

                Random random = new Random();
                Map<String, Territory> territories = RiskUtil.getAllTerritoryFromPlayer(GameManager.getInstance().getActivePlayer());
                String[] keys = territories.keySet().toArray(new String[0]);
                while (GameManager.getInstance().getActivePlayer().getArmies() > 0) {
                    GameManager.getInstance().getMap().getTerritories().get(keys[random.nextInt(keys.length)]).increaseArmies(GameManager.getInstance().getActivePlayer());
                    RiskUtil.delay(movementTime / 2);
                }

                RiskUtil.delay(movementTime * 2);
                GameManager.getInstance().nextRound();
                return ;
            }
            public Runnable start() {
                // TODO Auto-generated method stub
                return null;
            }
        }.start());



    }


}
