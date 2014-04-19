package com.lukekuza.zangleapp.smartdatabase;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: theshadow
 * Date: 4/22/13
 * Time: 6:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainDatabase extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "Zangle.sqlite";

    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE =
            "create table login (username text not null,password text not null); " +
                    "create table user_data (name text,school text,grade text); " +
                    "create table classes (class_id integer not null,class text not null,percent text,grade text,teacher text,period text); " +
                    "create table assignments (assignment_id integer primary key autoincrement,class_id integer not null,assignment_name text not null,due_date text,percent text,details text,points text,score text,extra_credit text,graded text);";

    public MainDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            String transaction[] = DATABASE_CREATE.split(";");

            for (String statement : transaction) {
                sqLiteDatabase.execSQL(statement.trim().concat(";"));
            }

        } catch (SQLException ex) {
            Log.e("Zangle", Log.getStackTraceString(ex));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
