package com.example.libraryapplication.model;

import java.util.Date;

public class PhieuViPham {
    private String maPhieuVP;
    private double soTienPhat;
    private int soNgayQuaHan;
    private String trangThai;
    private String kieuVP;
    private String maPM;
    private Date ngayLap;

    // Constructor không tham số
    public PhieuViPham() {
    }

    // Constructor đầy đủ tham số
    public PhieuViPham(String maPhieuVP, double soTienPhat, int soNgayQuaHan,
                       String trangThai, String kieuVP, String maPM, Date ngayLap) {
        this.maPhieuVP = maPhieuVP;
        this.soTienPhat = soTienPhat;
        this.soNgayQuaHan = soNgayQuaHan;
        this.trangThai = trangThai;
        this.kieuVP = kieuVP;
        this.maPM = maPM;
        this.ngayLap = ngayLap;
        this.ngayLap=ngayLap;
    }

    // Getter & Setter
    public String getMaPhieuVP() {
        return maPhieuVP;
    }

    public void setMaPhieuVP(String maPhieuVP) {
        this.maPhieuVP = maPhieuVP;
    }

    public double getSoTienPhat() {
        return soTienPhat;
    }

    public void setSoTienPhat(double soTienPhat) {
        this.soTienPhat = soTienPhat;
    }

    public int getSoNgayQuaHan() {
        return soNgayQuaHan;
    }

    public void setSoNgayQuaHan(int soNgayQuaHan) {
        this.soNgayQuaHan = soNgayQuaHan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getKieuVP() {
        return kieuVP;
    }

    public void setKieuVP(String kieuVP) {
        this.kieuVP = kieuVP;
    }

    public String getMaPM() {
        return maPM;
    }

    public void setMaPM(String maPM) {
        this.maPM = maPM;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }
    public Date getngayLap(){return ngayLap;}
    public void setngayLap(Date ngayLap){this.ngayLap=ngayLap;}
}
