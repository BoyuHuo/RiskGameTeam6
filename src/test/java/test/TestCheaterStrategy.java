package test;

import RiskGame.model.entity.BenevolentStrategy;
import RiskGame.model.entity.CheaterStrategy;
import RiskGame.model.entity.HumanStrategy;
import RiskGame.model.entity.Player;
import RiskGame.model.service.imp.GameManager;
import RiskGame.model.service.imp.MapManager;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestCheaterStrategy {
    MapManager mapManager;
    @Before
    public void setup(){
        mapManager = new MapManager();
        Map<String, Player> players = new HashMap<>();
        Player p1 = new Player("Player1", new HumanStrategy());
        Player p2 = new Player("Player2", new CheaterStrategy());
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
        Player player1 = GameManager.getInstance().getPlayers().get("Player1");
        Player player2 = GameManager.getInstance().getPlayers().get("Player2");
        Player player3 = GameManager.getInstance().getPlayers().get("Player3");
        Player p1 = GameManager.getInstance().getPlayers().get("Player1");
        Player p2 = GameManager.getInstance().getPlayers().get("Player2");
        Player p3 = GameManager.getInstance().getPlayers().get("Player3");


        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();

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
        GameManager.getInstance().getMap().getTerritories().get("WindDragon").setBelongs(p2);
        GameManager.getInstance().getMap().getTerritories().get("WindDragon").setArmies(0);
        GameManager.getInstance().getMap().getTerritories().get("WindHorse").setBelongs(p3);
        GameManager.getInstance().getMap().getTerritories().get("WindHorse").setArmies(0);
        GameManager.getInstance().getMap().getTerritories().get("IceDragon").setBelongs(p2);
        GameManager.getInstance().getMap().getTerritories().get("IceDragon").setArmies(20);
        GameManager.getInstance().getMap().getTerritories().get("IceHorse").setBelongs(p3);
        GameManager.getInstance().getMap().getTerritories().get("IceHorse").setArmies(0);

        System.out.println( GameManager.getInstance().getMap().getTerritories().get("IceDragon").getArmies());
        p2.excuteReinforceStrategy(0);
        assertEquals(40, GameManager.getInstance().getMap().getTerritories().get("IceDragon").getArmies());
        System.out.println( GameManager.getInstance().getMap().getTerritories().get("IceDragon").getArmies());
    }

    @Test
    public  void testAttack(){

    }
    @Test
    public void testFortification(){

        Player player1 = GameManager.getInstance().getPlayers().get("Player1");
        Player player2 = GameManager.getInstance().getPlayers().get("Player2");
        Player player3 = GameManager.getInstance().getPlayers().get("Player3");
        Player p1 = GameManager.getInstance().getPlayers().get("Player1");
        Player p2 = GameManager.getInstance().getPlayers().get("Player2");
        Player p3 = GameManager.getInstance().getPlayers().get("Player3");


        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();

        if(GameManager.getInstance().getGamePhase().equals("Attack")) {
            GameManager.getInstance().nextRound();
        }

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
        GameManager.getInstance().getMap().getTerritories().get("WindDragon").setBelongs(p2);
        GameManager.getInstance().getMap().getTerritories().get("WindDragon").setArmies(0);
        GameManager.getInstance().getMap().getTerritories().get("WindHorse").setBelongs(p3);
        GameManager.getInstance().getMap().getTerritories().get("WindHorse").setArmies(0);
        GameManager.getInstance().getMap().getTerritories().get("IceDragon").setBelongs(p2);
        GameManager.getInstance().getMap().getTerritories().get("IceDragon").setArmies(20);
        GameManager.getInstance().getMap().getTerritories().get("IceHorse").setBelongs(p3);
        GameManager.getInstance().getMap().getTerritories().get("IceHorse").setArmies(5);

        System.out.println( GameManager.getInstance().getMap().getTerritories().get("IceDragon").getArmies());
        p2.excuteFortifyStrategy(0);
        assertEquals(40, GameManager.getInstance().getMap().getTerritories().get("IceDragon").getArmies());
        System.out.println( GameManager.getInstance().getMap().getTerritories().get("IceDragon").getArmies());

    }
}
