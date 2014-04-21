package com.lukekuza.zangleapp.zangle;

import android.os.AsyncTask;
import android.util.Log;
import com.lukekuza.mistar.ZangleConnection;
import com.lukekuza.mistar.assignment.ZAssignment;
import com.lukekuza.mistar.classes.ZClass;
import com.lukekuza.zangleapp.smartdatabase.SmartAssignmentUtil;
import com.lukekuza.zangleapp.smartdatabase.SmartClassUtil;
import com.lukekuza.zangleapp.smartdatabase.SmartUserUtil;
import com.lukekuza.zangleapp.util.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: theshadow
 * Date: 4/22/13
 * Time: 9:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class ZangleTask extends AsyncTask<String, Void, String> {

    String command;
    private OnZangleTaskCompleted listener;

    private ZangleTask() {

    }

    public ZangleTask(OnZangleTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        command = strings[0];

        if (command.equals("login")) {
            try {
                boolean connected = ZangleUtil.getInstance().getZangleConnection().connect(Constants.ZANGLE_URL, strings[1], strings[2]);
                return Boolean.toString(connected);
            } catch (Exception e) {
                Log.e("Zangle", Log.getStackTraceString(e));
                return Boolean.toString(false);
            }
        } else if (command.equals("save")) {
            ZangleConnection connection = ZangleUtil.getInstance().getZangleConnection();
            SmartUserUtil smartUserUtil = SmartUserUtil.getInstance(ZangleUtil.getInstance().getContext());

            smartUserUtil.saveUserData(connection.student().getName(), connection.student().getSchool(), connection.student().getGrade());

            SmartClassUtil classUtil = SmartClassUtil.getInstance(ZangleUtil.getInstance().getContext());
            SmartAssignmentUtil assignmentUtil = SmartAssignmentUtil.getInstance(ZangleUtil.getInstance().getContext());
            classUtil.clearClassData();
            assignmentUtil.clearAssignmentData();

            ZClass classes[] = connection.student().getClasses().getClasses();
            int id = 0;
            for (ZClass zClass : classes) {
                Log.d("Zangle", Integer.toString(id));
                classUtil.saveClass(id, zClass.getName(), Double.toString(zClass.getPercent()), zClass.getGrade(), zClass.getTeacher(), zClass.getPeriod());
                int assignmentLength = zClass.getAssignments().getSize();
                for (int i = 0; i < assignmentLength; i++) {
                    try {
                        ZAssignment assignment = zClass.getAssignments().getAssignment(i);
                        assignmentUtil.saveAssignment(id, assignment.getAssignmentName(), assignment.getDueDate(), Double.toString(assignment.getPercent()), assignment.getDetails(),
                                assignment.getPoints(), assignment.getScore(), Boolean.toString(assignment.isExtraCredit()), Boolean.toString(!assignment.isNotGraded()));
                    } catch (Exception e) {
                        Log.e("Zangle", Log.getStackTraceString(e));
                    }
                }
                id++;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        listener.zangleTaskCompleted(command, result);
    }

    public interface OnZangleTaskCompleted {
        public void zangleTaskCompleted(String command, String result);
    }

}
