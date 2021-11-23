package com.cuoiky.coffee3ae.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class NhanVien {
    private int maNV;
    private String hoTenNV, tenDN, matKhau, email, sdt, gioiTinh, ngaySinh;
    public Quyen quyen;

    public NhanVien(int maNV, String hoTenNV, String tenDN, String matKhau, String email, String sdt, String gioiTinh, String ngaySinh, Quyen quyen) {
        this.maNV = maNV;
        this.hoTenNV = hoTenNV;
        this.tenDN = tenDN;
        this.matKhau = matKhau;
        this.email = email;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.quyen = quyen;
    }
    public NhanVien() {
    }


    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public Quyen getQuyen() {
        return quyen;
    }

    public void setQuyen(Quyen quyen) {
        this.quyen = quyen;
    }

    public String getHoTenNV() {
        return hoTenNV;
    }

    public void setHoTenNV(String hoTenNV) {
        this.hoTenNV = hoTenNV;
    }

    public String getTenDN() {
        return tenDN;
    }

    public void setTenDN(String tenDN) {
        this.tenDN = tenDN;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
}
