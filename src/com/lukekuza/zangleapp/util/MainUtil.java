package com.lukekuza.zangleapp.util;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.lukekuza.zangleapp.classes.ClassFragment;
import com.lukekuza.zangleapp.smartdatabase.SmartClassUtil;

/**
 * Created with IntelliJ IDEA.
 * User: theshadow
 * Date: 4/28/13
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainUtil {

    private static MainUtil instance;

    private MainUtil() {

    }

    public static MainUtil getInstance() {
        if (instance == null) {
            instance = new MainUtil();
        }
        return instance;
    }

    public Fragment[] getFragments(Context context) {
        int classesLength = SmartClassUtil.getInstance(context).getNumberOfClasses();
        Fragment fragments[] = new Fragment[classesLength];

        for (int i = 0; i < classesLength; i++) {
            Log.d("Zangle", "Fragment Created");
            Fragment fragment = new ClassFragment();

            Bundle bundle = new Bundle();
            bundle.putInt("class_id", i);
            fragment.setArguments(bundle);

            fragments[i] = fragment;
        }
        return fragments;
    }

    public void setText(View view, String text, int id) {
        ((TextView) view.findViewById(id)).setText(text);
    }

}
