/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Map;
import java.util.Map;
/**
 *
 * @author ADMIN
 */
public class Test {
    public static void main(String[] args) {
        MapKhuyenMai map = new MapKhuyenMai();
        Map<String, Integer> list = map.getMapData();
        
        for (Map.Entry<String, Integer> o : list.entrySet()) {
            System.out.println(o.getKey());
            System.out.println(o.getValue());
        }
        
////        System.out.println(map.getIDByValue("Nguyễn Thành Lý"));
//        System.out.println(map.getValueByID("PS43138"));
    }
}
