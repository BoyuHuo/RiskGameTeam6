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

    @Test
    public void testMapLoadNotNull() {
        GameMap map = mapManager.LoadMap(getClass().getResource("/map/1.map").getPath());
        assertNotNull(map);
    }

    @Test
    public void testMapLoadContinentCtrNum() {
        GameMap map = mapManager.LoadMap("C:\\Users\\14748\\IdeaProjects\\RiskGameTeam6\\map\\PekmonLand.map");
        Continent c = map.getContinents().get("FireLand");
        assertEquals(5, c.getCtrNum());
    }

    ///@Test
    ///public void testContinentNotNull()
    ///{
    ///    GameMap map = mapManager.LoadMap("C:\\Users\\14748\\IdeaProjects\\RiskGameTeam6\\map\\PekmonLand.map");
    ///    Continent c = map.getContinents().get("WaterLand");
    ///    assertNotNull(continets);
    ///}
}