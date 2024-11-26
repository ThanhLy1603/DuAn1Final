/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Entity.ChiTietHoaDon;
import Interfaces.DAO;
import Utils.JDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */

public class ChiTietHoaDonDAO implements DAO<ChiTietHoaDon, String>{

    @Override
    public List<ChiTietHoaDon> getAllData() {
        List<ChiTietHoaDon> list = new ArrayList<>();
        
        String sql = "SELECT * FROM ChiTietHoaDon";
        Object[] values = {};
        
        ResultSet rs = JDBC.executeQuery(sql, values);
        
        try {
            while (rs.next()){
                list.add(new ChiTietHoaDon(
                        rs.getString("MaCTHD"), 
                        rs.getString("MaHD"), 
                        rs.getString("MaSanPham"), 
                        rs.getString("MaKM"), 
                        rs.getInt("SoLuong"), 
                        rs.getDouble("Gia"), 
                        rs.getFloat("Thue")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietHoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }

    @Override
    public ChiTietHoaDon getDataById(String ma) {
        ChiTietHoaDon cthd = null;
        
        String sql = "SELECT * FROM ChiTietHoaDon WHERE MaCTHD = ?";
        Object[] values = {ma};
        
        ResultSet rs = JDBC.executeQuery(sql, values);
        
        try {
            while (rs.next()) {
                cthd = new ChiTietHoaDon(
                        rs.getString("MaCTHD"), 
                        rs.getString("MaHD"), 
                        rs.getString("MaSanPham"), 
                        rs.getString("MaKM"), 
                        rs.getInt("SoLuong"), 
                        rs.getDouble("Gia"), 
                        rs.getFloat("Thue")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietHoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cthd;
    }

    @Override
    public void insertData(ChiTietHoaDon o) {
        String sql = "EXEC SP_InsertUpdateChiTietHoaDon ?,?,?,?,?,?,?";
        Object[] values = {
            o.getMaCTHD(),
            o.getMaHD(),
            o.getMaSP(),
            o.getMaKM(),
            o.getSoLuong(),
            o.getGia(),
            o.getThue()
        };
        
        JDBC.executeUpdate(sql, values);
    }
    
    @Override
    public void updateData(ChiTietHoaDon o) {
        String sql = "EXEC SP_InsertUpdateChiTietHoaDon ?,?,?,?,?,?,?";
        Object[] values = {
            o.getMaCTHD(),
            o.getMaHD(),
            o.getMaSP(),
            o.getMaKM(),
            o.getSoLuong(),
            o.getGia(),
            o.getThue()
        };
        
        JDBC.executeUpdate(sql, values);
    }

    @Override
    public void deleteById(String ma) {
        String sql = "DELETE FROM ChiTietHoaDon WHERE MaCTHD = ?";
        Object[] values = {ma};
        
        JDBC.executeUpdate(sql, values);
    }


  

    @Override
    public List<ChiTietHoaDon> getDataByValue(String value) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
