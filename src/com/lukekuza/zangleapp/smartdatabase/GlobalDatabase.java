package com.lukekuza.zangleapp.smartdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: theshadow
 * Date: 4/22/13
 * Time: 7:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class GlobalDatabase {

    private static GlobalDatabase instance;
    private SQLiteDatabase database;

    private GlobalDatabase() {

    }

    public synchronized static GlobalDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new GlobalDatabase();

            try {
                instance.setDatabase((new MainDatabase(context)).getWritableDatabase());
            } catch (SQLException ex) {
                Log.e("Zangle", Log.getStackTraceString(ex));
            }
        }
        return instance;
    }

    protected SQLiteDatabase getDatabase() {
        return database;
    }

    protected void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    protected int getLength(String table) {
        return getLength(table, null);
    }

    public void deleteDatabase() {

    }

    /**
     * Get length with where filter
     *
     * @param table Table
     * @param where SQL WHERE statement excluding the WHERE
     * @return
     */
    protected int getLength(String table, String where) {
        String whereState = "";
        if (where != null) {
            whereState = " WHERE " + where;
        }
        String sql = "SELECT COUNT(*) FROM " + table + whereState;
        SQLiteStatement statement = database.compileStatement(sql);

        long count = statement.simpleQueryForLong();
        statement.close();
        return (int) count;
    }


    protected String[] getStringArrayForColumn(String table, String column, String orderBy, boolean sort) {
        return getStringArrayForColumn(table, column, null, null, orderBy, sort);
    }

    protected String[] getStringArrayForColumn(String table, String column, String filterColumn, String filter, String orderByColumn, boolean sort) {
        String filterArray[] = null;
        String columnFilter = null;

        if (filter != null) {
            columnFilter = filterColumn + "=?";
            filterArray = new String[]{filter};
        }

        String order = null;
        if (sort) {
            if (orderByColumn == null) {
                order = column;
            } else {
                order = orderByColumn;
            }

        }

        Cursor cursor = getDatabase().query(table, new String[]{column}, columnFilter, filterArray, null, null, order);

        String[] data = new String[getLength(table)];
        cursor.moveToFirst();
        int i = 0;
        while (!cursor.isAfterLast()) {
            data[i] = (cursor.getString(cursor.getColumnIndex(column)));
            cursor.moveToNext();
            i++;
        }
        cursor.close();
        return data;
    }

    protected int[] getIntArrayForColumn(String table, String column, String orderBy, boolean sort) {
        return getIntArrayForColumn(table, column, null, null, orderBy, sort);
    }

    protected int[] getIntArrayForColumn(String table, String column, String filterColumn, String filter, String orderByColumn, boolean sort) {
        String filterArray[] = null;
        String columnFilter = null;

        if (filter != null) {
            columnFilter = filterColumn + "=?";
            filterArray = new String[]{filter};
        }

        String order = null;
        if (sort) {
            if (orderByColumn == null) {
                order = column;
            } else {
                order = orderByColumn;
            }

        }

        Cursor cursor = getDatabase().query(table, new String[]{column}, columnFilter, filterArray, null, null, order);

        int[] data = new int[getLength(table)];
        cursor.moveToFirst();
        int i = 0;
        while (!cursor.isAfterLast()) {
            data[i] = (cursor.getInt(cursor.getColumnIndex(column)));
            cursor.moveToNext();
            i++;
        }
        cursor.close();
        return data;
    }

    protected int getIntForSearch(String table, String column, String whereColumn, String where) {
        Cursor cursor = getDatabase().query(table, new String[]{column}, whereColumn + "=?", new String[]{where}, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            return -1;
        } else {
            int data = cursor.getInt(cursor.getColumnIndex(column));
            cursor.close();
            return data;
        }
    }

    protected int getIntForSearch(String table, String column, String where, String[] whereArgs) {
        Cursor cursor = getDatabase().query(table, new String[]{column}, where, whereArgs, null, null, null);
        cursor.moveToFirst();
        int data = cursor.getInt(cursor.getColumnIndex(column));
        cursor.close();
        return data;
    }

    protected String getStringForSearch(String table, String column, String whereColumn, String where) {
        Cursor cursor = getDatabase().query(table, new String[]{column}, whereColumn + "=?", new String[]{where}, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            cursor.close();
            return null;
        } else {
            String data = cursor.getString(cursor.getColumnIndex(column));
            cursor.close();
            return data;
        }
    }

    protected String getStringForSearchCustom(String table, String column, String where, String[] whereArgs) {
        Cursor cursor = getDatabase().query(table, new String[]{column}, where, whereArgs, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            cursor.close();
            return null;
        } else {
            String data = cursor.getString(cursor.getColumnIndex(column));
            cursor.close();
            return data;
        }
    }

    protected String[] convertIntArrayToString(int[] array) {
        int length = array.length;

        String[] newArray = new String[length];

        for (int i = 0; i < length; i++) {
            newArray[i] = Integer.toString(array[i]);
        }
        return newArray;
    }

    protected void insertData(String table, ContentValues values) {
        getDatabase().beginTransaction();
        try {
            getDatabase().insert(table, null, values);
            getDatabase().setTransactionSuccessful();
        } finally {
            getDatabase().endTransaction();
        }
    }

    protected void clearTable(String table) {
        getDatabase().beginTransaction();
        try {
            getDatabase().delete(table, null, null);
            getDatabase().setTransactionSuccessful();
        } finally {
            getDatabase().endTransaction();
        }
    }

}
