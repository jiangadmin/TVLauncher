package com.jiang.tvlauncher.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.jiang.tvlauncher.MyApp;
import com.jiang.tvlauncher.R;
import com.jiang.tvlauncher.dialog.Loading;
import com.jiang.tvlauncher.dialog.NetDialog;
import com.jiang.tvlauncher.dialog.PwdDialog;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.entity.FindChannelList_Model;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.entity.Theme_Model;
import com.jiang.tvlauncher.servlet.DownUtil;
import com.jiang.tvlauncher.servlet.FindChannelList_Servlet;
import com.jiang.tvlauncher.servlet.GetVIP_Servlet;
import com.jiang.tvlauncher.servlet.Get_Theme_Servlet;
import com.jiang.tvlauncher.utils.AnimUtils;
import com.jiang.tvlauncher.utils.FileUtils;
import com.jiang.tvlauncher.utils.ImageUtils;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.SaveUtils;
import com.jiang.tvlauncher.utils.ShellUtils;
import com.jiang.tvlauncher.utils.Tools;
import com.jiang.tvlauncher.view.TitleView;
import com.lgeek.tv.jimi.LgeekTVSdkMrg;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangadmin
 * date: 2018/10/12.
 * Email: www.fangmu@qq.com
 * Phone: 186 6120 1018
 * TODO: 新主页
 */

public class LauncherActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private static final String TAG = "LauncherActivity";
    RelativeLayout toolbar_view;
    LinearLayout back;
    ImageView main_bg, main_bg_0, back_img;
    TextView back_txt, title_0, title, title_2;

    LinearLayout setting;
    ImageView bg, setting_img, title_icon;
    TextView setting_txt;

    LinearLayout wifiAp, title_view;
    TextView wifiAp_txt;

    TitleView titleview;

    ImageView home1, home2, home3, home4;
    TextView name1, name2, name3, name4;

    TextView ver;

    List<ImageView> homeList = new ArrayList<>();
    List<TextView> nameList = new ArrayList<>();
    List<Integer> homeType = new ArrayList<>();

    boolean toolbar_show = false;
    boolean ifnet = false;//判断有无网络使用

    static FindChannelList_Model channelList;

    TimeCount timeCount;
    TitleTime titleTime;

    ImageView imageView;
    VideoView videoView;

    WarningDialog warningDialog = null;

    int i = 0;
    String[] title_list;

    /**
     * 存储位置
     */
    String file = Environment.getExternalStorageDirectory().getPath() + "/feekr/Download/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        setContentView(R.layout.activty_launcher);
        MyApp.activity = this;

        initView();
        initEven();

        //判断网络
        if (!Tools.isNetworkConnected())
            NetDialog.showL();

