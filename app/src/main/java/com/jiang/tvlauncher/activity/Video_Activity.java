package com.jiang.tvlauncher.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.VideoView;

import com.jiang.tvlauncher.R;
import com.jiang.tvlauncher.utils.LogUtil;

/**
 * Created by wwwfa
 * on 2017/8/22.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 视频播放
 * update：
 */
public class Video_Activity extends Base_Activity {
    private static final String TAG = "Video_Activity";

    String videourl;
    VideoView videoView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        LogUtil.e(TAG,"视频播放");

        videourl = getIntent().getStringExtra("url");

        videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setZOrderOnTop(true);
        videoView.setVideoURI(Uri.parse(videourl));
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });
        videoView.start();
    }
}
