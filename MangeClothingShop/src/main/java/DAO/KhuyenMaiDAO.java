/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Entity.KhuyenMai;
import Interfaces.DAO;
import Utils.JDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hp
 */
public class KhuyenMaiDAO implements DAO<KhuyenMai, String> {

    @Override
    public List<KhuyenMai> getAllData() {
        List<KhuyenMai> list = new ArrayList<>();
        String sql = "SELECT * FROM KhuyenMai";
        Object[] values = {};
        ResultSet rs = JDBC.executeQuery(sql, values);
        
        try {
            while (rs.next()) {
                list.add(new KhuyenMai(
                        rs.getString("MaKM"),
                        rs.getString("TenKM"),
                        rs.getFloat("MucKM"),
                        rs.getDate("NgayBatDau"),
                        rs.getDate("NgayKetThuc")
                ));
            }
            
            list.remove(0);
        } catch (SQLException ex) {
            Logger.getLogger(KhuyenMaiDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }

    @Override
    public KhuyenMai getDataById(String ma) {
        KhuyenMai km = null;
        String sql = "SELECT * FROM KhuyenMai WHERE MaKM = ?";
        Object[] values = {ma};
        
        ResultSet rs = JDBC.executeQuery(sql, values);
        
        try {
            while (rs.next()) {
                km = new KhuyenMai(
                        rs.getString("MaKM"),
                        rs.getString("TenKM"),
                        rs.getFloat("MucKM"),
                        rs.getDate("NgayBatDau"),
                        rs.getDate("NgayKetThuc")
                );
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhuyenMaiDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return km;
    }

    @Override
    public void insertData(KhuyenMai o) {
        String sql = "EXEC SP_InsertUpdateKhuyenMai ?,?,?,?,?";
        Object[] values = {
            o.getMaKM(), 
            o.getTenKM(), 
            o.getMucKM(), 
            o.getNgayBatDau(), 
            o.getNgayKetThuc()
        };
        
        JDBC.executeUpdate(sql, values);
    }

    @Override
    public void updateData(KhuyenMai o) {
        String sql = "EXEC SP_InsertUpdateKhuyenMai ?,?,?,?,?";
        Object[] values = {
            o.getMaKM(), 
            o.getTenKM(), 
            o.getMucKM(), 
            o.getNgayBatDau(), 
            o.getNgayKetThuc()
        };
        
        JDBC.executeUpdate(sql, values);
    }

    @Override
    public void deleteById(String ma) {
        String sql = "DELETE FROM KhuyenMai WHERE MaKM = ?";
        Object[] values = {ma};
        
        JDBC.executeUpdate(sql, values);
    }
    
    public float getDataByDate(Date date) throws SQLException {
        String sql = "SELECT Top 1 MucKM\n" +
                        "FROM KhuyenMai\n" +
                        "WHERE ? BETWEEN NgayBatDau AND NgayKetThuc\n" +
                        "ORDER BY MucKM DESC";
        
        Object[] values = {date};
        float mucKM = 0;
        
        ResultSet rs = JDBC.executeQuery(sql, values);
        
        while (rs.next()) {
            mucKM = rs.getFloat("MucKM");
        }
        
        return mucKM;
    }
    
    @Override
    public List<KhuyenMai> getDataByValue(String value) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
