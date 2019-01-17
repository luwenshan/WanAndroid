package com.lws.wanandroid.core.bean.navigation;

import com.lws.wanandroid.core.bean.main.collect.FeedArticleData;

import java.util.List;

public class NavigationListData {

    private int cid;
    private String name;
    private List<FeedArticleData> articles;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FeedArticleData> getArticles() {
        return articles;
    }

    public void setArticles(List<FeedArticleData> articles) {
        this.articles = articles;
    }
}
