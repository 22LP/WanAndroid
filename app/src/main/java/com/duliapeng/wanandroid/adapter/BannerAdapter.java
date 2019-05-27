package com.duliapeng.wanandroid.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * 轮播图适配器
 */
public class BannerAdapter extends PagerAdapter {

    private List<ImageView> mImageList;

    public BannerAdapter(List<ImageView> imageViews) {
        mImageList = imageViews;
    }

    @Override
    public int getCount() {
        return mImageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(mImageList.get(position));
        return mImageList.get(position);
    }
}
