package com.example.libraryapplication.model;

import java.io.Serializable;

public class TheLoai implements Serializable {
    private String maTL;
    private String tenTL;

    public TheLoai() {
    }

    public TheLoai(String maTL, String tenTL) {
        this.maTL = maTL;
        this.tenTL = tenTL;
    }

    public String getMaTL() {
        return maTL;
    }

    public void setMaTL(String maTL) {
        this.maTL = maTL;
    }

    public String getTenTL() {
        return tenTL;
    }

    public void setTenTL(String tenTL) {
        this.tenTL = tenTL;
    }
}

