package com.example.libraryapplication.model;

import java.time.LocalDate;

public class PhieuMuon {
    private String maPM;
    private LocalDate ngayMuon;
    private String trangThai;
    private int soLuongMuon;
    private String maSinhVien;
    private String maSach;

    public PhieuMuon() {
    }

    public PhieuMuon(String maPM, LocalDate ngayMuon,
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

    public LocalDate getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(LocalDate ngayMuon) {
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
        return "PhieuMuon{" +
                "maPM='" + maPM + '\'' +
                ", ngayMuon=" + ngayMuon +
                ", trangThai='" + trangThai + '\'' +
                ", soLuongMuon=" + soLuongMuon +
                ", maSinhVien='" + maSinhVien + '\'' +
                ", maSach='" + maSach + '\'' +
                '}';
    }
}
