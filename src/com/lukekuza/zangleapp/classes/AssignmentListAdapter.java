package com.lukekuza.zangleapp.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.lukekuza.zangleapp.R;
import com.lukekuza.zangleapp.smartdatabase.SmartAssignmentUtil;

/**
 * Created with IntelliJ IDEA.
 * User: theshadow
 * Date: 4/28/13
 * Time: 4:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class AssignmentListAdapter extends BaseAdapter {


    private Context context;
    private int classId;

    public AssignmentListAdapter(Context context, int classId) {
        this.context = context;
        this.classId = classId;
    }

    @Override
    public int getCount() {
        return SmartAssignmentUtil.getInstance(context).getAssignmentLengthForClass(classId);
    }

    @Override
    public Object getItem(int i) {
        return SmartAssignmentUtil.getInstance(context).getAssignmentData(classId, i, "assignment_name");
    }

    @Override
    public long getItemId(int i) {
        return i;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SmartAssignmentUtil assignmentUtil = SmartAssignmentUtil.getInstance(context);
        String assignmentName = assignmentUtil.getAssignmentData(classId, i, "assignment_name");

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.assignment, null);
            createAssignmentView(view, assignmentUtil, i);
            return view;
        } else if (!assignmentName.equals(((TextView) view.findViewById(R.id.assignment_name)).getText())) {
            createAssignmentView(view, assignmentUtil, i);
            return view;
        } else {
            return view;
        }
    }

    private void createAssignmentView(View view, SmartAssignmentUtil assignmentUtil, int i) {
        String assignmentName = assignmentUtil.getAssignmentData(classId, i, "assignment_name");

        String dueDate = assignmentUtil.getAssignmentData(classId, i, "due_date");
        String percent = assignmentUtil.getAssignmentData(classId, i, "percent");
        String score = assignmentUtil.getAssignmentData(classId, i, "score") + "/" + assignmentUtil.getAssignmentData(classId, i, "points");
        String details = assignmentUtil.getAssignmentData(classId, i, "details");
        String extraCredit = "No";
        String graded = "No";


        if (assignmentUtil.getAssignmentData(classId, i, "extra_credit").equals("true")) {
            extraCredit = "Yes";
        }

        if (assignmentUtil.getAssignmentData(classId, i, "graded").equals("true")) {
            graded = "Yes";
        }

        ((TextView) view.findViewById(R.id.assignment_name)).setText(assignmentName);
        ((TextView) view.findViewById(R.id.due_date)).setText(dueDate);
        ((TextView) view.findViewById(R.id.percent)).setText(percent);
        ((TextView) view.findViewById(R.id.score)).setText(score);
        ((TextView) view.findViewById(R.id.details)).setText(details);
        ((TextView) view.findViewById(R.id.extra_credit)).setText(extraCredit);
        ((TextView) view.findViewById(R.id.graded)).setText(graded);
    }

}
