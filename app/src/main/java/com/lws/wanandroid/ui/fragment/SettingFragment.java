package com.lws.wanandroid.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lws.wanandroid.R;
import com.lws.wanandroid.app.Constants;
import com.lws.wanandroid.base.fragment.BaseFragment;
import com.lws.wanandroid.contract.SettingContract;
import com.lws.wanandroid.event.NightModeEvent;
import com.lws.wanandroid.presenter.SettingPresenter;
import com.lws.wanandroid.utils.ACache;
import com.lws.wanandroid.utils.RxBus;
import com.lws.wanandroid.utils.ShareUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingFragment extends BaseFragment<SettingPresenter>
        implements SettingContract.View, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.cb_setting_cache)
    AppCompatCheckBox mCbSettingCache;
    @BindView(R.id.cb_setting_image)
    AppCompatCheckBox mCbSettingImage;
    @BindView(R.id.cb_setting_night)
    AppCompatCheckBox mCbSettingNight;
    @BindView(R.id.ll_setting_feedback)
    TextView mLlSettingFeedback;
    @BindView(R.id.ll_setting_clear)
    LinearLayout mLlSettingClear;
    @BindView(R.id.tv_setting_clear)
    TextView mTvSettingClear;

    private File cacheFile;

    public static SettingFragment newInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initEventAndData() {
        cacheFile = new File(Constants.PATH_CACHE);
        mTvSettingClear.setText(ACache.getCacheSize(cacheFile));
        mCbSettingCache.setChecked(mPresenter.getAutoCacheState());
        mCbSettingImage.setChecked(mPresenter.getNoImageState());
        mCbSettingNight.setChecked(mPresenter.getNightModeState());
        mCbSettingCache.setOnCheckedChangeListener(this);
        mCbSettingImage.setOnCheckedChangeListener(this);
        mCbSettingNight.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_setting_night:
                mPresenter.setNoImageState(isChecked);
                RxBus.getDefault().post(new NightModeEvent(isChecked));
                break;
            case R.id.cb_setting_image:
                mPresenter.setNoImageState(isChecked);
                break;
            case R.id.cb_setting_cache:
                mPresenter.setAutoCacheState(isChecked);
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.ll_setting_feedback)
    void onFeedbackClicked(View view) {
        ShareUtil.sendEmail(_mActivity, getString(R.string.send_email));
    }

    @OnClick(R.id.ll_setting_clear)
    void onClearClicked(View view) {
        clearCache();
    }

    private void clearCache() {
        ACache.deleteDir(cacheFile);
        mTvSettingClear.setText(ACache.getCacheSize(cacheFile));
    }
}
