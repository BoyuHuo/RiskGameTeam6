package test;

import RiskGame.model.entity.Continent;
import RiskGame.model.entity.GameMap;
import RiskGame.model.entity.Player;
import RiskGame.model.entity.Territory;
import RiskGame.model.service.imp.GameManager;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * This is a Junit test Class, used for testing <b> Attack </b> function
 *
 * @author Hao Ma
 * @version  v1.0.0
 * @see GameManager
 */

public class TestAttack {

    /**
     * Set up method for every test cases
     */

    @Before
    public void setUp() {
        GameMap map = new GameMap();


        map.setAuthor("Baiyu Huo");
        map.setScroll("vertical");
        map.setWarn("no");
        map.setImage("null");
        map.setWrap("no");

        Territory t1 = new Territory("Ter1", 100, 100);
        Territory t2 = new Territory("Ter2", 50, 50);
        Territory t3 = new Territory("Ter3", 80, 80);
        Territory t4 = new Territory("Ter4", 120, 110);

        t1.addNeibor(t2);
        t2.addNeibor(t3);
        t3.addNeibor(t4);
        t4.addNeibor(t2);

        Continent c1 = new Continent("C1", 5);
        Continent c2 = new Continent("C2", 15);

        t1.setContinent(c1);
        t2.setContinent(c2);
        t3.setContinent(c2);
        t4.setContinent(c2);

        map.getContinents().put(c1.getName(), c1);
        map.getContinents().put(c2.getName(), c2);

        map.getTerritories().put(t1.getName(), t1);
        map.getTerritories().put(t2.getName(), t2);
        map.getTerritories().put(t3.getName(), t3);
        map.getTerritories().put(t4.getName(), t4);

        Map<String, Player> players = new HashMap<>();
        Player p1 = new Player("Player1", 10);
        Player p2 = new Player("Player2", 15);
        Player p3 = new Player("Player3", 20);
        players.put(p1.getName(), p1);
        players.put(p2.getName(), p2);
        players.put(p3.getName(), p3);

        GameManager.getInstance().setPlayers(players);
        GameManager.getInstance().setMap(map);
        GameManager.getInstance().newGame();
    }
    /**
     * test case 1
     * Purpose: testing the function of a normal Attack action
     * Process:
     * <ul>
     *     <li>Set up two territories and their ownership</li>
     *     <li>Let t1 attack t2</li>
     *     <li>Check if the result is expected</li>
     * </ul>
     *
     */
    @Test
    public void testAttackOne() {
        Territory t1 = GameManager.getInstance().getMap().getTerritories().get("Ter1");
        Territory t2 = GameManager.getInstance().getMap().getTerritories().get("Ter2");
        t1.setArmies(20);
        t1.setBelongs(GameManager.getInstance().getPlayers().get("Player1"));
        t2.setArmies(1);
        t2.setBelongs(GameManager.getInstance().getPlayers().get("Player2"));
        GameManager.getInstance().getPlayers().get("Player1").launchAttack(t1,t2,2,1);
        int result = t2.getArmies();
        assertTrue(result == 0||result == 1);
    }
    /**
     * test case 2
     * Purpose: testing the function of a normal Attack action
     * Process:
     * <ul>
     *     <li>Set up two territories and their ownership</li>
     *     <li>Let t1 attack t2(The t1 has no army)</li>
     *     <li>Check if the result is expected</li>
     * </ul>
     *
     */
    @Test
    public void testAttackTwo() {
        Territory t3 = GameManager.getInstance().getMap().getTerritories().get("Ter3");
        Territory t4 = GameManager.getInstance().getMap().getTerritories().get("Ter4");
        t3.setArmies(0);
        t3.setBelongs(GameManager.getInstance().getPlayers().get("Player1"));
        t4.setArmies(16);
        t4.setBelongs(GameManager.getInstance().getPlayers().get("Player2"));
        GameManager.getInstance().getPlayers().get("Player1").launchAttack(t3,t4,3,2);
        assertEquals(-1,GameManager.getInstance().getPlayers().get("Player1").launchAttack(t3,t4,3,2));
    }
    /**
     * test case 3
     * Purpose: testing the function of a normal Attack action
     * Process:
     * <ul>
     *     <li>Set up two territories and their ownership</li>
     *     <li>Let t1 attack t2（t1 and t2 are all belonged to Player2）</li>
     *     <li>Check if the result is expected</li>
     * </ul>
     *
     */
    @Test
    public void testAttackThree() {
        Territory t3 = GameManager.getInstance().getMap().getTerritories().get("Ter3");
        Territory t4 = GameManager.getInstance().getMap().getTerritories().get("Ter4");
        t3.setArmies(18);
        t3.setBelongs(GameManager.getInstance().getPlayers().get("Player2"));
        t4.setArmies(16);
        t4.setBelongs(GameManager.getInstance().getPlayers().get("Player2"));
        GameManager.getInstance().getPlayers().get("Player2").launchAttack(t3,t4, 3, 2);
        assertEquals(-2, GameManager.getInstance().getPlayers().get("Player2").launchAttack(t3,t4, 3, 2));
    }
    /**
     * test case 4
     * Purpose: testing the function of a normal Attack action
     * Process:
     * <ul>
     *     <li>Set up two territories and their ownership</li>
     *     <li>Let t1 attack t2（t1 and t2 are not connected）</li>
     *     <li>Check if the result is expected</li>
     * </ul>
     *
     */
    @Test
    public void testAttackFour() {
        Territory t1 = GameManager.getInstance().getMap().getTerritories().get("Ter1");
        Territory t3 = GameManager.getInstance().getMap().getTerritories().get("Ter3");
        t1.setArmies(25);
        t1.setBelongs(GameManager.getInstance().getPlayers().get("Player1"));
        t3.setArmies(16);
        t3.setBelongs(GameManager.getInstance().getPlayers().get("Player2"));
        GameManager.getInstance().getPlayers().get("Player1").launchAttack(t1,t3,3,2);
        assertEquals(-3, GameManager.getInstance().getPlayers().get("Player2").launchAttack(t1,t3, 3, 2));
    }
    /**
     * test case 5
     * Purpose: testing the function of AllInMode
     * Process:
     * <ul>
     *     <li>Set up two territories and their ownership</li>
     *     <li>Let t1 attack t2 by AllInMode</li>
     *     <li>Check if the result is expected</li>
     * </ul>
     *
     */
    @Test
    public void testAttackAllInMode(){
        Territory t1 = GameManager.getInstance().getMap().getTerritories().get("Ter1");
        Territory t2 = GameManager.getInstance().getMap().getTerritories().get("Ter2");
        t1.setArmies(26);
        t1.setBelongs(GameManager.getInstance().getPlayers().get("Player1"));
        t2.setArmies(22);
        t2.setBelongs(GameManager.getInstance().getPlayers().get("Player2"));
        GameManager.getInstance().getPlayers().get("Player1").allInMode(t1,t2);
        assertTrue(t1.getArmies()==0||t2.getArmies()==0);
    }
    /**
     * test case 6
     * Purpose: testing the Ownership of Territory after Attack
     * Process:
     * <ul>
     *     <li>Set up two territories and their ownership</li>
     *     <li>Let t1 attack t2 by AllInMode</li>
     *     <li>Check if the result of the ownership after attack is expected</li>
     * </ul>
     *
     */
    @Test
    public void testOwnershipOfTerritoryAfterAttack() {
        Territory t1 = GameManager.getInstance().getMap().getTerritories().get("Ter1");
        Territory t2 = GameManager.getInstance().getMap().getTerritories().get("Ter2");
        t1.setArmies(222);
        t1.setBelongs(GameManager.getInstance().getPlayers().get("Player1"));
        t2.setArmies(1);
        t2.setBelongs(GameManager.getInstance().getPlayers().get("Player2"));
        GameManager.getInstance().getPlayers().get("Player1").allInMode(t1,t2);
        GameManager.getInstance().getPlayers().get("Player1").allInMode(t1,t2);
        GameManager.getInstance().getPlayers().get("Player1").captureTerritory(t1,t2,22);
        assertEquals("Player1",t2.getBelongs().getName());
    }

}
