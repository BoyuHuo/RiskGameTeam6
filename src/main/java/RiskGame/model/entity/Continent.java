package RiskGame.model.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
