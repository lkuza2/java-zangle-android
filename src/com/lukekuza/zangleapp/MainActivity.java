package com.lukekuza.zangleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.lukekuza.zangleapp.about.AboutActivity;
import com.lukekuza.zangleapp.login.LoadingFragment;
import com.lukekuza.zangleapp.login.LoginFragment;
import com.lukekuza.zangleapp.smartdatabase.SmartClassUtil;
import com.lukekuza.zangleapp.smartdatabase.SmartLoginUtil;
import com.lukekuza.zangleapp.smartdatabase.SmartUserUtil;
import com.lukekuza.zangleapp.user.UserActivity;
import com.lukekuza.zangleapp.util.CollectionPagerAdapter;
import com.lukekuza.zangleapp.util.InterfaceDynamicsUtil;
import com.lukekuza.zangleapp.util.MainUtil;
import com.lukekuza.zangleapp.util.OnSendTabs;

public class MainActivity extends SherlockFragmentActivity implements ActionBar.TabListener, ViewPager.OnPageChangeListener, OnSendTabs {

    ViewPager viewPager;
    private boolean hideMenu;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        InterfaceDynamicsUtil interfaceDynamicsUtil = InterfaceDynamicsUtil.getInstance();
        interfaceDynamicsUtil.clearFragments(this);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        if (!SmartUserUtil.getInstance(this).loginDataExists()) {
            interfaceDynamicsUtil.setFragments(new Fragment[]{new LoginFragment()});
            getSupportActionBar().addTab(getSupportActionBar().newTab().setText("Login").setTabListener(this));
            hideMenu = true;
        } else {
            processTabs(MainUtil.getInstance().getFragments(this), SmartClassUtil.getInstance(this).getClassesOrderedById(), false);
        }

        interfaceDynamicsUtil.setCollectionPagerAdapter(new CollectionPagerAdapter(getSupportFragmentManager(), this));

        viewPager.setOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(8);

        viewPager.setAdapter(interfaceDynamicsUtil.getCollectionPagerAdapter());


        getSupportActionBar().show();
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onPageSelected(int i) {
        getSupportActionBar().setSelectedNavigationItem(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onSendTabs(Fragment[] fragments, String[] tabs) {
        processTabs(fragments, tabs, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        if (hideMenu) {
            for (int i = 0; i < menu.size(); i++) {
                if (menu.getItem(i).getItemId() != R.id.menu_about) {
                    menu.getItem(i).setVisible(false);
                }
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                hideMenu = true;
                invalidateOptionsMenu();
                processTabs(new Fragment[]{new LoadingFragment()}, new String[]{"Loading"}, true);
                return true;
            case R.id.menu_logout:
                logout();
                return true;
            case R.id.menu_user_info:
                startActivity(new Intent(this, UserActivity.class));
                return true;
            case R.id.menu_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        hideMenu = true;
        invalidateOptionsMenu();
        SmartLoginUtil.getInstance(this).clearLogin();
        processTabs(new Fragment[]{new LoginFragment()}, new String[]{"Login"}, true);
    }

    private void processTabs(Fragment[] fragments, String[] tabs, boolean refresh) {
        InterfaceDynamicsUtil interfaceDynamicsUtil = InterfaceDynamicsUtil.getInstance();

        getSupportActionBar().removeAllTabs();

        boolean hide = false;

        for (String tab : tabs) {
            Log.d("Zangle", tab);
            if (tab.equals("Login") || tab.equals("Loading")) {
                hide = true;
            }
            getSupportActionBar().addTab(getSupportActionBar().newTab().setText(tab).setTabListener(this));
        }

        if (hide) {
            hideMenu = true;
            invalidateOptionsMenu();
        } else {
            hideMenu = false;
            invalidateOptionsMenu();
        }

        interfaceDynamicsUtil.clearFragments(this);
        interfaceDynamicsUtil.setFragments(fragments);
        if (refresh) {
            interfaceDynamicsUtil.getCollectionPagerAdapter().notifyDataSetChanged();
        }
    }


}
