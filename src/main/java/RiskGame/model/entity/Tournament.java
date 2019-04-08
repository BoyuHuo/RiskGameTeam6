package RiskGame.model.entity;

import RiskGame.model.service.imp.GameManager;

import java.util.List;

public class Tournament {
    private String[][] result;
    private List<GameMap> maps;
    private List<Player> players;
    private int maximumTurn;
    private int gameLoop;

    public Tournament(List<GameMap> maps,List<Player> players, int gameLoop, int maximumTurn){
        this.maps = maps;
        this.players = players;
        this.gameLoop = gameLoop;
        this.maximumTurn = maximumTurn;
    }

    public boolean luncheTheMatch(){
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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
