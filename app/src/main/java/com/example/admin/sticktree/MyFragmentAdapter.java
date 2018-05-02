package com.example.admin.sticktree;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by timqossible on 11/22/2017.
 */

public class MyFragmentAdapter extends FragmentStatePagerAdapter {
    public MyFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FragmentManageTree();
            case 1: return new FragmentUpdateTree();
            default: return new FragmentManageTree();
        }
    }

    @Override
    public int getCount() {return 2;}

    public CharSequence getPageTitle(int position){
        String[] tabs = {"ManageTree","UpdateTree"};
        return tabs[position];
    }
}
