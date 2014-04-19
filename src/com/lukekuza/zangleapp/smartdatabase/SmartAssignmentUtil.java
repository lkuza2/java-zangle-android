package com.lukekuza.zangleapp.smartdatabase;

import android.content.ContentValues;
import android.content.Context;

/**
 * Created with IntelliJ IDEA.
 * User: theshadow
 * Date: 4/28/13
 * Time: 2:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmartAssignmentUtil {

    private static SmartAssignmentUtil instance;
    private Context context;

    private SmartAssignmentUtil() {
    }

    public static SmartAssignmentUtil getInstance(Context context) {
        if (instance == null) {
            instance = new SmartAssignmentUtil();
        }
        instance.setContext(context);
        return instance;
    }

    public void saveAssignment(int classId, String assignmentName, String dueDate, String percent, String details, String points, String score, String extraCredit, String graded) {
        ContentValues values = new ContentValues();
        values.put("class_id", classId);
        values.put("assignment_name", assignmentName);
        values.put("due_date", dueDate);
        values.put("percent", percent);
        values.put("details", details);
        values.put("points", points);
        values.put("score", score);
        values.put("extra_credit", extraCredit);
        values.put("graded", graded);

        GlobalDatabase.getInstance(getContext()).insertData("assignments", values);
    }

    public int getAssignmentLengthForClass(int classId) {
        return GlobalDatabase.getInstance(getContext()).getLength("assignments", "class_id=" + Integer.toString(classId));
    }

    public String getAssignmentData(int classId, int assignmentIndex, String data) {
        return GlobalDatabase.getInstance(getContext()).getStringArrayForColumn("assignments", data, "class_id", Integer.toString(classId), "assignment_id", true)[assignmentIndex];
    }

    public void clearAssignmentData() {
        GlobalDatabase.getInstance(getContext()).clearTable("assignments");
    }

    private Context getContext() {
        return context;
    }

    private void setContext(Context context) {
        this.context = context;
    }

}
