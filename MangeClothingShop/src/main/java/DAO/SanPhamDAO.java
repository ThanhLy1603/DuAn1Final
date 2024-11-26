/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Interfaces.DAO;
import Entity.SanPham;
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
public class SanPhamDAO implements DAO<SanPham, String>{

    @Override
    public List<SanPham> getAllData() {
        // Nên truy vấn sắp xếp mã sản phẩm
        List<SanPham> list = new ArrayList();
        String sql = "select * from SanPham";
        Object[] values = {};
        
        ResultSet rs = JDBC.executeQuery(sql, values);
        try {
            while(rs.next()){
                list.add(new SanPham(
                        rs.getString("MaSanPham"),
                        rs.getString("MaLoai"), 
                        rs.getString("TenSanPham"),
                        rs.getDouble("DonGia"), 
                        rs.getInt("SoLuong"), 
                        rs.getString("MauSac"),
                        rs.getString("ChatLieu"),
                        rs.getString("Size"),
                        rs.getString("HinhAnh"),
                        rs.getByte("TrangThai")==1
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public SanPham getDataById(String ma) {
        SanPham sp = null;
        
        String sql = "SELECT * FROM SanPham where MaSanPham=?";
        Object[] values = {ma};
        
        ResultSet rs = JDBC.executeQuery(sql, values);
        try {
            while(rs.next()){
                sp = new SanPham(
                        rs.getString("MaSanPham"),
                        rs.getString("MaLoai"), 
                        rs.getString("TenSanPham"),
                        rs.getDouble("DonGia"), 
                        rs.getInt("SoLuong"), 
                        rs.getString("MauSac"),
                        rs.getString("ChatLieu"),
                        rs.getString("Size"),
                        rs.getString("HinhAnh"),
                        rs.getByte("TrangThai")==1
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sp;
    }

    @Override
    public void insertData(SanPham o) {
        String sql = "EXEC SP_InsertUpdateSanPham ?,?,?,?,?,?,?,?,?,?";
        Object[] values = {
            o.getMaSP(),
            o.getMaLoai(),
            o.getTenSP(),
            o.getDonGia(),
            o.getSoLuong(),
            o.getMauSac(),
            o.getChatLieu(),
            o.getSize(),
            o.getHinhAnh(),
            o.isTrangThai()?1:0
        };
            
        JDBC.executeUpdate(sql, values);
    }

    @Override
    public void updateData(SanPham o) {
        String sql = "EXEC SP_InsertUpdateSanPham ?,?,?,?,?,?,?,?,?,?";
        Object[] values = {
            o.getMaSP(),
            o.getMaLoai(),
            o.getTenSP(),
            o.getDonGia(),
            o.getSoLuong(),
            o.getMauSac(),
            o.getChatLieu(),
            o.getSize(),
            o.getHinhAnh(),
            o.isTrangThai()?1:0
        };
            
        JDBC.executeUpdate(sql, values);
    }
    
    public void updateChiTietSanPham(SanPham o) {
        String sql = "UPDATE SanPham SET MauSac = ?, ChatLieu = ?, Size = ?, HinhAnh = ? WHERE MaSanPham = ?";
        Object[] values = {
            o.getMauSac(),
            o.getChatLieu(),
            o.getSize(),
            o.getHinhAnh(),
            o.getMaSP()
        };
        
        JDBC.executeUpdate(sql, values);
    }
    
    @Override
    public void deleteById(String ma) {
        String sql = "DELETE FROM SanPham WHERE MaSanPham=?";
        Object[] values = {ma};
        
        JDBC.executeUpdate(sql, values);
    }

    @Override
    public List<SanPham> getDataByValue(String value) {
        List<SanPham> list = new ArrayList();
        String sql = "SELECT * FROM SanPham WHERE TenSanPham like ?";
        Object[] values = {"%" + value + "%"};
        
        ResultSet rs = JDBC.executeQuery(sql, values);
        try {
            while(rs.next()){
                list.add(new SanPham(
                        rs.getString("MaSanPham"),
                        rs.getString("MaLoai"), 
                        rs.getString("TenSanPham"),
                        rs.getDouble("DonGia"), 
                        rs.getInt("SoLuong"), 
                        rs.getString("MauSac"),
                        rs.getString("ChatLieu"),
                        rs.getString("Size"),
                        rs.getString("HinhAnh"),
                        rs.getByte("TrangThai")==1
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
