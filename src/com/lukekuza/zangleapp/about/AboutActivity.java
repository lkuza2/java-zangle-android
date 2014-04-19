package com.lukekuza.zangleapp.about;

import android.os.Bundle;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.lukekuza.zangleapp.R;

/**
 * Created with IntelliJ IDEA.
 * User: theshadow
 * Date: 4/28/13
 * Time: 11:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class AboutActivity extends SherlockActivity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        ((TextView) findViewById(R.id.version)).setText(getString(R.string.app_version_text) + getString(R.string.app_version));
    }


}
