package test;

import RiskGame.model.entity.*;
import RiskGame.model.service.RiskUtil;
import RiskGame.model.service.imp.GameManager;
import RiskGame.model.service.imp.MapManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This is a Junit test Class, used for testing <b> Tournament </b> function
 *
 * @author Hao Ma
 * @version  v1.0.0
 * @see GameManager
 */
public class TestTournament {

    MapManager mapManager;
    /**
     * Set up method for every test cases
     */
    @Before
    public void setup() {
        mapManager = new MapManager();
    }
    /**
     * test case 1
     * Purpose: testing the function of LunchMatch
     * Process:
     * <ul>
     *     <li>Set up the map and Players</li>
     *     <li>Check if the result is expected</li>
     * </ul>
     *
     */
    @Test
    public void testLunchMatch() {
        Map<String, Player> players = new HashMap<>();
        Player p1 = new Player("Player1", new AggressiveStrategy());
        Player p2 = new Player("Player2", new BenevolentStrategy());
        Player p3 = new Player("Player3", new CheaterStrategy());
        Player p4 = new Player("Player4", new RandomStrategy());
        players.put(p1.getName(), p1);
        players.put(p2.getName(), p2);
        players.put(p3.getName(), p3);
        players.put(p4.getName(),p4);

        List<GameMap> maps = new ArrayList<>();
        GameMap m1 = mapManager.loadMap(getClass().getResource("/map/PekmonLand.map").getPath());

        maps.add(m1);

        Tournament tournament = new Tournament(maps, players, 1, 50);
        String result = "";
        try {
             result = tournament.luncheTheMatch(0);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        assertTrue("aggressive,benevolent,cheater,random,Draw".contains(result));


    }
    /**
     * test case 2
     * Purpose: testing the function of StartBenevolent
     * Process:
     * <ul>
     *     <li>Set up the map and Players</li>
     *     <li>Check if the result is expected</li>
     * </ul>
     *
     */
    @Test
    public void testStartBenevolent() {
        Map<String, Player> players = new HashMap<>();
        Player p1 = new Player("Player1", new AggressiveStrategy());
        Player p2 = new Player("Player2", new CheaterStrategy());
        Player p3 = new Player("Player3", new BenevolentStrategy());
        players.put(p1.getName(), p1);
        players.put(p2.getName(), p2);
        players.put(p3.getName(), p3);

        List<GameMap> maps = new ArrayList<>();
        GameMap m1 = mapManager.loadMap(getClass().getResource("/map/FireWorld.map").getPath());
        GameMap m2 = mapManager.loadMap(getClass().getResource("/map/PekmonLand.map").getPath());


        maps.add(m1);
        maps.add(m2);

        Tournament tournament = new Tournament(maps, players, 2, 50);
        tournament.start();
        String[][] result = tournament.getResult();
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j <  result[i].length;j++){
                System.out.print(result[i][j]+" ");
            }
            System.out.println();
        }
        assertEquals(2,result.length);
    }
}
