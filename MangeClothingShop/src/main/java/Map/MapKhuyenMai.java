/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Map;

import Interfaces.MapData;
import Utils.JDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class MapKhuyenMai implements MapData<String, Integer>{

    @Override
    public Map<String, Integer> getMapData() {
        Map<String, Integer> map = new TreeMap<>();
        
        String sql = "select * from KhuyenMai";
        Object[] values = {};
        
        ResultSet rs = JDBC.executeQuery(sql, values);
        try {
            while (rs.next()){
                map.put(
                    rs.getString("MaKM"), 
                    rs.getInt("MucKM")
                );
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MapNhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return map;
    }

    @Override
    public Integer getValueByID(String id) {
        Map<String, Integer> map = getMapData();
        int value = 0;
        value = map.get(id);
        
        return value;
    }

    @Override
    public String getIDByValue(Integer value) {
        Map<String, Integer> map = getMapData();
        String id = null;
        for (Map.Entry<String, Integer> o : map.entrySet()) {
            if (Objects.equals(value, o.getValue())) id = o.getKey();
        }
        
        return id;
    }
}
