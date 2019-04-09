package test;

import RiskGame.model.entity.*;
import RiskGame.model.service.RiskUtil;
import RiskGame.model.service.imp.GameManager;
import RiskGame.model.service.imp.MapManager;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestAggressiveStrategy {
    MapManager mapManager;

    @Before
    public void setup() {
        mapManager = new MapManager();
        Map<String, Player> players = new HashMap<>();
        Player p1 = new Player("Player1", new HumanStrategy());
        Player p2 = new Player("Player2", new AggressiveStrategy());
        Player p3 = new Player("Player3", new HumanStrategy());
        players.put(p1.getName(), p1);
        players.put(p2.getName(), p2);
        players.put(p3.getName(), p3);

        GameManager.getInstance().setPlayers(players);
        GameManager.getInstance().setMap(mapManager.loadMap(getClass().getResource("/map/PekmonLand.map").getPath()));
        GameManager.getInstance().newGame();

    }

    @Test
    public void testReinforce() {
        Territory t1 = GameManager.getInstance().getMap().getTerritories().get("FireDragon");
        Player p2 = GameManager.getInstance().getPlayers().get("Player2");
        t1.setBelongs(p2);
        t1.setArmies(30);
        p2.setArmies(20);

        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        System.out.println(GameManager.getInstance().getGamePhase());
        System.out.println(GameManager.getInstance().getActivePlayer().getName());
        p2.excuteReinforceStrategy(0);
        System.out.println(t1.getArmies());
        assertEquals(30+20+3,t1.getArmies());
        System.out.println(GameManager.getInstance().getGamePhase());
    }
    @Test
    public void testAttackOne(){
        Territory t1 = GameManager.getInstance().getMap().getTerritories().get("FireDragon");
        Territory t2 = GameManager.getInstance().getMap().getTerritories().get("FireHorse");
        Territory t3 = GameManager.getInstance().getMap().getTerritories().get("FireBird");
        Territory t4 = GameManager.getInstance().getMap().getTerritories().get("WaterDragon");
        Player p2 = GameManager.getInstance().getPlayers().get("Player2");
        Player p1 = GameManager.getInstance().getPlayers().get("Player1");

        t1.setBelongs(p2);
        t1.setArmies(30);

        t2.setBelongs(p1);
        t3.setBelongs(p1);
        t4.setBelongs(p1);

        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        System.out.println(GameManager.getInstance().getGamePhase());
        System.out.println(GameManager.getInstance().getActivePlayer().getName());
        System.out.println(RiskUtil.getAllTerritoryFromPlayer(p2).size());
        int originalNum=RiskUtil.getAllTerritoryFromPlayer(p2).size();
        p2.excuteAttackStrategy(0);
        System.out.println(RiskUtil.getAllTerritoryFromPlayer(p2).size());
        assertEquals(originalNum+3, RiskUtil.getAllTerritoryFromPlayer(p2).size());
    }


    @Test
    public void testFortification(){
        Territory t1 = GameManager.getInstance().getMap().getTerritories().get("FireDragon");
        Territory t2 = GameManager.getInstance().getMap().getTerritories().get("FireHorse");
        Territory t3 = GameManager.getInstance().getMap().getTerritories().get("FireBird");
        Territory t4 = GameManager.getInstance().getMap().getTerritories().get("WaterDragon");
        Player p2 = GameManager.getInstance().getPlayers().get("Player2");
        Player p1 = GameManager.getInstance().getPlayers().get("Player1");

        t1.setBelongs(p2);
        t2.setBelongs(p2);
        t3.setBelongs(p2);
        t4.setBelongs(p2);

        t1.setArmies(20);
        t2.setArmies(5);

        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();

        if(GameManager.getInstance().getGamePhase().equals("Attack")) {
            GameManager.getInstance().nextRound();
        }

        System.out.println(GameManager.getInstance().getGamePhase());
        System.out.println(GameManager.getInstance().getActivePlayer().getName());

        System.out.println(t1.getArmies());
        p2.excuteFortifyStrategy(0);
        System.out.println(t1.getArmies());
    }

}
