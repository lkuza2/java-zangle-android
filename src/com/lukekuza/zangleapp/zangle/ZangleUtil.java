package com.lukekuza.zangleapp.zangle;

import android.content.Context;
import com.darkscripting.zangle.ZangleConnection;

/**
 * Created with IntelliJ IDEA.
 * User: theshadow
 * Date: 4/22/13
 * Time: 9:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class ZangleUtil {
    private static ZangleUtil instance;
    private ZangleConnection zangleConnection;
    private Context context;

    private ZangleUtil() {
    }

    public static ZangleUtil getInstance() {
        if (instance == null) {
            instance = new ZangleUtil();
        }
        return instance;
    }

    public void login(String username, String password, ZangleTask.OnZangleTaskCompleted listener) {
        setZangleConnection(new ZangleConnection());
        new ZangleTask(listener).execute("login", username, password);
    }

    public void saveUserData(Context context, ZangleTask.OnZangleTaskCompleted listener) {
        setContext(context);
        new ZangleTask(listener).execute("save");
    }

    public ZangleConnection getZangleConnection() {
        return zangleConnection;
    }

    public void setZangleConnection(ZangleConnection zangleConnection) {
        this.zangleConnection = zangleConnection;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
