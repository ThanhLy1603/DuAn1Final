/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.List;
import Entity.DoanhThu;

/**
 *
 * @author ADMIN
 */
public class TestDoanhThu {
    public static void main(String[] args) {
        DoanhThuDAO dao = new DoanhThuDAO();
        
        List<DoanhThu> list = dao.getDataByValue("Váy");
        
//        for (DoanhThu doanhThu : list) {
//            System.out.println(doanhThu.toString());
//        }
              
    }
}
