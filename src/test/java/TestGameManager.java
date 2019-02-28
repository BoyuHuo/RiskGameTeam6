import RiskGame.model.entity.GameMap;
import RiskGame.model.entity.Player;
import RiskGame.model.service.imp.GameManager;
import RiskGame.model.service.imp.MapManager;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestGameManager {
    GameManager gameManager;
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

        gameManager = new GameManager(mapManager.LoadMap(getClass().getResource("/map/PekmonLand.map").getPath()),players);
        gameManager.NewGame();

        for(String key:gameManager.getPlayers().keySet()){
            System.out.println(key);
        }
        System.out.println("-----------------");


/*        Player p2 = new Player("Player2", 15);
        String url
        gameManager.NewGame(mapManager.LoadMap());*/
    }

    @Test
    public void testPlayerIterator() {
        assertEquals("Player2",gameManager.getActivePlayer().getName());
        System.out.println(gameManager.getActivePlayer().getName());
        gameManager.nextPlayer();
        System.out.println(gameManager.getActivePlayer().getName());
        gameManager.nextPlayer();
        System.out.println(gameManager.getActivePlayer().getName());
        gameManager.nextPlayer();
        System.out.println(gameManager.getActivePlayer().getName());
        gameManager.nextPlayer();
        System.out.println(gameManager.getActivePlayer().getName());
        gameManager.nextPlayer();
        System.out.println(gameManager.getActivePlayer().getName());
        gameManager.nextPlayer();
        System.out.println(gameManager.getActivePlayer().getName());
        assertEquals("Player2",gameManager.getActivePlayer().getName());

    }
}
