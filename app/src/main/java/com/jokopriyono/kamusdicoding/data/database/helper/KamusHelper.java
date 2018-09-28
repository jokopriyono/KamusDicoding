package com.jokopriyono.kamusdicoding.data.database.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jokopriyono.kamusdicoding.data.database.model.KamusModel;

import java.util.ArrayList;
import java.util.List;

public class KamusHelper {
    private static String ENGLISH = DBHelper.TABLE_ENG;
    private static String INDONESIA = DBHelper.TABLE_IDN;

    private Context context;
    private SQLiteDatabase db;

    public KamusHelper(Context context) {
        this.context = context;
    }

    public KamusHelper open() throws SQLException {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public void insertKataKata(List<KamusModel> kamusModels, boolean bahasa) {
        String pilihTable = bahasa ? ENGLISH : INDONESIA;
        String sql = "INSERT INTO " + pilihTable + " (" +
                DBHelper.COL_WORD + ", " +
                DBHelper.COL_TRANSLATE + ") VALUES (?, ?)";

        db.beginTransaction();

        SQLiteStatement stmt = db.compileStatement(sql);
        for (int i = 0; i < kamusModels.size(); i++) {
            stmt.bindString(1, kamusModels.get(i).getWord());
            stmt.bindString(2, kamusModels.get(i).getTranslate());
            stmt.execute();
            stmt.clearBindings();
        }

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public List<KamusModel> cariKata(@NonNull String query, boolean bahasa) {
        KamusModel kamusModel;
        List<KamusModel> kumpulanData = new ArrayList<>();

        String pilihTable = bahasa ? ENGLISH : INDONESIA;
        Cursor cursor = db.rawQuery("SELECT * FROM " + pilihTable +
                " WHERE " + DBHelper.COL_WORD + " LIKE '" + query + "%'", null);

        cursor.moveToFirst();
        Log.d("pesan", "jum "+cursor.getCount());
        for (int i=0; i<cursor.getCount();i++){
            kamusModel = new KamusModel();
            kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_ID)));
            kamusModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_WORD)));
            kamusModel.setTranslate(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_TRANSLATE)));

            kumpulanData.add(kamusModel);
            cursor.moveToNext();
        }

        cursor.close();
        return kumpulanData;
    }
}
