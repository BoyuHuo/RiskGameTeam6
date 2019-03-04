package RiskGame.model.entity;

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
    public Player(){}

    /**
     * A constructor for player which can initial the player's name and player's armies.
     */
    public Player(String name, int armies){
        this.name = name;
        this.armies = armies;
    }

    /**
     * getter function for name, it is the nickname for player while playing the game.
     * @return name the name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * setter function for player nickname.
     * @param name the name of player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter function for color, it is the color for player while playing the game.
     * It can used for differenciate the player's territory.
     * @return color the value of the color.
     */
    public String getColor() {
        return color;
    }


    /**
     * setter function for player color value, it needs a color value like #ffffff as the color code
     * @param color the value of the color.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * getter function for cards, it will return the cards information of this player.
     * @return cards card information.
     * @see Card
     */
    public Card getCards() {
        return cards;
    }

    /**
     * setter function for card, it will set up a player's card information.
     * @param cards the card information.
     */
    public void setCards(Card cards) {
        this.cards = cards;
    }



    /**
     * getter function for the number of arimies that players have.
     * @return armies the number of armies.
     */
    public int getArmies() {
        return armies;
    }


    /**
     * setter function for the number of armies that players want to set.
     * @param armies the number of armies.
     */
    public void setArmies(int armies) {
        this.armies = armies;
    }
}
