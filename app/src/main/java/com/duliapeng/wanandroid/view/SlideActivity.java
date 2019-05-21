package com.duliapeng.wanandroid.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.duliapeng.wanandroid.R;

import java.util.ArrayList;
import java.util.List;

public class SlideActivity extends AppCompatActivity {

    private ViewPager mVpSlide;
    private TabLayout mTlSlide;
    private ListView listView;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tabs = new ArrayList<>();
    private String[] text = {"111","222","333","444","5555","666","777","111","222","333","444","5555","666","777"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        listView = (ListView) findViewById(R.id.fragment_lv_list);

        initData();
        initView();
    }

    private void initData() {
        tabs.add("首页");
        tabs.add("体系");
        tabs.add("项目");
        fragments.add(new ProjectFragment(this, tabs.get(0)));
        fragments.add(new ProjectFragment(this, tabs.get(1)));
        fragments.add(new ProjectFragment(this, tabs.get(2)));
    }

    private void initView() {
        mTlSlide = findViewById(R.id.slide_tl_tab);
        mVpSlide = findViewById(R.id.slide_vp_view);

        mTlSlide.setTabMode(TabLayout.MODE_FIXED);
        mVpSlide.setAdapter(new TabAdapter(getSupportFragmentManager()));

        mTlSlide.setupWithViewPager(mVpSlide);
    }


    class TabAdapter extends FragmentPagerAdapter {
        public TabAdapter(FragmentManager fragmentManager) {

            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position);
        }
    }
}
