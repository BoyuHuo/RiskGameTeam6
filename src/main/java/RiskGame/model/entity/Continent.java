package RiskGame.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is the Continent class, it is a java bean, use for store the continent data
 * It including the name, control number and the list of territories that belongs to it.
 *
 * @author Sudhanva Muralidhar
 * @version v1.0.0
 */
public class Continent implements Serializable {
    private String name;
    private int ctrNum;
    private HashMap<String,Territory> territories = new HashMap<String,Territory>();

    /**
     * An empty constructor for Continent
     */
    public Continent(){}
    /**
     * The constructor for Continent which can also initiate the continent name and control number
     * @param name the name of continent
     * @param ctrNum the control number of this continent
     */
    public Continent(String name, int ctrNum){
        this.name = name;
        this.ctrNum = ctrNum;
    }

    /**
     * getter function for name, it will return the continent's name
     * @return name the name of continent
     */
    public String getName() {
        return name;
    }

    /**
     * setter function for name, it used for setting the continent's name
     * @param  name the name of continent
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter function for control number, it will return the continent's control number.
     * @return name the name of continent
     */
    public int getCtrNum() {
        return ctrNum;
    }

    /**
     * setter function for control number, it used for setting the continent's control number.
     * @param  ctrNum the control number of the continent
     */
    public void setCtrNum(int ctrNum) {
        this.ctrNum = ctrNum;
    }

    /**
     * getter function for territories list, it will return the list of territories.
     * @return territories the territories which are belong to this continent.
     */
    public HashMap<String, Territory> getTerritories() {
        return territories;
    }

    /**
     * setter function for territories list, it will set up a list of territories for this continent.
     * @param territories the territories which are belong to this continent.
     */
    public void setTerritories(HashMap<String, Territory> territories) {
        this.territories = territories;
    }
}
