package RiskGame.model.entity;

import RiskGame.model.service.imp.GameManager;
import javafx.scene.control.Alert;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.*;

/**
 * This is the Player class, it is a java bean, use for store the player's information
 * it will contain the information such as player's name, player's color, player's cards, player's armies left and etc..
 *
 * @author Runsen Tian
 * @version v1.0.0
 */
public class Player implements Serializable {
    private String name;
    private String color;
    private Strategy strategy;
    private HashMap<CardType, Integer> cards = new HashMap<>();
    private int armies;
    private boolean live = true;
    private double percentageOfMap;

    /**
     * An empty constructor for Player
     */
    public Player() {
    }

    public Player(Strategy strategy) {
        this.strategy = strategy;
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

    public Player(String name, Strategy strategy) {
        this.name = name;
        this.strategy = strategy;
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
    public HashMap<CardType, Integer> getCards() {
        return cards;
    }

    /**
     * setter function for card, it will set up a player's card information.
     *
     * @param cards the card information.
     */
    public void setCards(HashMap<CardType, Integer> cards) {
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
     * take the cards from other players, it only called when defeat a player
     *
     * @param defenderPlayer the player who has been defeated.
     */
    public void takeCardFromOthers(Player defenderPlayer) {

        Player attackingPlayer = this;
        for (Map.Entry<CardType, Integer> card : defenderPlayer.getCards().entrySet()) {
            if (attackingPlayer.getCards().containsKey(card.getKey())) {
                attackingPlayer.getCards().put(card.getKey(), attackingPlayer.getCards().get(card.getKey()) + 1);
            } else {
                attackingPlayer.getCards().put(card.getKey(), 1);
            }
        }

    }

    /**
     * add a card to player
     *
     * @param card the card which will given to the player.
     */
    public void addCard(CardType card) {

        Player attackingPlayer = this;
        if (attackingPlayer.getCards().containsKey(card)) {
            attackingPlayer.getCards().put(card, attackingPlayer.getCards().get(card) + 1);
        } else {
            attackingPlayer.getCards().put(card, 1);
        }
    }

    /**
     * add a cards to player
     *
     * @param cardlist the card which will given to the player.
     */
    public void addCard(HashMap<CardType, Integer> cardlist) {

        Player attackingPlayer = this;
        for (Map.Entry<CardType, Integer> card : cardlist.entrySet()) {

            if (attackingPlayer.getCards().containsKey(card.getKey())) {
                attackingPlayer.getCards().put(card.getKey(), attackingPlayer.getCards().get(card.getKey()) + card.getValue());
            } else {
                attackingPlayer.getCards().put(card.getKey(), 1);
            }

        }

    }

    /**
     * add a random card for player
     */
    public void addRandomCard() {

        Random random = new Random();
        CardType randomCard = CardType.values()[random.nextInt(CardType.values().length)];
        this.addCard(randomCard);
    }


    /**
     * It is used in fortification phase which will be move armies from one territory to another territory
     *
     * @param source      the source territory
     * @param num         the number of armies tha need to be move from one territory to another territory.
     * @param destination the destination territory
     * @return returns if its valid to immgigrate armies.
     * @see Player#validedToImmgrant(Territory, Territory)
     */
    public boolean immigrantArimies(int num, Territory source, Territory destination) {
        if (!validedToImmgrant(source, destination)) {
            return false;
        } else {
            if (num <= source.getArmies()) {
                source.setArmies(source.getArmies() - num);
                destination.setArmies(num + destination.getArmies());
                GameManager.getInstance().setMessage(source.getName() + " has moved " + num + " army(ies) to " + destination.getName() + "\n");
                return true;
            }
        }
        return false;
    }

    /**
     * It is used to valided if the territory allows to move the arimies to another territory.
     *
     * @param destionation the destination territory.
     * @param source       the source city that wanna move the armies
     * @return returns true if its valid army transfer or not.
     * @see Player#DFS(Territory, Territory, ArrayList)
     */
    public boolean validedToImmgrant(Territory source, Territory destionation) {
        if (!source.getBelongs().getName().equals(this.getName()) || !destionation.getBelongs().getName().equals(this.getName())) {
            return false;
        } else {
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

    /**
     * use for giving the player the curtain number of reinforcement armies.
     *
     * @return boolean true: function successful calculated false: there are something wrong, player cannot get the reinforcement armies.
     */
    public boolean reignforceArmies() {
        int controlNum = 0;
        int armiesFromTerr = 0;
        int terrNum = 0;
        for (Territory t : GameManager.getInstance().getMap().getTerritories().values()) {
            if (t.getBelongs().getName().equals(this.getName())) {
                terrNum++;
            }
        }
        if ((terrNum / 3) < 3) {
            armiesFromTerr = 3;
        } else {
            armiesFromTerr = terrNum / 3;
        }
        if (this.getName().equals(GameManager.getInstance().getActivePlayer().getName())) {
            for (Continent c : GameManager.getInstance().getMap().getContinents().values()) {
                int countBelongs = 0;
                for (Territory t : c.getTerritories().values()) {
                    if (t.getBelongs().getName().equals(this.getName())) {
                        countBelongs++;
                    }
                }
                if (countBelongs == c.getTerritories().size()) {
                    controlNum += c.getCtrNum();
                }
            }
        }
        this.setArmies(this.getArmies() + controlNum + armiesFromTerr);
        int result = controlNum + armiesFromTerr;
        GameManager.getInstance().setMessage(this.getName() + " get " + result + " reinforce armies!\n");
        return true;
    }


    /**
     * It is used to travel the neibors territory in a DFS way, while all the territory that may passed by must from the same player.
     *
     * @param target     your attacking target.
     * @param source     the attacker's territory
     * @param diceNumAtt the dice number of the attacker.
     * @param diceNumDef the dice number of the defender.
     * @return int   0: successful
     * -1: you dont have enough arimies to attack
     * -2: attacking your own territory
     * -3: attacking a Ter which is not a direct neibor
     */
    public int launchAttack(Territory source, Territory target, int diceNumAtt, int diceNumDef) {
        if (source.getArmies() <= 0) {
            return -1;
        } else if (target.getBelongs() == source.getBelongs()) {
            return -2;
        } else if (source.getNeighbors().get(target.getName()) == null) {
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

            GameManager.getInstance().setMessage("[Attacker] " + this.getName() + "'s Total toll: " + resInt[0] + "\n");
            GameManager.getInstance().setMessage("[Defender] " + target.getName() + "'s Total toll:" + resInt[1] + "\n");

            source.setArmies(source.getArmies() - Integer.parseInt(resInt[0]));
            target.setArmies(target.getArmies() - Integer.parseInt(resInt[1]));

            if (target.getArmies() == 0) {
                target.setCaptureDiceNum(diceNumAtt);
            }
            return 0;
        }
    }

    /**
     * all in mode is a method that attacker can use for use all of his arimies to attack the target territory
     *
     * @param target your attacking target.
     * @param source attacker's territory
     * @return boolean   true: successful  false: unsuccessful
     */

    public boolean allInMode(Territory source, Territory target) {
        while (source.getArmies() > 0 && target.getArmies() > 0) {
            int attackDiceNum = 3;
            int defDiceNum = 2;
            if (attackDiceNum > source.getArmies()) {
                attackDiceNum = source.getArmies();
            }
            if (defDiceNum > target.getArmies()) {
                defDiceNum = target.getArmies();
            }
            launchAttack(source, target, attackDiceNum, defDiceNum);
        }
        return true;
    }

    /**
     * use for randomly roll a dice
     *
     * @return int the dice result that you roll
     */
    private int randomRoll() {
        Random r = new Random();
        return r.nextInt(6) + 1;
    }

    /**
     * use for comparing 2 sets of dice, and calculate the number of troll in this battle
     *
     * @return String result will be in "attarcker's troll:defender's troll"
     */
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

    /**
     * use for comparing 2 sets of dice, and calculate the number of troll in this battle
     *
     * @param source   the territory which lanuch the attack and want to capture another city
     * @param target   the territory which is about to be captured by another player
     * @param moveArmy the number of armies that player want to move to new captured territory
     * @return String result will be in "attarcker's troll:defender's troll"
     */
    public int captureTerritory(Territory source, Territory target, int moveArmy) {
        Player defender = target.getBelongs();
        if (moveArmy < target.getCaptureDiceNum()) {
            return -1;
        }
        if (moveArmy > source.getArmies()) {
            return -2;
        } else if (target.getArmies() != 0) {
            return -3;
        } else {
            target.setBelongs(source.getBelongs());
            source.setArmies(source.getArmies() - moveArmy);
            target.setArmies(moveArmy);
            target.setCaptureDiceNum(0);
            this.updatePrecentageOfMap();
            defender.updatePrecentageOfMap();
            GameManager.getInstance().setMessage("Player: " + source.getBelongs().getName() + " has captured the territory " + target.getName() + "\n");
            return 0;
        }
    }

    /**
     * is this player still alive in the game?
     *
     * @return boolean True: alive False: dead
     */
    public boolean isLive() {
        return live;
    }

    /**
     * use for chaning the live status of a player
     *
     * @param live the status that you wanna set
     */
    public void setLive(boolean live) {
        this.live = live;
    }

    /**
     * get the percentage of map this player already covered
     *
     * @return percentageOfMap the percentage of the map this player already occupied
     */
    public double getPrecentageOfMap() {
        return percentageOfMap;
    }


    /**
     * use for calculate the coverage percentage of the map for this player
     */
    public void updatePrecentageOfMap() {
        if (live == false) {
            percentageOfMap = 0.0;
            return;
        } else {
            double totalTer = GameManager.getInstance().getMap().getTerritories().size();
            double myTer = 0.0;
            for (Territory t : GameManager.getInstance().getMap().getTerritories().values()) {
                if (t.getBelongs() == this) {
                    myTer++;
                }
            }
            percentageOfMap = myTer / totalTer * 100;
            if (getPrecentageOfMap() == 0) {
                live = false;
            }
            DecimalFormat fmt = new DecimalFormat("##0.0");
            fmt.format(percentageOfMap);
            if (percentageOfMap <= 0) {
                this.live = false;
            }
            GameManager.getInstance().checkGameOver();
        }

    }

    /**
     * use for get how many continent this player already control
     *
     * @return result the list of continent that this player control
     */
    public List<Continent> getControlContinent() {
        List<Continent> result = new ArrayList<>();
        for (Continent c : GameManager.getInstance().getMap().getContinents().values()) {
            boolean blongFlag = true;
            for (Territory t : c.getTerritories().values()) {
                if (t.getBelongs() != this) {
                    blongFlag = false;
                    break;
                }
            }
            if (blongFlag) {
                result.add(c);
            }
        }
        return result;
    }


    /**
     * use for trading the card for armies
     *
     * @param cardList the list of card that player wanna trade
     * @param player   the player who wanna trade the card
     * @return boolean True：trade successful False: trade unsuccessful
     */
    public boolean cardTrade(ArrayList<CardType> cardList, Player player) {

        if (isValidSet(cardList)) {
            GameManager.cardSet++;
            HashMap<CardType, Integer> playerCards = player.getCards();
            for (CardType card : cardList) {

                if (playerCards.containsKey(card)) {
                    playerCards.put(card, playerCards.get(card) - 1);
                }

            }

            switch (GameManager.cardSet) {

                case 1:
                    player.setArmies(player.getArmies() + 4);
                    break;
                case 2:
                    player.setArmies(player.getArmies() + 6);
                    break;
                case 3:
                    player.setArmies(player.getArmies() + 8);
                    break;
                case 4:
                    player.setArmies(player.getArmies() + 10);
                    break;
                case 5:
                    player.setArmies(player.getArmies() + 12);
                    break;
                case 6:
                    player.setArmies(player.getArmies() + 15);
                    break;
            }

            if (GameManager.cardSet >= 7) {
                player.setArmies(player.getArmies() + ((GameManager.cardSet - 6) * 5) + 15);
            }

            GameManager.getInstance().setMessage("Trade Successful!");
            return true;
        }

        return false;
    }

    /**
     * use for checking if the trading cards is valid to trade
     *
     * @param cardList the list of card that player wanna trade
     * @return boolean True：trade successful False: trade unsuccessful
     */
    public boolean isValidSet(ArrayList<CardType> cardList) {

        int infantryCount = 0, cavalaryCount = 0, artilleryCount = 0;

        for (CardType card : cardList) {

            switch (card) {

                case CAVALRY:
                    cavalaryCount++;
                    break;

                case INFANTRY:
                    infantryCount++;
                    break;

                case ARTILLERY:
                    artilleryCount++;
                    break;

            }

        }

        if (infantryCount == 1 && cavalaryCount == 1 && artilleryCount == 1)
            return true;
        if (infantryCount == 3)
            return true;

        if (cavalaryCount == 3)
            return true;

        if (artilleryCount == 3)
            return true;

        return false;

    }

    public boolean excuteAttackStrategy(int ms) {
        if (isHuman()) {
            return false;
        }
        strategy.attack(ms);
        return true;
    }

    public boolean excuteReinforceStrategy(int ms) {
        if (isHuman()) {
            return false;
        }
        strategy.reinforce(ms);
        return true;
    }

    public boolean excuteFortifyStrategy(int ms) {
        if (isHuman()) {
            return false;
        }
        strategy.fortify(ms);
        return true;
    }

    public boolean excuteStartupStrategy(int ms) {
        if (isHuman()) {
            return false;
        }
        strategy.startup(ms);
        return true;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public boolean isHuman() {
        if (strategy instanceof HumanStrategy) {
            return true;
        } else {
            return false;
        }
    }
}
