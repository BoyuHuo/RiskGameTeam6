package test;
import RiskGame.model.entity.Continent;
import RiskGame.model.entity.GameMap;


import RiskGame.model.entity.Player;
import RiskGame.model.entity.Territory;
import RiskGame.model.service.imp.GameManager;
import RiskGame.model.service.imp.MapManager;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * This is a Junit test Class, used for testing <b> Territory </b> function
 *
 * @author Baiyu Huo
 * @version  v1.0.0
 * @see GameManager
 */
public class TestTerritory {
    MapManager mapManager;

    /**
     * Set up method for every test cases
     */
    @Before
    public void setUp() {
        mapManager = new MapManager();
        Map<String, Player> players = new HashMap<>();
        Player p1 = new Player("Player1", 10);
        Player p2 = new Player("Player2", 15);
        Player p3 = new Player("Player3", 20);
        players.put(p1.getName(), p1);
        players.put(p2.getName(), p2);
        players.put(p3.getName(), p3);

        GameManager.getInstance().setPlayers(players);
        GameManager.getInstance().setMap(mapManager.LoadMap(getClass().getResource("/map/PekmonLand.map").getPath()));
        GameManager.getInstance().NewGame();
    }

    /**
     * This is the test case for random assign the Territory, although it isn't using assert
     */
    @Test
    public void testRandomTerritory() {

        GameManager.getInstance().ramdomAssignTerritoryToPlayer();

        System.out.println(GameManager.getInstance().getMap().getTerritories().get("WindHorse").getBelongs().getName());
        System.out.println(GameManager.getInstance().getMap().getTerritories().get("WaterDragon").getBelongs().getName());
        System.out.println(GameManager.getInstance().getMap().getTerritories().get("IceDragon").getBelongs().getName());

        GameManager.getInstance().ramdomAssignTerritoryToPlayer();
    }

    /**
     * This is the test case for testing move the arimies from one territory to another territory
     * It create a valid map, and a ideal situation for transfer the armies
     * which means, the territory has enough armies to move and, both the source territory and destination belongs to the same player
     * and it won't pass any enemy's territory.
     */
    @Test
    public void testImmgrantOne() {
        System.out.println("Test case 1: Test Immgrant has been start...");
        GameMap map = new GameMap();
        map.setAuthor("Hao Ma");
        map.setScroll("vertical");
        map.setWarn("no");
        map.setImage("null");
        map.setWrap("no");
        Territory f1 = new Territory("Fire1", 100, 100);
        Territory f2 = new Territory("Fire2", 100, 150);
        Territory f3 = new Territory("Fire3", 200, 150);
        Territory w1 = new Territory("Water1", 200, 200);
        Territory w2 = new Territory("Water2", 300, 100);
        f1.addNeibor(f2);
        f2.addNeibor(f3);
        f3.addNeibor(w1);
        w1.addNeibor(w2);
        w2.addNeibor(f1);
        mapManager = new MapManager();
        Map<String, Player> players = new HashMap<>();
        Player p1 = new Player("Player1", 10);
        Player p2 = new Player("Player2", 15);
        players.put(p1.getName(), p1);
        players.put(p2.getName(), p2);
        f1.setBelongs(p1);
        f2.setBelongs(p1);
        f3.setBelongs(p1);
        w1.setBelongs(p2);
        w2.setBelongs(p2);
        f1.setArmies(12);
        f2.setArmies(16);
        boolean result = f1.immigrantArimies(2, f2);
        assertTrue(result);
    }

    /**
     * This is the test case for testing move the arimies from one territory to another territory
     * It create a valid map, and a not ideal situation for transfer the armies
     * which means, the territory has enough armies to move, but the path has to pass the enemy's territory.
     */
    @Test
    public void testImmgrantTwo() {
        System.out.println("Test case 2: Test Immgrant has been start...");
        GameMap map = new GameMap();
        map.setAuthor("Hao Ma");
        map.setScroll("vertical");
        map.setWarn("no");
        map.setImage("null");
        map.setWrap("no");
        Territory m1 = new Territory("Moon1", 125, 125);
        Territory m2 = new Territory("Moon2", 125, 150);
        Territory m3 = new Territory("Moon3", 225, 150);
        Territory s1 = new Territory("Sun1", 225, 225);
        Territory s2 = new Territory("Sun2", 325, 125);
        m1.addNeibor(m2);
        m2.addNeibor(m3);
        m3.addNeibor(s1);
        s1.addNeibor(s2);
        s2.addNeibor(m1);
        mapManager = new MapManager();
        Map<String, Player> players = new HashMap<>();
        Player p1 = new Player("Player1", 22);
        Player p2 = new Player("Player2", 18);
        players.put(p1.getName(), p1);
        players.put(p2.getName(), p2);
        m1.setBelongs(p1);
        m2.setBelongs(p2);
        m3.setBelongs(p1);
        s1.setBelongs(p2);
        s2.setBelongs(p2);
        m1.setArmies(20);
        m3.setArmies(16);
        boolean result = m1.immigrantArimies(2, m3);
        assertFalse(result);
    }


    /**
     * This is the test case for testing move the arimies from one territory to another territory
     * It create a valid map, and a not ideal situation for transfer the armies
     * which means, the territory has enough armies to move, but it's not this player's territories.
     */
    @Test
    public void testImmgrantThree() {
        System.out.println("Test case 3: Test Immgrant has been start...");
        GameMap map = new GameMap();
        map.setAuthor("Hao Ma");
        map.setScroll("vertical");
        map.setWarn("no");
        map.setImage("null");
        map.setWrap("no");
        Territory a1 = new Territory("Apple1", 150, 150);
        Territory a2 = new Territory("Apple2", 150, 200);
        Territory a3 = new Territory("Apple3", 250, 200);
        Territory b1 = new Territory("Banana1", 250, 250);
        Territory b2 = new Territory("Banana2", 350, 150);
        a1.addNeibor(a2);
        a2.addNeibor(a3);
        a3.addNeibor(b1);
        b1.addNeibor(b2);
        b2.addNeibor(a1);
        mapManager = new MapManager();
        Map<String, Player> players = new HashMap<>();
        Player p1 = new Player("Player1", 22);
        Player p2 = new Player("Player2", 18);
        players.put(p1.getName(), p1);
        players.put(p2.getName(), p2);
        a1.setBelongs(p1);
        a2.setBelongs(p2);
        a3.setBelongs(p1);
        b1.setBelongs(p2);
        b2.setBelongs(p2);
        a1.setArmies(22);
        b1.setArmies(20);
        boolean result = a1.immigrantArimies(2, b1);
        assertFalse(result);
    }
}
