package com.jiang.tvlauncher.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.jiang.tvlauncher.MyAppliaction;
import com.jiang.tvlauncher.R;
import com.jiang.tvlauncher.dialog.Loading;
import com.jiang.tvlauncher.dialog.PwdDialog;
import com.jiang.tvlauncher.entity.FindChannelList;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.servlet.DownUtil;
import com.jiang.tvlauncher.servlet.FindChannelList_Servlet;
import com.jiang.tvlauncher.servlet.Update_Servlet;
import com.jiang.tvlauncher.utils.AnimUtils;
import com.jiang.tvlauncher.utils.ImageUtils;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.SaveUtils;
import com.jiang.tvlauncher.utils.Tools;
import com.jiang.tvlauncher.view.TitleView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  jiang
 * on 2017/7/3.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 主页
 * update：
 */
public class Home_Activity extends Base_Activity implements View.OnClickListener, View.OnFocusChangeListener {
    private static final String TAG = "Home_Activity";
    RelativeLayout toolbar_view;
    LinearLayout back;
    ImageView back_img;
    TextView back_txt;

    LinearLayout setting;
    ImageView setting_img;
    TextView setting_txt;

    TitleView titleview;

    RelativeLayout home1, home2, home3, home4;
    ImageView home1bg, home2bg, home3bg, home4bg;
    TextView name1, name2, name3, name4;

    TextView ver;

    List<TextView> namelist = new ArrayList();
    List<ImageView> homebglist = new ArrayList();
    List<RelativeLayout> homelist = new ArrayList();
    List<Integer> hometype = new ArrayList();

    boolean toolbar_show = false;

    static FindChannelList channelList;

    TimeCount timeCount;

