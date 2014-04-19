package com.lukekuza.zangleapp.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: theshadow
 * Date: 4/22/13
 * Time: 6:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class InterfaceDynamicsUtil {

    private static InterfaceDynamicsUtil instance;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private CollectionPagerAdapter collectionPagerAdapter;

    private InterfaceDynamicsUtil() {

    }

    public synchronized static InterfaceDynamicsUtil getInstance() {
        if (instance == null) {
            instance = new InterfaceDynamicsUtil();
        }
        return instance;
    }

    public void clearFragments(SherlockFragmentActivity activity) {
        Iterator<Fragment> iterator = getFragments().listIterator(0);
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

        while (iterator.hasNext()) {
            transaction.remove(iterator.next());
        }
        getFragments().clear();
        transaction.commit();
    }

    public List<Fragment> getFragments() {
        return fragmentList;
    }

    public void setFragments(Fragment[] fragments) {
        for (Fragment fragment : fragments) {
            getFragments().add(fragment);
        }
    }

    public CollectionPagerAdapter getCollectionPagerAdapter() {
        return collectionPagerAdapter;
    }

    public void setCollectionPagerAdapter(CollectionPagerAdapter collectionPagerAdapter) {
        this.collectionPagerAdapter = collectionPagerAdapter;
    }


}
