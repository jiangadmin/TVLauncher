package com.jiang.tvlauncher.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.Nullable;

import com.jiang.tvlauncher.MyApp;
import com.jiang.tvlauncher.R;
import com.jiang.tvlauncher.adapter.AppAdapter;
import com.jiang.tvlauncher.entity.AppBean;
import com.jiang.tvlauncher.entity.AppDataManage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangadmin
 * date: 2017/6/12.
 * Email: www.fangmu@qq.com
 * Phone: 186 6120 1018
 * TODO: 应用列表
 */

public class APPListActivity extends BaseActivity {

    private GridView mGridView;
    private List<AppBean> showlist = new ArrayList<>();

    String packageName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applist);
        MyApp.activity = this;
        packageName = getIntent().getStringExtra("packagename");
        initView();
        initEven();
    }

    private void initView() {
        mGridView = findViewById(R.id.app_grid);
    }

    //能显示的程序包名
    //String packagename = Const.芒果TV + "," + Const.优酷XL + "," + Const.魔力视频;

    private void initEven() {
        AppDataManage getAppInstance = new AppDataManage(this);
        List<AppBean> mAppList = getAppInstance.getLaunchAppList();
        for (int i = 0; i < mAppList.size(); i++) {
            if (packageName.equals("ALL")) {
                for (int j = 0; j < 10; j++) {
                    showlist.addAll(mAppList);
                }
                break;
            }
            if (packageName.contains(mAppList.get(i).getPackageName())) {
                showlist.add(mAppList.get(i));
            }
        }
        AppAdapter mAdapter = new AppAdapter(this, showlist);
        mGridView.setAdapter(mAdapter);
        mGridView.setSmoothScrollbarEnabled(true);

        if (showlist.size() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("错误");
            builder.setMessage("资源缺失，请联系服务人员!");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.show();
        } else if (showlist.size() == 1) {
            //如果就一个，那就直接启动
            startActivity(new Intent(getPackageManager().getLaunchIntentForPackage(showlist.get(0).getPackageName())));
            finish();
        }


    }
}
