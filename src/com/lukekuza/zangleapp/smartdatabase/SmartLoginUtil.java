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
public class SmartLoginUtil {
    private static SmartLoginUtil instance;
    private Context context;

    private SmartLoginUtil() {
    }

    public static SmartLoginUtil getInstance(Context context) {
        if (instance == null) {
            instance = new SmartLoginUtil();
        }
        instance.setContext(context);
        return instance;
    }

    public boolean loginDataExists() {
        return GlobalDatabase.getInstance(getContext()).getLength("login") > 0;
    }

    public void saveLogin(String username, String password) {
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        GlobalDatabase.getInstance(getContext()).insertData("login", values);
    }

    public String getUsername() {
        return GlobalDatabase.getInstance(getContext()).getStringForSearchCustom("login", "username", null, null);
    }

    public String getPassword() {
        return GlobalDatabase.getInstance(getContext()).getStringForSearchCustom("login", "password", null, null);
    }

    public void clearLogin() {
        GlobalDatabase.getInstance(getContext()).clearTable("login");
    }

    private Context getContext() {
        return context;
    }

    private void setContext(Context context) {
        this.context = context;
    }
}
