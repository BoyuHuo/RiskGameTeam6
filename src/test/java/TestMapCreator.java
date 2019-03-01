import RiskGame.model.entity.Continent;
import RiskGame.model.entity.GameMap;
import RiskGame.model.entity.Territory;
import RiskGame.model.service.imp.MapManager;
import org.junit.*;

import java.io.File;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * This is a Junit Test Class, used for testing <b> Map Creation </b> function
 * It contains all test cases which is related to map creation.
 *
 * @author Baiyu Huo
 * @version  v1.0.0
 * @since v1.0.0
 * @see MapManager
 */

public class TestMapCreator {
    MapManager mapManager;

    /**
     * This is the setUp method before each test case, its main purpose is use for initiate the instance.
     */
    @Before
    public void setUp() {
        mapManager = new MapManager();
    }
    /**
     * This is the clean up method after each test case, to clean the file which generated during the test.
     */
/*    @After
    public void cleanUp(){
        try{
            String encodeUrl = java.net.URLDecoder.decode(getClass().getResource("/map/").getPath()+"NewMap1.map", "utf-8");
            File file = new File(encodeUrl);
            if(file.delete()){
                System.out.println("OK! Test file: "+file.getName() + " has been cleaned");
            }else{
                System.out.println("Fail to delete the test file");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }*/

    /**
     * Test case 1
     * Purpose: testing the function of creating a map
     * Process:
     * <ul>
     *     <li>create a connected map, which contains 3 territory</li>
     *     <li>3 continent, which is also connected sub-graph</li>
     *     <li>save into source folder as NewMap1.map</li>
     *     <li>read the map file</li>
     *     <li>compare the information</li>
     * </ul>
     *
     */
    @Test
    public void testCreate() {
        System.out.println("Test case: Test Map Create has been start...");
        GameMap map = new GameMap();


        map.setAuthor("Baiyu Huo");
        map.setScroll("vertical");
        map.setWarn("no");
        map.setImage("null");
        map.setWrap("no");


        Territory t1= new Territory("Ter1",100,100);
        Territory t2 = new Territory("Ter2",50,50);
        Territory t3 = new Territory("Ter3",80,80);


        t1.getNeighbors().put(t2.getName(),t2);
        t2.getNeighbors().put(t3.getName(),t3);
        t3.getNeighbors().put(t1.getName(),t1);


        Continent c1= new Continent("C1",5);
        Continent c2 = new Continent("C2",15);


        t1.setContinent(c1);
        t2.setContinent(c1);
        t3.setContinent(c2);

        map.getContinents().put(c1.getName(),c1);
        map.getContinents().put(c2.getName(),c2);

        map.getTerritories().put(t1.getName(),t1);
        map.getTerritories().put(t2.getName(),t2);
        map.getTerritories().put(t3.getName(),t3);

        mapManager.CreateMap(getClass().getResource("/map/").getPath()+"NewMap1.map", map);
        GameMap result = mapManager.LoadMap(getClass().getResource("/map/").getPath()+"NewMap1.map");
        assertEquals("Baiyu Huo",result.getAuthor());
        assertEquals(3,result.getTerritories().size());
        assertEquals(2,result.getContinents().size());

    }





}
