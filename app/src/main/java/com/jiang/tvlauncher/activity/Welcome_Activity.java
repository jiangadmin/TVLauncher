package com.jiang.tvlauncher.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.jiang.tvlauncher.R;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.utils.ImageUtils;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.SaveUtils;
import com.jiang.tvlauncher.utils.Tools;

import java.io.File;

/**
 * Created by  jiang
 * on 2017/7/4.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 欢迎页
 * update：
 */
public class Welcome_Activity extends Base_Activity {
    private static final String TAG = "Welcome_Activity";

    ImageView imageView;
    VideoView videoView;

    TimeCount timeCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        timeCount = new TimeCount(3000, 1000);

        imageView = (ImageView) findViewById(R.id.image);
        videoView = (VideoView) findViewById(R.id.video);

        if (!Tools.ping()) {
            finish();

            return;
        }
        //如果有图片
        if (SaveUtils.getBoolean(Save_Key.NewImage)) {
            LogUtil.e(TAG, "有图片");
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(ImageUtils.getBitmap(Environment.getExternalStorageDirectory() + File.separator + "/welcomeImage.png"));
            timeCount.start();
        }

        //如果有视频
        else if (SaveUtils.getBoolean(Save_Key.NewVideo)) {
            LogUtil.e(TAG, "有视频 " + SaveUtils.getString(Save_Key.NewVideoUrl));
            videoView.setVisibility(View.VISIBLE);
            videoView.setZOrderOnTop(true);
            videoView.setVideoURI(Uri.parse(SaveUtils.getString(Save_Key.NewVideoUrl)));
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {

                    finish();
                }
            });
            videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    return false;
                }
            });
            videoView.start();

        }
        //如果都没有
        else {
            LogUtil.e(TAG, "退出");
            finish();
        }
    }

    /**
     * 计时器
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        //倒计时完成
        @Override
        public void onFinish() {

            finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示

        }
    }

    @Override
    public void onBackPressed() {

    }
}
