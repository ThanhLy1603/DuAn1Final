/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Entity.SanPham;
import java.util.List;
import Entity.KhuyenMai;
import java.util.Date;
import Entity.LoaiSanPham;
import DAO.SanPhamDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author anhth
 */
public class Test {
    public static void main(String[] args) {
        KhuyenMaiDAO dao = new KhuyenMaiDAO();
        Date date = new Date();
        try {
            
            
            float mucKM = dao.getDataByDate(date);
            
            System.out.println(date);
            System.out.println(mucKM);
            
        } catch (SQLException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}

