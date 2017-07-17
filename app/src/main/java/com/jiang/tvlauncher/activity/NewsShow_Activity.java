package com.jiang.tvlauncher.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.jiang.tvlauncher.R;
import com.jiang.tvlauncher.entity.Const;

/**
 * Created by  jiang
 * on 2017/7/15.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 最新推荐
 * update：
 */
public class NewsShow_Activity extends Base_Activity {
    private static final String TAG = "NewsShow_Activity";
    ImageView imageView;
    VideoView videoView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsshow);

        initview();
        initeven();
    }

    private void initview() {
        imageView  = (ImageView) findViewById(R.id.show_img);
        videoView = (VideoView) findViewById(R.id.show_video);
    }

    private void initeven() {
        switch (Const.ShowType){
            //图片
            case 1:
                imageView.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.GONE);
                break;
            //视频
            case 2:
                videoView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                break;
        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return super.onKeyDown(keyCode, event);
    }
}
