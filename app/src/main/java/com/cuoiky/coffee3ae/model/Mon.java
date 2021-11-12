package com.cuoiky.coffee3ae.model;

public class Mon {
    private int maMon, maLoai;
    private String tenMon, giaTien, tinhTrang;
    private String hinhAnh;

    public Mon(int maMon, int maLoai, String tenMon, String giaTien, String tinhTrang, String hinhAnh) {
        this.maMon = maMon;
        this.maLoai = maLoai;
        this.tenMon = tenMon;
        this.giaTien = giaTien;
        this.tinhTrang = tinhTrang;
        this.hinhAnh = hinhAnh;
    }

    public int getMaMon() {
        return maMon;
    }

    public void setMaMon(int maMon) {
        this.maMon = maMon;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
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
