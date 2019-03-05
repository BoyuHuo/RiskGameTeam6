
import RiskGame.model.entity.Player;
import RiskGame.model.service.imp.GameManager;
import RiskGame.model.service.imp.MapManager;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

    /**
    * This is a Junit Test Class, used for testing <b> Game Operating </b> function
    * It contains all test cases which is related to game operating.
    *
    * @author Hao Ma
    * @version  v1.0.0
    * @since v1.0.0
    * @see MapManager
    */
public class TestGameManager {
    MapManager mapManager;
    /**
     * This is the setUp method before each test case, its main purpose is use for initiate the instance.
     */
    @Before
    public void setUp() {
        mapManager = new MapManager();
        Map<String, Player> players = new HashMap<>();
        Player p1 = new Player("Player1", 10);
        Player p2 = new Player("Player2", 15);
        Player p3 = new Player("Player3",20);
        players.put(p1.getName(),p1);
        players.put(p2.getName(),p2);
        players.put(p3.getName(),p3);

        GameManager.getInstance().setPlayers(players);
        GameManager.getInstance().setMap(mapManager.LoadMap(getClass().getResource("/map/PekmonLand.map").getPath()));
        GameManager.getInstance().NewGame();

        for(String key:GameManager.getInstance().getPlayers().keySet()){
            System.out.println(key);
        }
        System.out.println("-----------------");


/*        Player p2 = new Player("Player2", 15);
        String url
        gameManager.NewGame(mapManager.LoadMap());*/
    }
        /**
         * Test case 1
         * Purpose: testing the operating sequence of the players
         * Process:
         * <ul>
         *     <li>let the exist three players keep running</li>
         *     <li>check if the information of PlayerName in testcase is correct</li>
         * </ul>
         *
         */
    @Test
    public void testPlayerIterator() {
        assertEquals("Player2",GameManager.getInstance().getActivePlayer().getName());
        GameManager.getInstance().nextPlayer();
        assertEquals("Player1",GameManager.getInstance().getActivePlayer().getName());
        GameManager.getInstance().nextPlayer();
        assertEquals("Player3",GameManager.getInstance().getActivePlayer().getName());
        GameManager.getInstance().nextPlayer();
        GameManager.getInstance().nextPlayer();
        GameManager.getInstance().nextPlayer();
        assertEquals("Player3",GameManager.getInstance().getActivePlayer().getName());
        GameManager.getInstance().nextPlayer();
        assertEquals("Player2",GameManager.getInstance().getActivePlayer().getName());
        GameManager.getInstance().nextPlayer();
        GameManager.getInstance().nextPlayer();
        assertEquals("Player3",GameManager.getInstance().getActivePlayer().getName());

    }
        /**
         * Test case 2
         * Purpose: testing the operating sequence of the GamePhase
         * Process:
         * <ul>
         *     <li>let the exist three players keep running</li>
         *     <li>check if the information of GamePhase in testcase is correct</li>
         * </ul>
         *
         */
    @Test
    public void testPhase() {
        assertEquals("Start Up",GameManager.getInstance().getGamePhase());
        GameManager.getInstance().nextPhase();
        assertEquals("Reinforcements",GameManager.getInstance().getGamePhase());
        GameManager.getInstance().nextPhase();
        assertEquals("Attack",GameManager.getInstance().getGamePhase());
        GameManager.getInstance().nextPhase();
        assertEquals("Fortification",GameManager.getInstance().getGamePhase());
        GameManager.getInstance().nextPhase();
        assertEquals("Reinforcements",GameManager.getInstance().getGamePhase());
        GameManager.getInstance().nextPhase();
        assertEquals("Attack",GameManager.getInstance().getGamePhase());
        GameManager.getInstance().nextPhase();
        GameManager.getInstance().nextPhase();
        GameManager.getInstance().nextPhase();
        GameManager.getInstance().nextPhase();
        GameManager.getInstance().nextPhase();
        GameManager.getInstance().nextPhase();
        assertEquals("Attack",GameManager.getInstance().getGamePhase());
        GameManager.getInstance().nextPhase();
        GameManager.getInstance().nextPhase();
        assertEquals("Reinforcements",GameManager.getInstance().getGamePhase());
        GameManager.getInstance().nextPhase();
    }
        /**
         * Test case 3
         * Purpose: testing the operating result of the NextRound
         * Process:
         * <ul>
         *     <li>let the testcase runs for many times</li>
         *     <li>check if the information of NextRound in testcase is correct</li>
         * </ul>
         *
         */
/*    @Test
    public void testNextRound(){
        for(int i=0;i<7;i++) {
            GameManager.getInstance().nextRound();
        }
        assertEquals("Attack",GameManager.getInstance().getGamePhase());
        assertEquals("Player1",GameManager.getInstance().getActivePlayer().getName());
        assertEquals(15,GameManager.getInstance().getActivePlayer().getArmies();
    }*/
        @Test
        public void testReignforcementNum() {
            System.out.println(GameManager.getInstance().getPlayers().get("Player2").getArmies());
            GameManager.getInstance().nextRound();
            GameManager.getInstance().nextRound();
            GameManager.getInstance().nextRound();
            System.out.println(GameManager.getInstance().getGamePhase());
            System.out.println(GameManager.getInstance().getActivePlayer().getName());
            GameManager.getInstance().nextRound();
            System.out.println(GameManager.getInstance().getPlayers().get("Player2").getArmies());

        }


}
