/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author ADMIN
 */
public class ChiTietHoaDon {
    private String maCTHD;
    private String maHD;
    private String maSP;
    private String maKM;
    private int soLuong;
    private double gia;
    private float thue;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(String maCTHD, String maHD, String maSP, String maKM, int soLuong, double gia, float thue) {
        this.maCTHD = maCTHD;
        this.maHD = maHD;
        this.maSP = maSP;
        this.maKM = maKM;
        this.soLuong = soLuong;
        this.gia = gia;
        this.thue = thue;
    }

    public String getMaCTHD() {
        return maCTHD;
    }

    public void setMaCTHD(String maCTHD) {
        this.maCTHD = maCTHD;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public float getThue() {
        return thue;
    }

    public void setThue(float thue) {
        this.thue = thue;
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon{" + "maCTHD=" + maCTHD + ", maHD=" + maHD + ", maSP=" + maSP + ", maKM=" + maKM + ", soLuong=" + soLuong + ", gia=" + gia + ", thue=" + thue + '}';
    }
}
