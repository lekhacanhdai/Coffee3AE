package com.cuoiky.coffee3ae.model;

public class ChiTietDonDat {
    private int maDonDat, maMon, soLuong;

    public ChiTietDonDat(int maDonDat, int maMon, int soLuong) {
        this.maDonDat = maDonDat;
        this.maMon = maMon;
        this.soLuong = soLuong;
    }

    public int getMaDonDat() {
        return maDonDat;
    }

    public void setMaDonDat(int maDonDat) {
        this.maDonDat = maDonDat;
    }

    public int getMaMon() {
        return maMon;
    }

    public void setMaMon(int maMon) {
        this.maMon = maMon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
