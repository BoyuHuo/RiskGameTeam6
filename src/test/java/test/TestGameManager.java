package test;

import RiskGame.model.entity.Continent;
import RiskGame.model.entity.Player;
import RiskGame.model.entity.Territory;
import RiskGame.model.service.imp.GameManager;
import RiskGame.model.service.imp.MapManager;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

    /**
    * This is a Junit test Class, used for testing <b> Game Operating </b> function
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
            Player p3 = new Player("Player3", 20);
            players.put(p1.getName(), p1);
            players.put(p2.getName(), p2);
            players.put(p3.getName(), p3);

            GameManager.getInstance().setPlayers(players);
            GameManager.getInstance().setMap(mapManager.loadMap(getClass().getResource("/map/PekmonLand.map").getPath()));
            GameManager.getInstance().newGame();

            for (String key : GameManager.getInstance().getPlayers().keySet()) {
                System.out.println(key);
            }
            System.out.println("-----------------");


/*        Player p2 = new Player("Player2", 15);
        String url
        gameManager.NewGame(mapManager.LoadMap());*/
        }


        /**
         * test case 1
         * Purpose: testing the operating sequence of the players
         * Process:
         * <ul>
         * <li>let the exist three players keep running</li>
         * <li>check if the information of PlayerName in testcase is correct</li>
         * </ul>
         */
        @Test
        public void testPlayerIterator() {
            assertEquals("Player2", GameManager.getInstance().getActivePlayer().getName());
            GameManager.getInstance().nextPlayer();
            assertEquals("Player1", GameManager.getInstance().getActivePlayer().getName());
            GameManager.getInstance().nextPlayer();
            assertEquals("Player3", GameManager.getInstance().getActivePlayer().getName());
            GameManager.getInstance().nextPlayer();
            GameManager.getInstance().nextPlayer();
            GameManager.getInstance().nextPlayer();
            assertEquals("Player3", GameManager.getInstance().getActivePlayer().getName());
            GameManager.getInstance().nextPlayer();
            assertEquals("Player2", GameManager.getInstance().getActivePlayer().getName());
            GameManager.getInstance().nextPlayer();
            GameManager.getInstance().nextPlayer();
            assertEquals("Player3", GameManager.getInstance().getActivePlayer().getName());

        }

        /**
         * test case 2
         * Purpose: testing the operating sequence of the GamePhase
         * Process:
         * <ul>
         * <li>let the exist three players keep running</li>
         * <li>check if the information of GamePhase in testcase is correct</li>
         * </ul>
         */
        @Test
        public void testPhase() {
            assertEquals("Start Up", GameManager.getInstance().getGamePhase());
            GameManager.getInstance().nextPhase();
            assertEquals("Reinforcements", GameManager.getInstance().getGamePhase());
            GameManager.getInstance().nextPhase();
            assertEquals("Attack", GameManager.getInstance().getGamePhase());
            GameManager.getInstance().nextPhase();
            assertEquals("Fortification", GameManager.getInstance().getGamePhase());
            GameManager.getInstance().nextPhase();
            assertEquals("Reinforcements", GameManager.getInstance().getGamePhase());
            GameManager.getInstance().nextPhase();
            assertEquals("Attack", GameManager.getInstance().getGamePhase());
            GameManager.getInstance().nextPhase();
            GameManager.getInstance().nextPhase();
            GameManager.getInstance().nextPhase();
            GameManager.getInstance().nextPhase();
            GameManager.getInstance().nextPhase();
            GameManager.getInstance().nextPhase();
            assertEquals("Attack", GameManager.getInstance().getGamePhase());
            GameManager.getInstance().nextPhase();
            GameManager.getInstance().nextPhase();
            assertEquals("Reinforcements", GameManager.getInstance().getGamePhase());
            GameManager.getInstance().nextPhase();
        }

        /**
         * test case 3
         * Purpose: testing the operating result of the NextRound
         * Process:
         * <ul>
         * <li>let the testcase runs for many times</li>
         * <li>check if the information of NextRound in testcase is correct</li>
         * </ul>
         */
        @Test
        public void testNextRound() {
            for (int i = 0; i < 7; i++) {
                GameManager.getInstance().nextRound();
            }
            assertEquals("Reinforcements", GameManager.getInstance().getGamePhase());
            assertEquals("Player3", GameManager.getInstance().getActivePlayer().getName());
        }


        /**
         * test case 4
         * Purpose: testing the reinforcement number
         */
        @Test
        public void testReignforcementNumOne() {
            mapManager = new MapManager();
            Map<String, Player> players = new HashMap<>();
            Player p1 = new Player("Player1", 9);
            Player p2 = new Player("Player2", 10);
            Player p3 = new Player("Player3", 12);
            players.put(p1.getName(), p1);
            players.put(p2.getName(), p2);
            players.put(p3.getName(), p3);

            GameManager.getInstance().setPlayers(players);
            GameManager.getInstance().setMap(mapManager.loadMap(getClass().getResource("/map/FireWorld.map").getPath()));
            GameManager.getInstance().newGame();

            assertEquals(35, GameManager.getInstance().getPlayers().get("Player2").getArmies());
            GameManager.getInstance().nextRound();
            GameManager.getInstance().nextRound();
            GameManager.getInstance().nextRound();
            System.out.println(GameManager.getInstance().getGamePhase());
            System.out.println(GameManager.getInstance().getActivePlayer().getName());
            GameManager.getInstance().nextRound();
            assertEquals(38, GameManager.getInstance().getPlayers().get("Player2").getArmies());
        }
        /**
         * test case 4
         * Purpose: testing the reinforcement number
         */
        @Test
        public void testReignforcementNumTwo() {
            mapManager = new MapManager();
            Map<String, Player> players = new HashMap<>();
            Player p1 = new Player("Player1", 9);
            Player p2 = new Player("Player2", 10);
            Player p3 = new Player("Player3", 12);
            players.put(p1.getName(), p1);
            players.put(p2.getName(), p2);
            players.put(p3.getName(), p3);

            GameManager.getInstance().setPlayers(players);
            GameManager.getInstance().setMap(mapManager.loadMap(getClass().getResource("/map/FireWorld.map").getPath()));
            GameManager.getInstance().newGame();

            assertEquals(35, GameManager.getInstance().getPlayers().get("Player2").getArmies());
            for(Territory t: GameManager.getInstance().getMap().getTerritories().values()){
                t.setBelongs(GameManager.getInstance().getPlayers().get("Player2"));
            }
            GameManager.getInstance().nextRound();
            GameManager.getInstance().nextRound();
            GameManager.getInstance().nextRound();
            System.out.println(GameManager.getInstance().getGamePhase());
            System.out.println(GameManager.getInstance().getActivePlayer().getName());
            GameManager.getInstance().nextRound();
            assertEquals(43, GameManager.getInstance().getPlayers().get("Player2").getArmies());
        }

        /**
         * test case 5
         * Purpose: testing the player map percentage
         */
        @Test
        public void testPercentage() {
            mapManager = new MapManager();
            Map<String, Player> players = new HashMap<>();
            Player p1 = new Player("Player1", 9);
            Player p2 = new Player("Player2", 10);
            Player p3 = new Player("Player3", 12);
            players.put(p1.getName(), p1);
            players.put(p2.getName(), p2);
            players.put(p3.getName(), p3);

            GameManager.getInstance().setPlayers(players);
            GameManager.getInstance().setMap(mapManager.loadMap(getClass().getResource("/map/FireWorld.map").getPath()));
            GameManager.getInstance().newGame();
            GameManager.getInstance().randomAssignTerritoryToPlayer();

            for(Player p:GameManager.getInstance().getPlayers().values()){
                System.out.println(p.getPrecentageOfMap());
            }
        }

        /**
         * test case 6
         * Purpose: testing the reinforcement number
         */
        @Test
        public void testGetContinentList() {
            mapManager = new MapManager();
            Map<String, Player> players = new HashMap<>();
            Player p1 = new Player("Player1", 9);
            Player p2 = new Player("Player2", 10);
            Player p3 = new Player("Player3", 12);
            players.put(p1.getName(), p1);
            players.put(p2.getName(), p2);
            players.put(p3.getName(), p3);

            GameManager.getInstance().setPlayers(players);
            GameManager.getInstance().setMap(mapManager.loadMap(getClass().getResource("/map/IceWorld.map").getPath()));
            GameManager.getInstance().newGame();
            GameManager.getInstance().randomAssignTerritoryToPlayer();

            List<Continent> cs1=p1.getControlContinent();
            List<Continent> cs2=p2.getControlContinent();
            System.out.println(cs1.size()+": "+cs1.get(0).getName());
            System.out.println(cs2.size()+": "+cs2.get(0).getName());

            System.out.println();

            for(Player p:GameManager.getInstance().getPlayers().values()){
                System.out.println(p.getPrecentageOfMap());
            }

            assertEquals(1.0/3.0*100,GameManager.getInstance().getPlayers().get("Player1").getPrecentageOfMap(),0);
        }
    }