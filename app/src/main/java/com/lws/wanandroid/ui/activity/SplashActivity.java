package com.lws.wanandroid.ui.activity;

import android.content.Intent;

import com.airbnb.lottie.LottieAnimationView;
import com.lws.wanandroid.R;
import com.lws.wanandroid.app.WanAndroidApp;
import com.lws.wanandroid.base.activity.BaseActivity;
import com.lws.wanandroid.contract.SplashContract;
import com.lws.wanandroid.presenter.SplashPresenter;
import com.lws.wanandroid.utils.StatusBarUtil;

import butterknife.BindView;

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {
    @BindView(R.id.one_animation)
    LottieAnimationView oneAnimation;
    @BindView(R.id.two_animation)
    LottieAnimationView twoAnimation;
    @BindView(R.id.three_animation)
    LottieAnimationView threeAnimation;
    @BindView(R.id.four_animation)
    LottieAnimationView fourAnimation;
    @BindView(R.id.five_animation)
    LottieAnimationView fiveAnimation;
    @BindView(R.id.six_animation)
    LottieAnimationView sixAnimation;
    @BindView(R.id.seven_animation)
    LottieAnimationView sevenAnimation;
    @BindView(R.id.eight_animation)
    LottieAnimationView eightAnimation;
    @BindView(R.id.nine_animation)
    LottieAnimationView nineAnimation;
    @BindView(R.id.ten_animation)
    LottieAnimationView tenAnimation;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initToolbar() {
        if (!WanAndroidApp.isFirstRun) {
            jumpToMain();
            return;
        }
        WanAndroidApp.isFirstRun = false;
        StatusBarUtil.immersive(this);
    }

    @Override
    protected void initEventAndData() {
        startAnimation(oneAnimation, "W.json");
        startAnimation(twoAnimation, "A.json");
        startAnimation(threeAnimation, "N.json");
        startAnimation(fourAnimation, "A.json");
        startAnimation(fiveAnimation, "N.json");
        startAnimation(sixAnimation, "D.json");
        startAnimation(sevenAnimation, "R.json");
        startAnimation(eightAnimation, "I.json");
        startAnimation(nineAnimation, "O.json");
        startAnimation(tenAnimation, "D.json");
    }

    @Override
    public void jumpToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onDestroy() {
        cancelAnimation(oneAnimation);
        cancelAnimation(twoAnimation);
        cancelAnimation(threeAnimation);
        cancelAnimation(fourAnimation);
        cancelAnimation(fiveAnimation);
        cancelAnimation(sixAnimation);
        cancelAnimation(sevenAnimation);
        cancelAnimation(eightAnimation);
        cancelAnimation(nineAnimation);
        cancelAnimation(tenAnimation);
        super.onDestroy();
    }

    private void startAnimation(LottieAnimationView view, String animationName) {
        view.setAnimation(animationName);
        view.playAnimation();
    }

    private void cancelAnimation(LottieAnimationView view) {
        if (view != null) {
            view.cancelAnimation();
        }
    }
}
