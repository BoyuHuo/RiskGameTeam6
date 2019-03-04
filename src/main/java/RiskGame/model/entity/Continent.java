package RiskGame.model.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is the Continent class, it is a java bean, use for store the continent data
 * It including the name, control number and the list of territories that belongs to it.
 *
 * @author Baiyu Huo
 * @version v1.0.0
 */
public class Continent {
    private String name;
    private int ctrNum;
    private HashMap<String,Territory> territories = new HashMap<String,Territory>();

    public Continent(){}
    public Continent(String name, int ctrNum){
        this.name = name;
        this.ctrNum = ctrNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCtrNum() {
        return ctrNum;
    }

    public void setCtrNum(int ctrNum) {
        this.ctrNum = ctrNum;
    }

    public HashMap<String, Territory> getTerritories() {
        return territories;
    }

    public void setTerritories(HashMap<String, Territory> territories) {
        this.territories = territories;
    }
}
