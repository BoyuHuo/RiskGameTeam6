package RiskGame.model.entity;

public class Player {
    private String name;
    private String color;
    private Card cards;
    private int armies;

    public Player(){}
    public Player(String name, int armies){
        this.name = name;
        this.armies = armies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Card getCards() {
        return cards;
    }

    public void setCards(Card cards) {
        this.cards = cards;
    }

    public int getArmies() {
        return armies;
    }

    public void setArmies(int armies) {
        this.armies = armies;
    }
}
