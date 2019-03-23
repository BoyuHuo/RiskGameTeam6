package test;

import RiskGame.model.entity.Continent;
import RiskGame.model.entity.GameMap;
import RiskGame.model.entity.Player;
import RiskGame.model.entity.Territory;
import RiskGame.model.service.imp.GameManager;
import RiskGame.model.service.imp.MapManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TestAttack {
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
        GameManager.getInstance().NewGame();
    }

    @Test
    public void testAttackOne() {
        Territory t1 = GameManager.getInstance().getMap().getTerritories().get("Ter1");
        Territory t2 = GameManager.getInstance().getMap().getTerritories().get("Ter2");
        t1.setArmies(20);
        t1.setBelongs(GameManager.getInstance().getPlayers().get("Player1"));
        t2.setArmies(2);
        t2.setBelongs(GameManager.getInstance().getPlayers().get("Player2"));
        GameManager.getInstance().getPlayers().get("Player1").launchAttack(t1,t2,3,2);
        System.out.println("T1:"+t1.getArmies());
        System.out.println("T2:"+t2.getArmies());

        System.out.println("Last Capture armies num: "+t2.getCaptureDiceNum());
        t1.captureTerritory(t2,3);
        System.out.println("Now T2's Armies: " +t2.getArmies());
        System.out.println("Now T2's belongs: "+t2.getBelongs().getName());
    }

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

    @Test
    public void testOwnershipOfTerritoryAfterAttack() {
        Territory t1 = GameManager.getInstance().getMap().getTerritories().get("Ter1");
        Territory t2 = GameManager.getInstance().getMap().getTerritories().get("Ter2");
        t1.setArmies(222);
        t1.setBelongs(GameManager.getInstance().getPlayers().get("Player1"));
        t2.setArmies(1);
        t2.setBelongs(GameManager.getInstance().getPlayers().get("Player2"));
        GameManager.getInstance().getPlayers().get("Player1").allInMode(t1,t2);
        System.out.println("t1's armies: "+t1.getArmies());
        System.out.println("t2's armies: "+t2.getArmies());
        System.out.println(GameManager.getInstance().getMessage());
        GameManager.getInstance().getPlayers().get("Player1").allInMode(t1,t2);
        t1.captureTerritory(t2,22);
        assertEquals("Player1",t2.getBelongs().getName());
    }


}
