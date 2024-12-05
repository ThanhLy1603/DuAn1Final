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
public class MapDonGia implements MapData<String, Double>{
    private MapChiTietSanPham mapCTSP = new MapChiTietSanPham();
    
    @Override
    public Map<String, Double> getMapData() {
        Map<String, Double> map = new TreeMap<>();
        
        String sql = "SELECT * FROM DonGia";
        Object[] values = {};
        
        ResultSet rs = JDBC.executeQuery(sql, values);
        
        try {
            while (rs.next()) {
                map.put(
                    mapCTSP.getValueByID(rs.getString("MaSanPham")), 
                    rs.getDouble("DonGia")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(MapDonGia.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return map;
    }

    @Override
    public Double getValueByID(String id) {
        Map<String, Double> map = getMapData();
        double value = 0;
        value = map.get(id);
        
        return value;
    }

    @Override
    public String getIDByValue(Double value) {
        Map<String, Double> map = getMapData();
        String id = null;
        for (Map.Entry<String, Double> o : map.entrySet()) {
            if (Objects.equals(value, o.getValue())) id = o.getKey();
        }
        
        return id; 
    } 
}
