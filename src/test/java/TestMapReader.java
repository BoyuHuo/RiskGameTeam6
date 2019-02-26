import RiskGame.model.entity.Continent;
import RiskGame.model.entity.GameMap;
import RiskGame.model.service.imp.GameManager;
import RiskGame.model.service.imp.MapManager;
import org.junit.*;

import static org.junit.Assert.*;

public class TestMapReader {
    MapManager mapManager;

    @Before
    public void setUp() {
        mapManager = new MapManager();
    }


    /**
    * This is the test method which can check if the loaded map is not null.
    * @author Hao Ma
    * @version 1.0.0
     */
    @Test
    public void testMapLoadNotNull() {
        GameMap map = mapManager.LoadMap(getClass().getResource("/map/1.map").getPath());
        assertNotNull(map);
    }
    /**
     * This is the test method which can check if the CtrNum is correct.
     * @author Hao Ma
     * @version 1.0.0
     */
    @Test
    public void testMapLoadContinentCtrNum() {
        GameMap map = mapManager.LoadMap(getClass().getResource("/map/PekmonLand.map").getPath());
        Continent c = map.getContinents().get("FireLand");
        assertEquals(5, c.getCtrNum());
    }
    /**
     * This is the test method which can check if the Continent is not null.
     * @author Hao Ma
     * @version 1.0.0
     */
    @Test
    public void testMapContinentNotNull(){
        GameMap map = mapManager.LoadMap(getClass().getResource("/map/PekmonLand.map").getPath());
        Continent d = map.getContinents().get("WindLand");
        assertNotNull(d.getTerritories());
    }
}