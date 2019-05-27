package com.duliapeng.wanandroid.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * 页面切换的适配器
 */
public class TabAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private List<String> tabs;

    public TabAdapter(FragmentManager fragmentManager, List<Fragment> mFragmentList, List<String> tabs) {
        super(fragmentManager);
        this.mFragmentList = mFragmentList;
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position);
    }
}

