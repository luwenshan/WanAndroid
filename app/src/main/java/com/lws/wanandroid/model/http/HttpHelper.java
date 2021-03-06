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

import java.util.List;

import io.reactivex.Observable;

public interface HttpHelper {
    /**
     * 首页文章列表
     *
     * @param pageNum 页码，从0开始
     */
    Observable<BaseResponse<ArticleListBean>> getFeedArticleList(int pageNum);

    /**
     * 首页banner
     */
    Observable<BaseResponse<List<BannerBean>>> getBannerData();

    /**
     * 常用网站
     */
    Observable<BaseResponse<List<UsefulSiteData>>> getUsefulSites();

    /**
     * 搜索热词
     */
    Observable<BaseResponse<List<TopSearchData>>> getTopSearchData();

    /**
     * 体系数据
     */
    Observable<BaseResponse<List<KnowledgeHierarchyData>>> getKnowledgeHierarchyData();

    /**
     * 知识体系下的文章
     *
     * @param pageNum 页码，从0开始
     * @param cid     分类的id，上述二级目录的id
     */
    Observable<BaseResponse<ArticleListBean>> getKnowledgeHierarchyDetailData(int pageNum, int cid);

    /**
     * 导航数据
     */
    Observable<BaseResponse<List<NavigationListData>>> getNavigationListData();

    /**
     * 项目分类
     */
    Observable<BaseResponse<List<ProjectClassifyData>>> getProjectClassifyData();

    /**
     * 项目列表数据
     *
     * @param pageNum 页码，从1开始
     * @param cid     分类的id，上面项目分类接口
     */
    Observable<BaseResponse<ProjectListData>> getProjectListData(int pageNum, int cid);

    /**
     * 获取公众号列表
     */
    Observable<BaseResponse<List<WxAuthor>>> getWxAuthorListData();

    /**
     * 查看某个公众号历史数据
     *
     * @param id      公众号 ID：拼接在 url 中，eg:405
     * @param pageNum 公众号页码：拼接在url 中，从1开始，eg:1
     */
    Observable<BaseResponse<ArticleListBean>> getWxSumData(int id, int pageNum);

    /**
     * 在某个公众号中搜索历史文章
     *
     * @param id      公众号 ID：拼接在 url 中，eg:405
     * @param pageNum 公众号页码：拼接在url 中，从1开始，eg:1
     * @param k       搜索关键字
     */
    Observable<BaseResponse<ArticleListBean>> getWxSearchSumData(int id, int pageNum, String k);

    /**
     * 登录
     */
    Observable<BaseResponse<LoginData>> getLoginData(String username, String password);

    /**
     * 注册
     */
    Observable<BaseResponse<LoginData>> getRegisterData(String username, String password, String repassword);

    /**
     * 退出登录
     */
    Observable<BaseResponse<LoginData>> logout();

    /**
     * 搜索
     *
     * @param pageNum 页码：拼接在链接上，从0开始
     * @param k       搜索关键词，支持多个关键词，用空格隔开
     */
    Observable<BaseResponse<ArticleListBean>> getSearchList(int pageNum, String k);

    /**
     * 收藏文章列表
     *
     * @param pageNum 页码：拼接在链接中，从0开始
     */
    Observable<BaseResponse<ArticleListBean>> getCollectList(int pageNum);

    /**
     * 收藏站内文章
     *
     * @param id 文章id，拼接在链接中
     */
    Observable<BaseResponse<ArticleListBean>> addCollectArticle(int id);

    /**
     * 收藏站外文章
     */
    Observable<BaseResponse<ArticleListBean>> addCollectOutsideArticle(String title, String author, String link);

    /**
     * 取消收藏文章列表中的文章
     *
     * @param id 列表中文章的id
     */
    Observable<BaseResponse<ArticleListBean>> cancelCollectArticle(int id);

    /**
     * 取消收藏我的收藏页面中的文章
     */
    Observable<BaseResponse<ArticleListBean>> cancelCollectPageArticle(int id);
}
