package com.cuoiky.coffee3ae.model;

import android.net.Uri;

public class LoaiMon {
    private int maLoai;
    private String tenLoai;
    private String hinhAnh;

    public LoaiMon(int maLoai, String tenLoai, String hinhAnh) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.hinhAnh = hinhAnh;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
