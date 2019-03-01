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
    @After
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
    }

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

    /**
     * Test case 2
     * Purpose: testing the function of creating a map
     * Process:
     * <ul>
     *     <li>create a connected map, which contains 5 territory</li>
     *     <li>3 continents which are also connected sub-graph</li>
     *     <li>save into source folder as NewMap1.map</li>
     *     <li>read the map file</li>
     *     <li>check if the information is correct</li>
     * </ul>
     *
     */
    @Test
    public void testCreateTwo() {
        System.out.println("Test case 2: Test Map Create has been start...");
        GameMap map = new GameMap();
        map.setAuthor("Hao Ma");
        map.setScroll("vertical");
        map.setWarn("no");
        map.setImage("null");
        map.setWrap("no");
        Territory f1= new Territory("Fire1",100,100);
        Territory f2 = new Territory("Fire2",100,150);
        Territory w1 = new Territory("Water1",200,150);
        Territory w2= new Territory("Water2",200,200);
        Territory i1= new Territory("Ice1",300,100);
        f1.getNeighbors().put(f2.getName(),f2);
        f2.getNeighbors().put(w1.getName(),w1);
        w1.getNeighbors().put(w2.getName(),w2);
        w2.getNeighbors().put(i1.getName(),i1);
        i1.getNeighbors().put(f1.getName(),f1);
        Continent c = new Continent("Canada",22);
        Continent m = new Continent("Montrea",16);
        Continent q = new Continent("Quebec",18);
        f1.setContinent(c);
        f2.setContinent(c);
        w1.setContinent(m);
        w2.setContinent(m);
        i1.setContinent(q);
        map.getContinents().put(c.getName(),c);
        map.getContinents().put(m.getName(),m);
        map.getContinents().put(q.getName(),q);
        map.getTerritories().put(f1.getName(),f1);
        map.getTerritories().put(f2.getName(),f2);
        map.getTerritories().put(w1.getName(),w1);
        map.getTerritories().put(w2.getName(),w2);
        map.getTerritories().put(i1.getName(),i1);

        mapManager.CreateMap(getClass().getResource("/map/").getPath()+"NewMap1.map", map);
        GameMap result = mapManager.LoadMap(getClass().getResource("/map/").getPath()+"NewMap1.map");
        assertEquals("Hao Ma",result.getAuthor());
        assertEquals(3,result.getContinents().size());
        assertEquals(5,result.getTerritories().size());
        assertEquals(16,m.getCtrNum());
        assertEquals(22,c.getCtrNum());
    }

    /**
     * Test case 3
     * Purpose: testing the function of creating a map
     * Process:
     * <ul>
     *     <li>create a test map, which contains 4 territory</li>
     *     <li>3 continents which have some disconnected relationship</li>
     *     <li>save into source folder as NewMap1.map</li>
     *     <li>read the map file</li>
     *     <li>check if the information is correct</li>
     * </ul>
     *
     */
    @Test
    public void testCreateThree() {
        System.out.println("Test case 3: Test Map Create has been start...");
        GameMap map = new GameMap();
        map.setAuthor("Hao Ma");
        map.setScroll("vertical");
        map.setWarn("no");
        map.setImage("null");
        map.setWrap("no");
        Territory w1= new Territory("Wind1",125,125);
        Territory w2 = new Territory("Wind2",125,175);
        Territory s1 = new Territory("Sun1",225,175);
        Territory m1= new Territory("Moon1",325,125);
        w1.getNeighbors().put(w2.getName(),w2);
        s1.getNeighbors().put(m1.getName(),m1);
        Continent a = new Continent("Apple",23);
        Continent b = new Continent("Banana",12);
        Continent p = new Continent("Peach",16);
        w1.setContinent(a);
        w2.setContinent(a);
        s1.setContinent(b);
        m1.setContinent(p);
        map.getContinents().put(a.getName(),a);
        map.getContinents().put(b.getName(),b);
        map.getContinents().put(p.getName(),p);
        map.getTerritories().put(w1.getName(),w1);
        map.getTerritories().put(w2.getName(),w2);
        map.getTerritories().put(s1.getName(),s1);
        map.getTerritories().put(m1.getName(),m1);

        mapManager.CreateMap(getClass().getResource("/map/").getPath()+"NewMap1.map", map);
        GameMap result = mapManager.LoadMap(getClass().getResource("/map/").getPath()+"NewMap1.map");
    }

    /**
     * Test case 4
     * Purpose: testing the function of creating a map
     * Process:
     * <ul>
     *     <li>create a test map, which contains 3 continents</li>
     *     <li>4 territories which have some disconnected relationship</li>
     *     <li>save into source folder as NewMap1.map</li>
     *     <li>read the map file</li>
     *     <li>check if the information is correct</li>
     * </ul>
     *
     */
    @Test
    public void testCreateThree() {
        System.out.println("Test case 4: Test Map Create has been start...");
        GameMap map = new GameMap();
        map.setAuthor("Hao Ma");
        map.setScroll("vertical");
        map.setWarn("no");
        map.setImage("null");
        map.setWrap("no");
        Territory b1= new Territory("Basketball1",225,225);
        Territory b2 = new Territory("Basketball2",225,275);
        Territory f1 = new Territory("Football1",325,275);
        Territory s1= new Territory("Swimming1",425,225);
        b2.getNeighbors().put(f1.getName(),f1);
        f1.getNeighbors().put(s1.getName(),s1);
        s1.getNeighbors().put(b1.getName(),b1);
        Continent r = new Continent("Red",16);
        Continent y = new Continent("Yellow",19);
        Continent d = new Continent("Darkness",18);
        b1.setContinent(r);
        b2.setContinent(r);
        f1.setContinent(y);
        s1.setContinent(d);
        map.getContinents().put(r.getName(),r);
        map.getContinents().put(y.getName(),y);
        map.getContinents().put(d.getName(),d);
        map.getTerritories().put(b1.getName(),b1);
        map.getTerritories().put(b2.getName(),b2);
        map.getTerritories().put(f1.getName(),f1);
        map.getTerritories().put(s1.getName(),s1);

        mapManager.CreateMap(getClass().getResource("/map/").getPath()+"NewMap1.map", map);
        GameMap result = mapManager.LoadMap(getClass().getResource("/map/").getPath()+"NewMap1.map");
    }
}
