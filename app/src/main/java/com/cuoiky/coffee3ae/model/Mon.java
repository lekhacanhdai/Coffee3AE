package com.cuoiky.coffee3ae.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Mon {
    public int maMon;
    public String tenMon, giaTien, tinhTrang;
    public String hinhAnh;
    public LoaiMon loaiMon;

    public Mon(){}
    public int getMaMon() {
        return maMon;
    }

    public void setMaMon(int maMon) {
        this.maMon = maMon;
    }

    public LoaiMon getLoaiMon() {
        return loaiMon;
    }

    public Mon(int maMon, String tenMon, String giaTien, String tinhTrang, String hinhAnh, LoaiMon loaiMon) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.giaTien = giaTien;
        this.tinhTrang = tinhTrang;
        this.hinhAnh = hinhAnh;
        this.loaiMon = loaiMon;
    }

    public void setLoaiMon(LoaiMon loaiMon) {
        this.loaiMon = loaiMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
