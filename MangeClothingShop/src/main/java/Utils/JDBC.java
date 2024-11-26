/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class JDBC {
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=DA_QuanLyBanQuanAo;encrypt=true;trustServerCertificate=true";
    private static final String USERNAME = "DuAn1";
    private static final String PASSWORD = "123";
    
    private static Connection connection;
    
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static Connection getConnection() throws SQLException{
        if(connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        }
        return connection;
    }
    
    public static PreparedStatement createPreparedStatement(String sql, Object...values) throws SQLException{
        PreparedStatement ps = getConnection().prepareStatement(sql);
        for(int i=0; i<values.length; i++){
            ps.setObject(i+1, values[i]);
        }
        return ps;
    }
    
    public static ResultSet executeQuery(String sql, Object...values){
        try {
            PreparedStatement ps = createPreparedStatement(sql, values);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static int executeUpdate(String sql, Object...values){
        try {
            PreparedStatement ps = createPreparedStatement(sql, values);
            int count = ps.executeUpdate();
            return count;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static <T> T getValue(String sql, Object...values){
        try {
            ResultSet rs = executeQuery(sql, values);
            if(rs.next()){
                return (T) rs.getObject(1);
            } else {
                throw new RuntimeException("Not found!");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private static void query() {
        String sql = "SELECT count(*) FROM NhanVien";
        Object[] values = {};
        
        Integer value = getValue(sql, values);
    }
}
