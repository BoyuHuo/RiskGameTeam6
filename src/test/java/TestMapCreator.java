import RiskGame.model.entity.Continent;
import RiskGame.model.entity.GameMap;
import RiskGame.model.service.imp.MapManager;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestMapCreator {
    MapManager mapManager;

    @Before
    public void setUp() {
        mapManager = new MapManager();
    }

    @Test
    public void testCreate() {
        GameMap map=new GameMap();

       // GameMap map = mapManager.CreateMap("");
        assertNotNull(map);
    }


}
