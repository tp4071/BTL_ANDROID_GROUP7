package com.example.libraryapplication.model;

public class ThuThu {
    private String maTK;
    private String tenTK;
    private String matKhau;

    public ThuThu() {
    }

    public ThuThu(String maTK, String tenTK, String matKhau) {
        this.maTK = maTK;
        this.tenTK = tenTK;
        this.matKhau = matKhau;
    }

    public String getMaTK() {
        return maTK;
    }

    public void setMaTK(String maTK) {
        this.maTK = maTK;
    }

    public String getTenTK() {
        return tenTK;
    }

    public void setTenTK(String tenTK) {
        this.tenTK = tenTK;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}