//        onMessage("update");

        //首先显示本地资源
        if (!TextUtils.isEmpty(SaveUtils.getString(Save_Key.Channe))) {
            onMessage(new Gson().fromJson(SaveUtils.getString(Save_Key.Channe), FindChannelList_Model.class));
        }

        //首先显示本地资源
        if (!TextUtils.isEmpty(SaveUtils.getString(Save_Key.Theme))) {
            onMessage(new Gson().fromJson(SaveUtils.getString(Save_Key.Theme), Theme_Model.class));
        }
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    /**
     * 设置颜色
     *
     * @param color
     */
    private void title_color(String color) {

        title_0.setBackground(new BitmapDrawable(getResources(), ImageUtils.tintBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.kuang_0), Color.parseColor(color))));
        title.setBackground(new BitmapDrawable(getResources(), ImageUtils.tintBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.kuang_1), Color.parseColor(color))));
        title_2.setBackground(new BitmapDrawable(getResources(), ImageUtils.tintBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.kuang_2), Color.parseColor(color))));
        title_icon.setBackground(new BitmapDrawable(getResources(), ImageUtils.tintBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.round), Color.parseColor(color))));

        name1.setTextColor(Color.parseColor(color));
        name2.setTextColor(Color.parseColor(color));
        name3.setTextColor(Color.parseColor(color));
        name4.setTextColor(Color.parseColor(color));

    }

    @Subscribe
    public void onMessage(String shoWarn) {
        switch (shoWarn) {
            case "0":
                if (warningDialog == null) {
                    warningDialog = new WarningDialog(this);
                }
                warningDialog.show();
                break;
            case "1":
                if (warningDialog != null) {
                    warningDialog.dismiss();
                }
                break;

            case "update":
                //检查更新
//                new Update_Servlet(this).execute();

                new FindChannelList_Servlet().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                //获取主题
                new Get_Theme_Servlet().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                break;
            default:
                break;
        }
    }

    private void initView() {

        main_bg = findViewById(R.id.main_bg);
        main_bg_0 = findViewById(R.id.main_bg_0);
        title_view = findViewById(R.id.title_view);
        title_icon = findViewById(R.id.title_icon);
        title_0 = findViewById(R.id.title_0);
        title = findViewById(R.id.title);
        title_2 = findViewById(R.id.title_2);

        home1 = findViewById(R.id.home_1);
        home2 = findViewById(R.id.home_2);
        home3 = findViewById(R.id.home_3);
        home4 = findViewById(R.id.home_4);

        name1 = findViewById(R.id.home_1_name);
        name2 = findViewById(R.id.home_2_name);
        name3 = findViewById(R.id.home_3_name);
        name4 = findViewById(R.id.home_4_name);

        toolbar_view = findViewById(R.id.toolbar_view);
        back = findViewById(R.id.back);
        wifiAp = findViewById(R.id.wifiap);
        wifiAp_txt = findViewById(R.id.wifiap_txt);
        back_img = findViewById(R.id.back_img);
        back_txt = findViewById(R.id.back_txt);

        setting = findViewById(R.id.setting);
        setting_img = findViewById(R.id.setting_img);
        setting_txt = findViewById(R.id.setting_txt);

        titleview = findViewById(R.id.titleview);

        ver = findViewById(R.id.ver);
        ver.setText(String.format("V %s", Tools.getVersionName(MyApp.context)));

        homeList.add(home1);
        homeList.add(home2);
        homeList.add(home3);
        homeList.add(home4);

        nameList.add(name1);
        nameList.add(name2);
        nameList.add(name3);
        nameList.add(name4);

        imageView = findViewById(R.id.image);
        videoView = findViewById(R.id.video);

        //如果有图片
        if (SaveUtils.getBoolean(Save_Key.NewImage)) {
            LogUtil.e(TAG, "有图片");
            imageView.setVisibility(View.VISIBLE);
            Glide.with(this).load(SaveUtils.getString(Save_Key.NewImageUrl)).into(imageView);
            timeCount = new TimeCount(5000, 1000);
            timeCount.start();
        }

        //如果有视频
        else if (SaveUtils.getBoolean(Save_Key.NewVideo)) {
            LogUtil.e(TAG, "有视频 " + SaveUtils.getString(Save_Key.NewVideoUrl));
            videoView.setVisibility(View.VISIBLE);
            videoView.setZOrderOnTop(true);
            videoView.setVideoURI(Uri.parse(SaveUtils.getString(Save_Key.NewVideoUrl)));
            videoView.setOnCompletionListener(mediaPlayer -> videoView.setVisibility(View.GONE));
            videoView.setOnErrorListener((mediaPlayer, i, i1) -> {
                videoView.setVisibility(View.GONE);
                return false;
            });
            videoView.start();
        }
    }

    private void initEven() {

        home1.setOnClickListener(this);
        home2.setOnClickListener(this);
        home3.setOnClickListener(this);
        home4.setOnClickListener(this);

        back.setOnClickListener(this);
        wifiAp.setOnClickListener(this);
        setting.setOnClickListener(this);

        home1.setOnFocusChangeListener(this);
        home2.setOnFocusChangeListener(this);
        home3.setOnFocusChangeListener(this);
        home4.setOnFocusChangeListener(this);

        back.setOnFocusChangeListener(this);
        wifiAp.setOnFocusChangeListener(this);
        setting.setOnFocusChangeListener(this);

        back.setVisibility(View.GONE);

        //切换焦点给第一个
        home1.setFocusable(true);
        home1.setFocusableInTouchMode(true);
        home1.requestFocus();
    }

    @Override
    public void onBackPressed() {

    }

    boolean showToast = true;
    long[] mHits = new long[7];


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);// 数组向左移位操作
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();
                if (mHits[0] >= (SystemClock.uptimeMillis() - 5000)) {
                    LogUtil.e(TAG, "Password:" + SaveUtils.getString(Save_Key.Password));
                    if (TextUtils.isEmpty(SaveUtils.getString(Save_Key.Password))) {
                        SettingActivity.start(this);
                    } else {
                        new PwdDialog(this, R.style.MyDialog).show();
                    }
                } else {
                    showToast = true;
                }
                return true;
            case KeyEvent.KEYCODE_BACK:
                return true;
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:

                return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (toolbar_show) {
            AnimUtils.Y(toolbar_view, 0, -42);
            AnimUtils.Y(titleview, -42, 0);
            toolbar_view.setVisibility(View.GONE);
            toolbar_show = false;
        }
    }

    /**
     * 主题返回 网络正常情况下
     *
     * @param model 数据
     */
    @Subscribe
    public void onMessage(Theme_Model model) {
        Theme_Model.ResultBean bean = model.getResult();
        if (bean != null) {

            //图片名
            String imgName = Tools.getFileNameWithSuffix(bean.getBgImg());
            //判断图片文件是否存在
            if (!FileUtils.checkFileExists(imgName)) {
                //下载图片
                new DownUtil().downLoad(bean.getBgImg(), imgName, false);

                //记录图片名
                SaveUtils.setString(Save_Key.BackGround, imgName);
            }

            //赋值背景 前景显示
            try {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), ImageUtils.getBitmap(new File(file + SaveUtils.getString(Save_Key.BackGround))));
                RequestOptions builder = new RequestOptions();
                builder.placeholder(bitmapDrawable);
                builder.error(bitmapDrawable);
                Glide.with(this).load(bean.getBgImg()).apply(builder).into(main_bg);
            } catch (Exception e) {
                Glide.with(this).load(bean.getBgImg()).into(main_bg);
            }

            //设置图标背景色 对话框颜色
            title_color(bean.getMicLogoColor());
            SaveUtils.setString(Save_Key.MicLogoColor, bean.getMicLogoColor());

            //设置对话框内容颜色
            title.setTextColor(Color.parseColor(bean.getTipFontColor()));
            SaveUtils.setString(Save_Key.TipFontColor, bean.getTipFontColor());

            //设置时间颜色
            titleview.setTimeColor(bean.getTimesCtrlColor());

            //标题集合
            title_list = null;
            title_list = bean.getTipContents().split("#");
            SaveUtils.setString(Save_Key.TipContents, bean.getTipContents());

            //是否显示标题
            title_view.setVisibility(bean.getTipShowFlag() == 1 ? View.VISIBLE : View.GONE);
            SaveUtils.setInt(Save_Key.TipShowFlag, bean.getTipShowFlag());

            //是否显示控制台
            setting.setVisibility(bean.getConsoleShowFlag() == 1 ? View.VISIBLE : View.GONE);
            SaveUtils.setInt(Save_Key.ConsoleShowFlag, bean.getConsoleShowFlag());

            //是否初始化逻辑科技
            if (bean.getStartLgeekFlag() == 1) {
                LgeekTVSdkMrg.getInstance().init(MyApp.context);
            }
            SaveUtils.setInt(Save_Key.StartLgeekFlag, bean.getStartLgeekFlag());

            //是否显示栏目名
            name1.setVisibility(bean.getCnameShowFlag() == 1 ? View.VISIBLE : View.GONE);
            name2.setVisibility(bean.getCnameShowFlag() == 1 ? View.VISIBLE : View.GONE);
            name3.setVisibility(bean.getCnameShowFlag() == 1 ? View.VISIBLE : View.GONE);
            name4.setVisibility(bean.getCnameShowFlag() == 1 ? View.VISIBLE : View.GONE);
            SaveUtils.setInt(Save_Key.CnameShowFlag, bean.getCnameShowFlag());

            //是否启动逻辑科技服务

            //标题轮询时间
            int title_time = bean.getTipSwitchRate();
            SaveUtils.setInt(Save_Key.TipSwitchRate, bean.getTipSwitchRate());
            if (title_list != null && title_list.length > 0) {
                title.setText(title_list[0]);
            }

            //倒计时
            if (title_list != null && title_list.length > 1) {
                if (titleTime != null)
                    titleTime.cancel();
                titleTime = null;

                titleTime = new TitleTime(title_time, title_time);
                titleTime.start();

            }
        }
    }

    /**
     * 更新页面
     */
    @Subscribe
    public void onMessage(FindChannelList_Model channelList) {
        LauncherActivity.channelList = channelList;

        //更改开机动画
        if (!TextUtils.isEmpty(SaveUtils.getString(Save_Key.BootAn))) {
            //判断文件是否存在
            LogUtil.e(TAG, "开始下载");
            new DownUtil().downLoad(SaveUtils.getString(Save_Key.BootAn),
                    Tools.getFileNameWithSuffix(SaveUtils.getString(Save_Key.BootAn)), false);
        }

        if (channelList != null) {
            for (int i = 0; i < channelList.getResult().size(); i++) {

                //限制最大个数
                if (i > 3)
                    return;
                //图片网络地址
                String url = channelList.getResult().get(i).getBgUrl();
                //图片文件名
                String filename = Tools.getFileNameWithSuffix(channelList.getResult().get(i).getBgUrl());
                //设置栏目名称
                nameList.get(i).setText(channelList.getResult().get(i).getChannelName());
                try {
                    //加载图片 优先本地
                    RequestOptions options = new RequestOptions();
                    options.placeholder(new BitmapDrawable(getResources(), ImageUtils.getBitmap(new File(file + SaveUtils.getString(Save_Key.ItemImage + i)))));
                    options.error(new BitmapDrawable(getResources(), ImageUtils.getBitmap(new File(file + SaveUtils.getString(Save_Key.ItemImage + i)))));
                    Glide.with(this).load(url).apply(options).into(homeList.get(i));
                } catch (Exception e) {
                    Glide.with(this).load(url).into(homeList.get(i));
                }

                homeType.add(channelList.getResult().get(i).getContentType());

                //判断文件是否存在
                if (filename != null && !FileUtils.checkFileExists(filename)) {
                    //下载图片
                    new DownUtil().downLoad(url, filename, false);

                    //记录文件名
                    SaveUtils.setString(Save_Key.ItemImage + i, filename);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {

        //判断网络
        if (!Tools.isNetworkConnected() && view.getId() != R.id.setting) {
            NetDialog.showW(this);
            return;
        }

        //账户（信号源）判断
        if (Const.BussFlag == 0) {
            if (warningDialog == null) {
                warningDialog = new WarningDialog(this);
            }
            warningDialog.show();
            return;
        }

        switch (view.getId()) {
            case R.id.back:
                new PwdDialog(this, R.style.MyDialog).show();
                break;
            case R.id.setting:
                LogUtil.e(TAG, "Password:" + SaveUtils.getString(Save_Key.Password));
                if (TextUtils.isEmpty(SaveUtils.getString(Save_Key.Password))) {
                    SettingActivity.start(this);
                } else {
                    new PwdDialog(this, R.style.MyDialog).show();
                }
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

    /**
     * 启动栏目
     */
    public void open(int i) {
        switch (homeType.get(i)) {
            //无操作
            case 0:
                Toast.makeText(this, "栏目未开通", Toast.LENGTH_SHORT).show();
                break;
            //启动指定APP
            case 1:

                if (channelList.getResult().get(i).getAppList() != null && channelList.getResult().get(i).getAppList().size() > 0) {
                    String packname = channelList.getResult().get(i).getAppList().get(0).getPackageName();

                    //如果要启动定制版腾讯视频
                    if (packname.equals(Const.TvViedo)) {
                        SaveUtils.setString(Const.TvViedoDow, channelList.getResult().get(i).getAppList().get(0).getDownloadUrl());
                        Const.云视听Url = channelList.getResult().get(i).getAppList().get(0).getDownloadUrlBak();
                    }

                    //验证是否有此应用
                    if (Tools.isAppInstalled(packname)) {
                        //如果要启动定制版腾讯视频
                        if (packname.equals(Const.TvViedo)) {

                            //判断时候已经运行
                            if (!TextUtils.isEmpty(ShellUtils.execCommand("ps |grep com.ktcp.tvvideo:webview", false).successMsg)) {
                                startActivity(new Intent(getPackageManager().getLaunchIntentForPackage(packname)));
                            } else {
                                Loading.show(this, "请稍后");
                                //获取VIP账号
                                new GetVIP_Servlet(true).execute();
                            }
                        } else {
                            startActivity(new Intent(getPackageManager().getLaunchIntentForPackage(packname)));
                        }
                    } else {

                        Loading.show(this, "请稍后");
                        new DownUtil().downLoad(channelList.getResult().get(i).getAppList().get(0).getDownloadUrl(), channelList.getResult().get(i).getAppList().get(0).getAppName() + ".apk", true);
                    }
                } else
                    Toast.makeText(this, "栏目未开通", Toast.LENGTH_SHORT).show();
                break;
            //启动APP列表
            case 2:
                NewAPPListActivity.start(this, channelList.getResult().get(i).getAppList());
                break;
            //启动展示图片
            case 3:
                ImageActivity.start(this, channelList.getResult().get(i).getContentUrl());
                break;
            //启动展示视频
            case 4:
                VideoActivity.start(this, channelList.getResult().get(i).getContentUrl());
                break;
        }
    }

    /**
     * 密码输入返回
     */
    public void PwdRe() {
        SettingActivity.start(this);
    }

    /**
     * 焦点变化
     */
    @Override
    public void onFocusChange(View view, boolean b) {

        switch (view.getId()) {
            case R.id.setting:
                setting_txt.setTextColor(getResources().getColor(b ? R.color.white : R.color.gray));
                break;
            case R.id.back:
                back_txt.setTextColor(getResources().getColor(b ? R.color.white : R.color.gray));
                break;
            case R.id.wifiap:
                wifiAp_txt.setTextColor(getResources().getColor(b ? R.color.white : R.color.gray));
                break;
            default:
                if (b)
                    enlargeAnim(view);
                else
                    reduceAnim(view);
                break;
        }
    }

    /**
     * 计时器
     */
    class TimeCount extends CountDownTimer {
        TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        //倒计时完成
        @Override
        public void onFinish() {
            //如果有图片
            if (SaveUtils.getBoolean(Save_Key.NewImage))
                imageView.setVisibility(View.GONE);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示

        }
    }

    /**
     * 标题定时轮询
     */
    class TitleTime extends CountDownTimer {

        TitleTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture * 1000, countDownInterval * 1000);
        }

        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            title.setText(title_list[i]);
            if (i == title_list.length - 1) {
                i = 0;
            } else {
                i++;
            }
            start();
        }
    }

    /**
     * 警告框
     */
    public static class WarningDialog extends Dialog {
        WarningDialog(@NonNull Context context) {
            super(context, R.style.MyDialog);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_warning);
            setCanceledOnTouchOutside(false);
            setCancelable(false);
        }
    }
}
