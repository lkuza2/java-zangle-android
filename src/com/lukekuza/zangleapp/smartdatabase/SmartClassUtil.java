package com.lukekuza.zangleapp.smartdatabase;

import android.content.ContentValues;
import android.content.Context;

/**
 * Created with IntelliJ IDEA.
 * User: theshadow
 * Date: 4/28/13
 * Time: 1:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmartClassUtil {

    private static SmartClassUtil instance;
    private Context context;

    private SmartClassUtil() {
    }

    public static SmartClassUtil getInstance(Context context) {
        if (instance == null) {
            instance = new SmartClassUtil();
        }
        instance.setContext(context);
        return instance;
    }

    public void saveClass(int classId, String className, String percent, String grade, String teacher, String period) {
        ContentValues values = new ContentValues();
        values.put("class_id", classId);
        values.put("class", className);
        values.put("percent", percent);
        values.put("grade", grade);
        values.put("teacher", teacher);
        values.put("period", period);

        GlobalDatabase.getInstance(getContext()).insertData("classes", values);
    }

    public String getDataForClass(int classId, String data) {
        return GlobalDatabase.getInstance(getContext()).getStringForSearch("classes", data, "class_id", Integer.toString(classId));
    }

    public int getNumberOfClasses() {
        return GlobalDatabase.getInstance(getContext()).getLength("classes");
    }

    public String[] getClassesOrderedById() {
        return GlobalDatabase.getInstance(getContext()).getStringArrayForColumn("classes", "class", "class_id", true);
    }

    public void clearClassData() {
        GlobalDatabase.getInstance(getContext()).clearTable("classes");
    }

    private Context getContext() {
        return context;
    }

    private void setContext(Context context) {
        this.context = context;
    }

}
