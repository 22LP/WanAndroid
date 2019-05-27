package com.duliapeng.wanandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.duliapeng.wanandroid.R;
import com.duliapeng.wanandroid.bean.Article;

import java.util.List;

/**
 * 文章列表适配器
 */
public class ArticleAdapter extends BaseAdapter {
    private List<Article> mArticleList;
    private Context context;

    public ArticleAdapter(Context context, List<Article> objects) {
        this.context = context;
        this.mArticleList = objects;
    }

    @Override
    public int getCount() {
        return mArticleList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArticleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Article article = (Article) getItem(position);
        View view;
        ViewHolder viewHolder;
        //缓存布局——重用convertView
        //缓存控件——把控件实例缓存在ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_article, null);
            viewHolder = new ViewHolder();
            viewHolder.tvSuperChapterName = (TextView) view.findViewById(R.id.article_tv_superChapterName);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.article_tv_title);
            viewHolder.tvChapterName = (TextView) view.findViewById(R.id.article_tv_chapterName);
            viewHolder.tvAuthor = (TextView) view.findViewById(R.id.article_tv_author);
            viewHolder.tvNiceDate = (TextView) view.findViewById(R.id.article_tv_niceDate);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvSuperChapterName.setText(article.getSuperChapterName());
        viewHolder.tvTitle.setText(article.getTitle());
        viewHolder.tvChapterName.setText(article.getChapterName());
        viewHolder.tvAuthor.setText(article.getAuthor());
        viewHolder.tvNiceDate.setText(article.getNiceDate());
        return view;
    }

    class ViewHolder {
        TextView tvSuperChapterName;
        TextView tvTitle;
        TextView tvChapterName;
        TextView tvAuthor;
        TextView tvNiceDate;
    }
}
