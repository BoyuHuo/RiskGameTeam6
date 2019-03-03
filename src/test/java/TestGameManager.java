import RiskGame.model.entity.GameMap;
import RiskGame.model.entity.Player;
import RiskGame.model.service.imp.GameManager;
import RiskGame.model.service.imp.MapManager;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestGameManager {
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

        for(String key:GameManager.getInstance().getPlayers().keySet()){
            System.out.println(key);
        }
        System.out.println("-----------------");


/*        Player p2 = new Player("Player2", 15);
        String url
        gameManager.NewGame(mapManager.LoadMap());*/
    }

    @Test
    public void testPlayerIterator() {
        System.out.println(GameManager.getInstance().getActivePlayer().getName());
        GameManager.getInstance().nextPlayer();
        System.out.println(GameManager.getInstance().getActivePlayer().getName());
        GameManager.getInstance().nextPlayer();
        System.out.println(GameManager.getInstance().getActivePlayer().getName());
        GameManager.getInstance().nextPlayer();
        System.out.println(GameManager.getInstance().getActivePlayer().getName());
        GameManager.getInstance().nextPlayer();
        System.out.println(GameManager.getInstance().getActivePlayer().getName());
        GameManager.getInstance().nextPlayer();
        System.out.println(GameManager.getInstance().getActivePlayer().getName());
        GameManager.getInstance().nextPlayer();
        System.out.println(GameManager.getInstance().getActivePlayer().getName());
        assertEquals("Player1",GameManager.getInstance().getActivePlayer().getName());

    }

    @Test
    public void testPhase() {
        System.out.println(GameManager.getInstance().getGamePhase());
        GameManager.getInstance().nextPhase();
        System.out.println(GameManager.getInstance().getGamePhase());
        GameManager.getInstance().nextPhase();
        System.out.println(GameManager.getInstance().getGamePhase());
        GameManager.getInstance().nextPhase();
        System.out.println(GameManager.getInstance().getGamePhase());
        GameManager.getInstance().nextPhase();
        System.out.println(GameManager.getInstance().getGamePhase());
        GameManager.getInstance().nextPhase();
        System.out.println(GameManager.getInstance().getGamePhase());
        GameManager.getInstance().nextPhase();
        System.out.println(GameManager.getInstance().getGamePhase());
    }
    @Test
    public void testNextRound(){
        for(int i=0;i<10;i++) {
            System.out.println(GameManager.getInstance().getGamePhase());
            System.out.println(GameManager.getInstance().getActivePlayer().getName());
            GameManager.getInstance().nextRound();
        }
    }
}
