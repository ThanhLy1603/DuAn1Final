/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Entity.DoanhThu;
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
public class DoanhThuDAO implements DAO<DoanhThu, String>{
    public class MinMaxRevenue {
        private double min;
        private double max;

        public MinMaxRevenue() {
        }

        public MinMaxRevenue(double min, double max) {
            this.min = min;
            this.max = max;
        }
         
        public double getMin() {
            return min;
        }

        public void setMin(double min) {
            this.min = min;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        @Override
        public String toString() {
            return "MinMaxRevenue{" + "min=" + min + ", max=" + max + '}';
        }
    }
    
    @Override
    public List<DoanhThu> getAllData() {
        List<DoanhThu> list = new ArrayList<>();
        String sql = "SELECT * FROM V_DoanhThu ORDER BY NgayLap DESC";
        Object[] values = {};
        
        ResultSet rs = JDBC.executeQuery(sql, values);
        
        try {
            while (rs.next()) {
                list.add(new DoanhThu(
                        rs.getString("MaSanPham"), 
                        rs.getString("TenSanPham"), 
                        rs.getString("TenLoai"),
                        rs.getInt("SoLuong"),
                        rs.getDouble("Gia"),
                        rs.getDouble("TongTien"), 
                        rs.getDate("NgayLap")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoanhThuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
    
    @Override
    public List<DoanhThu> getDataByValue(String value) {
        List<DoanhThu> list = new ArrayList<>();
        String sql = "SELECT  * FROM V_DoanhThu "
                + "Where TenSanPham like ? ORDER BY NgayLap DESC";
        Object[] values = {"%" + value + "%"};
        
        ResultSet rs = JDBC.executeQuery(sql, values);
        
        try {
            while (rs.next()) {
                list.add(new DoanhThu(
                        rs.getString("MaSanPham"), 
                        rs.getString("TenSanPham"), 
                        rs.getString("TenLoai"),
                        rs.getInt("SoLuong"),
                        rs.getDouble("Gia"),
                        rs.getDouble("TongTien"), 
                        rs.getDate("NgayLap")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoanhThuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
    
    public List<Integer> getYear() {
        List<Integer> year = new ArrayList<>();
        String sql = "SELECT DISTINCT YEAR(NgayLap) as Nam FROM V_DoanhThu "
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
    
    public List<DoanhThu> getDataByValue(String value1, String value2, int value3) {
        List<DoanhThu> list = new ArrayList<>();
        String sql = "SELECT  * FROM V_DoanhThu "
                + "Where TenSanPham like ? and TenLoai like ? and Year(NgayLap) = ?"
                + "ORDER BY NgayLap DESC";
        Object[] values = {
            "%" + value1 + "%",
            "%" + value2 + "%",
            value3
        };
        
        ResultSet rs = JDBC.executeQuery(sql, values);
        
        try {
            while (rs.next()) {
                list.add(new DoanhThu(
                        rs.getString("MaSanPham"), 
                        rs.getString("TenSanPham"), 
                        rs.getString("TenLoai"),
                        rs.getInt("SoLuong"),
                        rs.getDouble("Gia"),
                        rs.getDouble("TongTien"), 
                        rs.getDate("NgayLap")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoanhThuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
    
    public List<DoanhThu> getDataByValue(String value1, String value2) {
        List<DoanhThu> list = new ArrayList<>();
        String sql = "SELECT  * FROM V_DoanhThu "
                + "Where TenSanPham like ? and TenLoai like ?"
                + "ORDER BY NgayLap DESC";
        Object[] values = {
            "%" + value1 + "%",
            "%" + value2 + "%",
        };
        
        ResultSet rs = JDBC.executeQuery(sql, values);
        
        try {
            while (rs.next()) {
                list.add(new DoanhThu(
                        rs.getString("MaSanPham"), 
                        rs.getString("TenSanPham"), 
                        rs.getString("TenLoai"),
                        rs.getInt("SoLuong"),
                        rs.getDouble("Gia"),
                        rs.getDouble("TongTien"), 
                        rs.getDate("NgayLap")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoanhThuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
    
    public double getTotalRevenue() {
        double total = 0;
        String sql = "SELECT SUM(TongTien) as Tong FROM V_DoanhThu";
        Object[] values = {};
        
        ResultSet rs = JDBC.executeQuery(sql, values);
        try {
            while (rs.next()) {
                total = rs.getDouble("Tong");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoanhThuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return total;
    }
    
    public MinMaxRevenue getMinMaxRevenue() {
        MinMaxRevenue o = null;
        
        String sql = "SELECT MAX(TongTien) as CaoNhat, MIN(TongTien) as ThapNhat FROM V_DoanhThu";
        Object[] values = {};
        ResultSet rs = JDBC.executeQuery(sql, values);
        
        try {
            while (rs.next()) {
                o = new MinMaxRevenue(
                        rs.getDouble("ThapNhat"), 
                        rs.getDouble("CaoNhat")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(DoanhThuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o;
    }
    
    @Override
    public DoanhThu getDataById(String ma) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insertData(DoanhThu o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void updateData(DoanhThu o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(String ma) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
