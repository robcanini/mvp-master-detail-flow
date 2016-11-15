package it.miriade.corsi.android.mvp.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.miriade.corsi.android.mvp.model.DbHelper;
import it.miriade.corsi.android.mvp.model.entity.User;

/**
 * Created by roberto on 15/11/16 for project MVP
 */

public class UserDao {

    private SQLiteDatabase db;

    public UserDao(DbHelper helper) {
        this.db = helper.getWritableDatabase();
    }

    public User getUserByEmail(String email) {
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = \"" + email + "\"", null);
        User user = null;
        try {
            if((cursor != null) && (cursor.getCount() > 0)) {
                cursor.moveToPosition(0);
                user = new User();
                user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                user.setFirstName(cursor.getString(cursor.getColumnIndex("first_name")));
                user.setLastName(cursor.getString(cursor.getColumnIndex("last_name")));
                user.setBirthday(new Date(cursor.getInt(cursor.getColumnIndex("birthday"))));
            }
        } finally {
            try {
                cursor.close();
            } catch (NullPointerException npe) {
                //
            }
        }
        return user;
    }

    public void save(User user) {
        ContentValues cv = new ContentValues();
        cv.put("email", user.getEmail());
        cv.put("password", user.getPassword());
        cv.put("first_name", user.getFirstName());
        cv.put("last_name", user.getLastName());
        cv.put("birthday", user.getBirthday().getTime());
        db.insert("users", null, cv);
    }

    public User getUserByEmailAndPassowrd(String email, String password) {
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = \"" + email + "\" AND password = \"" + password + "\"", null);
        User user = null;
        try {
            if((cursor != null) && (cursor.getCount() > 0)) {
                cursor.moveToPosition(0);
                user = new User();
                user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                user.setFirstName(cursor.getString(cursor.getColumnIndex("first_name")));
                user.setLastName(cursor.getString(cursor.getColumnIndex("last_name")));
                user.setBirthday(new Date(cursor.getLong(cursor.getColumnIndex("birthday"))));
            }
        } finally {
            try {
                cursor.close();
            } catch (NullPointerException npe) {
                //
            }
        }
        return user;
    }

    public List<User> getUsers() {
        Cursor cursor = db.rawQuery("SELECT * FROM users", null);
        List<User> users = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                User x = new User();
                x.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                x.setFirstName(cursor.getString(cursor.getColumnIndex("first_name")));
                x.setLastName(cursor.getString(cursor.getColumnIndex("last_name")));
                x.setBirthday(new Date(cursor.getLong(cursor.getColumnIndex("birthday"))));
                Log.w("UserDao", new Date(cursor.getLong(cursor.getColumnIndex("birthday"))).toString());
                users.add(x);
            }
        } finally {
            try {
                cursor.close();
            } catch (NullPointerException npe) {
                //
            }
        }
        return users;
    }

}
