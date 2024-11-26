/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Map;
import Interfaces.MapData;
import java.util.Map;
import java.util.TreeMap;
/**
 *
 * @author ADMIN
 */
public class MapGioiTinh implements MapData<Integer, String> {

    @Override
    public Map<Integer, String> getMapData() {
        Map<Integer, String> gender = new TreeMap<>();
        String[] gioiTinh = {
            "Nữ",
            "Nam",
            "Tất cả"
        };
        
        for (int i = 0; i < 3; i++) {
            gender.put(i, gioiTinh[i]);
        }
        
        return gender;
    }

    @Override
    public Integer getIDByValue(String value) {
        int id = 0;
        Map<Integer, String> gender = getMapData();
        
        for (Map.Entry<Integer, String> o : gender.entrySet()) {
            if (value.equalsIgnoreCase(o.getValue())) 
                id = o.getKey();
        }
        
        return id; 
    }

    @Override
    public String getValueByID(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
