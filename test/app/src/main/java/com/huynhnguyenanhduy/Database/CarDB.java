package com.huynhnguyenanhduy.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class CarDB extends SQLiteOpenHelper {
    public static final String DB_NAME = "cars.sqlite";
    public static final int DB_VERSION = 1;
    public static final String TBL_NAME = "Car";
    public static final String COL_CODE = "MaDon";
    public static final String COL_NAME = "TenHangXe";
    public static final String COL_PRICE = "GiaChoThue";
    public static final String COL_AREA = "DiaDiemChoThue";
    public static final String COL_TYPE = "LoaiXe";
    public static final String COL_IMAGE = "HinhAnh";

    public CarDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TBL_NAME + "("
                + COL_CODE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " VARCHAR(50), "
                + COL_PRICE + " REAL, "
                + COL_AREA + " VARCHAR(150), "
                + COL_TYPE + " VARCHAR(50), "
                + COL_IMAGE + " BLOB)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_NAME);
        onCreate(db);
    }

    //INSERT
    public Cursor getData(String sql) {
        Cursor cursor = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            cursor = db.rawQuery(sql, null);
        }
        catch (Exception e) {

        }
        return cursor;
    }

    //UPDATE, DELETE
    public boolean execSql(String sql) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(sql);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    //INSERT
    public  boolean insertData(String name, double price, String area, String type, byte[] photo) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            String sql = "INSERT INTO " + TBL_NAME + " VALUES(null, ?, ?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1, name);
            statement.bindDouble(2, price);
            statement.bindString(3, area);
            statement.bindString(4, type);
            statement.bindBlob(5, photo);

            statement.executeInsert();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    public  boolean updateImage(byte[] photo, int code) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            String sql = "UPDATE " + TBL_NAME + " SET " + COL_IMAGE + " = " + "? WHERE " + COL_CODE + " = " + code;
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindBlob(1, photo);

            statement.executeInsert();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

}
