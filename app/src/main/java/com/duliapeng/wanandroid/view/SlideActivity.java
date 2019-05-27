package com.duliapeng.wanandroid.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.duliapeng.wanandroid.R;
import com.duliapeng.wanandroid.adapter.TabAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * 三个模块的滑动切换
 */
public class SlideActivity extends AppCompatActivity {
    private ViewPager mVpSlide;
    private TabLayout mTlSlide;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tabs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        initView();
    }

    private void initView() {
        mTlSlide = findViewById(R.id.slide_tl_tab);
        mVpSlide = findViewById(R.id.slide_vp_view);

        tabs.add("首页");
        tabs.add("体系");
        tabs.add("项目");

        ArticleFragment mHomeFrag = new ArticleFragment();
        ItemFragment mItemFrag = new ItemFragment();
        SystemFragment mSystemFrag = new SystemFragment();
        fragments.add(mHomeFrag);
        fragments.add(mSystemFrag);
        fragments.add(mItemFrag);

        mTlSlide.setTabMode(TabLayout.MODE_FIXED);
        mVpSlide.setAdapter(new TabAdapter(getSupportFragmentManager(), fragments, tabs));
        mTlSlide.setupWithViewPager(mVpSlide);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }
    //搜索图标的监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.toolbar_tb_search) {
            Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}