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



    /**
     * It is used to travel the neibors territory in a DFS way, while all the territory that may passed by must from the same player.
     *
     *
     * @param target      your attacking target.
     * @param diceNumAtt  the dice number of the attacker.
     * @param diceNumDef  the dice number of the defender.
     * @return int   0: successful
     *               -1: you dont have enough arimies to attack
     *               -2: attacking your own territory
     *               -3: attacking a Ter which is not a direct neibor
     */
    public int launchAttack(Territory target, int diceNumAtt, int diceNumDef) {
        if (this.getArmies() <= 0 ) {
            return -1;
        } else if(target.getBelongs() == this.getBelongs()){
            return -2;
        }
        else if (this.getNeighbors().get(target.getName()) == null) {
            return -3;
        } else {

            int[] diceValueAtt = new int[diceNumAtt];
            int[] diceValueDef = new int[diceNumDef];

            GameManager.getInstance().setMessage("[Attacker] " + this.getName() + "'s dices: ");

            for (int i = 0; i < diceValueAtt.length; i++) {
                diceValueAtt[i] = randomRoll();

                GameManager.getInstance().setMessage(diceValueAtt[i] + " ");
            }

            GameManager.getInstance().setMessage("\n");
            GameManager.getInstance().setMessage("[Defender] " + target.getName() + "'s dices: ");

            for (int j = 0; j < diceValueDef.length; j++) {
                diceValueDef[j] = randomRoll();

                GameManager.getInstance().setMessage(diceValueDef[j] + " ");
            }
            GameManager.getInstance().setMessage("\n");

            String result = compareDiceSet(diceValueAtt, diceValueDef);
            String[] resInt = result.split(":");

            GameManager.getInstance().setMessage("[Attacker] " + this.getName() + "'s Total toll: " + resInt[0]+"\n");
            GameManager.getInstance().setMessage("[Defender] " + target.getName() + "'s Total toll:" + resInt[1]+"\n");

            this.setArmies(armies - Integer.parseInt(resInt[0]));
            target.setArmies(target.getArmies() - Integer.parseInt(resInt[1]));

            if (target.getArmies() == 0) {
                target.setCaptureDiceNum(diceNumAtt);
            }
            return 0;
        }
    }

    public boolean allInMode(Territory target) {
        while (this.armies > 0 && target.getArmies() > 0) {
            int attackDiceNum = 3;
            int defDiceNum = 2;
            if (attackDiceNum > this.armies) {
                attackDiceNum = this.armies;
            }
            if (defDiceNum > target.getArmies()) {
                defDiceNum = target.getArmies();
            }
            launchAttack(target, attackDiceNum, defDiceNum);
        }
        return true;
    }

    private int randomRoll() {
        Random r = new Random();
        return r.nextInt(6) + 1;
    }

    private String compareDiceSet(int[] att, int[] def) {
        int compareNum = att.length > def.length ? def.length : att.length;
        int attDeath = 0;
        int defDeath = 0;
        Arrays.sort(att);
        Arrays.sort(def);
        for (int i = 0; i < compareNum; i++) {
            if (att[att.length - i - 1] > def[def.length - i - 1]) {
                defDeath++;
            } else {
                attDeath++;
            }
        }
        return attDeath + ":" + defDeath;
    }

    public int getCaptureDiceNum() {
        return captureDiceNum;
    }

    public void setCaptureDiceNum(int captureDiceNum) {
        this.captureDiceNum = captureDiceNum;
    }
}