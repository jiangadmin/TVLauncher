package com.jiang.tvlauncher.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiang.tvlauncher.R;
import com.jiang.tvlauncher.dialog.PwdDialog;
import com.jiang.tvlauncher.entity.FindChannelList;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.servlet.FindChannelList_Servlet;
import com.jiang.tvlauncher.utils.AnimUtils;
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
    ImageView icon1, icon2, icon3, icon4;
    TextView name1, name2, name3, name4;


    List<TextView> namelist = new ArrayList();
    List<ImageView> homebglist = new ArrayList();
    List<RelativeLayout> homelist = new ArrayList();
    List<Integer> hometype = new ArrayList();

    boolean toolbar_show = false;

    static FindChannelList channelList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_home);
        initview();
        initeven();

        startActivity(new Intent(this,Welcome_Activity.class));
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

        icon1 = (ImageView) findViewById(R.id.home_1_icon);
        icon2 = (ImageView) findViewById(R.id.home_2_icon);
        icon3 = (ImageView) findViewById(R.id.home_3_icon);
        icon4 = (ImageView) findViewById(R.id.home_4_icon);

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

        //获取屏幕宽度
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);

        LinearLayout.LayoutParams ll_home = (LinearLayout.LayoutParams) home1.getLayoutParams();

        ll_home.height = metric.heightPixels / 2;

        ll_home.width = metric.widthPixels / 6;

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

        new FindChannelList_Servlet(this).execute();
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
                if (channelList.getResult().get(i).getContentType()==0)
                    homelist.get(i).setVisibility(View.GONE);
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
                LogUtil.e(TAG,"Password:"+SaveUtils.getString(Save_Key.Password));
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
        switch (hometype.get(i)) {
            //无操作
            case 0:
                break;
            //启动指定APP
            case 1:
                if (Tools.isAppInstalled(this, channelList.getResult().get(i).getAppList().get(0).getPackageName()))
                    startActivity(new Intent(getPackageManager().getLaunchIntentForPackage(channelList.getResult().get(i).getAppList().get(0).getPackageName())));
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("资源缺失是，请联系服务人员！");
                    builder.setPositiveButton("好的", null);
                    builder.show();
                }
                break;
            //启动指定的APP列表
            case 2:
                String packagename = "";
                if (channelList.getResult().get(i).getAppList() != null) {
                    for (int j = 0; j < channelList.getResult().get(i).getAppList().size(); j++) {
                        packagename += channelList.getResult().get(i).getAppList().get(j).getPackageName()+"/";
                    }
                    Intent intent = new Intent();
                    intent.setClass(this, APPList_Activity.class);
                    intent.putExtra("packagename", packagename);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("资源缺失是，请联系服务人员！");
                    builder.setPositiveButton("好的", null);
                    builder.show();
                }
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
}
