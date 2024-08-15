package com.example.apkdatamhs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "User.db";
    public static final String TABLE_NAME = "user_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "FULLNAME";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "PASSWORD";

    private static final String TABLE_NAME_2 = "mahasiswa_table";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NIM = "NIM";
    private static final String COLUMN_NAME = "NAMA";
    private static final String COLUMN_DOB = "DOB";
    private static final String COLUMN_GENDER = "GENDER";
    private static final String COLUMN_ADDRESS = "ADDRESS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, FULLNAME TEXT, EMAIL TEXT, PASSWORD TEXT)");
        db.execSQL("create table " + TABLE_NAME_2 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NIM TEXT, NAMA TEXT, DOB TEXT, GENDER TEXT, ADDRESS TEXT)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        onCreate(db);
    }

    public boolean insert(String username, String fullname, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, fullname);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, password);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL = ?", new String[]{email});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL = ? AND PASSWORD = ?", new String[]{email, password});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    public long addData(InfoMahasiswa data) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT 1 FROM " + TABLE_NAME_2 + " WHERE " + COLUMN_NIM + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{data.getNIM()});
        boolean exists = cursor.getCount() > 0;
        cursor.close();

        if (exists) {
            db.close();
            return -1;
        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NIM, data.getNIM());
            values.put(COLUMN_NAME, data.getNAMA());
            values.put(COLUMN_DOB, data.getDOB());
            values.put(COLUMN_GENDER, data.getGENDER());
            values.put(COLUMN_ADDRESS, data.getADDRESS());
            long id = db.insert(TABLE_NAME_2, null, values);
            db.close();
            return id;
        }
    }

    public InfoMahasiswa getData(long ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_2,
                new String[]{COLUMN_ID, COLUMN_NIM, COLUMN_NAME, COLUMN_DOB, COLUMN_GENDER, COLUMN_ADDRESS},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(ID)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        InfoMahasiswa infoMahasiswa = new InfoMahasiswa(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIM)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOB)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)));
        cursor.close();
        return infoMahasiswa;
    }

    public List<InfoMahasiswa> getAllData() {
        List<InfoMahasiswa> data = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME_2 + " ORDER BY " +
                COLUMN_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                InfoMahasiswa infoMahasiswa = new InfoMahasiswa();
                infoMahasiswa.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                infoMahasiswa.setNIM(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIM)));
                infoMahasiswa.setNAMA(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                infoMahasiswa.setDOB(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOB)));
                infoMahasiswa.setGENDER(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER)));
                infoMahasiswa.setADDRESS(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)));
                data.add(infoMahasiswa);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }

    public List<InfoMahasiswa> searchData(String keyword) {
        List<InfoMahasiswa> data = new ArrayList<>();
        String searchQuery = "SELECT * FROM " + TABLE_NAME_2 + " WHERE " +
                COLUMN_NAME + " LIKE ? OR " + COLUMN_NIM + " LIKE ? ORDER BY " +
                COLUMN_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(searchQuery, new String[]{"%" + keyword
                + "%", "%" + keyword + "%"});
        if (cursor.moveToFirst()) {
            do {
                InfoMahasiswa infoMahasiswa = new InfoMahasiswa();
                infoMahasiswa.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                infoMahasiswa.setNIM(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIM)));
                infoMahasiswa.setNAMA(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                infoMahasiswa.setDOB(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOB)));
                infoMahasiswa.setGENDER(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER)));
                infoMahasiswa.setADDRESS(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)));
                data.add(infoMahasiswa);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }

    public void deleteData(InfoMahasiswa data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_2, COLUMN_ID + " = ?",
                new String[]{String.valueOf(data.getId())});
        db.close();
    }

    public int updateNote(InfoMahasiswa data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NIM, data.getNIM());
        values.put(COLUMN_NAME, data.getNAMA());
        values.put(COLUMN_DOB, data.getDOB());
        values.put(COLUMN_GENDER, data.getGENDER());
        values.put(COLUMN_ADDRESS, data.getADDRESS());
        return db.update(TABLE_NAME_2, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(data.getId())});
    }

}