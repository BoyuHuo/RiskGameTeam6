package sample.model.entity;

import java.util.List;

public class Map {
    private String map;
    private String author;
    private List<Contient>  contients;
    private List<Territory> territories;

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Contient> getContients() {
        return contients;
    }

    public void setContients(List<Contient> contients) {
        this.contients = contients;
    }

    public List<Territory> getTerritories() {
        return territories;
    }

    public void setTerritories(List<Territory> territories) {
        this.territories = territories;
    }
}
