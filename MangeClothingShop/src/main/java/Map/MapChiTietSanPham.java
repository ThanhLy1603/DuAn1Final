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
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class MapChiTietSanPham implements MapData<String, String>{

    @Override
    public Map<String, String> getMapData() {
        Map<String, String> map = new TreeMap<>();
        String sql = "SELECT * FROM SanPham";
        Object[] values = {};
        
        ResultSet rs = JDBC.executeQuery(sql, values);
        
        try {
            while (rs.next()) {
                map.put(
                    rs.getString("MaSanPham"), 
                    rs.getString("TenSanPham") + ", " + rs.getString("MauSac") + ", " + rs.getString("Size")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(MapSanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return map;
    }

    @Override
    public String getValueByID(String id) {
        Map<String, String> map = getMapData();
        String value = null;
        value = map.get(id);
        
        return value;
    }

    @Override
    public String getIDByValue(String value) {
        Map<String, String> map = getMapData();
        String id = null;
        for (Map.Entry<String, String> o : map.entrySet()) {
            if (value.equalsIgnoreCase(o.getValue())) id = o.getKey();
        }
        
        return id;
    }
}
