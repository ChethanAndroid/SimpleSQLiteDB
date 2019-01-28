package com.example.simplesqlitedb.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDB extends SQLiteOpenHelper {

    public static int DB_VERSION = 1;
    public static String DB_NAME = "db1";
    public static String TABLE_NAME = "tb1";

    public static String NAME = "name";
    public static String AGE = "age";
    public static String DEPT = "dept";
    public static String GENDER = "gender";


    public CreateDB( Context context,  String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = " CREATE TABLE " + TABLE_NAME +

                "("+
                NAME + " TEXT, " +
                AGE + " TEXT, " +
                DEPT + " TEXT, " +
                GENDER + " TEXT " +
                ")";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("  DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }
}
