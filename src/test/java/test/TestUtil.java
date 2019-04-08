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
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();

        Player player2 = GameManager.getInstance().getPlayers().get("Player2");
        GameManager.getInstance().getMap().getTerritories().get("FireDragon").setBelongs(player2);
        GameManager.getInstance().getMap().getTerritories().get("FireDragon").setArmies(20);
        System.out.println(RiskUtil.getActivePlayerStrongestCountry().getName()+":"+RiskUtil.getActivePlayerStrongestCountry().getArmies());
    }

    @Test
    public void testGetActivePlayerWeakestCountry() {

    }

    @Test
    public void testGetAllTerrFromPlayer() {
        Map<String, Territory> terrs = RiskUtil.getAllTerritoryFromPlayer(GameManager.getInstance().getPlayers().get("Player1"));
        assertEquals(3,terrs.size());

    }

}
