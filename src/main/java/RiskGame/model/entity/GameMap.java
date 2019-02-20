package RiskGame.model.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameMap {
    private String image;
    private String wrap;
    private String scroll;
    private String author;
    private String warn;
    private HashMap<String,Continent> continents = new HashMap<String,Continent>();
    private HashMap<String,Territory> territories = new HashMap<String,Territory>();

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public String getScroll() {
        return scroll;
    }

    public void setScroll(String scroll) {
        this.scroll = scroll;
    }

    public String getWrap() {
        return wrap;
    }

    public void setWrap(String wrap) {
        this.wrap = wrap;
    }

    public String getWarn() {
        return warn;
    }

    public void setWarn(String warn) {
        this.warn = warn;
    }

    public HashMap<String, Continent> getContinents() {
        return continents;
    }

    public void setContinents(HashMap<String, Continent> continents) {
        this.continents = continents;
    }

    public HashMap<String, Territory> getTerritories() {
        return territories;
    }

    public void setTerritories(HashMap<String, Territory> territories) {
        this.territories = territories;
    }

    public void print(){
        System.out.println(image+","+author+","+wrap+","+warn+","+scroll);
        System.out.println("-- Contient --");
        for( String s:continents.keySet()){
            System.out.println(continents.get(s).getName()+","+continents.get(s).getCtrNum()+","+continents.get(s).getTerritories().size());
        }
        System.out.println("-- Territory --");
        for( String s:territories.keySet()){
            System.out.println(territories.get(s).getName()+","+territories.get(s).getX()+","+territories.get(s).getNeighbors().size());
        }
    }
}