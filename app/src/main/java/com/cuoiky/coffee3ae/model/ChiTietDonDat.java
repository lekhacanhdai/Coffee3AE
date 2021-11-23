package com.cuoiky.coffee3ae.model;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ChiTietDonDat {
    public int soLuong;
    public Mon mon;
    public DonDat donDat;

    public ChiTietDonDat(int soLuong, Mon mon, DonDat donDat) {
        this.soLuong = soLuong;
        this.mon = mon;
        this.donDat = donDat;
    }

    public ChiTietDonDat(){}

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Mon getMon() {
        return mon;
    }

    public void setMon(Mon mon) {
        this.mon = mon;
    }

    public DonDat getDonDat() {
        return donDat;
    }

    public void setDonDat(DonDat donDat) {
        this.donDat = donDat;
    }
}
