package com.lws.wanandroid.model.http.api;

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
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GeeksApis {
    String HOST = "https://www.wanandroid.com/";

    /**
     * 首页文章列表
     *
     * @param pageNum 页码，从0开始
     */
    @GET("article/list/{pageNum}/json")
    Observable<BaseResponse<ArticleListBean>> getFeedArticleList(@Path("pageNum") int pageNum);

    /**
     * 首页banner
     */
    @GET("banner/json")
    Observable<BaseResponse<List<BannerBean>>> getBannerData();

    /**
     * 常用网站
     */
    @GET("friend/json")
    Observable<BaseResponse<List<UsefulSiteData>>> getUsefulSites();

    /**
     * 搜索热词
     */
    @GET("hotkey/json")
    @Headers("Cache-Control: public, max-age=36000")
    Observable<BaseResponse<List<TopSearchData>>> getTopSearchData();

    /**
     * 体系数据
     */
    @GET("tree/json")
    Observable<BaseResponse<List<KnowledgeHierarchyData>>> getKnowledgeHierarchyData();

    /**
     * 知识体系下的文章
     *
     * @param pageNum 页码，从0开始
     * @param cid     分类的id，上述二级目录的id
     */
    @GET("article/list/{pageNum}/json")
    Observable<BaseResponse<ArticleListBean>> getKnowledgeHierarchyDetailData(
            @Path("pageNum") int pageNum, @Query("cid") int cid);

    /**
     * 导航数据
     */
    @GET("navi/json")
    Observable<BaseResponse<List<NavigationListData>>> getNavigationListData();

    /**
     * 项目分类
     */
    @GET("project/tree/json")
    Observable<BaseResponse<List<ProjectClassifyData>>> getProjectClassifyData();

    /**
     * 项目列表数据
     *
     * @param pageNum 页码，从1开始
     * @param cid     分类的id，上面项目分类接口
     */
    @GET("project/list/{pageNum}/json")
    Observable<BaseResponse<ProjectListData>> getProjectListData(
            @Path("pageNum") int pageNum, @Query("cid") int cid);

    /**
     * 获取公众号列表
     */
    @GET("wxarticle/chapters/json")
    Observable<BaseResponse<List<WxAuthor>>> getWxAuthorListData();

    /**
     * 查看某个公众号历史数据
     *
     * @param id      公众号 ID：拼接在 url 中，eg:405
     * @param pageNum 公众号页码：拼接在url 中，从1开始，eg:1
     */
    @GET("wxarticle/list/{id}/{pageNum}/json")
    Observable<BaseResponse<ArticleListBean>> getWxSumData(
            @Path("id") int id, @Path("pageNum") int pageNum);

    /**
     * 在某个公众号中搜索历史文章
     *
     * @param id      公众号 ID：拼接在 url 中，eg:405
     * @param pageNum 公众号页码：拼接在url 中，从1开始，eg:1
     * @param k       搜索关键字
     */
    @GET("wxarticle/list/{id}/{pageNum}/json")
    Observable<BaseResponse<ArticleListBean>> getWxSearchSumData(
            @Path("id") int id, @Path("pageNum") int pageNum, @Query("k") String k);

    /**
     * 登录
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse<LoginData>> getLoginData(
            @Field("username") String username, @Field("password") String password);

    /**
     * 注册
     */
    @POST("user/register")
    @FormUrlEncoded
    Observable<BaseResponse<LoginData>> getRegisterData(
            @Field("username") String username,
            @Field("password") String password,
            @Field("repassword") String repassword);

    /**
     * 退出登录
     */
    @GET("user/logout/json")
    Observable<BaseResponse<LoginData>> logout();

    /**
     * 搜索
     *
     * @param pageNum 页码：拼接在链接上，从0开始
     * @param k       搜索关键词，支持多个关键词，用空格隔开
     */
    @POST("article/query/{pageNum}/json")
    @FormUrlEncoded
    Observable<BaseResponse<ArticleListBean>> getSearchList(
            @Path("pageNum") int pageNum, @Field("k") String k);

    /**
     * 收藏文章列表
     *
     * @param pageNum 页码：拼接在链接中，从0开始
     */
    @GET("lg/collect/list/{pageNum}/json")
    Observable<BaseResponse<ArticleListBean>> getCollectList(@Path("pageNum") int pageNum);

    /**
     * 收藏站内文章
     *
     * @param id 文章id，拼接在链接中
     */
    @POST("lg/collect/{id}/json")
    Observable<BaseResponse<ArticleListBean>> addCollectArticle(@Path("id") int id);

    /**
     * 收藏站外文章
     */
    @POST("lg/collect/add/json")
    @FormUrlEncoded
    Observable<BaseResponse<ArticleListBean>> addCollectOutsideArticle(
            @Field("title") String title,
            @Field("author") String author,
            @Field("link") String link
    );

    /**
     * 取消收藏文章列表中的文章
     *
     * @param id 列表中文章的id
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<BaseResponse<ArticleListBean>> cancelCollectArticle(@Path("id") int id);

    /**
     * 取消收藏我的收藏页面中的文章
     *
     * @param id
     * @param originId 列表页下发，无则为-1
     */
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    Observable<BaseResponse<ArticleListBean>> cancelCollectPageArticle(
            @Path("id") int id, @Field("originId") int originId);
}
