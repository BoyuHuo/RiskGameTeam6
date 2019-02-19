package RiskGame.test;
import RiskGame.model.entity.GameMap;
import RiskGame.model.service.imp.MapManager;
import org.junit.*;

import static org.junit.Assert.*;

public class TestMapReader {
    MapManager mapManager ;
    @Before
    public void setUp(){
        mapManager=new MapManager();
    }

    @Test
    public void testMapLoadNotNull(){
        GameMap map=mapManager.LoadMap("D:\\1.Study\\Concordia\\SOEN6441 APP\\riskGameTeam6\\map\\2.map");
        assertNotNull(map);
    }
}
