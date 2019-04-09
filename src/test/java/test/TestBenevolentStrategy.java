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

public class TestBenevolentStrategy {
    MapManager mapManager;
    @Before
    public void setup(){
        mapManager = new MapManager();
        Map<String, Player> players = new HashMap<>();
        Player p1 = new Player("Player1", new HumanStrategy());
        Player p2 = new Player("Player2", new BenevolentStrategy());
        Player p3 = new Player("Player3", new HumanStrategy());
        players.put(p1.getName(), p1);
        players.put(p2.getName(), p2);
        players.put(p3.getName(), p3);

        GameManager.getInstance().setPlayers(players);
        GameManager.getInstance().setMap(mapManager.loadMap(getClass().getResource("/map/PekmonLand.map").getPath()));
        GameManager.getInstance().newGame();
    }

    @Test
    public void testReinforcement(){
        Player p1 = GameManager.getInstance().getPlayers().get("Player1");
        Player p2 = GameManager.getInstance().getPlayers().get("Player2");
        Player p3 = GameManager.getInstance().getPlayers().get("Player3");
        GameManager.getInstance().getMap().getTerritories().get("FireHorse").setBelongs(p1);
        GameManager.getInstance().getMap().getTerritories().get("FireHorse").setArmies(0);
        GameManager.getInstance().getMap().getTerritories().get("FireBird").setBelongs(p1);
        GameManager.getInstance().getMap().getTerritories().get("FireBird").setArmies(0);
        GameManager.getInstance().getMap().getTerritories().get("FireDragon").setBelongs(p2);
        GameManager.getInstance().getMap().getTerritories().get("FireDragon").setArmies(0);
        GameManager.getInstance().getMap().getTerritories().get("WaterElephant").setBelongs(p1);
        GameManager.getInstance().getMap().getTerritories().get("WaterElephant").setArmies(0);
        GameManager.getInstance().getMap().getTerritories().get("WaterDragon").setBelongs(p2);
        GameManager.getInstance().getMap().getTerritories().get("WaterDragon").setArmies(0);
        GameManager.getInstance().getMap().getTerritories().get("WindDragon").setBelongs(p3);
        GameManager.getInstance().getMap().getTerritories().get("WindDragon").setArmies(0);
        GameManager.getInstance().getMap().getTerritories().get("WindHorse").setBelongs(p3);
        GameManager.getInstance().getMap().getTerritories().get("WindHorse").setArmies(0);
        GameManager.getInstance().getMap().getTerritories().get("IceDragon").setBelongs(p2);
        GameManager.getInstance().getMap().getTerritories().get("IceDragon").setArmies(0);
        GameManager.getInstance().getMap().getTerritories().get("IceHorse").setBelongs(p3);
        GameManager.getInstance().getMap().getTerritories().get("IceHorse").setArmies(0);

        Territory t1 = GameManager.getInstance().getMap().getTerritories().get("FireDragon");
        t1.setArmies(30);
        p2.setArmies(21);

        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();

        GameManager.getInstance().getMap().getTerritories().get("IceDragon").setArmies(0);
        System.out.println(p2.getArmies());
        p2.excuteReinforceStrategy(0);

        Thread thread = p2.excuteReinforceStrategy(0);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(GameManager.getInstance().getMap().getTerritories().get("FireDragon").getArmies());
        System.out.println(GameManager.getInstance().getMap().getTerritories().get("WaterDragon").getArmies());
        System.out.println( GameManager.getInstance().getMap().getTerritories().get("WindDragon").getArmies());
        System.out.println( GameManager.getInstance().getMap().getTerritories().get("IceDragon").getArmies());
        assertEquals(12, GameManager.getInstance().getMap().getTerritories().get("IceDragon").getArmies());
    }

    @Test
    public void testFortification(){
        Player p1 = GameManager.getInstance().getPlayers().get("Player1");
        Player p2 = GameManager.getInstance().getPlayers().get("Player2");
        Player p3 = GameManager.getInstance().getPlayers().get("Player3");


        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();

        GameManager.getInstance().getMap().getTerritories().get("FireHorse").setBelongs(p2);
        GameManager.getInstance().getMap().getTerritories().get("FireHorse").setArmies(20);
        GameManager.getInstance().getMap().getTerritories().get("FireBird").setBelongs(p2);
        GameManager.getInstance().getMap().getTerritories().get("FireBird").setArmies(100);
        GameManager.getInstance().getMap().getTerritories().get("FireDragon").setBelongs(p1);
        GameManager.getInstance().getMap().getTerritories().get("FireDragon").setArmies(50);
        GameManager.getInstance().getMap().getTerritories().get("WaterElephant").setBelongs(p2);
        GameManager.getInstance().getMap().getTerritories().get("WaterElephant").setArmies(180);
        GameManager.getInstance().getMap().getTerritories().get("WaterDragon").setBelongs(p1);
        GameManager.getInstance().getMap().getTerritories().get("WaterDragon").setArmies(80);
        GameManager.getInstance().getMap().getTerritories().get("WindDragon").setBelongs(p3);
        GameManager.getInstance().getMap().getTerritories().get("WindDragon").setArmies(40);
        GameManager.getInstance().getMap().getTerritories().get("WindHorse").setBelongs(p2);
        GameManager.getInstance().getMap().getTerritories().get("WindHorse").setArmies(100);
        GameManager.getInstance().getMap().getTerritories().get("IceDragon").setBelongs(p3);
        GameManager.getInstance().getMap().getTerritories().get("IceDragon").setArmies(50);
        GameManager.getInstance().getMap().getTerritories().get("IceHorse").setBelongs(p3);
        GameManager.getInstance().getMap().getTerritories().get("IceHorse").setArmies(50);

        System.out.println(p2.getArmies());
        System.out.println(RiskUtil.getAllTerritoryFromPlayer(p2).size());
        p2.excuteFortifyStrategy(0);

        Thread thread1 = p2.excuteFortifyStrategy(0);
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(GameManager.getInstance().getMap().getTerritories().get("FireBird").getArmies());
        System.out.println(GameManager.getInstance().getMap().getTerritories().get("WindHorse").getArmies());
        System.out.println(GameManager.getInstance().getMap().getTerritories().get("FireHorse").getArmies());
        System.out.println(GameManager.getInstance().getMap().getTerritories().get("WaterElephant").getArmies());
        assertEquals((20+180)/2,GameManager.getInstance().getMap().getTerritories().get("FireHorse").getArmies());
        System.out.println(GameManager.getInstance().getGamePhase());
        System.out.println(GameManager.getInstance().getMap().getTerritories().get("FireHorse").getArmies());
    }
}
