/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.NhanVien;
import Interfaces.DAO;
import Utils.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.plaf.nimbus.NimbusStyle;

/**
 *
 * @author PHONG
 */
public class NhanVienDao implements DAO<NhanVien, String> {

    @Override
    public List<NhanVien> getAllData() {
        String sql = "SELECT * FROM NhanVien";
        Object[] values = {};
        ResultSet rs = JDBC.executeQuery(sql, values);
        List<NhanVien> list = new ArrayList<>();
        try {
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setMatKhau(rs.getString("MatKhau"));
                nv.setGioiTinh(rs.getInt("GioiTinh") == 1);
                nv.setChucVu(rs.getInt("ChucVu") == 1);
                nv.setLuong(rs.getDouble("Luong"));
                nv.setSoDT(rs.getString("SoDT"));
                nv.setEmail(rs.getString("Email"));
                list.add(nv);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public NhanVien getDataById(String ma) {
        String sql = "SELECT * FROM NhanVien WHERE MaNV=?";
        Object[] values = {ma};
        ResultSet rs = JDBC.executeQuery(sql, values);
        try {
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setMatKhau(rs.getString("MatKhau"));
                nv.setGioiTinh(rs.getBoolean("GioiTinh"));
                nv.setChucVu(rs.getBoolean("ChucVu"));
                nv.setLuong(rs.getDouble("Luong"));
                nv.setSoDT(rs.getString("SoDT"));
                nv.setEmail(rs.getString("Email"));
                return nv;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<NhanVien> getDataByValue(Integer gioiTinh, Integer chucVu) {
        List<NhanVien> list = new ArrayList<>();
        String sql = "EXEC SP_FilterNhanVien ?,?";
        Object[] values = {gioiTinh, chucVu};
        ResultSet rs = JDBC.executeQuery(sql, values);
        try {
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setMatKhau(rs.getString("MatKhau"));
                nv.setGioiTinh(rs.getBoolean("GioiTinh"));
                nv.setChucVu(rs.getBoolean("ChucVu"));
                nv.setLuong(rs.getDouble("Luong"));
                nv.setSoDT(rs.getString("SoDT"));
                nv.setEmail(rs.getString("Email"));
                list.add(nv);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<NhanVien> getDateByName(String name) {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE TenNV LIKE ?";
        Object[] values = {"%"+name +"%"};
        ResultSet rs = JDBC.executeQuery(sql, values);  // Giả sử JDBC.executeQuery đã hỗ trợ tham số an toàn
        try {
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setMatKhau(rs.getString("MatKhau"));
                nv.setGioiTinh(rs.getBoolean("GioiTinh"));
                nv.setChucVu(rs.getBoolean("ChucVu"));
                nv.setLuong(rs.getDouble("Luong"));
                nv.setSoDT(rs.getString("SoDT"));
                nv.setEmail(rs.getString("Email"));
                list.add(nv);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;  // Trả về danh sách nhân viên
    }

    @Override
    public void insertData(NhanVien nv) {
        String sql = "EXEC SP_InsertUpdateNhanVien ?, ?, ?,?,?,?,?,?";
        Object[] values = {
            nv.getMaNV(),
            nv.getMatKhau(),
            nv.getTenNV(),
            nv.isGioiTinh(),
            nv.isChucVu(),
            nv.getLuong(),
            nv.getSoDT(),
            nv.getEmail()
        };
        JDBC.executeUpdate(sql, values);
    }

    @Override
    public void updateData(NhanVien nv) {
        String sql = "EXEC SP_InsertUpdateNhanVien ?, ?, ?,?,?,?,?,?";
        Object[] values = {
            nv.getMaNV(),
            nv.getMatKhau(),
            nv.getTenNV(),
            nv.isGioiTinh(),
            nv.isChucVu(),
            nv.getLuong(),
            nv.getSoDT(),
            nv.getEmail()
        };
        JDBC.executeUpdate(sql, values);
    }

    @Override
    public void deleteById(String ma) {
        String sql = "Delete FROM NhanVien WHERE MaNV=?";
        Object[] values = {ma};
        JDBC.executeUpdate(sql, values);
    }

    @Override
    public List<NhanVien> getDataByValue(String value) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
