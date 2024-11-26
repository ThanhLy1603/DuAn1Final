/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author ADMIN
 */
public class NhanVien {
    private String maNV;
    private String matKhau;
    private String tenNV;
    private boolean gioiTinh;
    private boolean chucVu;
    private double luong;
    private String soDT;
    private String email;

    public NhanVien() {
    }

    public NhanVien(String maNV, String matKhau, String tenNV, boolean gioiTinh, boolean chucVu, double luong, String soDT, String email) {
        this.maNV = maNV;
        this.matKhau = matKhau;
        this.tenNV = tenNV;
        this.gioiTinh = gioiTinh;
        this.chucVu = chucVu;
        this.luong = luong;
        this.soDT = soDT;
        this.email = email;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public boolean isChucVu() {
        return chucVu;
    }

    public void setChucVu(boolean chucVu) {
        this.chucVu = chucVu;
    }

    public double getLuong() {
        return luong;
    }

    public void setLuong(double luong) {
        this.luong = luong;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public boolean isNam() {
        return isGioiTinh();
    }
    
    public boolean isNu() {
        return !isGioiTinh();
    }
    
    public boolean isTruongPhong() {
        return isChucVu();
    }
    
    public boolean isNhanVien() {
        return !isChucVu();
    }
    
    @Override
    public String toString() {
       return this.maNV; // Chỉ hiển thị mã nhân viên trong ComboBox
    }
}
