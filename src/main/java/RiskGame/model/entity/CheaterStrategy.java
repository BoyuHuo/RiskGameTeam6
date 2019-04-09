package RiskGame.model.entity;

import RiskGame.model.service.RiskUtil;
import RiskGame.model.service.imp.GameManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CheaterStrategy implements Strategy{
    @Override
    public boolean attack(int movementTime) {
        new Thread(){
            public void run(){
                try {
                    Map<String,Territory> territories = RiskUtil.getAllTerritoryFromPlayer(GameManager.getInstance().getActivePlayer());
                    List<Territory> boundryTerr = new ArrayList<>();
                    for(Territory t: territories.values()){
                        for(Territory n: t.getNeighbors().values()){
                            if(n.getBelongs()!= t.getBelongs()){
                                boundryTerr.add(n);
                            }
                        }
                    }

                    for(Territory a: territories.values()){
                        if(a.getArmies()>0) {
                            for (Territory b : a.getNeighbors().values()) {
                                if(b.getBelongs()!=a.getBelongs()){
                                    if(b.getArmies()<=0){
                                        GameManager.getInstance().getActivePlayer().captureTerritory(a,b,b.getCaptureDiceNum());
                                        Thread.sleep(movementTime);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if(GameManager.getInstance().getGamePhase().equals("Attack")) {
                        Thread.sleep(movementTime * 2);
                        GameManager.getInstance().nextRound();
                    }

                } catch (InterruptedException e) { }
            }
        }.start();



        return true;
    }

    @Override
    public boolean reinforce(int movementTime) {
        new Thread(){
            public void run(){
                try {
                    Map<String,Territory> territories = RiskUtil.getAllTerritoryFromPlayer(GameManager.getInstance().getActivePlayer());
                    for(Territory t: territories.values()){
                        t.setArmies(t.getArmies()*2);
                        GameManager.getInstance().setMessage("[Cheater]"+GameManager.getInstance().getActivePlayer()+" just double the armies in "+t.getName());
                        Thread.sleep(movementTime/2);
                    }
                    Thread.sleep(movementTime * 2);
                    GameManager.getInstance().nextRound();
                } catch (InterruptedException e) { }
            }
        }.start();

        return true;
    }

    @Override
    public boolean fortify(int movementTime) {

        new Thread(){
            public void run(){
                try {
                    Map<String,Territory> territories = RiskUtil.getAllTerritoryFromPlayer(GameManager.getInstance().getActivePlayer());
                    for(Territory t: territories.values()){
                        for(Territory n: t.getNeighbors().values()){
                            if(n.getBelongs()!= t.getBelongs()){
                                t.setArmies(t.getArmies()*2);
                                break;
                            }
                        }
                    }
                    if(GameManager.getInstance().getGamePhase().equals("Fortification")){
                        GameManager.getInstance().nextRound();
                    }
                } catch (Exception e) { }
            }
        }.start();


        return true;
    }

    @Override
    public boolean startup(int movementTime) {

        new Thread(){
            public void run(){
                try {
                    Random random = new Random();
                    Map<String,Territory> territories = RiskUtil.getAllTerritoryFromPlayer(GameManager.getInstance().getActivePlayer());
                    String[] keys = territories.keySet().toArray(new String[0]);
                    while(GameManager.getInstance().getActivePlayer().getArmies()>0){
                        GameManager.getInstance().getMap().getTerritories().get(keys[random.nextInt(keys.length)]).increaseArmies(GameManager.getInstance().getActivePlayer());
                        Thread.sleep(movementTime/2);
                    }
                    Thread.sleep(movementTime * 2);
                    GameManager.getInstance().nextRound();
                } catch (InterruptedException e) { }
            }
        }.start();


        return true;
    }
}
