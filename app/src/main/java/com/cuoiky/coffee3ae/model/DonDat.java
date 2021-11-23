package com.cuoiky.coffee3ae.model;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class DonDat {
    int maDonDat;
    private String tinhTrang, ngayDat, tongTien;
    public BanAn ban;
    public NhanVien nhanVien;

    public DonDat(int maDonDat, String tinhTrang, String ngayDat, String tongTien, BanAn ban, NhanVien nhanVien) {
        this.maDonDat = maDonDat;
        this.tinhTrang = tinhTrang;
        this.ngayDat = ngayDat;
        this.tongTien = tongTien;
        this.ban = ban;
        this.nhanVien = nhanVien;
    }
    public DonDat(){}
    public int getMaDonDat() {
        return maDonDat;
    }

    public void setMaDonDat(int maDonDat) {
        this.maDonDat = maDonDat;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(String ngayDat) {
        this.ngayDat = ngayDat;
    }

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }

    public BanAn getBan() {
        return ban;
    }

    public void setBan(BanAn ban) {
        this.ban = ban;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }
}
