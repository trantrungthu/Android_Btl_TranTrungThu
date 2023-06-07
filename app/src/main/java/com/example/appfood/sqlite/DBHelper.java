package com.example.appfood.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static String dbName = "food.sqlite";
    public static final String TB_FOOD="food";
    public static final String TB_USER="user";
    private static int version = 1;
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, version);
    }
    public DBHelper(@Nullable Context context) {
        super(context, dbName, null, version);
    }

    public void execSQL( String sql){
        getWritableDatabase().execSQL(sql);
    }

    public Cursor selecteSQL(String sql){
        return getReadableDatabase().rawQuery(sql, null);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Tạo bảng Food
        String sqlFoodTable = "create table if not exists food(" +
                "foodid integer primary key autoincrement," +
                "categoryid integer,foodname text not null," +
                "anh Blog not null, video text not null," +
                "address text not null," +
                "tacgia text not null,congthuc text not null, " +
                "motadai text not null)";
        sqLiteDatabase.execSQL(sqlFoodTable);
        //Tạo bảng User
        String sqlUserTable = "create table if not exists user(" +
                "accountID integer primary key autoincrement,fullName text not null," +
                " email text not null," +
                "password text not null," +
                "phone text not null,address text not null)";
        sqLiteDatabase.execSQL(sqlUserTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void ExcuteSQL(String sql){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.execSQL(sql);
    }
    public Cursor SelectData(String sql){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(sql,null);
        return c;
    }
    public long insert(String table, String nullCollumnHack, ContentValues values){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.insert(table,nullCollumnHack,values);
    }
    public long update(String table, ContentValues values, String whereClause, String[] whereArgs){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.update(table,values,whereClause,whereArgs);
    }
    public long delete(String table,String whereClause, String[] whereArgs){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.delete(table,whereClause,whereArgs);
    }
    public Boolean checkUserName(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cs = db.rawQuery("select * from user where email=?", new String[]{email});
        if (cs.getCount()>0)
            return true;
        else
            return false;
    }
    public Boolean checkUsernamePassword(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cs = db.rawQuery("select * from user where email=? and password=?", new String[]{email,password});
        if (cs.getCount()>0)
            return true;
        else
            return false;
    }
}
