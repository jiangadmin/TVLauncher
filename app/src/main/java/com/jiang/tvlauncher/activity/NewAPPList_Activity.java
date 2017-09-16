package com.jiang.tvlauncher.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.jiang.tvlauncher.R;
import com.jiang.tvlauncher.adapter.NewAppAdapter;
import com.jiang.tvlauncher.dialog.Loading;
import com.jiang.tvlauncher.entity.FindChannelList;
import com.jiang.tvlauncher.servlet.DownUtil;
import com.jiang.tvlauncher.utils.LogUtil;

import java.util.List;

/**
 * Created by  jiang
 * on 2017/6/12.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO NewAPP列表
 * update：
 */
public class NewAPPList_Activity extends Base_Activity implements AdapterView.OnItemClickListener {
    private static final String TAG = "APPList_Activity";

    private GridView mGridView;
    private NewAppAdapter mAdapter;
    static List<FindChannelList.ResultBean.AppListBean> appList;

    public static void start(Context context, List<FindChannelList.ResultBean.AppListBean> appListBeen) {
        appList = appListBeen;
        Intent intent = new Intent();
        intent.setClass(context, NewAPPList_Activity.class);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applist);
        initview();
        initeven();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mAdapter.notifyDataSetChanged();
    }

    private void initview() {
        mGridView = (GridView) findViewById(R.id.app_grid);
    }

    private void initeven() {

        mAdapter = new NewAppAdapter(this, appList);
        mGridView.setAdapter(mAdapter);
        mGridView.setFocusable(true);
//        mGridView.setSmoothScrollbarEnabled(true);

        mGridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        LogUtil.e(TAG, "点击了：" + i);
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(appList.get(i).getPackageName());
        if (launchIntent != null) {
            startActivity(launchIntent);
        } else {
            Loading.show(this, "请稍后");
            LogUtil.e(TAG, "开始下载" + appList.get(i).getPackageName());
            DownUtil.isopen = false;
            new DownUtil(this).downLoadApk(appList.get(i).getDownloadUrl(), appList.get(i).getAppName() + ".apk");
        }
    }
}
