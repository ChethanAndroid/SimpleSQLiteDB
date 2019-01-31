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

    public void deleteByName(String name){

        Cursor cursor = null;

        try {
            db = createDB.getWritableDatabase();
            cursor = db.query(CreateDB.TABLE_NAME,null,null,null,null,null,null);
            while (cursor.moveToNext()){
                String where = CreateDB.NAME + " ='" + name + "'";
                db.delete(CreateDB.TABLE_NAME,where,null);
                System.out.println("NameDeleted:"+where);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!cursor.isClosed()){
                cursor.close();
            }
            if (db!=null && db.isOpen()){
                db.close();
            }
        }

    }


    public void update(String name,BasePojo pojo){
        ContentValues contentValues = new ContentValues();
        Cursor cursor = null;



        contentValues.put(CreateDB.NAME,pojo.getName());
        contentValues.put(CreateDB.AGE,pojo.getAge());
        contentValues.put(CreateDB.GENDER,pojo.getGend());
        contentValues.put(CreateDB.DEPT,pojo.getDept());


        try {
            cursor = db.query(CreateDB.TABLE_NAME,null,null,null,null,null,null);
            while (cursor.moveToNext()){
                String where = CreateDB.NAME +"='" + name + "'";
                db.update(CreateDB.TABLE_NAME,contentValues,where,null);
                System.out.println("data Updated");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!cursor.isClosed()){
                cursor.close();
            }
            if (db!=null && db.isOpen()){
                db.close();
            }
        }

    }


    public List<BasePojo> FetchByName(String name){
        String sName = "";
        Cursor cursor = null;
        List<BasePojo> basePojoList = new ArrayList<>();

        try {
            db = createDB.getReadableDatabase();
            String where = CreateDB.NAME + "='"+ name + "'";
            cursor = db.query(CreateDB.TABLE_NAME,null,where,null,null,null,null);
            while (cursor.moveToNext()){

                basePojo = new BasePojo();

                basePojo.setName(cursor.getString(cursor.getColumnIndex(CreateDB.NAME)));
                basePojo.setAge(cursor.getString(cursor.getColumnIndex(CreateDB.AGE)));
                basePojo.setDept(cursor.getString(cursor.getColumnIndex(CreateDB.DEPT)));
                basePojo.setGend(cursor.getString(cursor.getColumnIndex(CreateDB.GENDER)));

                basePojoList.add(basePojo);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (!cursor.isClosed()){
                cursor.close();
            }
            if (db!=null && db.isOpen()){
                db.close();
            }

        }
        return basePojoList;
    }

}
