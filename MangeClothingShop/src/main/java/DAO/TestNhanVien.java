/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Entity.NhanVien;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hp
 */
public class TestNhanVien {
    public static void main(String[] args) {
        NhanVienDao nv= new NhanVienDao();
        List<NhanVien> list= nv.getAllData();
        
//        list.forEach(nv1->{
//            System.out.println(nv1.toString());
//            }
//        );
        list = nv.getDataByValue(1,1);
        list.forEach(o->{
            System.out.println(o.toString());
        });
                
    }   
}
