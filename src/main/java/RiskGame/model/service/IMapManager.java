package RiskGame.model.service;

import RiskGame.model.entity.GameMap;

public interface IMapManager {
    public GameMap LoadMap(String url);
    public boolean CreateMap(String url,GameMap gameMap);
    boolean IsValided(GameMap gameMap);
}
