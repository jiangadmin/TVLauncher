package com.jiang.tvlauncher.servlet;

import android.os.AsyncTask;

import com.jiang.tvlauncher.dialog.Loading;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.utils.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by  jiang
 * on 2017/6/19.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 检查更新请求
 * update：
 */
public class Update_Servlet extends AsyncTask<String, Integer, String> {
    private static final String TAG = "Update_Servlet";

    @Override
    protected String doInBackground(String... strings) {
        Map map = new HashMap();
        map.put("序列号", "");
        map.put("版本号", "");

        String res = HttpUtil.doPost(Const.URL, map);
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Loading.dismiss();
    }
}
