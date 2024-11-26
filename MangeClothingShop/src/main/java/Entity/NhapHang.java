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
public class NhapHang {
    private int maNhap;
    private String maSP;
    private Date ngayNhap;
    private int soLuong;

    public NhapHang(String par, Date ngayNhapHang, int parseInt) {
    }

    public NhapHang(int maNhap, String maSP, Date ngayNhap, int soLuong) {
        this.maNhap = maNhap;
        this.maSP = maSP;
        this.ngayNhap = ngayNhap;
        this.soLuong = soLuong;
    }

    public int getMaNhap() {
        return maNhap;
    }

    public void setMaNhap(int maNhap) {
        this.maNhap = maNhap;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return "NhapHang{" + "maNhap=" + maNhap + ", maSP=" + maSP + ", ngayNhap=" + ngayNhap + ", soLuong=" + soLuong + '}';
    }
}
