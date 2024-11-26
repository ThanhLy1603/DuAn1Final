/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.List;
import Entity.ChiTietHoaDon;
/**
 *
 * @author ADMIN
 */
public class TestCTHD {
    public static void main(String[] args) {
        ChiTietHoaDonDAO dao = new ChiTietHoaDonDAO();
        List<ChiTietHoaDon> list = dao.getAllData();
        
//        for (ChiTietHoaDon o : list) {
//            System.out.println(o.toString());
//        }

//        ChiTietHoaDon o = dao.getDataById("CTHD2");
//        
//        System.out.println(o.toString());

        dao.updateData(new ChiTietHoaDon(
                "CTHD10",
                "HD10", 
                "SP5", 
                "KM4", 
                4, 
                160000, 
                10
        ));
    }
}
