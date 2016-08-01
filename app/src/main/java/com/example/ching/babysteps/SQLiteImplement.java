
//SQLiteImplement.java
package com.example.ching.babysteps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Bevis on 2016/7/29.
 */
public class SQLiteImplement extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Record.db";
    public static final String TABLE_NAME = "record_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "EVENT";
    public static final String COL_3 = "DATE";
    public static final String COL_4 = "TIME";
    public static final String COL_5 = "MARK";

    public SQLiteImplement(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, EVENT TEXT, DATE TEXT, TIME TEXT, MARK TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Data insertData(Data data) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, data.getEvent());
        contentValues.put(COL_3, data.getDate());
        contentValues.put(COL_4, data.getTime());
        contentValues.put(COL_5, data.getMark());
        long id = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        data.setId(id);
        return data;
    }

    public boolean updateData(Data data) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, data.getId());
        contentValues.put(COL_2, data.getEvent());
        contentValues.put(COL_3, data.getDate());
        contentValues.put(COL_4, data.getTime());
        contentValues.put(COL_5, data.getMark());
        String where = COL_1 + "=" + data.getId();
        return sqLiteDatabase.update(TABLE_NAME, contentValues, where, null) > 0;
    }

    public boolean deleteData(long id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String where = COL_1 + "=" + id;
        return sqLiteDatabase.delete(TABLE_NAME, where, null) > 0;
    }

    public Data getData(long id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Data result = new Data(null, null, null, null);
        String where = COL_1 + "=" + id;
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null, where, null, null, null, null);

        if (cursor.moveToFirst()) {
            result.setId(cursor.getLong(0));
            result.setEvent(cursor.getString(1));
            result.setDate(cursor.getString(2));
            result.setTime(cursor.getString(3));
            result.setMark(cursor.getString(4));
            Log.d("Get Event", cursor.getLong(0) + cursor.getString(1) + cursor.getString(2) + cursor.getString(3) + cursor.getString(4));
        }

        cursor.close();
        return result;
    }

    public int getCount() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int result = 0;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        if (cursor.moveToNext())
            result = cursor.getInt(0);
        return result;
    }

    public void closeDatabase() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.close();
    }
}


/**
 * Created by Bevis on 2016/7/29.
 */
