package com.lukekuza.zangleapp.login;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;
import com.lukekuza.zangleapp.R;
import com.lukekuza.zangleapp.smartdatabase.SmartClassUtil;
import com.lukekuza.zangleapp.smartdatabase.SmartLoginUtil;
import com.lukekuza.zangleapp.util.MainUtil;
import com.lukekuza.zangleapp.util.OnSendTabs;
import com.lukekuza.zangleapp.zangle.ZangleTask;
import com.lukekuza.zangleapp.zangle.ZangleUtil;

/**
 * Created with IntelliJ IDEA.
 * User: theshadow
 * Date: 4/28/13
 * Time: 5:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoadingFragment extends SherlockFragment implements ZangleTask.OnZangleTaskCompleted {

    OnSendTabs sendTabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.loading, container, false);

        ZangleUtil.getInstance().setContext(getActivity());
        ZangleUtil.getInstance().login(SmartLoginUtil.getInstance(getActivity()).getUsername(), SmartLoginUtil.getInstance(getActivity()).getPassword(), this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        sendTabs = (OnSendTabs) activity;
    }

    @Override
    public void zangleTaskCompleted(String command, String result) {
        if (command.equals("login")) {
            ZangleUtil.getInstance().saveUserData(getActivity(), this);
        } else if (command.equals("save")) {
            Log.d("Zangle", Integer.toString(SmartClassUtil.getInstance(getActivity()).getNumberOfClasses()));
            sendTabs.onSendTabs(MainUtil.getInstance().getFragments(getActivity()), SmartClassUtil.getInstance(getActivity()).getClassesOrderedById());
        }
    }
}
