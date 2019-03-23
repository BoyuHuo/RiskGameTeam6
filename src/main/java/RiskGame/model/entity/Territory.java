package RiskGame.model.entity;

import RiskGame.model.service.imp.GameManager;
import RiskGame.model.service.imp.RiskUtil;

import java.sql.SQLOutput;
import java.util.*;

/**
 * This is the Territory class, it is a java bean, use for store the territory's information
 * it will contain the information such as territory's name, the position information , the continent that it belongs, the armies that it has and etc..
 *
 * @author Baiyu Huo
 * @version v1.0.0
 */
public class Territory {
    private String name;
    private int x;
    private int y;
    private Continent continent;
    private int armies = 0;
    private Player belongs;
    private HashMap<String, Territory> neighbors = new HashMap<String, Territory>();

    private int captureDiceNum = 0;


    /**
     * A constructor for territory which can initial the territory's name and it position.
     *
     * @param name name of territory
     * @param x    x-coordinate of territory.
     * @param y    y-coordinate of territory.
     */
    public Territory(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    /**
     * An empty constructor for Player
     */
    public Territory() {
    }


    /**
     * getter function for name, it will return the name of territory in this map.
     *
     * @return name the name of the territory.
     */
    public String getName() {
        return name;
    }

    /**
     * setter function for the territory's name.
     *
     * @param name the name of the territory.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter function for x position of the territory , it will return the value of x position of thi territory.
     *
     * @return x the x position of the territory.
     */
    public int getX() {
        return x;
    }

    /**
     * setter function for setup the X position of this territory.
     *
     * @param x the x position of this territory.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * getter function for y position of the territory , it will return the value of y position of thi territory.
     *
     * @return y the y position of the territory.
     */
    public int getY() {
        return y;
    }

    /**
     * setter function for setup the Y position of this territory.
     *
     * @param y the x position of this territory.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * getter function for continent , it will return which continent this territory belongs to.
     *
     * @return continent the continent that this territory belongs.
     */
    public Continent getContinent() {
        return continent;
    }

    /**
     * setter function for continent , it will set up which continent this territory belongs to.
     *
     * @param continent the continent that this territory belongs.
     */
    public void setContinent(Continent continent) {
        if (this.getContinent() != null) {
            this.continent.getTerritories().remove(this.getName());
        }
        this.continent = continent;
        continent.getTerritories().put(name, this);
    }

    /**
     * getter function for neighbors territories , it will return a list of territories which is directly connected to this territory.
     *
     * @return neighbors a list of directly connected territories.
     */
    public HashMap<String, Territory> getNeighbors() {
        return neighbors;
    }

    /**
     * setter function for neighbors territories , it will set up a list of territories which is directly connected to this territory.
     *
     * @param neighbors a list of directly connected territories.
     */
    public void setNeighbors(HashMap neighbors) {
        this.neighbors = neighbors;
    }

    /**
     * getter function for the number of armies, it will return a number of armies tha this territory's has.
     *
     * @return armies the number of armies.
     */
    public int getArmies() {
        return armies;
    }

    /**
     * getter function for the number of armies, it will return a number of armies tha this territory's has.
     *
     * @param armies the number of armies.
     */
    public void setArmies(int armies) {
        this.armies = armies;
    }

    /**
     * it used for increase the number of armies of this territory from a player.
     *
     * @param p which player now assigning the armies to the territory.
     */
    public void increaseArmies(Player p) {
        if (!p.equals(this.belongs)) {
            return;
        }
        if (p.getArmies() > 0) {
            p.setArmies(p.getArmies() - 1);
            armies++;
        }

    }


    /**
     * getter function for getting the instance of the player who own this territory.
     *
     * @return belongs the player instance which has the ownership of this territories.
     */
    public Player getBelongs() {
        return belongs;
    }

    /**
     * setter function for setting the instance of the player who own this territory.
     *
     * @param belongs the player instance which has the ownership of this territories.
     */
    public void setBelongs(Player belongs) {
        this.belongs = belongs;
    }

    /**
     * It is used for add a connected territory to this territory.
     *
     * @param t the territory which will be directly connect to this territory.
     */
    public void addNeibor(Territory t) {
        this.neighbors.put(t.getName(), t);
        t.getNeighbors().put(this.name, this);
    }

    /**
     * It is used for remove a connected territory from this territory.
     *
     * @param t the territory which will be disconnected from this territory.
     */
    public void removeNeibor(Territory t) {
        this.neighbors.remove(t.getName());
    }


    public boolean captureTerritory(Territory target, int moveArmy) {
        if (moveArmy > this.armies) {
            return false;
        } else if (target.getArmies() != 0) {
            return false;
        } else {
            target.setBelongs(this.getBelongs());
            this.armies -= moveArmy;
            target.setArmies(moveArmy);

            target.setCaptureDiceNum(0);
            return true;
        }
    }



    public int getCaptureDiceNum() {
        return captureDiceNum;
    }

    public void setCaptureDiceNum(int captureDiceNum) {
        this.captureDiceNum = captureDiceNum;
    }
}