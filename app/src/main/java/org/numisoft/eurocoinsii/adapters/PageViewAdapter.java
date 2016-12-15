package org.numisoft.eurocoinsii.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.numisoft.eurocoinsii.fragments.BasicFragment;

/**
 * Created by kukolka on 22.08.16.
 */
public class PageViewAdapter extends FragmentStatePagerAdapter {

    int numberOfTabs;
    String country;
    int[] years;


    public PageViewAdapter(FragmentManager fm, String country, int[] years) {
        super(fm);
        numberOfTabs = years.length;
        this.country = country;
        this.years = years;
    }

    @Override
    public Fragment getItem(int position) {
       return new BasicFragment(country, years[position]);
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }



}
