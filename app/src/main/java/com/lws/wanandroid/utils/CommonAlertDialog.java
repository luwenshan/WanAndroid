package com.lws.wanandroid.utils;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.lws.wanandroid.R;

public class CommonAlertDialog {
    private AlertDialog mAlertDialog;

    private static class CommonAlertDialogHolder {
        private static final CommonAlertDialog INSTANCE = new CommonAlertDialog();
    }

    public static CommonAlertDialog newInstance() {
        return CommonAlertDialogHolder.INSTANCE;
    }

    public void cancelDialog(boolean isAdd) {
        if (isAdd && mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
    }

    public void showDialog(Activity activity, String content, String btnContent) {
        if (activity == null) {
            return;
        }
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(activity, R.style.myCorDialog).create();
        }
        if (!mAlertDialog.isShowing()) {
            mAlertDialog.show();
        }
        mAlertDialog.setCanceledOnTouchOutside(false);
        Window window = mAlertDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.common_alert_dialog);
            TextView tvContent = window.findViewById(R.id.dialog_content);
            tvContent.setText(content);
            Button btnOk = window.findViewById(R.id.dialog_btn);
            btnOk.setText(btnContent);
            btnOk.setOnClickListener(v -> {
                if (mAlertDialog != null) {
                    mAlertDialog.cancel();
                    mAlertDialog = null;
                }
            });
            View divider = window.findViewById(R.id.dialog_btn_divider);
            divider.setVisibility(View.GONE);
            Button btnNeg = window.findViewById(R.id.dialog_negative_btn);
            btnNeg.setVisibility(View.GONE);
        }
    }

    public void showDialog(Activity activity, String content, String btnContent, View.OnClickListener onClickListener) {
        if (activity == null) {
            return;
        }
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(activity, R.style.myCorDialog).create();
        }
        if (!mAlertDialog.isShowing()) {
            mAlertDialog.show();
        }
        mAlertDialog.setCanceledOnTouchOutside(false);
        Window window = mAlertDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.common_alert_dialog);
            TextView tvContent = window.findViewById(R.id.dialog_content);
            tvContent.setText(content);
            Button btnOk = window.findViewById(R.id.dialog_btn);
            btnOk.setText(btnContent);
            btnOk.setOnClickListener(onClickListener);
            View divider = window.findViewById(R.id.dialog_btn_divider);
            divider.setVisibility(View.GONE);
            Button btnNeg = window.findViewById(R.id.dialog_negative_btn);
            btnNeg.setVisibility(View.GONE);
        }
    }

    public void showDialog(Activity mActivity, String content, String btnContent, String neContent,
                           final View.OnClickListener onPoClickListener,
                           final View.OnClickListener onNeClickListener) {
        if (mActivity == null) {
            return;
        }
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(mActivity, R.style.myCorDialog).create();
        }
        if (!mAlertDialog.isShowing()) {
            mAlertDialog.show();
        }
        mAlertDialog.setCanceledOnTouchOutside(false);
        Window window = mAlertDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.common_alert_dialog);
            TextView contentTv = window.findViewById(R.id.dialog_content);
            contentTv.setText(content);
            Button mOkBtn = window.findViewById(R.id.dialog_btn);
            mOkBtn.setText(btnContent);
            mOkBtn.setOnClickListener(onPoClickListener);
            View btnDivider = window.findViewById(R.id.dialog_btn_divider);
            btnDivider.setVisibility(View.VISIBLE);
            Button mNeBtn = window.findViewById(R.id.dialog_negative_btn);
            mNeBtn.setText(neContent);
            mNeBtn.setVisibility(View.VISIBLE);
            mNeBtn.setOnClickListener(onNeClickListener);
        }
    }
}
