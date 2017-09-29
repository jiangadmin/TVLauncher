package com.jiang.tvlauncher.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jiang.tvlauncher.R;

/**
 * Created by  jiang
 * on 2017/8/29.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 网络异常
 * update：
 */
public class NetWarningDialog extends Dialog {
    Activity activity;

    public NetWarningDialog(@NonNull Activity activity) {
        super(activity, R.style.MyDialog);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_netwarning);
    }

    public void esc(){
        dismiss();
    }
}
