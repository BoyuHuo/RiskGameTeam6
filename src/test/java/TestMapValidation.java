import RiskGame.model.entity.GameMap;
import RiskGame.model.service.imp.MapManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This is a Junit Test Class, used for testing <b> Map Validation </b> function
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

///    @Test
///    public void testMapIsValided() {
 ///       GameMap map = mapManager.LoadMap(getClass().getResource("/map/PekmonLand.map").getPath());
 ///       assertTrue(mapManager.IsValided(map));
    }
}
