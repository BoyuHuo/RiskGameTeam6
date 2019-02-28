package RiskGame.model.service.imp;

import RiskGame.model.entity.GameMap;
import RiskGame.model.entity.Player;
import RiskGame.model.service.IGameManager;
import com.sun.scenario.effect.impl.prism.ps.PPSBlend_ADDPeer;

import java.util.*;

public class GameManager extends Observable implements IGameManager {

    enum phase {STARTUP, REINFORCEMENTS, ATTACK, FORTIFICATION}

    private Map<String, Player> players;
    private Iterator playerIterator;
    private Player activePlayer;
    private GameMap map;
    private phase gamePhase;
    private static GameManager instance;


    public static GameManager getInstance(){
        if(instance==null){
            instance=new GameManager();
        }
        return instance;
    }
    private GameManager(){}
    public void NewGame() {
        start();
    }

    public void start() {
        startUpPhase();

    }

    public void gameOver() {

    }

    public void startUpPhase() {
        gamePhase = phase.STARTUP;
        nextPlayer();

    }

    public void nextPlayer() {
        if (playerIterator == null || !playerIterator.hasNext()) {
            playerIterator = players.values().iterator();
        }
        activePlayer = (Player) playerIterator.next();
    }

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public void setPlayers(Map<String, Player> players) {
        this.players = players;
    }

    public Map<String, Player> getPlayers() {
        return this.players;
    }

    public void addPlayer(Player p){
        if (players==null || players.isEmpty()){
            players=new HashMap<>();
        }
        players.put(p.getName(),p);
    }
    public void removePlayer(Player p){
        players.remove(p.getName());
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public phase getGamePhase() {
        return gamePhase;
    }

    public void setGamePhase(phase gamePhase) {
        this.gamePhase = gamePhase;
    }

    public Iterator getPlayerIterator() {
        return this.playerIterator;
    }

    public void setPlayerIterator(Iterator iterator) {
        this.playerIterator = iterator;
    }

    public void cleanUp(){
        this.map=new GameMap();
        this.players.clear();
    }


}
