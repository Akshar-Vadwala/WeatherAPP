package com.firstapp.weatherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME = "users";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "PASSWORD";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "PHONE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USERNAME TEXT, PASSWORD TEXT, EMAIL TEXT, PHONE TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String username, String password, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, password);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, phone);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_2 + "=? AND " + COL_3 + "=?", new String[]{username, password});
        boolean userExists = cursor.getCount() > 0; // Returns true if user exists
        cursor.close();
        return userExists;
    }

    public Cursor getUserData(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_2 + "=?", new String[]{username});
    }

    public boolean updateUserData(String username, String password, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3, password);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, phone);
        int result = db.update(TABLE_NAME, contentValues, COL_2 + " = ?", new String[]{username});
        return result > 0;
    }


}
