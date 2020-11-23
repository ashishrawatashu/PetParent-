package com.cynoteck.petofyparents.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cynoteck.petofyparents.fragments.AcceptDonationFragment;
import com.cynoteck.petofyparents.fragments.PendingDonationFragment;
import com.cynoteck.petofyparents.fragments.RejectDonationFragment;

public class MyAdapterDonation extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;

    public MyAdapterDonation(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PendingDonationFragment pendingDonationFragment = new PendingDonationFragment();
                return pendingDonationFragment;
            case 1:
                RejectDonationFragment rejectDonationFragment = new RejectDonationFragment();
                return rejectDonationFragment;
            case 2:
                AcceptDonationFragment acceptDonationFragment = new AcceptDonationFragment();
                return acceptDonationFragment;
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
