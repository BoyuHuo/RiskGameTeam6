package RiskGame.test;
import RiskGame.model.service.imp.MapManager;
import org.junit.*;

public class TestMapReader {
    MapManager mapManager ;
    @Before
    public void setUp(){
        mapManager = new MapManager();
    }

    @Test
    public void testMapReader1(){
        mapManager.LoadMap("");
    }
}
