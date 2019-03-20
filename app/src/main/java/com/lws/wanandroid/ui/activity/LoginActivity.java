package com.lws.wanandroid.ui.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.jaeger.library.StatusBarUtil;
import com.jakewharton.rxbinding2.view.RxView;
import com.lws.wanandroid.R;
import com.lws.wanandroid.app.Constants;
import com.lws.wanandroid.base.activity.BaseActivity;
import com.lws.wanandroid.contract.LoginContract;
import com.lws.wanandroid.presenter.LoginPresenter;
import com.lws.wanandroid.utils.CommonUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.login_group)
    RelativeLayout mLoginGroup;
    @BindView(R.id.login_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.login_account_edit)
    EditText mAccountEdit;
    @BindView(R.id.login_password_edit)
    EditText mPasswordEdit;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    @BindView(R.id.login_register_btn)
    Button mRegisterBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initToolbar() {
        StatusBarUtil.setTransparent(this);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initEventAndData() {
        mPresenter.addRxBindingSubscribe(RxView.clicks(mLoginBtn)
                .throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
                .filter(o -> mPresenter != null)
                .subscribe(o -> mPresenter.getLoginData(
                        mAccountEdit.getText().toString().trim(), mPasswordEdit.getText().toString().trim())
                ));
    }

    @Override
    public void showLoginSuccess() {
        CommonUtils.showMessage(this, getString(R.string.login_success));
        onBackPressedSupport();
    }

    @OnClick(R.id.login_register_btn)
    void onClick(View v) {
        startRegisterPager();
    }

    private void startRegisterPager() {
        ActivityOptions options = ActivityOptions.makeScaleUpAnimation(mRegisterBtn,
                mRegisterBtn.getWidth() / 2,
                mRegisterBtn.getHeight() / 2,
                0,
                0);
        startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
    }
}
