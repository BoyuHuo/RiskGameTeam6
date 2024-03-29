package RiskGame.model.service.imp;

import RiskGame.model.entity.Continent;
import RiskGame.model.entity.GameMap;
import RiskGame.model.entity.Player;
import RiskGame.model.entity.Territory;
import RiskGame.model.service.IGameManager;
import javafx.application.Preloader;

import java.util.*;

/**
 * This is implement class for game manager interface, which contains all the implementation of game related logic function.
 * It is a singleton instance, which can be used in globle scope.
 *
 * @author Baiyu Huo
 * @version v1.0.0
 * @see IGameManager
 * @since v1.0.0
 */
  public  class  GameManager extends Observable implements IGameManager {


    public enum phase {STARTUP, REINFORCEMENTS, ATTACK, FORTIFICATION}


    private Map<String, Player> players;
    private Iterator playerIterator;
    private Player activePlayer;
    private GameMap map;
    private phase gamePhase;
     private static GameManager instance;
    private String message = "";
    private boolean gameOver;
    private int totalTurn = 0;
    public static int cardSet = 0;


    /**
     * it is used for getting the instance
     * It is a part of singleton pattern.
     *
     * @return instance  it is a instance of this class, it will be only one instance all the time.
     */
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    /**
     * It is an empty constructor.
     */
    private GameManager() {
    }

    /**
     * It used for initial a new game, it will ramdomly assign territory to players and give players the initial armies, and start with start up phase.
     *
     * @see GameManager#initArmies()
     * @see GameManager#start()
     */
    public void newGame() {
        GameManager.getInstance().randomAssignTerritoryToPlayer();
        initArmies();
        playerIterator = players.values().iterator();
        for (Player p : players.values()) {
            p.setLive(true);
            p.updatePrecentageOfMap();
        }
        gameOver = false;
        start();
    }

    /**
     * It used for calculate the number of initial number of armies accroding to the players number.
     * <ul>
     * <li>2 players: 45</li>
     * <li>3 players: 35</li>
     * <li>4 players: 30</li>
     * <li> 5 players: 25</li>
     * <li> 6 players: 20</li>
     * <li>7 players: 15</li>
     * <li>8 players: 10</li>
     * </ul>
     */
    private void initArmies() {
        int armiesNum = 0;
        switch (players.values().size()) {
            case 2:
                armiesNum = 45;
                break;
            case 3:
                armiesNum = 35;
                break;
            case 4:
                armiesNum = 30;
                break;
            case 5:
                armiesNum = 25;
                break;
            case 6:
                armiesNum = 20;
                break;
            case 7:
                armiesNum = 15;
                break;
            case 8:
                armiesNum = 10;
                break;
            default:
                break;
        }
        for (Player p : players.values()) {
            p.setArmies(armiesNum);
        }
    }

    /**
     * Initial the phase of a new game.
     */
    public void start() {
        startUpPhase();
        setMessage("Welcome to Risk world! \n Game Start!\n");

    }

    /**
     * use for end a game, meanwhile clear the game data.
     */
    public void checkGameOver() {
        int playersNum = 0;
        for (Player p : GameManager.getInstance().getPlayers().values()) {
            if (p.isLive() == true) {
                playersNum++;
            }
        }
        if (playersNum <= 1) {
            GameManager.getInstance().setGameOver(true);
        }
    }

    /**
     * set game phase to start up
     */
    public void startUpPhase() {
        gamePhase = phase.STARTUP;
        nextPlayer();
    }

    /**
     * It use for forward the game process, it will decided if it is move to next phase or it should move to next player
     *
     * @see GameManager#nextPlayer()
     * @see GameManager#nextPhase()
     */
    public  synchronized void nextRound() {
        clearMessage();
        switch (gamePhase) {
            case STARTUP:
                Player p = (Player) players.values().toArray()[players.values().size() - 1];
                if (p.equals(activePlayer)) {
                    nextPlayer();
                    nextPhase();
                } else {
                    nextPlayer();
                }
                break;
            case ATTACK:
                nextPhase();
                break;
            case REINFORCEMENTS:
                nextPhase();
                if (!possibleAttack())
                    nextPhase();
                break;
            case FORTIFICATION:
                nextPlayer();
                nextPhase();
                totalTurn++;
                break;
            default:
                break;
        }

        setMessage("[Active Player] " + getActivePlayer().getName() + "\n[Phase] " + getGamePhase() + "\n");
    }


    /**
     * it uses for moving to next player's turn.
     */
    public synchronized void nextPlayer() {
        if (playerIterator == null || !playerIterator.hasNext()) {
            playerIterator = players.values().iterator();
        }
        activePlayer = (Player) playerIterator.next();
        if(!activePlayer.isLive()){
            nextPlayer();
        }
        setMessage(getActivePlayer().getName() + "'s turn! \n");
    }

    /**
     * it uses for moving to phase.
     */
    public void nextPhase() {
        int tag = gamePhase.ordinal();
        tag++;
        if (tag >= phase.values().length) {
            tag = 1;
        }
        gamePhase = phase.values()[tag];
        if (getGamePhase().equals("Reinforcements")) {
            activePlayer.reignforceArmies();
        }
    }

    /**
     * getter for map
     *
     * @return map get a map from game, used for checking the map status.
     */
    public GameMap getMap() {
        return map;
    }

    /**
     * setter for map
     *
     * @param map set a map in the game.
     */
    public void setMap(GameMap map) {
        this.map = map;
    }

    /**
     * setter for players
     *
     * @param players set up a list of players for the game.
     */
    public void setPlayers(Map<String, Player> players) {
        this.players = players;
    }

    /**
     * getter for players
     *
     * @return players get a list of players from the game.
     */
    public Map<String, Player> getPlayers() {
        return this.players;
    }

    /**
     * Add a new player in the game.
     *
     * @param p players instance which needs to be added in the game.
     */
    public void addPlayer(Player p) {
        if (players == null || players.isEmpty()) {
            players = new HashMap<>();
        }
        players.put(p.getName(), p);
    }

    /**
     * remove a player from the game.
     *
     * @param p players instance which needs to be removed from the game.
     */
    public void removePlayer(Player p) {
        players.remove(p.getName());
    }

    /**
     * To get it is which players turn now.
     *
     * @return activePlayer the player instance which is now his turn.
     */
    public Player getActivePlayer() {
        return activePlayer;
    }

    /**
     * Used for move to another player's turn
     *
     * @param activePlayer the player who's next.
     */
    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    /**
     * To get the game phase.
     *
     * @return result the String description of the phase.
     */
    public String getGamePhase() {
        String result = "";
        switch (gamePhase) {
            case STARTUP:
                result = "Start Up";
                break;
            case REINFORCEMENTS:
                result = "Reinforcements";
                break;
            case ATTACK:
                result = "Attack";
                break;
            case FORTIFICATION:
                result = "Fortification";
                break;
        }

        return result;


    }

    /**
     * It used to set the game phase
     *
     * @param gamePhase the game phase
     */
    public void setGamePhase(phase gamePhase) {
        this.gamePhase = gamePhase;
    }

    /**
     * It used to get a iterator from players list. So it can be traveled in a unified way.
     * it is a part of Iterator Pattern
     *
     * @return playerIterator  the iterator of player list object.
     */
    public Iterator getPlayerIterator() {
        return this.playerIterator;
    }

    /**
     * Set up an iterator for player, it is not being used.
     *
     * @param iterator a iterator object for players
     */
    public void setPlayerIterator(Iterator iterator) {
        this.playerIterator = iterator;
    }

    /**
     * It used for cleaning up the game data.So it can start a new game.
     */
    public void cleanUp() {
        this.map = new GameMap();
        this.players = new HashMap<>();
        this.message = "";
        this.totalTurn = 0;
    }

    /**
     * It used for randomly assign Territory to players.
     */
    public void randomAssignTerritoryToPlayer() {
        Random generator = new Random();
        Object[] keys = map.getTerritories().keySet().toArray();

        Iterator<Player> tempIterator = players.values().iterator();
        while (keys.length > 0) {
            if (!tempIterator.hasNext()) {
                tempIterator = players.values().iterator();
            }
            int randomTag = generator.nextInt(keys.length);
            Player player = tempIterator.next();

            map.getTerritories().get(keys[randomTag]).setBelongs(player);
            keys = deleteInArray(randomTag, keys);
        }
    }

    /**
     * It is a tool method, which used for delete an object in array.
     *
     * @param index the index of the object in array.
     * @param array the array that need to edit.
     * @return arrNew the new array which has already delete the underlying object.
     */
    private Object[] deleteInArray(int index, Object array[]) {
        Object[] arrNew = new Object[array.length - 1];
        for (int i = index; i < array.length - 1; i++) {
            array[i] = array[i + 1];
        }
        System.arraycopy(array, 0, arrNew, 0, arrNew.length);
        return arrNew;
    }

    /**
     * it will clear all the message which is records in the GameManager instance
     */
    public void clearMessage() {
        message = "";
        setChanged();
        notifyObservers(this);
    }


    /**
     * it will check if active player can still perform attack behavior in this turn, if not move to next phase automatically.
     *
     * @return boolean true: possible to attack  false: impossible to attack
     */

    public boolean possibleAttack() {
        for (Territory t : map.getTerritories().values()) {
            if (t.getBelongs() == activePlayer) {
                for (Territory neibor : t.getNeighbors().values()) {
                    if (neibor.getBelongs() != activePlayer) {
                        if (t.getArmies() > 0) {
                            return true;
                        }
                    }
                }
            }
        }
        setMessage("No possible to Attack, pass the attacking phase!\n");
        return false;
    }


    /**
     * getter for the message instance
     * @return the message in the game
     */
    public String getMessage() {
        return message;
    }

    /**
     * setter for the message instance
     * @return the message in the game
     */
    public void setMessage(String message) {
        this.message += message;
        setChanged();
        notifyObservers(this);
    }

    /**
     * use for check if the the game is over
     * @return the game over flag
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * use to set the game over flag
     * @param gameOver the game over flag
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * use to get  the total number of game
     * @return the total number of the game
     */
    public int getTotalTurn() {
        return totalTurn;
    }

    /**
     * use to set the total number of the turn
     * @param totalTurn the total number of the game
     */
    public void setTotalTurn(int totalTurn) {
        this.totalTurn = totalTurn;
    }

}
