package com.lukekuza.zangleapp.util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: theshadow
 * Date: 1/3/13
 * Time: 8:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class CollectionPagerAdapter extends FragmentStatePagerAdapter {

    Context context;


    public CollectionPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        InterfaceDynamicsUtil interfaceDynamicsUtil = InterfaceDynamicsUtil.getInstance();
        List<Fragment> fragments = interfaceDynamicsUtil.getFragments();

        if (fragments != null && fragments.size() >= i)
            return fragments.get(i);
        else return null;
    }

    @Override
    public int getCount() {
        return InterfaceDynamicsUtil.getInstance().getFragments().size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


}

