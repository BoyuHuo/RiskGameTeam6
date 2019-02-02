package sample.model.entity;

import java.util.List;

public class Continent {
    private String name;
    private int ctrNum;
    private List<Territory> territories;

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

    public List<Territory> getTerritories() {
        return territories;
    }

    public void setTerritories(List<Territory> territories) {
        this.territories = territories;
    }
}
