package com.jiang.tvlauncher.servlet;

import android.content.Context;
import android.os.AsyncTask;

import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.utils.HttpUtil;
import com.jiang.tvlauncher.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by  jiang
 * on 2017/6/19.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 定时发送
 * update：
 */
public class Timing_Servlet extends AsyncTask<String, Integer, String> {

    private static final String TAG = "Timing_Servlet";
    Context context;

    public Timing_Servlet(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {

        Map map = new HashMap();

        map.put("text", "定时发送请求");
        map.put("序列号", "");
        String res = HttpUtil.doPost(Const.URL + "Timing", map);
        LogUtil.e(TAG, "定时发送");

        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
