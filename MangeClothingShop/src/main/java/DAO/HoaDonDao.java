/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.HoaDon;
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
 * @author PHONG
 */
public class HoaDonDao implements DAO<HoaDon, String> {

    @Override
    public List<HoaDon> getAllData() {
        String sql = "SELECT * FROM HoaDon";
        Object[] values = {};
        ResultSet rs = JDBC.executeQuery(sql, values);
        List<HoaDon> list = new ArrayList<>();
        try {
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("MaHD"));
                hd.setMaNV(rs.getString("MaNV"));
                hd.setMaKH(rs.getString("MaKH"));
                hd.setNgayLap(rs.getDate("NgayLap"));
                hd.setHinhThuc(rs.getString("HinhThuc"));
                hd.setTrangThai(rs.getInt("TrangThai") == 1);
                list.add(hd);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HoaDon getDataById(String ma) {
        String sql = "SELECT * FROM HoaDon WHERE MaHD=?";
        Object[] values = {ma};
        ResultSet rs = JDBC.executeQuery(sql, values);
        try {
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("MaHD"));
                hd.setMaNV(rs.getString("MaNV"));
                hd.setMaKH(rs.getString("MaKH"));
                hd.setNgayLap(rs.getDate("NgayLap"));
                hd.setHinhThuc(rs.getString("HinhThuc"));
                hd.setTrangThai(rs.getInt("TrangThai") == 1);
                return hd;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void insertData(HoaDon hd) {
        String sql = "EXEC SP_InsertUpdateHoaDon ?,?,?,?,?,?";
        Object[] values = {
            hd.getMaHD(),
            hd.getMaNV(),
            hd.getMaKH(),
            hd.getNgayLap(),
            hd.getHinhThuc(),
            hd.isTrangThai() ? 1 : 0
        };

        JDBC.executeUpdate(sql, values);
    }

    @Override
    public void updateData(HoaDon hd) {
        String sql = "EXEC SP_InsertUpdateHoaDon ?,?,?,?,?,?";
        Object[] values = {
            hd.getMaHD(),
            hd.getMaNV(),
            hd.getMaKH(),
            hd.getNgayLap(),
            hd.getHinhThuc(),
            hd.isTrangThai() ? 1 : 0
        };

        JDBC.executeUpdate(sql, values);
    }

    @Override
    public void deleteById(String ma) {
        String sql = "Delete FROM HoaDon WHERE MaHD=?";
        Object[] values = {ma};
        JDBC.executeUpdate(sql, values);
    }

    public List<HoaDon> getDataByValue(String maNV, int thang, int nam) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "select * from HoaDon where MaNV like ? and MONTH(NgayLap)=? and YEAR(NgayLap)=?";
        Object[] values = {maNV, thang, nam};
        ResultSet rs = JDBC.executeQuery(sql, values);
        try {
            while (rs.next()) {
                list.add(new HoaDon(
                        rs.getString("MaHD"),
                        rs.getString("MaNV"),
                        rs.getString("MaKh"),
                        rs.getDate("NgayLap"),
                        rs.getString("HinhThuc"),
                        rs.getByte("TrangThai") == 1
                ));
            }
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console
        }
        return list;
    }
    
     public List<HoaDon> getDataTime(int thang, int nam) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "select * from HoaDon where MONTH(NgayLap)=? and YEAR(NgayLap)=?";
        Object[] values = {thang, nam};
        ResultSet rs = JDBC.executeQuery(sql, values);
        try {
            while (rs.next()) {
                list.add(new HoaDon(
                        rs.getString("MaHD"),
                        rs.getString("MaNV"),
                        rs.getString("MaKh"),
                        rs.getDate("NgayLap"),
                        rs.getString("HinhThuc"),
                        rs.getByte("TrangThai") == 1
                ));
            }
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console
        }
        return list;
    }

