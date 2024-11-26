/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.KhachHang;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hp
 */
public class TestKhachHang {

    public static void main(String[] args) {
        KhachHangDAO dao = new KhachHangDAO();
//    List<KhachHang> list=dao.getAllData();
//    list.forEach(kh->{
//        System.out.println(kh.getMaKH());
//        System.out.println(kh.getTenKH());
//    });
//    }
//        String ma = "KH1";
//        KhachHang kh = dao.getDataById(ma);
//        System.out.println(ma);
//        System.out.println(kh.getTenKH());
//
//        KhachHang kh=new KhachHang();
//        kh.setMaKH("KH10");
//        kh.setTenKH("Kiet");
//        kh.setGioiTinh(true);
//        kh.setDiaChi("11/48c");
//        kh.setSoDT("0769034419");
//        dao.insertData(kh);
        List<KhachHang> list = dao.getDataByValue("Leao");
        
        for (KhachHang o : list) {
            System.out.println(o.toString());
        }
    }
}
