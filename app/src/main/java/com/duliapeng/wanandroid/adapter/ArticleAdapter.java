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

public class ArticleAdapter extends BaseAdapter {
    private List<Article> articleList;
    private LayoutInflater inflater;
    private int ResourceId;
    public ArticleAdapter(Context context, int resourceId, List<Article> objects) {
        this.inflater = LayoutInflater.from(context);
        this.ResourceId=resourceId;
        this.articleList = objects;
    }

    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public Object getItem(int position) {
        return articleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Article article = (Article) getItem(position);
        View view =  inflater.inflate(R.layout.layout_article,null);


        TextView tvSuperChapterName = (TextView) view.findViewById(R.id.article_tv_superChapterName);
        TextView tvTitle = (TextView) view.findViewById(R.id.article_tv_title);
        TextView tvChapterName = (TextView) view.findViewById(R.id.article_tv_chapterName);
        TextView tvAuthor = (TextView) view.findViewById(R.id.article_tv_author);
        TextView tvNiceDate = (TextView) view.findViewById(R.id.article_tv_niceDate);

        tvSuperChapterName.setText(article.getSuperChapterName());
        tvTitle.setText(article.getTitle());
        tvChapterName.setText(article.getChapterName());
        tvAuthor.setText(article.getAuthor());
        tvNiceDate.setText(article.getNiceDate());
        return view;
    }
}
