    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Entity.NhapHang;
import Interfaces.DAO;
import Map.MapSanPham;
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
public class NhapHangDAO implements DAO<NhapHang, Integer>{
    private MapSanPham mapSP = new MapSanPham();
    @Override
    public List<NhapHang> getAllData() {
        List<NhapHang> list = new ArrayList<>();
        String sql = "SELECT * FROM NhapHang";
        Object[] values = {};
        
        ResultSet rs = JDBC.executeQuery(sql, values);
        try {
            while (rs.next()) {
                list.add(new NhapHang(
                        rs.getInt("MaNhap"), 
                        rs.getString("MaSanPham"), 
                        rs.getDate("NgayNhap"), 
                        rs.getInt("SoLuong")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }

    @Override
    public NhapHang getDataById(Integer ma) {
        NhapHang nh = null;
        String sql = "SELECT * FROM NhapHang WHERE MaNhap = ?";
        Object[] values = {ma};
        
        ResultSet rs = JDBC.executeQuery(sql, values);
        try {
            while (rs.next()) {
                nh = new NhapHang(
                    rs.getInt("MaNhap"), 
                    rs.getString("MaSanPham"), 
                    rs.getDate("NgayNhap"), 
                    rs.getInt("SoLuong")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhapHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nh;
    }

    @Override
    public void insertData(NhapHang o) {
        String sql = "INSERT INTO NhapHang (MaSanPham, NgayNhap, SoLuong) VALUES (?, ?, ?)";
        Object[] values = {
            o.getMaSP(),
            o.getNgayNhap(),
            o.getSoLuong()
        };
        
        JDBC.executeUpdate(sql, values);
    }

    @Override
    public void updateData(NhapHang o) {
        String sql = "EXEC SP_InsertUpdateNhapHang ?,?,?,?";
        Object[] values = {
            o.getMaNhap(),
            o.getMaSP(),
            o.getNgayNhap(),
            o.getSoLuong()
        };
        
        JDBC.executeUpdate(sql, values);
    }

    @Override
    public void deleteById(Integer ma) {
        throw new UnsupportedOperationException("Not Available");
    }

    @Override
    public List<NhapHang> getDataByValue(Integer value) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public List<NhapHang> getDataByValue(String value) {
        List<NhapHang> list = new ArrayList();
        String sql = "select nh.MaNhap, sp.TenSanPham, nh.NgayNhap, nh.SoLuong\n" +
                        "from NhapHang as nh\n" +
                        "inner join SanPham as sp\n" +
                        "on sp.MaSanPham = nh.MaSanPham\n" +
                        "Where sp.TenSanPham like ?";
        Object[] values = {"%" + value + "%"};
        
        ResultSet rs = JDBC.executeQuery(sql, values);
        try {
            while(rs.next()){
                list.add(new NhapHang(
                        rs.getInt("MaNhap"),
                        mapSP.getIDByValue(rs.getString("TenSanPham")),
                        rs.getDate("NgayNhap"),
                        rs.getInt("SoLuong")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;            
    }
}
