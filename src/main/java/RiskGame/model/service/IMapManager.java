package RiskGame.model.service;

import RiskGame.model.entity.GameMap;
import RiskGame.model.service.imp.MapManager;

/**
 * This is interface for map manager interface, which contains all the external interface of map related logic function.
 *
 * @author Baiyu Huo
 * @version v1.0.0
 * @see MapManager
 * @since v1.0.0
 */
public interface IMapManager {
    /**
     * This is use for loading the map into games
     * It will convert a *.map file into a map instance, which contains all the information in the map.
     *
     * @param url the path of map file
     * @return gameMap the instance in the game, which represent the whole relationship between territories and continents within the map.
     */
    public GameMap loadMap(String url);
    /**
     * This is use for creating a map file from a GameMap instance
     * It will convert a java instance into map file (*.map), which is in a formal Risk format.
     *
     * @param url     the path of map file
     * @param gameMap the instance that need to be saved
     * @return boolean  True: Create successful  False: Create fail
     */
    public boolean createMap(String url,GameMap gameMap);

    /**
     * This is use for tell if the map is a valid map.
     *
     * @param gameMap the instance that need to be saved
     * @return boolean  True: A valid map  False: A invalid map
     */
    boolean isValided(GameMap gameMap);
}
