/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Entity.DonGia;
import Interfaces.DAO;
import Map.MapChiTietSanPham;
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
public class DonGiaDAO implements DAO<DonGia, String>{
    private MapChiTietSanPham map = new MapChiTietSanPham();
    
    @Override
    public List<DonGia> getAllData() {
        List<DonGia> list = new ArrayList<>();
        String sql = "SELECT * FROM DonGia";
        Object[] values = {};
        
        ResultSet rs = JDBC.executeQuery(sql, values);
        
        try {
            while (rs.next()) {
                list.add(new DonGia(
                        map.getValueByID(rs.getString("MaSanPham")), 
                        rs.getFloat("DonGia")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DonGiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }

    @Override
    public List<DonGia> getDataByValue(String value) {
        return null;
    }

    @Override
    public DonGia getDataById(String ma) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insertData(DonGia o) {
        String sql = "INSERT INTO DonGia VALUES (?,?)";
        Object[] values = {
            map.getIDByValue(o.getTenSP()),
            o.getDonGia()
        };
        
        JDBC.executeUpdate(sql, values);
    }

    @Override
    public void updateData(DonGia o) {
        String sql = "UPDATE DonGia SET DonGia = ? WHERE MaSanPham = ?";
        Object[] values = {
            o.getDonGia(),
            map.getIDByValue(o.getTenSP())
        };
        
        JDBC.executeUpdate(sql, values);
    }

    @Override
    public void deleteById(String ma) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
