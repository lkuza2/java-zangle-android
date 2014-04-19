package com.lukekuza.zangleapp.classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.actionbarsherlock.app.SherlockFragment;
import com.lukekuza.zangleapp.R;
import com.lukekuza.zangleapp.smartdatabase.SmartClassUtil;
import com.lukekuza.zangleapp.util.MainUtil;

/**
 * Created with IntelliJ IDEA.
 * User: theshadow
 * Date: 4/28/13
 * Time: 3:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClassFragment extends SherlockFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.zclass, container, false);

        int classId = getArguments().getInt("class_id");

        MainUtil util = MainUtil.getInstance();
        SmartClassUtil smartClassUtil = SmartClassUtil.getInstance(getActivity());

        util.setText(view, smartClassUtil.getDataForClass(classId, "grade"), R.id.grade);
        util.setText(view, smartClassUtil.getDataForClass(classId, "percent"), R.id.percent);
        util.setText(view, smartClassUtil.getDataForClass(classId, "period"), R.id.period);
        util.setText(view, smartClassUtil.getDataForClass(classId, "teacher"), R.id.teacher);

        AssignmentListAdapter adapter = new AssignmentListAdapter(getActivity(), classId);

        LinearLayout linear = new LinearLayout(getActivity());
        linear.setOrientation(LinearLayout.VERTICAL);
        linear.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linear.setDividerDrawable(getResources().getDrawable(R.drawable.line));

        for (int i = 0; i < adapter.getCount(); i++) {
            linear.addView(adapter.getView(i, null, linear));
        }

        ((ScrollView) view.findViewById(R.id.scrollView)).addView(linear);

        return view;
    }

}
