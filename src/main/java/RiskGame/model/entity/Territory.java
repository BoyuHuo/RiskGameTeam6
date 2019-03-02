package RiskGame.model.entity;

import java.util.ArrayList;
import java.util.HashMap;

public class Territory {
    private String name;
    private int x;
    private int y;
    private Continent continent;
    private int armies = 0;
    private Player belongs;
    private HashMap<String, Territory> neighbors = new HashMap<String, Territory>();

    public Territory(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public Territory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
        continent.getTerritories().put(name, this);
    }

    public HashMap<String, Territory> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(HashMap neighbors) {
        this.neighbors = neighbors;
    }

    public int getArmies() {
        return armies;
    }

    public void setArmies(int armies) {
        this.armies = armies;
    }

    public void increaseArmies(Player p) {
        if (!p.equals(this.belongs)) {
            return;
        }
        if (p.getArmies() > 0) {
            p.setArmies(p.getArmies() - 1);
            armies++;
        }

    }


    public Player getBelongs() {
        return belongs;
    }

    public void setBelongs(Player belongs) {
        this.belongs = belongs;
    }

    public void addNeibor(Territory t) {
        this.neighbors.put(t.getName(), t);
        //t.getNeighbors().put(this.name,this);
    }

    public void removeNeibor(Territory t) {
        this.neighbors.remove(t.getName());
    }

    public boolean immigrantArimies(int num, Territory destination) {
        if (num <= this.armies) {
            this.armies -= num;
            destination.setArmies(num + destination.getArmies());
            return true;
        }

        return false;
    }

    public boolean validedToImmgrant(Territory destionation){
        if(!belongs.getName().equals(destionation.getBelongs().getName()))
           return false;
        else{
            return DFS(this,destionation,new ArrayList<String>());
        }
    }


    private boolean DFS(Territory current, Territory destination,ArrayList<String> connectedTerrs) {
        boolean result = false;

            for (String key : current.getNeighbors().keySet()) {
                Territory neightbor = current.getNeighbors().get(key);
                if (neightbor.getBelongs().equals(destination.getBelongs())) {
                    if(neightbor.getName().equals(destination.getName())){
                        return true;
                    }
                    if (!connectedTerrs.contains(neightbor.getName())) {
                        connectedTerrs.add(neightbor.getName());
                       result=result||DFS(neightbor,destination, connectedTerrs);
                    }
                }

            }
            return result;
    }

}
