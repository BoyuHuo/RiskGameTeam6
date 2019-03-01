import RiskGame.model.entity.Player;
import RiskGame.model.entity.Territory;
import RiskGame.model.service.imp.GameManager;
import RiskGame.model.service.imp.MapManager;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestTerritory {
    MapManager mapManager;

    @Before
    public void setUp() {
        mapManager = new MapManager();
        Map<String, Player> players = new HashMap<>();
        Player p1 = new Player("Player1", 10);
        Player p2 = new Player("Player2", 15);
        Player p3 = new Player("Player3",20);
        players.put(p1.getName(),p1);
        players.put(p2.getName(),p2);
        players.put(p3.getName(),p3);

        GameManager.getInstance().setPlayers(players);
        GameManager.getInstance().setMap(mapManager.LoadMap(getClass().getResource("/map/PekmonLand.map").getPath()));
        GameManager.getInstance().NewGame();


    }

    @Test
    public void testRandomTerritory() {

        GameManager.getInstance().ramdomAssignTerritoryToPlayer();

        for(Territory t: GameManager.getInstance().getMap().getTerritories().values()){
            System.out.println(t.getBelongs().getName());
        }

        GameManager.getInstance().ramdomAssignTerritoryToPlayer();

        for(Territory t: GameManager.getInstance().getMap().getTerritories().values()){
            System.out.println(t.getBelongs().getName());
        }


    }
}
