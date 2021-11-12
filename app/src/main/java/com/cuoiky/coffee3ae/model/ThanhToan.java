package com.cuoiky.coffee3ae.model;

public class ThanhToan {
    private String tenMon;
    private int soLuong, giaTien;
    private String hinhAnh;

    public ThanhToan(String tenMon, int soLuong, int giaTien, String hinhAnh) {
        this.tenMon = tenMon;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.hinhAnh = hinhAnh;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
