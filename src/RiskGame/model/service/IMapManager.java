package RiskGame.model.service;

import RiskGame.model.entity.GameMap;

public interface IMapManager {
    public GameMap LoadMap(String url);
    public boolean createMap();
    boolean isValided(GameMap gameMap);
}
