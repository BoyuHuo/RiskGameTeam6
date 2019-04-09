package RiskGame.model.entity;

import RiskGame.model.service.RiskUtil;
import RiskGame.model.service.imp.GameManager;

import java.util.Map;
import java.util.Random;

public class BenevolentStrategy implements Strategy {
    @Override
    public boolean attack(int movementTime) {
        GameManager.getInstance().setMessage("[Benevolent Player]" + GameManager.getInstance().getActivePlayer().getName() + " loves peace! \n " +
                "and he has finished the attacking phase!\n" +
                "It will automatically move to next step in " + movementTime * 2 + "s");

        RiskUtil.delay(movementTime * 2);

        GameManager.getInstance().nextRound();

        return true;
    }

    @Override
    public boolean reinforce(int movementTime) {
        while (GameManager.getInstance().getActivePlayer().getArmies() > 0) {
            RiskUtil.getActivePlayerWeakestCountry().increaseArmies(GameManager.getInstance().getActivePlayer());
            RiskUtil.delay(movementTime / 2);
        }
        GameManager.getInstance().setMessage("[Benevolent Player]" + GameManager.getInstance().getActivePlayer().getName() + " has finished the Reinforcement phase!\n" +
                "It will automatically move to next step in " + movementTime * 2 + "s");
        RiskUtil.delay(movementTime * 2);
        GameManager.getInstance().nextRound();
        return true;
    }

    @Override
    public boolean fortify(int movementTime) {
        Territory largestTer = RiskUtil.getActivePlayerStrongestCountry();
        Territory weakestTer = RiskUtil.getActivePlayerWeakestCountry();

        if (largestTer != null && weakestTer != null) {
            int armies = largestTer.getArmies() - weakestTer.getArmies();
            if (armies > 1) {
                armies = armies / 2;
            } else if (armies == 1) {
                armies = 1;
            }
            if (GameManager.getInstance().getActivePlayer().immigrantArimies(largestTer.getArmies() - weakestTer.getArmies() / 2, largestTer, weakestTer)) {
                return true;
            }
        }

        for (Territory t : RiskUtil.getAllTerritoryFromPlayer(GameManager.getInstance().getActivePlayer()).values()) {
            if (t != weakestTer && t != largestTer && t.getBelongs() == GameManager.getInstance().getActivePlayer() && t.getArmies() > 0) {
                if (GameManager.getInstance().getActivePlayer().immigrantArimies((t.getArmies()-weakestTer.getArmies())/2, t, largestTer)) {
                    break;
                }
            }
        }

        if(GameManager.getInstance().getGamePhase().equals("Fortification")){
            GameManager.getInstance().nextRound();
        }

        return true;
    }

    @Override
    public boolean startup(int movementTime) {
        Random random = new Random();
        Map<String,Territory> territories = RiskUtil.getAllTerritoryFromPlayer(GameManager.getInstance().getActivePlayer());
        String[] keys = territories.keySet().toArray(new String[0]);
        while(GameManager.getInstance().getActivePlayer().getArmies()>0){
            GameManager.getInstance().getMap().getTerritories().get(keys[random.nextInt(keys.length)]).increaseArmies(GameManager.getInstance().getActivePlayer());
            RiskUtil.delay(movementTime/2);
        }

        RiskUtil.delay(movementTime * 2);
        GameManager.getInstance().nextRound();
        return true;
    }


}