    ImageView imageView;
    VideoView videoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_home);
        MyAppliaction.activity = this;
        initview();
        initeven();

        update();
    }

    public void update() {
//        Toast.makeText(Home_Activity.this, "开始获取主页数据", Toast.LENGTH_SHORT).show();
        new FindChannelList_Servlet(Home_Activity.this).execute();

        //获取更新
        new Update_Servlet(this).execute();
    }

    private void initview() {

        home1 = (RelativeLayout) findViewById(R.id.home_1);
        home2 = (RelativeLayout) findViewById(R.id.home_2);
        home3 = (RelativeLayout) findViewById(R.id.home_3);
        home4 = (RelativeLayout) findViewById(R.id.home_4);

        home1bg = (ImageView) findViewById(R.id.home_1_bg);
        home2bg = (ImageView) findViewById(R.id.home_2_bg);
        home3bg = (ImageView) findViewById(R.id.home_3_bg);
        home4bg = (ImageView) findViewById(R.id.home_4_bg);

        name1 = (TextView) findViewById(R.id.home_1_name);
        name2 = (TextView) findViewById(R.id.home_2_name);
        name3 = (TextView) findViewById(R.id.home_3_name);
        name4 = (TextView) findViewById(R.id.home_4_name);

        toolbar_view = (RelativeLayout) findViewById(R.id.toolbar_view);
        back = (LinearLayout) findViewById(R.id.back);
        back_img = (ImageView) findViewById(R.id.back_img);
        back_txt = (TextView) findViewById(R.id.back_txt);

        setting = (LinearLayout) findViewById(R.id.setting);
        setting_img = (ImageView) findViewById(R.id.setting_img);
        setting_txt = (TextView) findViewById(R.id.setting_txt);

        titleview = (TitleView) findViewById(R.id.titleview);

        ver = (TextView) findViewById(R.id.ver);
        ver.setText("V " + Tools.getVersionName(MyAppliaction.context));

        //获取屏幕宽度
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);

        LinearLayout.LayoutParams ll_home = (LinearLayout.LayoutParams) home1.getLayoutParams();

        ll_home.height = metric.heightPixels / 2;

        ll_home.width = metric.widthPixels / 5;

        home1.setLayoutParams(ll_home);
        home2.setLayoutParams(ll_home);
        home3.setLayoutParams(ll_home);
        home4.setLayoutParams(ll_home);

        homelist.add(home1);
        homelist.add(home2);
        homelist.add(home3);
        homelist.add(home4);

        homebglist.add(home1bg);
        homebglist.add(home2bg);
        homebglist.add(home3bg);
        homebglist.add(home4bg);

        namelist.add(name1);
        namelist.add(name2);
        namelist.add(name3);
        namelist.add(name4);

        timeCount = new TimeCount(5000, 1000);

        imageView = (ImageView) findViewById(R.id.image);
        videoView = (VideoView) findViewById(R.id.video);

        //如果有图片
        if (SaveUtils.getBoolean(Save_Key.NewImage)) {
            LogUtil.e(TAG, "有图片");
            imageView.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(SaveUtils.getString(Save_Key.NewImageUrl), imageView);
            ImageUtils.setimgage(ImageUtils.getBitmap(SaveUtils.getString(Save_Key.NewImageUrl)),"Welcome");
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
                    videoView.setVisibility(View.GONE);
                }
            });
            videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    videoView.setVisibility(View.GONE);
                    return false;
                }
            });
            videoView.start();

        }

    }

    private void initeven() {

        home1.setOnClickListener(this);
        home2.setOnClickListener(this);
        home3.setOnClickListener(this);
        home4.setOnClickListener(this);

        back.setOnClickListener(this);
        setting.setOnClickListener(this);

        home1.setOnFocusChangeListener(this);
        home2.setOnFocusChangeListener(this);
        home3.setOnFocusChangeListener(this);
        home4.setOnFocusChangeListener(this);

        back.setOnFocusChangeListener(this);
        setting.setOnFocusChangeListener(this);

        back.setVisibility(View.GONE);

    }

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_HOME:
                LogUtil.e(TAG, "点击了HOME键");
                return true;
            case KeyEvent.KEYCODE_BACK:
                return true;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (!toolbar_show) {
                    toolbar_view.setVisibility(View.VISIBLE);
                    AnimUtils.animupnum(this, toolbar_view, -42, 0);
                    AnimUtils.animupnum(this, titleview, 0, -42);
                    toolbar_show = true;
                }
                return false;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (toolbar_show) {
                    AnimUtils.animupnum(this, toolbar_view, 0, -42);
                    AnimUtils.animupnum(this, titleview, -42, 0);
                    toolbar_view.setVisibility(View.GONE);
                    toolbar_show = false;
                }
                return false;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                return false;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (toolbar_show) {
            AnimUtils.animupnum(this, toolbar_view, 0, -42);
            AnimUtils.animupnum(this, titleview, -42, 0);
            toolbar_view.setVisibility(View.GONE);
            toolbar_show = false;
        }

        //禁止调焦
        try {
            MyAppliaction.apiManager.set("setFocusOnOff", "false", null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestart() {
        //禁止调焦
        try {
            MyAppliaction.apiManager.set("setFocusOnOff", "false", null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onRestart();
    }

    /**
     * 更新页面
     */
    public void updateshow(FindChannelList channelList) {
        this.channelList = channelList;
        if (channelList != null) {
            for (int i = 0; i < channelList.getResult().size(); i++) {
                namelist.get(i).setText(channelList.getResult().get(i).getChannelName());
                ImageLoader.getInstance().displayImage(channelList.getResult().get(i).getBgUrl(), homebglist.get(i));
                hometype.add(channelList.getResult().get(i).getContentType());
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                new PwdDialog(this, R.style.MyDialog).show();
                break;
            case R.id.setting:
                LogUtil.e(TAG, "Password:" + SaveUtils.getString(Save_Key.Password));
                if (TextUtils.isEmpty(SaveUtils.getString(Save_Key.Password)))
                    Setting_Activity.start(this);
                else
                    new PwdDialog(this, R.style.MyDialog).show();
                break;
            case R.id.home_1:
                open(0);
                break;
            case R.id.home_2:
                open(1);
                break;
            case R.id.home_3:
                open(2);
                break;
            case R.id.home_4:
                open(3);
                break;
        }
    }

    public void open(int i) {
        //数据缺失的情况
        if (hometype.size() <= i) {
            Toast.makeText(this, "栏目未开通！", Toast.LENGTH_SHORT).show();
            return;
        }
        //数据正常的情况
        switch (hometype.get(i)) {
            //无操作
            case 0:
                Toast.makeText(this, "栏目未开通", Toast.LENGTH_SHORT).show();
                break;
            //启动指定APP
            case 1:
                if (channelList.getResult().get(i).getAppList() != null && channelList.getResult().get(i).getAppList().size() > 0)
                    if (Tools.isAppInstalled(channelList.getResult().get(i).getAppList().get(0).getPackageName()))
                        startActivity(new Intent(getPackageManager().getLaunchIntentForPackage(channelList.getResult().get(i).getAppList().get(0).getPackageName())));
                    else {
                        Loading.show(this, "请稍后");
                        new DownUtil(this).downLoadApk(channelList.getResult().get(i).getAppList().get(0).getDownloadUrl(), channelList.getResult().get(i).getAppList().get(0).getAppName() + ".apk");
                    }
                else
                    Toast.makeText(this, "栏目未开通", Toast.LENGTH_SHORT).show();
                break;
            //启动APP列表
            case 2:
                NewAPPList_Activity.start(this, channelList.getResult().get(i).getAppList());
                break;
            //启动展示图片
            case 3:
                startActivity(new Intent(this, Image_Activity.class).putExtra("url", channelList.getResult().get(i).getContentUrl()));
                break;
            //启动展示视频
            case 4:
                startActivity(new Intent(this, Video_Activity.class).putExtra("url", channelList.getResult().get(i).getContentUrl()));
                break;
        }
    }

    /**
     * 密码输入返回
     */
    public void PwdRe() {
        Setting_Activity.start(this);
    }

    @Override
    public void onFocusChange(View view, boolean b) {

        //得到焦点
        if (b) {
            if (view == setting) {
                setting_txt.setTextColor(getResources().getColor(R.color.white));
            }
            if (view == back) {
                back_txt.setTextColor(getResources().getColor(R.color.white));
            }
            enlargeAnim(view);
        } else {
            if (view == setting) {
                setting_txt.setTextColor(getResources().getColor(R.color.gray));
            }
            if (view == back) {
                back_txt.setTextColor(getResources().getColor(R.color.gray));
            }
            reduceAnim(view);
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

            imageView.setVisibility(View.GONE);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示

        }
    }
}
