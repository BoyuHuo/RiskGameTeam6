package RiskGame.model.entity;

import RiskGame.model.service.RiskUtil;
import RiskGame.model.service.imp.GameManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tournament {
    private String[][] result;
    private List<GameMap> maps;
    private Map<String, Player> players;
    private int maximumTurn;
    private int gameLoop;

    public Tournament(List<GameMap> maps, Map<String, Player> players, int gameLoop, int maximumTurn) {
        this.maps = maps;
        this.players = players;
        this.gameLoop = gameLoop;
        this.maximumTurn = maximumTurn;
    }

    public String luncheTheMatch(int mapNum) {
        String r = "";
        GameManager.getInstance().setMap(maps.get(mapNum));
        GameManager.getInstance().setPlayers(players);
        GameManager.getInstance().newGame();
        while (true) {
            switch (GameManager.getInstance().getGamePhase()) {
                case "Reinforcements":
                    System.out.println("r");
                    GameManager.getInstance().getActivePlayer().excuteReinforceStrategy(0);
                    break;
                case "Attack":
                    System.out.println("a");
                    GameManager.getInstance().getActivePlayer().excuteAttackStrategy(0);
                    break;
                case "Fortification":
                    System.out.println("f");
                    GameManager.getInstance().getActivePlayer().excuteFortifyStrategy(0);
                    break;
                case "Start Up":
                    System.out.println("s");
                    GameManager.getInstance().getActivePlayer().excuteStartupStrategy(0);
                    break;
            }

            System.out.println(GameManager.getInstance().getActivePlayer().getName()+":"+GameManager.getInstance().getActivePlayer().getPrecentageOfMap());


            if (GameManager.getInstance().isGameOver()) {
                if (GameManager.getInstance().getActivePlayer().getStrategy() instanceof AggressiveStrategy) {
                    r = "aggressive";
                } else if (GameManager.getInstance().getActivePlayer().getStrategy() instanceof BenevolentStrategy) {
                    r = "benevolent";
                } else if (GameManager.getInstance().getActivePlayer().getStrategy() instanceof CheaterStrategy) {
                    r = "cheater";
                }
                break;
            }
            if (GameManager.getInstance().getTotalTurn() > maximumTurn) {
                r = "Draw";
                break;
            }
        }
        return r;
    }

    public boolean start() {
        result= new String[maps.size()][gameLoop];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = "aggressive";
            }
        }
        return true;
    }


    public String[][] getResult() {
        return result;
    }

    public void setResult(String[][] result) {
        this.result = result;
    }

    public List<GameMap> getMaps() {
        return maps;
    }

    public void setMaps(List<GameMap> maps) {
        this.maps = maps;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, Player> players) {
        this.players = players;
    }
}
