package com.jiang.tvlauncher.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.jiang.tvlauncher.R;
import com.jiang.tvlauncher.utils.AnimUtils;

/**
 * @author: jiangadmin
 * @date: 2017/7/3.
 * @Email: www.fangmu@qq.com
 * @Phone: 186 6120 1018
 * TODO: 公共
 */

public class Base_Activity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void enlargeAnim(View v) {
        AnimUtils.S(v, 1, 1.2F);

    }

    public void reduceAnim(View v) {

        AnimUtils.S(v, 1.2F, 1);

    }

}
