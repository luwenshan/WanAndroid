package com.lws.wanandroid.core.http;

import com.lws.wanandroid.core.bean.BaseResponse;
import com.lws.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;
import com.lws.wanandroid.core.bean.main.banner.BannerData;
import com.lws.wanandroid.core.bean.main.collect.FeedArticleListData;
import com.lws.wanandroid.core.bean.main.login.LoginData;
import com.lws.wanandroid.core.bean.main.search.TopSearchData;
import com.lws.wanandroid.core.bean.main.search.UsefulSiteData;
import com.lws.wanandroid.core.bean.navigation.NavigationListData;
import com.lws.wanandroid.core.bean.project.ProjectClassifyData;
import com.lws.wanandroid.core.bean.project.ProjectListData;
import com.lws.wanandroid.core.bean.wx.WxAuthor;
import com.lws.wanandroid.core.http.api.GeeksApis;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class HttpHelperImpl implements HttpHelper {

    private GeeksApis mGeeksApis;

    @Inject
    public HttpHelperImpl(GeeksApis geeksApis) {
        mGeeksApis = geeksApis;
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> getFeedArticleList(int pageNum) {
        return mGeeksApis.getFeedArticleList(pageNum);
    }

    @Override
    public Observable<BaseResponse<List<BannerData>>> getBannerData() {
        return mGeeksApis.getBannerData();
    }

    @Override
    public Observable<BaseResponse<List<UsefulSiteData>>> getUsefulSites() {
        return mGeeksApis.getUsefulSites();
    }

    @Override
    public Observable<BaseResponse<List<TopSearchData>>> getTopSearchData() {
        return mGeeksApis.getTopSearchData();
    }

    @Override
    public Observable<BaseResponse<List<KnowledgeHierarchyData>>> getKnowledgeHierarchyData() {
        return mGeeksApis.getKnowledgeHierarchyData();
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> getKnowledgeHierarchyDetailData(int pageNum, int cid) {
        return mGeeksApis.getKnowledgeHierarchyDetailData(pageNum, cid);
    }

    @Override
    public Observable<BaseResponse<List<NavigationListData>>> getNavigationListData() {
        return mGeeksApis.getNavigationListData();
    }

    @Override
    public Observable<BaseResponse<List<ProjectClassifyData>>> getProjectClassifyData() {
        return mGeeksApis.getProjectClassifyData();
    }

    @Override
    public Observable<BaseResponse<ProjectListData>> getProjectListData(int pageNum, int cid) {
        return mGeeksApis.getProjectListData(pageNum, cid);
    }

    @Override
    public Observable<BaseResponse<List<WxAuthor>>> getWxAuthorListData() {
        return mGeeksApis.getWxAuthorListData();
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> getWxSumData(int id, int pageNum) {
        return mGeeksApis.getWxSumData(id, pageNum);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> getWxSearchSumData(int id, int pageNum, String k) {
        return mGeeksApis.getWxSearchSumData(id, pageNum, k);
    }

    @Override
    public Observable<BaseResponse<LoginData>> getLoginData(String username, String password) {
        return mGeeksApis.getLoginData(username, password);
    }

    @Override
    public Observable<BaseResponse<LoginData>> getRegisterData(String username, String password, String repassword) {
        return mGeeksApis.getRegisterData(username, password, repassword);
    }

    @Override
    public Observable<BaseResponse<LoginData>> logout() {
        return mGeeksApis.logout();
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> getSearchList(int pageNum, String k) {
        return mGeeksApis.getSearchList(pageNum, k);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> getCollectList(int pageNum) {
        return mGeeksApis.getCollectList(pageNum);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> addCollectArticle(int id) {
        return mGeeksApis.addCollectArticle(id);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> addCollectOutsideArticle(String title, String author, String link) {
        return mGeeksApis.addCollectOutsideArticle(title, author, link);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> cancelCollectArticle(int id) {
        return mGeeksApis.cancelCollectArticle(id);
    }

    @Override
    public Observable<BaseResponse<FeedArticleListData>> cancelCollectPageArticle(int id) {
        return mGeeksApis.cancelCollectPageArticle(id, -1);
    }
}
