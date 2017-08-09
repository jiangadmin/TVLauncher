package com.jiang.tvlauncher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.jiang.tvlauncher.R;
import com.jiang.tvlauncher.dialog.Loading;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.servlet.Update_Servlet;

/**
 * Created by  jiang
 * on 2017/7/3.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 控制台
 * update：
 */
public class Setting_Activity extends Base_Activity implements View.OnClickListener {
    private static final String TAG = "Setting_Activity";

    //网络，蓝牙，设置，文件，更新，关于
    LinearLayout setting1, setting2, setting3, setting4, setting5, setting6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initview();
        initeven();
    }

    private void initview() {
        setting1 = (LinearLayout) findViewById(R.id.setting_1);
        setting2 = (LinearLayout) findViewById(R.id.setting_2);
        setting3 = (LinearLayout) findViewById(R.id.setting_3);
        setting4 = (LinearLayout) findViewById(R.id.setting_4);
        setting5 = (LinearLayout) findViewById(R.id.setting_5);
        setting6 = (LinearLayout) findViewById(R.id.setting_6);
    }

    private void initeven() {
        setting1.setOnClickListener(this);
        setting2.setOnClickListener(this);
        setting3.setOnClickListener(this);
        setting4.setOnClickListener(this);
        setting5.setOnClickListener(this);
        setting6.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //网络设置
            case R.id.setting_1:
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                break;
            //蓝牙设置
            case R.id.setting_2:
                startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));//网络设置
//                startActivity(new Intent(getPackageManager().getLaunchIntentForPackage("com.gitvdemo.video")));
                break;
            //投影设置
            case R.id.setting_3:
                break;
            //文件管理
            case R.id.setting_4:
                startActivity(new Intent(getPackageManager().getLaunchIntentForPackage(Const.资源管理器)));
                break;
            //检测更新
            case R.id.setting_5:
                Loading.show(this, "检查更新");
                new Update_Servlet(this).execute();
                break;
            //关于本机
            case R.id.setting_6:
                startActivity(new Intent(Settings.ACTION_SETTINGS));
                break;
        }
    }
}
