package RiskGame.model.entity;

import java.util.ArrayList;

/**
 * This is the Player class, it is a java bean, use for store the player's information
 * it will contain the information such as player's name, player's color, player's cards, player's armies left and etc..
 *
 * @author Runsen Tian
 * @version v1.0.0
 */
public class Player {
    private String name;
    private String color;
    private Card cards;
    private int armies;

    /**
     * An empty constructor for Player
     */
    public Player() {
    }

    /**
     * A constructor for player which can initial the player's name and player's armies.
     *
     * @param name   name of the player.
     * @param armies number of armies.
     */
    public Player(String name, int armies) {
        this.name = name;
        this.armies = armies;
    }

    /**
     * getter function for name, it is the nickname for player while playing the game.
     *
     * @return name the name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * setter function for player nickname.
     *
     * @param name the name of player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter function for color, it is the color for player while playing the game.
     * It can used for differenciate the player's territory.
     *
     * @return color the value of the color.
     */
    public String getColor() {
        return color;
    }


    /**
     * setter function for player color value, it needs a color value like #ffffff as the color code
     *
     * @param color the value of the color.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * getter function for cards, it will return the cards information of this player.
     *
     * @return cards card information.
     * @see Card
     */
    public Card getCards() {
        return cards;
    }

    /**
     * setter function for card, it will set up a player's card information.
     *
     * @param cards the card information.
     */
    public void setCards(Card cards) {
        this.cards = cards;
    }


    /**
     * getter function for the number of arimies that players have.
     *
     * @return armies the number of armies.
     */
    public int getArmies() {
        return armies;
    }


    /**
     * setter function for the number of armies that players want to set.
     *
     * @param armies the number of armies.
     */
    public void setArmies(int armies) {
        this.armies = armies;
    }


    /**
     * It is used in fortification phase which will be move armies from one territory to another territory
     *
     * @param num         the number of armies tha need to be move from one territory to another territory.
     * @param destination the destination territory
     * @return returns if its valid to immgigrate armies.
     * @see Player#validedToImmgrant(Territory,Territory)
     */
    public boolean immigrantArimies(int num, Territory source, Territory destination) {
        if (!validedToImmgrant(source,destination)) {
            return false;
        } else {
            if (num <= source.getArmies()) {
                source.setArmies(source.getArmies() - num);
                destination.setArmies(num + destination.getArmies());
                return true;
            }
        }
        return false;
    }

    /**
     * It is used to valided if the territory allows to move the arimies to another territory.
     *
     * @param destionation the destination territory.
     * @return returns true if its valid army transfer or not.
     * @see Player#DFS(Territory, Territory, ArrayList)
     */
    public boolean validedToImmgrant(Territory source,Territory destionation) {
        if (!source.getBelongs().getName().equals(this.getName())||!destionation.getBelongs().getName().equals(this.getName()))
            return false;
        else {
            return DFS(source, destionation, new ArrayList<String>());
        }
    }

    /**
     * It is used to travel the neibors territory in a DFS way, while all the territory that may passed by must from the same player.
     *
     * @param current        the current territory.
     * @param destination    the destination territory.
     * @param connectedTerrs the territory list which is already passed.
     */
    private boolean DFS(Territory current, Territory destination, ArrayList<String> connectedTerrs) {
        boolean result = false;

        for (String key : current.getNeighbors().keySet()) {
            Territory neightbor = current.getNeighbors().get(key);
            if (neightbor.getBelongs().equals(destination.getBelongs())) {
                if (neightbor.getName().equals(destination.getName())) {
                    return true;
                }
                if (!connectedTerrs.contains(neightbor.getName())) {
                    connectedTerrs.add(neightbor.getName());
                    result = result || DFS(neightbor, destination, connectedTerrs);
                }
            }

        }
        return result;
    }


}
