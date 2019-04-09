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

        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();

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
        GameManager.getInstance().getMap().getTerritories().get("WindDragon").setBelongs(p2);
        GameManager.getInstance().getMap().getTerritories().get("WindDragon").setArmies(0);
        GameManager.getInstance().getMap().getTerritories().get("WindHorse").setBelongs(p3);
        GameManager.getInstance().getMap().getTerritories().get("WindHorse").setArmies(0);
        GameManager.getInstance().getMap().getTerritories().get("IceDragon").setBelongs(p2);
        GameManager.getInstance().getMap().getTerritories().get("IceDragon").setArmies(0);
        GameManager.getInstance().getMap().getTerritories().get("IceHorse").setBelongs(p3);
        GameManager.getInstance().getMap().getTerritories().get("IceHorse").setArmies(0);

        Territory t1 = GameManager.getInstance().getMap().getTerritories().get("FireDragon");
        t1.setArmies(30);
        p2.setArmies(20);


        //System.out.println(GameManager.getInstance().getGamePhase());
        //System.out.println(GameManager.getInstance().getActivePlayer().getName());

        System.out.println(GameManager.getInstance().getActivePlayer().getName());
        System.out.println(GameManager.getInstance().getGamePhase());

        Thread thread = p2.excuteReinforceStrategy(0);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        assertEquals(30 + 20 , t1.getArmies());


        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();

        Territory t2 = GameManager.getInstance().getMap().getTerritories().get("WaterDragon");
        t1.immigrantArimies(36,t2);
        p2.setArmies(30);
        System.out.println(GameManager.getInstance().getActivePlayer().getName());
        System.out.println(RiskUtil.getActivePlayerStrongestCountry().getName());

        Thread thread1 = p2.excuteReinforceStrategy(0);
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(36 + 30, t2.getArmies());
        System.out.println(t2.getArmies());
        //System.out.println(t2.getArmies());*/
    }

    @Test
    public void testAttackOne() {
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

        int originalNum = RiskUtil.getAllTerritoryFromPlayer(p2).size();
        p2.excuteAttackStrategy(0);

        Thread thread2 = p2.excuteReinforceStrategy(0);
        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(originalNum + 3, RiskUtil.getAllTerritoryFromPlayer(p2).size());
    }

    @Test
    public void testAttackTwo(){
        Territory t1 = GameManager.getInstance().getMap().getTerritories().get("FireDragon");
        Territory t2 = GameManager.getInstance().getMap().getTerritories().get("FireHorse");
        Territory t3 = GameManager.getInstance().getMap().getTerritories().get("FireBird");
        Territory t4 = GameManager.getInstance().getMap().getTerritories().get("WaterDragon");
        Territory t5 = GameManager.getInstance().getMap().getTerritories().get("WaterElephant");
        Territory t6 = GameManager.getInstance().getMap().getTerritories().get("WindDragon");
        Territory t7 = GameManager.getInstance().getMap().getTerritories().get("WindHorse");
        Territory t8 = GameManager.getInstance().getMap().getTerritories().get("IceDragon");
        Territory t9 = GameManager.getInstance().getMap().getTerritories().get("IceHorse");
        Player p2 = GameManager.getInstance().getPlayers().get("Player2");
        Player p1 = GameManager.getInstance().getPlayers().get("Player1");
        Player p3 = GameManager.getInstance().getPlayers().get("Player3");

        t1.setBelongs(p2);
        t1.setArmies(22);
        t4.setBelongs(p2);
        t2.setBelongs(p1);
        t3.setBelongs(p1);
        t5.setBelongs(p1);
        t6.setBelongs(p1);
        t7.setBelongs(p3);
        t8.setBelongs(p3);
        t9.setBelongs(p3);

        System.out.println(t1.getArmies());
        System.out.println(t2.getArmies());
        System.out.println(t3.getArmies());
        System.out.println(t4.getArmies());

        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        System.out.println(RiskUtil.getActivePlayerStrongestCountry().getName());
        System.out.println(RiskUtil.getActivePlayerStrongestCountry().getArmies());
        p2.excuteAttackStrategy(0);

        Thread thread5 = p2.excuteReinforceStrategy(0);
        try {
            thread5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(2+2, RiskUtil.getAllTerritoryFromPlayer(p2).size());
        System.out.println(t1.getArmies());
        System.out.println(t2.getArmies());
        System.out.println(t3.getArmies());
        System.out.println(t4.getArmies());
        p2.captureTerritory(t1,t3,22);

        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();
        GameManager.getInstance().nextRound();

        System.out.println(RiskUtil.getActivePlayerStrongestCountry().getName());
        System.out.println(RiskUtil.getActivePlayerStrongestCountry().getArmies());
        p2.excuteAttackStrategy(0);

        Thread thread3 = p2.excuteReinforceStrategy(0);
        try {
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        assertEquals(4+1, RiskUtil.getAllTerritoryFromPlayer(p2).size());
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

        p2.excuteFortifyStrategy(0);

        Thread thread4 = p2.excuteReinforceStrategy(0);
        try {
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(t1.getArmies());
        System.out.println(t2.getArmies());
        System.out.println(t3.getArmies());
        System.out.println(t4.getArmies());
        assertEquals(25,RiskUtil.getActivePlayerStrongestCountry().getArmies());
    }

}
