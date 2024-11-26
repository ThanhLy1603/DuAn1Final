/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class DoanhThu {
    private String maSP;
    private String tenSP;
    private String tenLoai;
    private int soLuong;
    private double donGia;
    private double tongTien;
    private Date ngayLap;

    public DoanhThu() {
    }

    public DoanhThu(String maSP, String tenSP, String tenLoai, int soLuong, double donGia, double tongTien, Date ngayLap) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.tenLoai = tenLoai;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.tongTien = tongTien;
        this.ngayLap = ngayLap;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    @Override
    public String toString() {
        return "DoanhThu{" + "maSP=" + maSP + ", tenSP=" + tenSP + ", tenLoai=" + tenLoai + ", soLuong=" + soLuong + ", donGia=" + donGia + ", tongTien=" + tongTien + ", ngayLap=" + ngayLap + '}';
    }
}
