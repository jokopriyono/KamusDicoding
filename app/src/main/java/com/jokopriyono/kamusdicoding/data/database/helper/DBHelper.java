package com.jokopriyono.kamusdicoding.data.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
    private static String DB_NAME = "kamus.db";
    public static String TABLE_ENG = "english";
    public static String TABLE_IDN = "indonesia";
    public static String COL_ID = "id";
    public static String COL_WORD = "word";
    public static String COL_TRANSLATE = "translate";

    private static String CREATE_TABLE_ENGLISH =
            "create table " + TABLE_ENG + " (" +
            COL_ID + " integer primary key autoincrement, " +
            COL_WORD + " text not null, " +
            COL_TRANSLATE + " text not null);";

    private static String CREATE_TABLE_INDONESIA =
            "create table " + TABLE_IDN + " (" +
            COL_ID + " integer primary key autoincrement, " +
            COL_WORD + " text not null, " +
            COL_TRANSLATE + " text not null);";

    private static final int DB_VERSION = 1;


    DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_ENGLISH);
        sqLiteDatabase.execSQL(CREATE_TABLE_INDONESIA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IDN);
        onCreate(db);
    }
}
