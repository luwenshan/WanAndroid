package com.lws.wanandroid.utils;

import com.lws.wanandroid.model.bean.BaseResponse;
import com.lws.wanandroid.model.bean.ArticleListBean;
import com.lws.wanandroid.model.bean.LoginData;

import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {

    /**
     * 统一线程处理
     */
    public static <T> FlowableTransformer<T, T> rxFlSchedulerHelper() {
        return flowable -> flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 统一线程处理
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 网络请求统一结果处理
     */
    public static <T> ObservableTransformer<BaseResponse<T>, T> handleResult() {
        return httpResponseObservable ->
                httpResponseObservable.flatMap(baseResponse -> {
                    if (baseResponse.getErrorCode() == BaseResponse.SUCCESS
                            && baseResponse.getData() != null
                            && CommonUtils.isNetworkConnected()) {
                        return createObservable(baseResponse.getData());
                    } else {
                        return Observable.error(new Exception(baseResponse.getErrorMsg()));
                    }
                });
    }

    /**
     * 退出登录返回结果处理
     */
    public static <T> ObservableTransformer<BaseResponse<T>, T> handlerLogoutResult() {
        return httpResponseObservable ->
                httpResponseObservable.flatMap(baseResponse -> {
                    if (baseResponse.getErrorCode() == BaseResponse.SUCCESS
                            && CommonUtils.isNetworkConnected()) {
                        //创建一个非空数据源，避免onNext()传入null
                        return createObservable((T) new LoginData());
                    } else {
                        return Observable.error(new Exception());
                    }
                });
    }

    /**
     * 收藏返回结果处理
     *
     * @param <T> 指定的泛型类型
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<BaseResponse<T>, T> handleCollectResult() {
        return httpResponseObservable ->
                httpResponseObservable.flatMap(baseResponse -> {
                    if (baseResponse.getErrorCode() == BaseResponse.SUCCESS
                            && CommonUtils.isNetworkConnected()) {
                        //创建一个非空数据源，避免onNext()传入null
                        return createObservable((T) new ArticleListBean());
                    } else {
                        return Observable.error(new Exception());
                    }
                });
    }

    private static <T> Observable<T> createObservable(final T t) {
        return Observable.create(emitter -> {
            try {
                emitter.onNext(t);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
}
