package com.duliapeng.wanandroid.bean;

/**
 *首页文章实体类
 */
public class Article {
    private String author;
    private String chapterName;
    private String niceDate;
    private String superChapterName;
    private String title;
    private String link;

    public Article(String author, String niceDate,String chapterName, String superChapterName, String title) {
        this.author = author;
        this.niceDate = niceDate;
        this.chapterName = chapterName;
        this.superChapterName = superChapterName;
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getSuperChapterName() {
        return superChapterName;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Article{" +
                "author='" + author + '\'' +
                ", chapterName='" + chapterName + '\'' +
                ", niceDate='" + niceDate + '\'' +
                ", superChapterName='" + superChapterName + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
