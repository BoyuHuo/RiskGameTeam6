package RiskGame.model.entity;

import RiskGame.model.service.RiskUtil;
import RiskGame.model.service.imp.GameManager;

import java.util.Map;
import java.util.Random;

public class AggressiveStrategy implements Strategy {

    @Override
    public boolean attack(int movementTime) {
        Territory largestTer = RiskUtil.getActivePlayerStrongestCountry();
        Map<String, Territory> neibors = largestTer.getNeighbors();
        if (largestTer == null) {
            return false;
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

        return true;
    }

    @Override
    public boolean reinforce(int movementTime) {
        Territory largestTer = RiskUtil.getActivePlayerStrongestCountry();
        while (largestTer != null && GameManager.getInstance().getActivePlayer().getArmies() > 0) {
            largestTer.increaseArmies(GameManager.getInstance().getActivePlayer());

            RiskUtil.delay(movementTime / 2);
        }
        GameManager.getInstance().setMessage("[Aggressive Player]" + GameManager.getInstance().getActivePlayer().getName() + " has finished the Reinforcement phase!\n" +
                "It will automatically move to next step in " + movementTime * 2 + "s");
        RiskUtil.delay(movementTime * 2);
        GameManager.getInstance().nextRound();
        return true;
    }

    @Override
    public boolean fortify(int movementTime) {
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
            here:
            for (Territory t : territories.values()) {
                for (Territory n : t.getNeighbors().values()) {
                    if (n.getBelongs() != t.getBelongs()) {
                        if (GameManager.getInstance().getActivePlayer().immigrantArimies(largestTer.getArmies(), largestTer, t)) {
                            System.out.println("moveeeeeeeeee"+ largestTer.getName()+" to "+t.getName());
                            break here;
                        }
                    }
                }
            }
        }
        if (GameManager.getInstance().getGamePhase().equals("Fortification")) {
            GameManager.getInstance().nextRound();
        }
        return true;
    }

    @Override
    public boolean startup(int movementTime) {
        Random random = new Random();
        Map<String, Territory> territories = RiskUtil.getAllTerritoryFromPlayer(GameManager.getInstance().getActivePlayer());
        String[] keys = territories.keySet().toArray(new String[0]);
        while (GameManager.getInstance().getActivePlayer().getArmies() > 0) {
            GameManager.getInstance().getMap().getTerritories().get(keys[random.nextInt(keys.length)]).increaseArmies(GameManager.getInstance().getActivePlayer());
            RiskUtil.delay(movementTime / 2);
        }

        RiskUtil.delay(movementTime * 2);
        GameManager.getInstance().nextRound();
        return true;
    }


}
