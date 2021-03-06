package com.jiang.tvlauncher.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.jiang.tvlauncher.MyApp;
import com.jiang.tvlauncher.R;
import com.jiang.tvlauncher.adapter.NewAppAdapter;
import com.jiang.tvlauncher.dialog.Loading;
import com.jiang.tvlauncher.entity.FindChannelList_Model;
import com.jiang.tvlauncher.servlet.DownUtil;
import com.jiang.tvlauncher.utils.LogUtil;

import java.util.List;

/**
 * author: jiangadmin
 * date: 2017/6/12.
 * Email: www.fangmu@qq.com
 * TODO: 新应用列表
 */

public class NewAPPListActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "APPListActivity";

    private GridView mGridView;
    static List<FindChannelList_Model.ResultBean.AppListBean> appList;

    public static void start(Context context, List<FindChannelList_Model.ResultBean.AppListBean> appListBeen) {
        appList = appListBeen;
        Intent intent = new Intent();
        intent.setClass(context, NewAPPListActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applist);
        MyApp.activity = this;
        initView();
        initEven();

    }

    private void initView() {
        mGridView =  findViewById(R.id.app_grid);
    }

    private void initEven() {

        NewAppAdapter mAdapter = new NewAppAdapter(this, appList);
        mGridView.setAdapter(mAdapter);
        mGridView.setFocusable(true);
        mGridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(appList.get(i).getPackageName());
        if (launchIntent != null) {
            startActivity(launchIntent);
        } else {
            Loading.show(this, "正在安装");
            LogUtil.e(TAG, "开始下载" + appList.get(i).getPackageName());
            new DownUtil().downLoad(appList.get(i).getDownloadUrl(), appList.get(i).getAppName() + ".apk",true);
        }
    }
}
