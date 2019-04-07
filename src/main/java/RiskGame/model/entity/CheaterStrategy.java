package RiskGame.model.entity;

import RiskGame.model.service.RiskUtil;
import RiskGame.model.service.imp.GameManager;
import sun.security.krb5.internal.crypto.RsaMd5CksumType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheaterStrategy implements Strategy{
    @Override
    public boolean attack(int movementTime) {
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
                            RiskUtil.delay(movementTime);
                            break;
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean reinforce(int movementTime) {
        Map<String,Territory> territories = RiskUtil.getAllTerritoryFromPlayer(GameManager.getInstance().getActivePlayer());
        for(Territory t: territories.values()){
            t.setArmies(t.getArmies()*2);
            GameManager.getInstance().setMessage("[Cheater]"+GameManager.getInstance().getActivePlayer()+" just double the armies in "+t.getName());
            RiskUtil.delay(movementTime/2);
        }
        return true;
    }

    @Override
    public boolean fortify(int movementTime) {
        Map<String,Territory> territories = RiskUtil.getAllTerritoryFromPlayer(GameManager.getInstance().getActivePlayer());
        for(Territory t: territories.values()){
            for(Territory n: t.getNeighbors().values()){
                if(n.getBelongs()!= t.getBelongs()){
                    n.setArmies(n.getArmies()*2);
                }
            }
        }
        return true;
    }
}
