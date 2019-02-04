package sample.model.service;

import sample.model.entity.Map;

public interface IMapManager {
    public Map LoadMap(String url);
    public boolean createMapt();
    boolean isValided(Map map);
}
