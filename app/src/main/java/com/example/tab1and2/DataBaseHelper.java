package com.example.tab1and2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "todo.db";
    public final static String TABLE_NAME = "tasks";
    public final static String COL_1 = "ID";
    public final static String COL_3 = "Task";

    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCommand = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,TASK TEXT)";
        db.execSQL(sqlCommand);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlCommand = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        db.execSQL(sqlCommand);
        onCreate(db);
    }
    public boolean insertData(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_3,task);
        long result = db.insert(TABLE_NAME,null,cv);
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor getData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME +"WHERE ID = '"+id+"'";
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }
    public boolean updateData(String id,String task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_3,task);
        db.update(TABLE_NAME,contentValues,"ID=?",new String[]{id});
        return true;
    }
    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?",new String[]{id});
    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_NAME,null);
        return res;
    }
    public Cursor getItemId(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+COL_1+" FROM "+TABLE_NAME+"WHERE "+ COL_3 +"='"+task+"'";
        Cursor id = db.rawQuery(query,null);
        return id;
    }
}
