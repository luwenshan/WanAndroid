package com.lws.wanandroid.model.http;

import com.lws.wanandroid.model.bean.BaseResponse;
import com.lws.wanandroid.model.bean.KnowledgeHierarchyData;
import com.lws.wanandroid.model.bean.BannerBean;
import com.lws.wanandroid.model.bean.ArticleListBean;
import com.lws.wanandroid.model.bean.LoginData;
import com.lws.wanandroid.model.bean.TopSearchData;
import com.lws.wanandroid.model.bean.UsefulSiteData;
import com.lws.wanandroid.model.bean.NavigationListData;
import com.lws.wanandroid.model.bean.ProjectClassifyData;
import com.lws.wanandroid.model.bean.ProjectListData;
import com.lws.wanandroid.model.bean.WxAuthor;
import com.lws.wanandroid.model.http.api.GeeksApis;

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
    public Observable<BaseResponse<ArticleListBean>> getFeedArticleList(int pageNum) {
        return mGeeksApis.getFeedArticleList(pageNum);
    }

    @Override
    public Observable<BaseResponse<List<BannerBean>>> getBannerData() {
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
    public Observable<BaseResponse<ArticleListBean>> getKnowledgeHierarchyDetailData(int pageNum, int cid) {
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
    public Observable<BaseResponse<ArticleListBean>> getWxSumData(int id, int pageNum) {
        return mGeeksApis.getWxSumData(id, pageNum);
    }

    @Override
    public Observable<BaseResponse<ArticleListBean>> getWxSearchSumData(int id, int pageNum, String k) {
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
    public Observable<BaseResponse<ArticleListBean>> getSearchList(int pageNum, String k) {
        return mGeeksApis.getSearchList(pageNum, k);
    }

    @Override
    public Observable<BaseResponse<ArticleListBean>> getCollectList(int pageNum) {
        return mGeeksApis.getCollectList(pageNum);
    }

    @Override
    public Observable<BaseResponse<ArticleListBean>> addCollectArticle(int id) {
        return mGeeksApis.addCollectArticle(id);
    }

    @Override
    public Observable<BaseResponse<ArticleListBean>> addCollectOutsideArticle(String title, String author, String link) {
        return mGeeksApis.addCollectOutsideArticle(title, author, link);
    }

    @Override
    public Observable<BaseResponse<ArticleListBean>> cancelCollectArticle(int id) {
        return mGeeksApis.cancelCollectArticle(id);
    }

    @Override
    public Observable<BaseResponse<ArticleListBean>> cancelCollectPageArticle(int id) {
        return mGeeksApis.cancelCollectPageArticle(id, -1);
    }
}
