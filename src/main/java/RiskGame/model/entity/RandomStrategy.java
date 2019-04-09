package RiskGame.model.entity;

import RiskGame.model.service.RiskUtil;
import RiskGame.model.service.imp.GameManager;

import java.util.*;

public class RandomStrategy implements Strategy {
    @Override
    public Thread attack(int movementTime) {
        Thread thread =new Thread(){
            public void run(){
                try {
                    Map<String, Territory> neibors = new HashMap<>();
                    Map<String, Territory> territories = RiskUtil.getAllTerritoryFromPlayer(GameManager.getInstance().getActivePlayer());
                    for (Territory t : territories.values()) {
                        for (Territory n : t.getNeighbors().values()) {
                            if (n.getBelongs() != t.getBelongs()) {
                                neibors.put(n.getName(), n);
                            }
                        }
                    }
                    String key = RiskUtil.randomGetATerrKey(neibors);
                    Territory target = neibors.get(key);
                    List<Territory> sources = new ArrayList<>();
                    for (Territory s : target.getNeighbors().values()) {
                        if (s.getBelongs() == GameManager.getInstance().getActivePlayer()) {
                            if (s.getArmies() > 2) {
                                sources.add(s);
                            }
                        }
                    }
                    if (sources.size() <= 0) {
                        GameManager.getInstance().setMessage("[Random Player]" + GameManager.getInstance().getActivePlayer().getName() + "\n randomly choose to attack " + target.getName() + " but he don't have enough armies to attack!" +
                                "\n Next phase will automatically continue in " + movementTime * 2 + " s");
                        Thread.sleep(movementTime * 2);
                        GameManager.getInstance().nextRound();
                    } else {
                        Territory source = sources.get(0);
                        Random rand = new Random();
                        int attackTime = rand.nextInt(5);
                        GameManager.getInstance().setMessage("[Random Player]" + GameManager.getInstance().getActivePlayer().getName() +
                                "\n randomly choose to attack " + target.getName() + " will attacking " + attackTime + " times \n");
                        for (int i = 0; i < attackTime; i++) {
                            int attackingDice = 3;
                            int defDice = 2;
                            if (target.getArmies() <= 0) {
                                GameManager.getInstance().getActivePlayer().captureTerritory(source, target, target.getCaptureDiceNum());
                                break;
                            } else {
                                if (source.getArmies() < 3) {
                                    attackingDice = source.getArmies();
                                }
                                if (target.getArmies() < 2) {
                                    defDice = target.getArmies();
                                }
                                GameManager.getInstance().getActivePlayer().launchAttack(source, target, attackingDice, defDice);
                            }
                            Thread.sleep(movementTime);
                        }
                        GameManager.getInstance().setMessage("[Random Player]" + GameManager.getInstance().getActivePlayer().getName() + " has finished his attacking phase" +
                                "\n Next phase will automatically continue in " + movementTime * 2 + " s");
                        Thread.sleep(movementTime * 2);
                        GameManager.getInstance().nextRound();
                    }

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
                    Map<String,Territory> list = RiskUtil.getAllTerritoryFromPlayer(GameManager.getInstance().getActivePlayer());
                    Random random = new Random();
                    String[] keys = list.keySet().toArray(new String[0]);
                    String key = keys[random.nextInt(keys.length)];
                    while(GameManager.getInstance().getActivePlayer().getArmies()<0){
                        GameManager.getInstance().getMap().getTerritories().get(key).increaseArmies(GameManager.getInstance().getActivePlayer());
                        Thread.sleep(movementTime/2);
                    }

                    GameManager.getInstance().setMessage("[Random Player]" + GameManager.getInstance().getActivePlayer().getName() + " has finished the Reinforcement phase!\n" +
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

        Thread thread =new Thread(){
            public void run(){
                try {
                    Map<String, Territory> territories = RiskUtil.getAllTerritoryFromPlayer(GameManager.getInstance().getActivePlayer());
                    if (territories.size() <= 1) {
                        GameManager.getInstance().nextRound();
                        return ;
                    }
                    String targetKey = RiskUtil.randomGetATerrKey(territories);
                    for (Territory t : territories.values()) {
                        if (t.getName().equals(targetKey)) {
                            continue;
                        } else {
                            Random random = new Random();
                            int armies = random.nextInt(t.getArmies()) + 1;
                            GameManager.getInstance().getActivePlayer().immigrantArimies(armies, t, GameManager.getInstance().getMap().getTerritories().get(targetKey));
                        }
                    }
                    if(GameManager.getInstance().getGamePhase().equals("Fortification")){
                        Thread.sleep(movementTime*2);
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
