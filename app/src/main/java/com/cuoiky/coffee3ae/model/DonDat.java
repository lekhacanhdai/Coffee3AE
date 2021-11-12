package com.cuoiky.coffee3ae.model;

public class DonDat {
    private int maDonDat, maBan, maNV;
    private String tinhTrang, ngayDat, tongTien;

    public DonDat(int maDonDat, int maBan, int maNV, String tinhTrang, String ngayDat, String tongTien) {
        this.maDonDat = maDonDat;
        this.maBan = maBan;
        this.maNV = maNV;
        this.tinhTrang = tinhTrang;
        this.ngayDat = ngayDat;
        this.tongTien = tongTien;
    }

    public int getMaDonDat() {
        return maDonDat;
    }

    public void setMaDonDat(int maDonDat) {
        this.maDonDat = maDonDat;
    }

    public int getMaBan() {
        return maBan;
    }

    public void setMaBan(int maBan) {
        this.maBan = maBan;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
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
}
