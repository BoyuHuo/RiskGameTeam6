package sample.model.service.imp;

import sample.model.entity.Map;
import sample.model.service.IMapManager;

import java.io.*;

public class MapManager implements IMapManager {
    @Override
    public Map LoadMap(String url) {
        File filename = new File(url);
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(
                    new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(reader);
        String line = "";
        try {
            line = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (line != null) {
            try {
                line = br.readLine(); // 一次读入一行数据
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean createMapt() {
        return false;
    }

    @Override
    public boolean isValided(Map map) {
        return false;
    }
}
