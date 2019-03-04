package RiskGame.model.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is the GameMap class, it is a java bean, use for store the map data
 * It including all the information like author, continents and territories as well as the relationship between territories and continents.
 *
 * @author Baiyu Huo
 * @version v1.0.0
 * @see RiskGame.model.service.imp.MapManager
 */
public class  GameMap {
    private String image=" ";
    private String wrap=" ";
    private String scroll=" ";
    private String author=" ";
    private String warn=" ";

    private HashMap<String,Continent> continents = new HashMap<String,Continent>();
    private HashMap<String,Territory> territories = new HashMap<String,Territory>();

    /**
     * getter function for author, it will return the author of this map.
     * @return author the name of the author of this map.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * setter function for author, it set up the information the author of this map.
     * @param author the name of author
     */
    public void setAuthor(String author) {
        this.author = author;
    }



    /**
     * getter function for image path, it will return the path of the image of this map.
     * @return image the path of the image.
     */
    public String getImage() {
        return image;
    }

    /**
     * setter function for image path, it set up the information of image path of this map.
     * @param image the path of the image
     */
    public void setImage(String image) {
        this.image = image;
    }


    /**
     * getter function for scroll type, it will return the type of scroll of this game map.
     * @return scroll the type of scroll.
     */
    public String getScroll() {
        return scroll;
    }


    /**
     * setter function for scroll type, it set up the information map scroll type ,whether it is a vertical map or a horizontal map.
     * @param scroll scroller type.
     */
    public void setScroll(String scroll) {
        this.scroll = scroll;
    }


    /**
     * getter function for wrap type, it will return the type of wrap of this game map.
     * @return wrap the type of wrap of this map.
     */
    public String getWrap() {
        return wrap;
    }

    /**
     * setter function for wrap, it set up the information map wrap, whether it is unwraped or wraped by father or by its container.
     * @param wrap wrap type.
     */
    public void setWrap(String wrap) {
        this.wrap = wrap;
    }


    /**
     * getter function for wrap type, it will return the type of wrap of this game map.
     * @return wrap the type of wrap of this map.
     */
    public String getWarn() {
        return warn;
    }

    /**
     * setter function for won, to represent if this map is allow to send a warn in the beginning of the game.
     * @param warn the information of warn on this map.
     */
    public void setWarn(String warn) {
        this.warn = warn;
    }

    /**
     * getter function for Continents, it will return a list of continents.
     * @return continents a list of continents in this game map..
     */
    public HashMap<String, Continent> getContinents() {
        return continents;
    }

    /**
     * setter function for continents, it is a game element.
     * @param continents the list of continents in the game.
     */
    public void setContinents(HashMap<String, Continent> continents) {
        this.continents = continents;
    }


    /**
     * getter function for territories, it will return a list of territories in this game.
     * @return territories a list of territories.
     */
    public HashMap<String, Territory> getTerritories() {
        return territories;
    }

    /**
     * setter function for territories, it is a game elements.
     * @param territories the list of territories in the game.
     */
    public void setTerritories(HashMap<String, Territory> territories) {
        this.territories = territories;
    }

    /**
     * It used for remove a territory from the map, it will do follow things also
     * <ul>
     *     <li>delete the territory from continent</li>
     *     <li>delete the territory from neibors information </li>
     *     <li>delete the territory from the territories list</li>
     * </ul>
     * @param name the name of territory that needs to be delete.
     */
    public void removeTerrtory(String name){
        for(Continent c:continents.values()){
            if(c.getTerritories().get(name)!=null){
                c.getTerritories().remove(name);
            }
        }
        for(Territory t:territories.values()){
            if(t.getNeighbors().get(name)!=null){
                t.getNeighbors().remove(name);
            }
        }
        territories.remove(name);
    }

    /**
     * print out the gmapMap information in a plain text.
     * This is use for testing.
     */
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
