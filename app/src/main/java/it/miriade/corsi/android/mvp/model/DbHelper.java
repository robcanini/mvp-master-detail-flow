package it.miriade.corsi.android.mvp.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import it.miriade.corsi.android.mvp.AppConst;

/**
 * Created by roberto on 15/11/16 for project MVP
 */

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, AppConst.DB_NAME, null, AppConst.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE users (" +
                        "email TEXT PRIMARY KEY, " +
                        "first_name TEXT NOT NULL," +
                        "last_name TEXT NOT NULL, " +
                        "birthday INTEGER DEFAULT 0," +
                        "password TEXT NOT NULL) "
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }

}
