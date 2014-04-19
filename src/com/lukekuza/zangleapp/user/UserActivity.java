package com.lukekuza.zangleapp.user;

import android.os.Bundle;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.lukekuza.zangleapp.R;
import com.lukekuza.zangleapp.smartdatabase.SmartLoginUtil;
import com.lukekuza.zangleapp.smartdatabase.SmartUserUtil;

/**
 * Created with IntelliJ IDEA.
 * User: theshadow
 * Date: 4/28/13
 * Time: 10:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserActivity extends SherlockActivity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);

        SmartUserUtil userUtil = SmartUserUtil.getInstance(this);

        setViewText(SmartLoginUtil.getInstance(this).getUsername(), R.id.username);
        setViewText(userUtil.getUserData("name"), R.id.name);
        setViewText(userUtil.getUserData("school"), R.id.school);
        setViewText(userUtil.getUserData("grade"), R.id.grade);
    }

    private void setViewText(String text, int id) {
        ((TextView) findViewById(id)).setText(text);
    }

}
