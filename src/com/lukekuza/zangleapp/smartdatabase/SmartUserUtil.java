package com.lukekuza.zangleapp.smartdatabase;

import android.content.ContentValues;
import android.content.Context;

/**
 * Created with IntelliJ IDEA.
 * User: theshadow
 * Date: 4/22/13
 * Time: 8:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmartUserUtil {
    private static SmartUserUtil instance;
    private Context context;

    private SmartUserUtil() {
    }

    public static SmartUserUtil getInstance(Context context) {
        if (instance == null) {
            instance = new SmartUserUtil();
        }
        instance.setContext(context);
        return instance;
    }

    public boolean loginDataExists() {
        return GlobalDatabase.getInstance(getContext()).getLength("login") > 0;
    }

    public void saveUserData(String name, String school, String grade) {
        clearUserData();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("school", school);
        values.put("grade", grade);

        GlobalDatabase.getInstance(getContext()).insertData("user_data", values);
    }

    public String getUserData(String data) {
        return GlobalDatabase.getInstance(getContext()).getStringArrayForColumn("user_data", data, null, false)[0];
    }

    public void clearUserData() {
        GlobalDatabase.getInstance(getContext()).clearTable("user_data");
    }

    private Context getContext() {
        return context;
    }

    private void setContext(Context context) {
        this.context = context;
    }
}
