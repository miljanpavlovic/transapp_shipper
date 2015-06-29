package com.transapp.shipper.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.transapp.shipper.db.data.User;

/**
 * Created by Miljan on 6/25/2015.
 */
public class FeedReaderDbHelper extends SQLiteOpenHelper {

    private static final String TAG = "FeedReaderDbHeleper";

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "android_api";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbConstans.SQL_CREATE_USER_ENTRIES);
        Log.d(TAG, "login table created.");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop old login table
        db.execSQL("DROP TABLE IF EXISTS " + DbConstans.UserEntry.TABLE_LOGIN);
        // create a new one
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbConstans.UserEntry.KEY_ID, user.getId());
        values.put(DbConstans.UserEntry.KEY_FIRST_NAME, user.getFirstName());
        values.put(DbConstans.UserEntry.KEY_LAST_NAME, user.getLastName());
        values.put(DbConstans.UserEntry.KEY_COMPANY_NAME, user.getCompanyName());
        values.put(DbConstans.UserEntry.KEY_PHONE, user.getPhone());
        values.put(DbConstans.UserEntry.KEY_EMAIL, user.getEmail());
        values.put(DbConstans.UserEntry.KEY_UID, user.getUid());
        values.put(DbConstans.UserEntry.KEY_CREATED_AT, user.getCreatedAt());

        long id = db.insert(DbConstans.UserEntry.TABLE_LOGIN, null, values);
        db.close();

        Log.d(TAG, "new user inserted in db: " + id);
    }

    public User getUser(){
        User user = new User();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(DbConstans.SQL_SELECT_LOGIN_TABLE, null);

        cursor.moveToFirst();
        if(cursor.getCount()>0){
            user.setId(cursor.getInt(0));
            user.setFirstName(cursor.getString(1));
            user.setLastName(cursor.getString(2));
            user.setCompanyName(cursor.getString(3));
            user.setPhone(cursor.getString(4));
            user.setEmail(cursor.getString(5));
            user.setUid(cursor.getString(6));
            user.setCreatedAt(cursor.getString(7));
        }

        cursor.close();
        db.close();
        Log.d(TAG, "Selecting user from db: " + user.toString());
        return user;
    }

    public int getRowCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(DbConstans.SQL_SELECT_LOGIN_TABLE, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        return rowCount;
    }

    public void deleteUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbConstans.UserEntry.TABLE_LOGIN, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info");
    }
}
