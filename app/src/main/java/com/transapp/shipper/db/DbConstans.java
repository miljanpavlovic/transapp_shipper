package com.transapp.shipper.db;

import android.provider.BaseColumns;

import com.transapp.shipper.db.data.User;

/**
 * Created by Miljan on 6/29/2015.
 */
public class DbConstans {
    private static final String TAG = "DbConstants";


    private static final String INT_TYPE = " INTEGER";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_USER_ENTRIES =
            "CREATE TABLE " + UserEntry.TABLE_LOGIN + "("
                    + UserEntry.KEY_ID + " INTEGER PRIMARY KEY," + UserEntry.KEY_FIRST_NAME + TEXT_TYPE + COMMA_SEP
                    + UserEntry.KEY_LAST_NAME + TEXT_TYPE + COMMA_SEP + UserEntry.KEY_COMPANY_NAME + TEXT_TYPE + COMMA_SEP
                    + UserEntry.KEY_PHONE + TEXT_TYPE + COMMA_SEP +  UserEntry.KEY_EMAIL + " TEXT UNIQUE,"
                    + UserEntry.KEY_UID + TEXT_TYPE + COMMA_SEP + UserEntry.KEY_CREATED_AT + TEXT_TYPE + ")";

    public static final String SQL_SELECT_LOGIN_TABLE = "SELECT * FROM " + UserEntry.TABLE_LOGIN;

    public abstract class UserEntry implements BaseColumns{

        //table name
        public static final String TABLE_LOGIN = "login";

        //table columns
        public static final String KEY_ID = "id";
        public static final String KEY_FIRST_NAME = "first_name";
        public static final String KEY_LAST_NAME = "last_name";
        public static final String KEY_COMPANY_NAME = "company_name";
        public static final String KEY_PHONE = "phone";
        public static final String KEY_EMAIL = "email";
        public static final String KEY_UID = "uid";
        public static final String KEY_CREATED_AT = "created_at";
    }
}
