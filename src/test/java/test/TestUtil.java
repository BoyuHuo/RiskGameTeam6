package test;

import RiskGame.model.entity.*;
import RiskGame.model.service.RiskUtil;
import RiskGame.model.service.imp.GameManager;
import RiskGame.model.service.imp.MapManager;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class TestUtil {
    MapManager mapManager = new MapManager();


    @Before
    public void setup() {
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
    }

    @Test
    public void testGetActivePlayerStrongestCountry() {
        Player player2 = GameManager.getInstance().getPlayers().get("Player2");
        Player player3 = GameManager.getInstance().getPlayers().get("Player3");

        GameManager.getInstance().getMap().getTerritories().get("FireDragon").setBelongs(player2);
        GameManager.getInstance().getMap().getTerritories().get("FireDragon").setArmies(19);
        GameManager.getInstance().getMap().getTerritories().get("WaterDragon").setBelongs(player2);
        GameManager.getInstance().getMap().getTerritories().get("WaterDragon").setArmies(28);
        GameManager.getInstance().getMap().getTerritories().get("WindDragon").setBelongs(player2);
        GameManager.getInstance().getMap().getTerritories().get("WindDragon").setArmies(25);
        GameManager.getInstance().getMap().getTerritories().get("FireHorse").setBelongs(player3);
        GameManager.getInstance().getMap().getTerritories().get("FireHorse").setArmies(20);
        GameManager.getInstance().getMap().getTerritories().get("FireBird").setBelongs(player3);
        GameManager.getInstance().getMap().getTerritories().get("FireBird").setArmies(16);

        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();

        assertEquals("FireHorse",RiskUtil.getActivePlayerStrongestCountry().getName());
        assertEquals(20,RiskUtil.getActivePlayerStrongestCountry().getArmies());

        GameManager.getInstance().nextRound();

        assertEquals("WaterDragon",RiskUtil.getActivePlayerStrongestCountry().getName());
        assertEquals(28,RiskUtil.getActivePlayerStrongestCountry().getArmies());
    }

    @Test
    public void testGetActivePlayerWeakestCountry() {
        Player player1 = GameManager.getInstance().getPlayers().get("Player1");
        Player player2 = GameManager.getInstance().getPlayers().get("Player2");
        Player player3 = GameManager.getInstance().getPlayers().get("Player3");

        GameManager.getInstance().getMap().getTerritories().get("FireHorse").setBelongs(player1);
        GameManager.getInstance().getMap().getTerritories().get("FireHorse").setArmies(88);
        GameManager.getInstance().getMap().getTerritories().get("FireBird").setBelongs(player1);
        GameManager.getInstance().getMap().getTerritories().get("FireBird").setArmies(8);
        GameManager.getInstance().getMap().getTerritories().get("FireDragon").setBelongs(player1);
        GameManager.getInstance().getMap().getTerritories().get("FireDragon").setArmies(28);
        GameManager.getInstance().getMap().getTerritories().get("WaterElephant").setBelongs(player2);
        GameManager.getInstance().getMap().getTerritories().get("WaterElephant").setArmies(66);
        GameManager.getInstance().getMap().getTerritories().get("WaterDragon").setBelongs(player2);
        GameManager.getInstance().getMap().getTerritories().get("WaterDragon").setArmies(60);
        GameManager.getInstance().getMap().getTerritories().get("WindDragon").setBelongs(player2);
        GameManager.getInstance().getMap().getTerritories().get("WindDragon").setArmies(6);
        GameManager.getInstance().getMap().getTerritories().get("WindHorse").setBelongs(player2);
        GameManager.getInstance().getMap().getTerritories().get("WindHorse").setArmies(55);
        GameManager.getInstance().getMap().getTerritories().get("IceDragon").setBelongs(player3);
        GameManager.getInstance().getMap().getTerritories().get("IceDragon").setArmies(22);
        GameManager.getInstance().getMap().getTerritories().get("IceHorse").setBelongs(player3);
        GameManager.getInstance().getMap().getTerritories().get("IceHorse").setArmies(2);

        GameManager.getInstance().nextRound();
        assertEquals("FireBird",RiskUtil.getActivePlayerWeakestCountry().getName());
        assertEquals(8,RiskUtil.getActivePlayerWeakestCountry().getArmies());
        GameManager.getInstance().nextRound();
        assertEquals("IceHorse",RiskUtil.getActivePlayerWeakestCountry().getName());
        assertEquals(2,RiskUtil.getActivePlayerWeakestCountry().getArmies());
        GameManager.getInstance().nextRound();
        assertEquals("WindDragon",RiskUtil.getActivePlayerWeakestCountry().getName());
        assertEquals(6,RiskUtil.getActivePlayerWeakestCountry().getArmies());
    }

    @Test
    public void testGetAllTerrFromPlayer() {
        Player player1 = GameManager.getInstance().getPlayers().get("Player1");
        Player player2 = GameManager.getInstance().getPlayers().get("Player2");
        Player player3 = GameManager.getInstance().getPlayers().get("Player3");

        GameManager.getInstance().getMap().getTerritories().get("FireHorse").setBelongs(player1);
        GameManager.getInstance().getMap().getTerritories().get("FireBird").setBelongs(player1);
        GameManager.getInstance().getMap().getTerritories().get("FireDragon").setBelongs(player1);
        GameManager.getInstance().getMap().getTerritories().get("WaterElephant").setBelongs(player2);
        GameManager.getInstance().getMap().getTerritories().get("WaterDragon").setBelongs(player2);
        GameManager.getInstance().getMap().getTerritories().get("WindDragon").setBelongs(player2);
        GameManager.getInstance().getMap().getTerritories().get("WindHorse").setBelongs(player2);
        GameManager.getInstance().getMap().getTerritories().get("IceDragon").setBelongs(player3);
        GameManager.getInstance().getMap().getTerritories().get("IceHorse").setBelongs(player3);

        Map<String, Territory> terrs = RiskUtil.getAllTerritoryFromPlayer(GameManager.getInstance().getPlayers().get("Player2"));
        assertEquals(4,terrs.size());

    }

}
