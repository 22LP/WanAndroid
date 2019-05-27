package com.duliapeng.wanandroid.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.duliapeng.wanandroid.R;
import com.duliapeng.wanandroid.bean.ItemArticle;
import com.duliapeng.wanandroid.service.ItemDownImage;

import java.util.List;

/**
 * 项目板块的文章适配器
 */
public class ItemAdapter extends BaseAdapter {
    private List<ItemArticle> mItemArticleList;
    private Context context;

    public ItemAdapter(Context context, List<ItemArticle> objects){
        this.context = context;
        this.mItemArticleList = objects;
    }
    @Override
    public int getCount() {
        return mItemArticleList.size();

    }

    @Override
    public Object getItem(int position) {
        return mItemArticleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemArticle itemArticle = (ItemArticle) getItem(position);
        View view;
        final ItemAdapter.ViewHolder viewHolder;
        //缓存布局——重用convertView
        //缓存控件——把控件实例缓存在ViewHolder
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.layout_item,null);
            viewHolder = new ItemAdapter.ViewHolder();
            viewHolder.ivEnvelopePic = (ImageView ) view.findViewById(R.id.item_iv_picture);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.item_tv_title);
            viewHolder.tvAuthor = (TextView) view.findViewById(R.id.item_tv_author);
            viewHolder.tvNiceDate = (TextView) view.findViewById(R.id.item_tv_time);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ItemAdapter.ViewHolder) view.getTag();
        }
        viewHolder.ivEnvelopePic.setImageResource(R.drawable.ic_launcher_background);
        viewHolder.tvTitle.setText(itemArticle.getTitle());
        viewHolder.tvAuthor.setText(itemArticle.getAuthor());
        viewHolder.tvNiceDate.setText(itemArticle.getNiceDate());
        ItemDownImage downEnvelopePic = new ItemDownImage(itemArticle.getEnvelopePic());
        downEnvelopePic.loadImage(new ItemDownImage.ImageCallBack() {
            @Override
            public void getDrawable(Drawable drawable) {
                viewHolder.ivEnvelopePic.setImageDrawable(drawable);
            }
        });

        return view;
    }
    class ViewHolder{
        ImageView ivEnvelopePic;
        TextView tvTitle;
        TextView tvAuthor;
        TextView tvNiceDate;
    }

}
