package com.cuoiky.coffee3ae.model;

public class BanAn {
    private int maBan;
    private String tenBan;
    private boolean duocChon;

    public BanAn(int maBan, String tenBan, boolean duocChon) {
        this.maBan = maBan;
        this.tenBan = tenBan;
        this.duocChon = duocChon;
    }

    public int getMaBan() {
        return maBan;
    }

    public String getTenBan() {
        return tenBan;
    }

    public boolean isDuocChon() {
        return duocChon;
    }

    public void setMaBan(int maBan) {
        this.maBan = maBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public void setDuocChon(boolean duocChon) {
        this.duocChon = duocChon;
    }
}
