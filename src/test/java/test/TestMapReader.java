package test;

import RiskGame.model.entity.Continent;
import RiskGame.model.entity.GameMap;
import RiskGame.model.service.imp.MapManager;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * This is a Junit test Class, used for testing <b> Map Reading </b> function
 * It contains all test cases which is related to map reading.
 *
 * @author Hao Ma
 * @version  v1.0.0
 * @since v1.0.0
 * @see MapManager
 */
public class TestMapReader {
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
     * Purpose: testing the function of loading a map
     * Process:
     * <ul>
     *     <li>load a existed map</li>
     *     <li>check if the map is not null</li>
     * </ul>
     *
     */

    @Test
    public void testMapLoadNotNull() {
        GameMap map = mapManager.LoadMap(getClass().getResource("/map/1.map").getPath());
        assertNotNull(map);
    }

    /**
     * test case 2
     * Purpose: testing the control number of a Continent
     * Process:
     * <ul>
     *     <li>load a existed map</li>
     *     <li>get a Continent from the map</li>
     *     <li>check if the control number equals to the test case</li>
     * </ul>
     *
     */
    @Test
    public void testMapLoadContinentCtrNum() {
        GameMap map = mapManager.LoadMap(getClass().getResource("/map/PekmonLand.map").getPath());
        Continent c = map.getContinents().get("FireLand");
        assertEquals(5, c.getCtrNum());
    }
    /**
     * test case 3
     * Purpose: testing the Continent of the map
     * Process:
     * <ul>
     *     <li>load a existed map</li>
     *     <li>get a Continent from the map</li>
     *     <li>check if the Continent is not null</li>
     * </ul>
     *
     */
    @Test
    public void testMapContinentNotNull(){
        GameMap map = mapManager.LoadMap(getClass().getResource("/map/PekmonLand.map").getPath());
        Continent d = map.getContinents().get("WindLand");
        assertNotNull(d.getTerritories());
    }

    /**
     * test case 4
     * Purpose: testing the size of Territories or Continents
     * Process:
     * <ul>
     *     <li>load a existed map</li>
     *     <li>get the size of Continentsand the size of Territories of a Continent</li>
     *     <li>check if the results equals the test cases</li>
     * </ul>
     *
     */
    @Test
    public void testMapContinentSize(){
        GameMap map = mapManager.LoadMap(getClass().getResource("/map/PekmonLand.map").getPath());
        int m = map.getContinents().size();
        int h = map.getContinents().get("WindLand").getTerritories().size();
        assertEquals(4,m);
        assertEquals(2,h);
    }
}