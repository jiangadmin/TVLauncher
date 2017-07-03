package com.jiang.tvlauncher.servlet;

import android.os.AsyncTask;

import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.utils.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by  jiang
 * on 2017/6/19.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 关机发送
 * update：
 */

public class TurnOff_servlet extends AsyncTask<String,Integer,String> {
    @Override
    protected String doInBackground(String... strings) {
        Map map = new HashMap();
        map.put("text", "关机发送请求");
        map.put("序列号","");

        String res = HttpUtil.doPost(Const.URL + "TurnOff", map);
        return null;
    }
}
