package com.huynhnguyenanhduy.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class ProductDB extends SQLiteOpenHelper {

    public static final String DB_NAME = "products.sqlite";
    public static final int DB_VERSION = 1;
    public static final String TB_NAME = "Product";
    public static final String COL_MASP = "MaSanPham";
    public static final String COL_TENSP = "TenSanPham";
    public static final String COL_GIASP = "GiaBan";
    public ProductDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TB_NAME + " ("
                + COL_MASP + " NVARCHAR(50), "
                + COL_TENSP + " NVARCHAR(50), "
                + COL_GIASP + " REAL )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(db);
    }

    //Get Data
    public Cursor GetData(String sql) {
        Cursor cursor = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            cursor = db.rawQuery(sql, null);
        }catch (Exception e) {

        }
        return cursor;
    }

    //update, delete
    public boolean execSQL(String sql) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(sql);

            return true;
        }catch (Exception e) {
            return false;
        }
    }

    //insert
    public boolean insertData(String maSP, String tenSP, double giaSP) {
        String sql = "INSERT INTO " + TB_NAME + " VALUES(?, ?, ?)";
        try{
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(sql);

            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1, maSP);
            statement.bindString(2, tenSP);
            statement.bindDouble(3, giaSP);

            statement.executeInsert();

            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
