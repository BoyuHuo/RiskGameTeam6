package RiskGame.model.entity;

import java.util.HashMap;

public class Territory {
    private String name;
    private int x;
    private int y;
    private Continent continent;
    private HashMap<String,Territory> neighbors = new HashMap<String, Territory>();

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
    }

    public HashMap<String,Territory> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(HashMap neighbors) {
        this.neighbors = neighbors;
    }
}
