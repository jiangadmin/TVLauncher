package com.jiang.tvlauncher.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.jiang.tvlauncher.R;
import com.jiang.tvlauncher.utils.LogUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by wwwfa
 * on 2017/8/22.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 图片展示
 * update：
 */
public class Image_Activity extends Base_Activity {
    private static final String TAG = "Image_Activity";

    ImageView imageView;

    String imageurl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        LogUtil.e(TAG,"图片展示");

        imageurl = getIntent().getStringExtra("url");

        imageView  = (ImageView) findViewById(R.id.imageView);

        ImageLoader.getInstance().displayImage(imageurl,imageView);

    }
}