    public List<HoaDon> getDataByOnlyYear(int nam) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "select * from HoaDon where YEAR(NgayLap)=?";
        Object[] values = {nam};
        ResultSet rs = JDBC.executeQuery(sql, values);
        try {
            while (rs.next()) {
                list.add(new HoaDon(
                        rs.getString("MaHD"),
                        rs.getString("MaNV"),
                        rs.getString("MaKh"),
                        rs.getDate("NgayLap"),
                        rs.getString("HinhThuc"),
                        rs.getByte("TrangThai") == 1
                ));
            }
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console
        }
        return list;
    }

 
    public List<HoaDon> getDataByOnlyMonth(int thang) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "select * from HoaDon where MONTH(NgayLap)=?";
        Object[] values = {thang};
        ResultSet rs = JDBC.executeQuery(sql, values);
        try {
            while (rs.next()) {
                list.add(new HoaDon(
                        rs.getString("MaHD"),
                        rs.getString("MaNV"),
                        rs.getString("MaKh"),
                        rs.getDate("NgayLap"),
                        rs.getString("HinhThuc"),
                        rs.getByte("TrangThai") == 1
                ));
            }
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console
        }
        return list;
    }

    public List<HoaDon> getDataByNam(String maNV, int nam) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "select * from HoaDon where MaNV like ? and YEAR(NgayLap)=?";
        Object[] values = {maNV, nam};
        ResultSet rs = JDBC.executeQuery(sql, values);
        try {
            while (rs.next()) {
                list.add(new HoaDon(
                        rs.getString("MaHD"),
                        rs.getString("MaNV"),
                        rs.getString("MaKh"),
                        rs.getDate("NgayLap"),
                        rs.getString("HinhThuc"),
                        rs.getByte("TrangThai") == 1
                ));
            }
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console
        }
        return list;
    }

    public List<HoaDon> getDataByThang(String maNV, int thang) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "select * from HoaDon where MaNV like ? and MONTH(NgayLap)=?";
        Object[] values = {maNV, thang};
        ResultSet rs = JDBC.executeQuery(sql, values);
        try {
            while (rs.next()) {
                list.add(new HoaDon(
                        rs.getString("MaHD"),
                        rs.getString("MaNV"),
                        rs.getString("MaKh"),
                        rs.getDate("NgayLap"),
                        rs.getString("HinhThuc"),
                        rs.getByte("TrangThai") == 1
                ));
            }
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console
        }
        return list;
    }

    public List<HoaDon> getDataByValues(String maNV) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "Select * from HoaDon where MaNV like ?";
        Object[] values = {maNV};
        ResultSet rs = JDBC.executeQuery(sql, values);
        try {
            while (rs.next()) {
                list.add(new HoaDon(
                        rs.getString("MaHD"),
                        rs.getString("MaNV"),
                        rs.getString("MaKh"),
                        rs.getDate("NgayLap"),
                        rs.getString("HinhThuc"),
                        rs.getByte("TrangThai") == 1
                ));
            }
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console
        }
        return list;
    }

    public List<Integer> getYear() {
        List<Integer> year = new ArrayList<>();
        String sql = "SELECT DISTINCT YEAR(NgayLap) as Nam FROM HoaDon "
                + "ORDER BY Nam DESC";
        Object[] values = {};
        ResultSet rs = JDBC.executeQuery(sql, values);
        try {
            while (rs.next()) {
                year.add(rs.getInt("Nam"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoanhThuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return year;
    }

    public List<Integer> getMonth() {
        List<Integer> month = new ArrayList<>();
        String sql = "SELECT DISTINCT MONTH(NgayLap) as Thang FROM HoaDon "
                + "ORDER BY Thang DESC";
        Object[] values = {};
        ResultSet rs = JDBC.executeQuery(sql, values);
        try {
            while (rs.next()) {
                month.add(rs.getInt("Thang"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoanhThuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return month;
    }

    public List<String> getMaNV() {
        List<String> manv = new ArrayList<>();
        String sql = "SELECT MaNV FROM NhanVien";
        Object[] values = {};
        ResultSet rs = JDBC.executeQuery(sql, values);
        try {
            while (rs.next()) {
                manv.add(rs.getString("MaNV"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoanhThuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return manv;
    }

    @Override
    public List<HoaDon> getDataByValue(String value) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
