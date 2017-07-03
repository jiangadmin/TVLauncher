package com.jiang.tvlauncher.servlet;

import android.os.AsyncTask;

import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.utils.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by  jiang
 * on 2017/6/30.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 设备注册
 * update：
 */
public class Register_Servlet extends AsyncTask<Register_Servlet.Info, Integer, String> {

    private static final String TAG = "Register_Servlet";

    @Override
    protected String doInBackground(Info... infos) {
        Map map = new HashMap();
        map.put("设备号", "");

        String res = HttpUtil.doPost(Const.URL, map);
        if (res != null) {
            try {

            } catch (Exception e) {

            }
        } else {

        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    public static class Info {

    }

}
