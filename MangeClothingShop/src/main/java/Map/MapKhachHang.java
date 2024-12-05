/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Map;
import DAO.KhachHangDAO;
import Entity.KhachHang;
import Interfaces.MapData;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
/**
 *
 * @author ADMIN
 */
public class MapKhachHang implements MapData<String, String> {

    @Override
    public Map<String, String> getMapData() {
        Map<String, String> map = new TreeMap<>();
        KhachHangDAO dao = new KhachHangDAO();
        List<KhachHang> list = dao.getAllData();
        
        for (KhachHang o : list) {
            map.put(
                o.getMaKH(), 
                o.getTenKH()
            );
        }
        
        return map;
    }

    @Override
    public String getValueByID(String id) {
        String value = null;
        Map<String, String> map = getMapData();   
        value = map.get(id);
        
        return value;
    }

    @Override
    public String getIDByValue(String value) {
        String id = null;
        Map<String, String> map = getMapData();
        
        for (Map.Entry<String, String> o : map.entrySet()) {
            if (value.equalsIgnoreCase(o.getValue()))
            id = o.getKey();
        }
        
        return id;
    }
    
}
