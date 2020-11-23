package com.cynoteck.petofyparents.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cynoteck.petofyparents.fragments.AcceptAdoptionFragments;
import com.cynoteck.petofyparents.fragments.AcceptDonationFragment;
import com.cynoteck.petofyparents.fragments.PendingAdoptionFragments;
import com.cynoteck.petofyparents.fragments.PendingDonationFragment;
import com.cynoteck.petofyparents.fragments.RejectAdoptionFragments;
import com.cynoteck.petofyparents.fragments.RejectDonationFragment;

public class MyAdopterAdoption extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public MyAdopterAdoption(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PendingAdoptionFragments pendingAdoptionFragments = new PendingAdoptionFragments();
                return pendingAdoptionFragments;
            case 1:
                RejectAdoptionFragments rejectAdoptionFragments = new RejectAdoptionFragments();
                return rejectAdoptionFragments;
            case 2:
                AcceptAdoptionFragments acceptAdoptionFragments = new AcceptAdoptionFragments();
                return acceptAdoptionFragments;
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
