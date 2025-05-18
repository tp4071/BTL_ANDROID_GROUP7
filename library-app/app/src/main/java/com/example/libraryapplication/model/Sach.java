package com.example.libraryapplication.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sach implements Serializable {
    private String maSach;
    private String tenSach;
    private String nxb;
    private Date nph;
    private int soLuong;
    private int soTrang;
    private String tacGia;
    private double giaTien;
    private String maTL;
    private Integer soLuotMuon;

    public Sach() {
    }

    public Sach(String maSach, String tenSach, String nxb, Date nph, int soLuong,
                int soTrang, String tacGia, double giaTien, String maTL, int soLuotMuon) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.nxb = nxb;
        this.nph = nph;
        this.soLuong = soLuong;
        this.soTrang = soTrang;
        this.tacGia = tacGia;
        this.giaTien = giaTien;
        this.maTL = maTL;
        this.soLuotMuon=soLuotMuon;
    }

    public Sach(String maSach, String tenSach, String nxb, Date nph, int soLuong,
                int soTrang, String tacGia, double giaTien, String maTL) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.nxb = nxb;
        this.nph = nph;
        this.soLuong = soLuong;
        this.soTrang = soTrang;
        this.tacGia = tacGia;
        this.giaTien = giaTien;
        this.maTL = maTL;
    }
    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getNxb() {
        return nxb;
    }

    public void setNxb(String nxb) {
        this.nxb = nxb;
    }

    public Date getNph() {
        return nph;
    }

    public void setNph(Date nph) {
        this.nph = nph;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getSoTrang() {
        return soTrang;
    }

    public void setSoTrang(int soTrang) {
        this.soTrang = soTrang;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }

    public String getMaTL() {
        return maTL;
    }

    public void setMaTL(String maTL) {
        this.maTL = maTL;
    }

    public int getSlMuon(){return soLuotMuon;}
    public void setSLMuon(int slMuon){this.soLuotMuon=slMuon;}

    @NonNull
    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return "\n"+tenSach+" \n"+"Tác giả: "+tacGia+"\nNhà xuất bản: "+nxb + "\n";
    }
}

