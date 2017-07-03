package com.jiang.tvlauncher.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiang.tvlauncher.R;

/**
 * Created by  jiang
 * on 2017/6/12.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 等待框
 * update：
 */
public class Loading {

    private static LoadingDialog progressDialog;

    public static void show(Context context, String message) {
        Activity activity = (Activity) context;
        if (activity != null) {
            if (!activity.isFinishing()) {

                while (activity.getParent() != null) {
                    activity = activity.getParent();
                }
                if (progressDialog == null) {
                    progressDialog = LoadingDialog.create(activity, message);
                }
                progressDialog.setCancelable(true);
                progressDialog.show();
            }
        }
    }


    public static void showmessage(Context context, String message) {
        Activity activity = (Activity) context;
        if (activity != null) {
            if (!activity.isFinishing()) {

                while (activity.getParent() != null) {
                    activity = activity.getParent();
                }
                if (progressDialog == null) {
                    progressDialog = LoadingDialog.create(activity, message);
                }
                progressDialog.setCancelable(true);
                progressDialog.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        dismiss();
                    }
                }).run();
            }
        }
    }


    public static void dismiss() {
        try {
            if (null != progressDialog) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
        }

    }


    public static class LoadingDialog extends Dialog {
        public LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
            super(context, themeResId);
        }

        public LoadingDialog(@NonNull Context context) {
            super(context);
        }

        /**
         * Create the custom dialog
         */
        public static LoadingDialog create(Context context, String message) {
            if (TextUtils.isEmpty(message))
                message = "加载中...";
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.dialog_loading, null);
            TextView txtInfo = (TextView) layout.findViewById(R.id.txt_info);
            txtInfo.setText(message);

            LoadingDialog dialog = new LoadingDialog(context, R.style.LoadingDialog);
            dialog.setCanceledOnTouchOutside(false);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dialog.setContentView(layout);
            return dialog;
        }
    }
}
