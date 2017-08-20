package com.jiang.tvlauncher.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiang.tvlauncher.MyAppliaction;
import com.jiang.tvlauncher.R;
import com.jiang.tvlauncher.dialog.PwdDialog;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.servlet.FindChannelList_Servlet;
import com.jiang.tvlauncher.servlet.Timing_Servlet;
import com.jiang.tvlauncher.utils.AnimUtils;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.Tools;
import com.jiang.tvlauncher.view.TitleView;

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
    ImageView home1bg,home2bg,home3bg,home4bg;
    ImageView icon1,icon2,icon3,icon4;
    TextView name1,name2,name3,name4;

    boolean toolbar_show = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_home);
        initview();
        initeven();
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

        new FindChannelList_Servlet().execute();

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                new PwdDialog(this, R.style.MyDialog).show();
                break;
            case R.id.setting:
                startActivity(new Intent(this, Setting_Activity.class));
                break;
            case R.id.home_1:
//                startActivity(new Intent(this,APPList_Activity.class));

//                if (Tools.isAppInstalled(this, MyAppliaction.channelList.getResult().get(0).get))
//                    startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.xgimi.wasutv")));
//                if (Tools.isAppInstalled(this, Const.奇异果))
//                    startActivity(new Intent(getPackageManager().getLaunchIntentForPackage(Const.奇异果)));
//
//                else {
////                    new TurnOn_servlet().execute();
////                    new Update_Servlet(this).execute();
//                    new Timing_Servlet().execute();
////                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
////                    builder.setMessage("资源缺失是，请联系服务人员！");
////                    builder.setPositiveButton("好的", null);
////                    builder.show();
//                }

                break;
            case R.id.home_2:
//                startActivity(new Intent(this, APPList_Activity.class));
                if (Tools.isAppInstalled(this, Const.影视快搜))
                    startActivity(new Intent(getPackageManager().getLaunchIntentForPackage(Const.影视快搜)));
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("资源缺失是，请联系服务人员！");
                    builder.setPositiveButton("好的", null);
                    builder.show();
                }

                break;
            case R.id.home_3:
                //直接启动 HDP直播
                if (Tools.isAppInstalled(this, Const.HDP直播)) {
                    startActivity(new Intent(getPackageManager().getLaunchIntentForPackage(Const.HDP直播)));
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("资源缺失是，请联系服务人员！");
                    builder.setPositiveButton("好的", null);
                    builder.show();
                }
                break;
            case R.id.home_4:
                startActivity(new Intent(this, NewsShow_Activity.class));
                break;
        }
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
