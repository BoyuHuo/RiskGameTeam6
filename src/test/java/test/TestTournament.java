package test;

import RiskGame.model.entity.*;
import RiskGame.model.service.RiskUtil;
import RiskGame.model.service.imp.GameManager;
import RiskGame.model.service.imp.MapManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestTournament {

    MapManager mapManager;

    @Before
    public void setup() {
        mapManager = new MapManager();
    }

    @Test
    public void testLunchMatch() {
        Map<String, Player> players = new HashMap<>();
        Player p1 = new Player("Player1", new AggressiveStrategy());
        Player p2 = new Player("Player2", new BenevolentStrategy());
        Player p3 = new Player("Player3", new CheaterStrategy());
        players.put(p1.getName(), p1);
        players.put(p2.getName(), p2);
        players.put(p3.getName(), p3);

        List<GameMap> maps = new ArrayList<>();
        GameMap m1 = mapManager.loadMap(getClass().getResource("/map/PekmonLand.map").getPath());

        maps.add(m1);

        Tournament tournament = new Tournament(maps, players, 1, 50);
        try {
            System.out.println(tournament.luncheTheMatch(0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStartBenevolent() {
        Map<String, Player> players = new HashMap<>();
        Player p1 = new Player("Player1", new AggressiveStrategy());
        Player p2 = new Player("Player2", new CheaterStrategy());
        Player p3 = new Player("Player3", new BenevolentStrategy());
        players.put(p1.getName(), p1);
        players.put(p2.getName(), p2);
        players.put(p3.getName(), p3);

        List<GameMap> maps = new ArrayList<>();
        GameMap m1 = mapManager.loadMap(getClass().getResource("/map/FireWorld.map").getPath());
        GameMap m2 = mapManager.loadMap(getClass().getResource("/map/PekmonLand.map").getPath());
        GameMap m3 = mapManager.loadMap(getClass().getResource("/map/IceWorld.map").getPath());


        maps.add(m1);
        maps.add(m2);
        maps.add(m3);

        Tournament tournament = new Tournament(maps, players, 3, 50);
        tournament.start();
        String[][] result = tournament.getResult();
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j <  result[i].length;j++){
                System.out.print(result[i][j]+" ");
            }
            System.out.println();
        }
    }
}
