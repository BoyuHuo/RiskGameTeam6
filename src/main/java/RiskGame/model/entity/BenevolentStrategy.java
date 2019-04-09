package RiskGame.model.entity;

import RiskGame.model.service.RiskUtil;
import RiskGame.model.service.imp.GameManager;

import java.util.Map;
import java.util.Random;

public class BenevolentStrategy implements Strategy {
    @Override
    public Thread attack(int movementTime) {
        Thread thread =new Thread(){
            public void run(){
                try {

                    GameManager.getInstance().setMessage("[Benevolent Player]" + GameManager.getInstance().getActivePlayer().getName() + " loves peace! \n " +
                            "and he has finished the attacking phase!\n" +
                            "It will automatically move to next step in " + movementTime * 2 + "s");

                    Thread.sleep(movementTime * 2);

                    GameManager.getInstance().nextRound();
                } catch (InterruptedException e) { }
            }
        };
        thread.start();
        return thread;
    }

    @Override
    public Thread reinforce(int movementTime) {
        Thread thread = new Thread(){
            public void run(){
                try {
                    while (GameManager.getInstance().getActivePlayer().getArmies() > 0) {
                        if( RiskUtil.getActivePlayerWeakestCountry()!=null) {
                            RiskUtil.getActivePlayerWeakestCountry().increaseArmies(GameManager.getInstance().getActivePlayer());
                        }
                        Thread.sleep(movementTime / 2);
                    }
                    GameManager.getInstance().setMessage("[Benevolent Player]" + GameManager.getInstance().getActivePlayer().getName() + " has finished the Reinforcement phase!\n" +
                            "It will automatically move to next step in " + movementTime * 2 + "s");
                    Thread.sleep(movementTime * 2);
                    GameManager.getInstance().nextRound();
                } catch (InterruptedException e) { }
            }
        };
        thread.start();
        return thread;
    }

    @Override
    public Thread fortify(int movementTime) {
       Thread thread = new Thread(){
            public void run(){
                try {

                    Territory largestTer = RiskUtil.getActivePlayerStrongestCountry();
                    Territory weakestTer = RiskUtil.getActivePlayerWeakestCountry();

                    if (largestTer != null && weakestTer != null&& largestTer!=weakestTer) {
                        int armies = largestTer.getArmies() - weakestTer.getArmies();
                        if (armies > 1) {
                            armies = armies / 2;
                        } else if (armies == 1) {
                            armies = 1;
                        }
                        if (GameManager.getInstance().getActivePlayer().immigrantArimies((largestTer.getArmies() - weakestTer.getArmies()) / 2, largestTer, weakestTer)) {
                            if (GameManager.getInstance().getGamePhase().equals("Fortification")) {
                                GameManager.getInstance().nextRound();
                            }
                            return ;
                        }
                    }

                    for (Territory t : GameManager.getInstance().getMap().getTerritories().values()) {
                        if (t != weakestTer && t != largestTer && t.getBelongs() == GameManager.getInstance().getActivePlayer() && t.getArmies() > 0) {
                            if (GameManager.getInstance().getActivePlayer().immigrantArimies((t.getArmies()-weakestTer.getArmies())/2, t, largestTer)) {
                                break;
                            }
                        }
                    }


                    if(GameManager.getInstance().getGamePhase().equals("Fortification")){
                        GameManager.getInstance().nextRound();
                    }

                } catch (Exception e) { }
            }
        };
       thread.start();
        return thread;
    }

    @Override
    public Thread startup(int movementTime) {
        Thread thread = new Thread(){
            public void run(){
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
                } catch (InterruptedException e) { }
            }
        };
        thread.start();



        return thread;
    }


}
