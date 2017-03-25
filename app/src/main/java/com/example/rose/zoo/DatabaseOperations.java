package com.example.rose.zoo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Rose on 01/09/2017.
 */

public class DatabaseOperations extends SQLiteOpenHelper {
    public static final int database_version = 1;
    public String CREATE_QUERY = "CREATE TABLE" + TableData.TableInfo.TABLE_NAME + "( " + TableData.TableInfo.USER_NAME +  "TEXT, " + TableData.TableInfo.USER_PASS + " TEXT );";

    public DatabaseOperations(Context context) {
        super(context, TableData.TableInfo.DATABASE_NAME, null, database_version);
        Log.d("Database operations: ", "Database created");
    }

    //this onCreate function is used to create a table
    @Override
    public void onCreate(SQLiteDatabase sdb) {
        sdb.execSQL(CREATE_QUERY);
        Log.d("Database operations: ", "Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sdb, int oldVersion, int newVersion) {
        sdb.execSQL("Drop table if exists" + TableData.TableInfo.TABLE_NAME);
    }

    public void putInformation(DatabaseOperations dbop, String name, String pass) {
        SQLiteDatabase SQ = dbop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableData.TableInfo.USER_NAME, name);
        cv.put(TableData.TableInfo.USER_PASS, pass);
        long k = SQ.insert(TableData.TableInfo.TABLE_NAME, null, cv);
        Log.d("Database operations: ","One raw data inserted");
    }
    
    public Cursor getInformation(DatabaseOperations dbop){
        SQLiteDatabase SQ = dbop.getReadableDatabase();
        String[] columns = {TableData.TableInfo.TABLE_NAME, TableData.TableInfo.USER_PASS};
        Cursor CR = SQ.query(TableData.TableInfo.TABLE_NAME, columns, null, null, null, null, null);
        return CR;
    }
}
