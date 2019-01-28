package com.example.simplesqlitedb.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BaseHelper {
    Context context;
    CreateDB createDB;
    BasePojo basePojo;
    SQLiteDatabase db;

    public BaseHelper(Context context) {
        this.context = context;
        createDB = new CreateDB(context,CreateDB.DB_NAME,null,CreateDB.DB_VERSION);
        db = createDB.getWritableDatabase();
    }

    public void ADDToDB(BasePojo pojo){
        ContentValues contentValues = new ContentValues();

        contentValues.put(CreateDB.NAME,pojo.getName());
        contentValues.put(CreateDB.AGE,pojo.getAge());
        contentValues.put(CreateDB.GENDER,pojo.getGend());
        contentValues.put(CreateDB.DEPT,pojo.getDept());

        try {
            db.insert(CreateDB.TABLE_NAME,null,contentValues);
            System.out.println("Insert Into DataBase");
        } finally {
            if (db!=null && db.isOpen()){
                db.close();
            }

        }

    }

    public List<BasePojo> FetchAll(){
        Cursor cursor = null;
        List<BasePojo> pojoList = new ArrayList<>();

        try {
            cursor = db.query(CreateDB.TABLE_NAME,null,null,null,null,null,null);
            while (cursor.moveToNext()){

                System.out.println("Fetch Inside");

                basePojo = new BasePojo();

                basePojo.setName(cursor.getString(cursor.getColumnIndex(CreateDB.NAME)));
                basePojo.setAge(cursor.getString(cursor.getColumnIndex(CreateDB.AGE)));
                basePojo.setDept(cursor.getString(cursor.getColumnIndex(CreateDB.DEPT)));
                basePojo.setGend(cursor.getString(cursor.getColumnIndex(CreateDB.GENDER)));

                pojoList.add(basePojo);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (db!=null && db.isOpen()){
                db.close();
            }
            if (!cursor.isClosed()){
                cursor.close();
            }
        }

        return pojoList;
    }


    public void deleteAll(){

        try {
            db = createDB.getWritableDatabase();
            db.delete(CreateDB.TABLE_NAME,null,null);
            System.out.println("Delete all");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db!=null && db.isOpen()){
                db.close();
            }
        }
    }

}
