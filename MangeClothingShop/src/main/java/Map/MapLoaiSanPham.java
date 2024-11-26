/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Map;

import DAO.LoaiSanPhamDAO;
import Entity.LoaiSanPham;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import Interfaces.MapData;
/**
 *
 * @author ADMIN
 */
public class MapLoaiSanPham implements MapData<String, String> {
    
    @Override
    public Map<String, String> getMapData() {
        Map<String, String> map = new TreeMap<>();
        LoaiSanPhamDAO dao = new LoaiSanPhamDAO();
        List<LoaiSanPham> list = dao.getAllData();
        
        for (LoaiSanPham o : list) {
            map.put(
                    o.getMaLoai(), 
                    o.getTenLoai()
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
