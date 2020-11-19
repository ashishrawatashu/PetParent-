package com.cynoteck.petofyparents.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cynoteck.petofyparents.fragments.AcceptFragment;
import com.cynoteck.petofyparents.fragments.PendingFragment;
import com.cynoteck.petofyparents.fragments.RejectFragment;

public class MyAdapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;

    public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PendingFragment pendingFragment = new PendingFragment();
                return pendingFragment;
            case 1:
                RejectFragment rejectFragment = new RejectFragment();
                return rejectFragment;
            case 2:
                AcceptFragment acceptFragment = new AcceptFragment();
                return acceptFragment;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
