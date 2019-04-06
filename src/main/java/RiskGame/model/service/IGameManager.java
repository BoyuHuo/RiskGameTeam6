package RiskGame.model.service;

import RiskGame.model.entity.GameMap;
import RiskGame.model.entity.Player;
import RiskGame.model.service.imp.GameManager;

import java.util.Map;

/**
 * This is interface class for game manager interface, which contains all the implementation of game related logic function.
 *
 * @author Baiyu Huo
 * @version v1.0.0
 * @see IGameManager
 * @since v1.0.0
 */
public interface IGameManager {
    /**
     * It used for initial a new game, it will ramdomly assign territory to players and give players the initial armies, and start with start up phase.
     * @see GameManager#start()
     */
    public void newGame();
    /**
     *  setter for players
     * @param players set up a list of players for the game.
     */
    public void setPlayers(Map<String, Player> players);
    /**
     *  getter for map
     * @return GameMap get a map from game, used for checking the map status.
     */
    public GameMap getMap();
    /**
     *  Add a new player in the game.
     * @param p players instance which needs to be added in the game.
     */
    public void addPlayer(Player p);
    /**
     *  remove a player from the game.
     * @param p players instance which needs to be removed from the game.
     */
    public void removePlayer(Player p);
    /**
     *  To get it is which players turn now.
     * @return Player the player instance which is now his turn.
     */
    public Player getActivePlayer();
    /**
     *  To get the game phase.
     * @return String the String description of the phase.
     */
    public String getGamePhase();
    /**
     * It used for randomly assign Territory to players.
     */
    public void randomAssignTerritoryToPlayer();
    /**
     * use for end a game, meanwhile clear the game data.
     */
    public void checkGameOver();


}
