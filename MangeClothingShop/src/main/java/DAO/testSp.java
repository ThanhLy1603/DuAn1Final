/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Entity.SanPham;
import DAO.SanPhamDAO;
import java.util.List;
/**
 *
 * @author PHONG
 */
public class testSp {
    public static void main(String[] args) {
        SanPhamDAO spDao = new SanPhamDAO();
        List<SanPham> list = spDao.getAllData();
        list.forEach(sp -> {System.out.println(sp.getMaSP());});
    }
}
