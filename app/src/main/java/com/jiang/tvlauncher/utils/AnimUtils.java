package com.jiang.tvlauncher.utils;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by 垚垚
 * on 15/6/24.
 * Email: jiangyaoyao@chinarb.cn
 * Phone：18605296932
 * Purpose: TODO 动画类
 */
public class AnimUtils {

    //左右摇晃
    public static void animyaohuang(View v) {
        ObjectAnimator animator0 = ObjectAnimator.ofFloat(v, "translationX", 0, 20);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(v, "translationX", 20, -20);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(v, "translationX", -20, 20);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(v, "translationX", 20, -20);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(v, "translationX", -20, 20);
        ObjectAnimator animator5 = ObjectAnimator.ofFloat(v, "translationX", 20, -20);
        ObjectAnimator animator6 = ObjectAnimator.ofFloat(v, "translationX", -20, 20);
        ObjectAnimator animator7 = ObjectAnimator.ofFloat(v, "translationX", 20, -20);
        ObjectAnimator animator8 = ObjectAnimator.ofFloat(v, "translationX", -20, 20);
        ObjectAnimator animator9 = ObjectAnimator.ofFloat(v, "translationX", 20, 0);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator0, animator1, animator2, animator3, animator4, animator5, animator6, animator7, animator8, animator9);
        set.setDuration(30);
        set.start();
    }

    //旋转
    public static void animxuanzhuan(View v) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "rotation", 0, 1440);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator);
        set.setDuration(1600);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }

    //头像旋转
    public static void animxuanzhuan90(View v) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "rotation", 0, 90);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator);
        set.setDuration(1);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }

    //头像旋转还原
    public static void animxuanzhuan270(View v) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "rotation", 90, 0);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator);
        set.setDuration(1);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }

    //向上
    public static void animxiangshang(View v) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationY", 1920, 0);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator);
        set.setDuration(120);
        set.setInterpolator(new AccelerateInterpolator());
        set.start();
    }

    //向下
    public static void animxiangxia(View v) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationY", 0, 1920);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator);
        set.setDuration(120);
        set.setInterpolator(new AccelerateInterpolator());
        set.start();
    }

    //向左
    public static void animxiangzuo(View v) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationX", 1080, 0);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator);
        set.setDuration(200);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }

    //向右
    public static void animxiangyou(View v) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationX", 0, 1080);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator);
        set.setDuration(200);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }

    //向右一点点
    public static void animxiangyouynum(View v, int num) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationX", 0, num);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator);
        set.setDuration(200);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }

    /**
     * 控制控件上下移动
     * @param v 控件
     * @param from 开始位置
     * @param to 结束位置
     */
    public static void animupnum(Context context, View v, int from, int to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationY", Tools.dp2px(context, from), Tools.dp2px(context, to));
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator);
        set.setDuration(200);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }
}
