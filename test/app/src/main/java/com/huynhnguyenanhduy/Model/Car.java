package com.huynhnguyenanhduy.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;

public class Car implements Serializable {
    private int maDon;
    private String tenHangXe;
    private double giaThue;
    private String diaDiem;
    private String loaiXe;
    private byte[] image;

    public Car(int maDon, String tenHangXe, double giaThue, String diaDiem, String loaiXe, byte[] image) {
        this.maDon = maDon;
        this.tenHangXe = tenHangXe;
        this.giaThue = giaThue;
        this.diaDiem = diaDiem;
        this.loaiXe = loaiXe;
        this.image = image;
    }

    public Car() {
    }

    public int getMaDon() {
        return maDon;
    }

    public void setMaDon(int maDon) {
        this.maDon = maDon;
    }

    public String getTenHangXe() {
        return tenHangXe;
    }

    public void setTenHangXe(String tenHangXe) {
        this.tenHangXe = tenHangXe;
    }

    public double getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(double giaThue) {
        this.giaThue = giaThue;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }

    public String getLoaiXe() {
        return loaiXe;
    }

    public void setLoaiXe(String loaiXe) {
        this.loaiXe = loaiXe;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    public Bitmap getImageBitmap(){
        byte[] data = image;
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        return bitmap;
    }
}
