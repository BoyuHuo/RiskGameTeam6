package test;

import RiskGame.model.entity.GameMap;
import RiskGame.model.service.imp.MapManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This is a Junit test Class, used for testing <b> Map Validation </b> function
 * It contains all test cases which is related to map validation.
 *
 * @author Hao Ma
 * @version  v1.0.0
 * @since v1.0.0
 * @see MapManager
 */

public class TestMapValidation {
    MapManager mapManager;

    /**
     * This is the setUp method before each test case, its main purpose is use for initiate the instance.
     */
    @Before
    public void setUp() {
        mapManager = new MapManager();
    }
    /**
     * test case 1
     * Purpose: testing the Validation of a loaded map
     * Process:
     * <ul>
     *     <li>load a existed map</li>
     *     <li>check if the map is valided</li>
     * </ul>
     *
     */
    @Test
    public void testMapIsValided() {
        GameMap map = mapManager.loadMap(getClass().getResource("/map/PekmonLand.map").getPath());
        assertTrue(mapManager.isValided(map));
    }
    /**
     * test case 2
     * Purpose: testing the Validation of a loaded map
     * Process:
     * <ul>
     *     <li>load a test map(Some Continents of this map are not connected)</li>
     *     <li>check if the map is not valided</li>
     * </ul>
     *
     */
    @Test
    public void testMapWithDisconnectedContientsIsNotValided(){
        GameMap map = mapManager.loadMap(getClass().getResource("/map/PekwrongLandOne.map").getPath());
        assertNull(map);
    }
    /**
     * test case 3
     * Purpose: testing the Validation of a loaded map
     * Process:
     * <ul>
     *     <li>load a test map(The Territories of some continents are not connected)</li>
     *     <li>check if the map is not valided</li>
     * </ul>
     *
     */
    @Test
    public void testMapWithDisconnectedTerritoriesIsNotValided(){
        GameMap map = mapManager.loadMap(getClass().getResource("/map/PekwrongLandTwo.map").getPath());
        assertNull(map);
    }
    /**
     * test case 4
     * Purpose: testing the Validation of a loaded map
     * Process:
     * <ul>
     *     <li>load a test map(There are some mistakes for the control number of continents)</li>
     *     <li>check if the map is not valided</li>
     * </ul>
     *
     */
    @Test
    public void testMapWithIllegalCtrNumIsNotValided(){
        GameMap map = mapManager.loadMap(getClass().getResource("/map/PekwrongLandMix.map").getPath());
        assertNull(map);
    }
}
