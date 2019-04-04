package com.lws.wanandroid.ui.fragment;

import android.app.ActivityOptions;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lws.wanandroid.R;
import com.lws.wanandroid.base.fragment.BaseMVPDialogFragment;
import com.lws.wanandroid.contract.UsageDialogContract;
import com.lws.wanandroid.model.bean.UsefulSiteData;
import com.lws.wanandroid.presenter.UsageDialogPresenter;
import com.lws.wanandroid.utils.CommonUtils;
import com.lws.wanandroid.utils.JudgeUtil;
import com.lws.wanandroid.widget.CircularRevealAnim;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UsageDialogFragment extends BaseMVPDialogFragment<UsageDialogPresenter>
        implements UsageDialogContract.View,
        CircularRevealAnim.AnimListener,
        ViewTreeObserver.OnPreDrawListener {

    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.useful_sites_flow_layout)
    TagFlowLayout mUsefulSitesFlowLayout;

    private List<UsefulSiteData> mUsefulSiteDataList;
    private CircularRevealAnim mCircularRevealAnim;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle);
    }

    @Override
    public void onStart() {
        super.onStart();
        initDialog();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_usage;
    }

    @Override
    protected void initEventAndData() {
        initCircleAnimation();
        initToolbar();
        mUsefulSiteDataList = new ArrayList<>();
        mPresenter.getUsefulSites();
    }

    @Override
    public void showUsefulSites(List<UsefulSiteData> usefulSiteDataList) {
        mUsefulSiteDataList = usefulSiteDataList;
        mUsefulSitesFlowLayout.setAdapter(new TagAdapter<UsefulSiteData>(mUsefulSiteDataList) {
            @Override
            public View getView(FlowLayout parent, int position, UsefulSiteData usefulSiteData) {
                assert getActivity() != null;
                TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.flow_layout_tv,
                        parent, false);
                assert usefulSiteData != null;
                String name = usefulSiteData.getName();
                tv.setText(name);
                setItemBackground(tv);
                mUsefulSitesFlowLayout.setOnTagClickListener((view, position1, parent1) -> {
                    startUsefulSitePager(view, position1);
                    return true;
                });
                return tv;
            }
        });
    }

    @Override
    public boolean onPreDraw() {
        mTitleTv.getViewTreeObserver().removeOnPreDrawListener(this);
        mCircularRevealAnim.show(mTitleTv, mRootView);
        return true;
    }

    @Override
    public void onHideAnimationEnd() {
        dismissAllowingStateLoss();
    }

    @Override
    public void onShowAnimationEnd() {

    }

    private void startUsefulSitePager(View view, int position1) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view, getString(R.string.share_view));
        JudgeUtil.startArticleDetailActivity(getActivity(),
                options,
                mUsefulSiteDataList.get(position1).getId(),
                mUsefulSiteDataList.get(position1).getName().trim(),
                mUsefulSiteDataList.get(position1).getLink().trim(),
                false,
                false,
                true);
    }

    private void setItemBackground(TextView tv) {
        tv.setBackgroundColor(CommonUtils.randomTagColor());
        tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
    }

    private void initCircleAnimation() {
        mCircularRevealAnim = new CircularRevealAnim();
        mCircularRevealAnim.setAnimListener(this);
        mTitleTv.getViewTreeObserver().addOnPreDrawListener(this);
    }

    private void initDialog() {
        Window window = getDialog().getWindow();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        //DialogSearch的宽
        int width = (int) (metrics.widthPixels * 0.98);
        assert window != null;
        window.setLayout(width, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.TOP);
        //取消过渡动画 , 使DialogSearch的出现更加平滑
        window.setWindowAnimations(R.style.DialogEmptyAnimation);
    }

    private void initToolbar() {
        mTitleTv.setText(R.string.useful_sites);
        if (mPresenter.getNightModeState()) {
            setToolbarView(R.color.comment_text, R.color.colorCard, R.drawable.ic_arrow_back_white_24dp);
        } else {
            setToolbarView(R.color.title_black, R.color.white, R.drawable.ic_arrow_back_grey_24dp);
        }
        mToolbar.setNavigationOnClickListener(v -> mCircularRevealAnim.hide(mTitleTv, mRootView));
    }

    private void setToolbarView(@ColorRes int textColor, @ColorRes int backgroundColor, @DrawableRes int navigationIcon) {
        mTitleTv.setTextColor(ContextCompat.getColor(getContext(), textColor));
        mToolbar.setBackgroundColor(ContextCompat.getColor(getActivity(), backgroundColor));
        mToolbar.setNavigationIcon(ContextCompat.getDrawable(getContext(), navigationIcon));
    }
}
