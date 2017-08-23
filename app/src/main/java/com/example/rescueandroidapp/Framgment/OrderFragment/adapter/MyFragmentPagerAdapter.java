package com.example.rescueandroidapp.Framgment.OrderFragment.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by CC on 2017/5/29
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> data;
    private String[] titles;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> data, String[] titles) {
        super(fm);
        this.data = data;
        this.titles = titles;
    }

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = data.get(position);
        Bundle args = new Bundle();
        args.putString("title",titles[position]);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles==null?null:titles[position];
    }


}
