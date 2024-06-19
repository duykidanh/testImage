package com.huynhnguyenanhduy.Model;

public class Product {
    private String maSP;
    private String tenSP;
    private double giaSP;

    public Product(String maSP, String tenSP, double giaSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
    }

    public Product() {
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public double getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(double giaSP) {
        this.giaSP = giaSP;
    }

    @Override
    public String toString() {
        return  "Ma San Pham: " + maSP + '\n' +
                "Ten San Pham: " + tenSP + '\n' +
                "Gia Ban: " + giaSP;
    }
}
