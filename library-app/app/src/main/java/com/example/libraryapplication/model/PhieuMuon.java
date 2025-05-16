package com.example.libraryapplication.model;

import java.util.Date;
import java.text.SimpleDateFormat;

public class PhieuMuon {
    private String maPM;
    private Date ngayMuon;
    private String trangThai;
    private int soLuongMuon;
    private String maSinhVien;
    private String maSach;

    public PhieuMuon() {
    }

    public PhieuMuon(String maPM, Date ngayMuon,
                     String trangThai, int soLuongMuon,
                     String maSinhVien, String maSach) {
        this.maPM = maPM;
        this.ngayMuon = ngayMuon;
        this.trangThai = trangThai;
        this.soLuongMuon = soLuongMuon;
        this.maSinhVien = maSinhVien;
        this.maSach = maSach;
    }

    public String getMaPM() {
        return maPM;
    }

    public void setMaPM(String maPM) {
        this.maPM = maPM;
    }

    public Date getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(Date ngayMuon) {
        this.ngayMuon = ngayMuon;
    }


    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getSoLuongMuon() {
        return soLuongMuon;
    }

    public void setSoLuongMuon(int soLuongMuon) {
        this.soLuongMuon = soLuongMuon;
    }

    public String getMaSinhVien() {
        return maSinhVien;
    }

    public void setMaSinhVien(String maSinhVien) {
        this.maSinhVien = maSinhVien;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        return "ID:"+maPM+" - Ngày mượn: "+formatter.format(ngayMuon)+"\nMã sinh viên: "+maSinhVien+"\nMã sách: "+maSach+"\nTrạng thái: "+trangThai;
    }

}
