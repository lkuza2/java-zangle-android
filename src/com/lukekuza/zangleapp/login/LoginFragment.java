package com.lukekuza.zangleapp.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.actionbarsherlock.app.SherlockFragment;
import com.lukekuza.zangleapp.R;
import com.lukekuza.zangleapp.smartdatabase.SmartAssignmentUtil;
import com.lukekuza.zangleapp.smartdatabase.SmartClassUtil;
import com.lukekuza.zangleapp.smartdatabase.SmartLoginUtil;
import com.lukekuza.zangleapp.util.MainUtil;
import com.lukekuza.zangleapp.util.OnSendTabs;
import com.lukekuza.zangleapp.zangle.ZangleTask;
import com.lukekuza.zangleapp.zangle.ZangleUtil;

/**
 * Created with IntelliJ IDEA.
 * User: theshadow
 * Date: 4/22/13
 * Time: 7:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginFragment extends SherlockFragment implements View.OnClickListener, ZangleTask.OnZangleTaskCompleted {

    String username;
    String password;
    OnSendTabs sendTabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login, container, false);

        view.findViewById(R.id.login_button).setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        sendTabs = (OnSendTabs) activity;
    }

    @Override
    public void onClick(View view) {
        view.setVisibility(View.INVISIBLE);
        view.setEnabled(false);
        getView().findViewById(R.id.login_progress).setVisibility(View.VISIBLE);
        username = ((EditText) getView().findViewById(R.id.username_edit_text)).getText().toString();
        password = ((EditText) getView().findViewById(R.id.password_edit_text)).getText().toString();
        ZangleUtil.getInstance().login(username, password, this);
    }

    @Override
    public void zangleTaskCompleted(String command, String result) {
        if (command.equals("login")) {
            boolean connected = Boolean.parseBoolean(result.trim());
            if (connected) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getView().findViewById(R.id.username_edit_text).setEnabled(false);
                        getView().findViewById(R.id.password_edit_text).setEnabled(false);
                        getView().findViewById(R.id.login_button).setVisibility(View.INVISIBLE);
                        getView().findViewById(R.id.username_edit_text).setVisibility(View.INVISIBLE);
                        getView().findViewById(R.id.password_edit_text).setVisibility(View.INVISIBLE);
                        getView().findViewById(R.id.username_text).setVisibility(View.INVISIBLE);
                        getView().findViewById(R.id.password_text).setVisibility(View.INVISIBLE);
                        getView().findViewById(R.id.login_progress).setVisibility(View.INVISIBLE);
                        getView().findViewById(R.id.login_text_view).setVisibility(View.INVISIBLE);
                        getView().findViewById(R.id.save_login_progress).setVisibility(View.VISIBLE);
                    }
                });

                SmartLoginUtil.getInstance(getActivity()).clearLogin();
                SmartLoginUtil.getInstance(getActivity()).saveLogin(username, password);
                username = null;
                password = null;
                SmartClassUtil.getInstance(getActivity()).clearClassData();
                SmartAssignmentUtil.getInstance(getActivity()).clearAssignmentData();
                ZangleUtil.getInstance().saveUserData(getActivity(), this);

            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage(R.string.login_failed).setCancelable(false).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getView().findViewById(R.id.login_progress).setVisibility(View.INVISIBLE);
                                getView().findViewById(R.id.login_button).setVisibility(View.VISIBLE);
                                getView().findViewById(R.id.login_button).setEnabled(true);
                            }
                        });
                        builder.create().show();
                    }
                });
            }

        } else if (command.equals("save")) {
            sendTabs.onSendTabs(MainUtil.getInstance().getFragments(getActivity()), SmartClassUtil.getInstance(getActivity()).getClassesOrderedById());
            Log.d("Zangle", "Saved");
        }
    }

}
