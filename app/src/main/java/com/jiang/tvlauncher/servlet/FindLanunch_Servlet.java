package com.jiang.tvlauncher.servlet;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.entity.FindLanunch;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.utils.HttpUtil;
import com.jiang.tvlauncher.utils.ImageUtils;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.SaveUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiang
 * on 2017/8/9.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 获取开屏图片
 * update：
 */

public class FindLanunch_Servlet extends AsyncTask<String, Integer, FindLanunch> {

    private static final String TAG = "FindLanunch_Servlet";

    public static int num = 1;

    @Override
    protected FindLanunch doInBackground(String... strings) {
        Map map = new HashMap();
        map.put("devId", SaveUtils.getString(Save_Key.ID));
        String res = HttpUtil.doPost(Const.URL + "cms/launchController/findLaunchList.do", map);
        FindLanunch lanunch;
        if (res != null) {
            try {
                lanunch = new Gson().fromJson(res, FindLanunch.class);
            } catch (Exception e) {
                lanunch = new FindLanunch();
                lanunch.setErrorcode(-1);
                lanunch.setErrormsg("连接服务器失败");
            }
        } else {
            lanunch = new FindLanunch();
            lanunch.setErrorcode(-1);
            lanunch.setErrormsg("连接服务器失败");
        }
        return lanunch;
    }

    @Override
    protected void onPostExecute(FindLanunch findLanunch) {
        super.onPostExecute(findLanunch);

        if (findLanunch.getErrorcode() == 1000) {
            for (int i = 0; i < findLanunch.getResult().size(); i++) {
                //方案类型（1=开机，2=屏保，3=互动）
                if (findLanunch.getResult().get(i).getLaunchType() == 1) {
                    //非空判断
                    if (!TextUtils.isEmpty(findLanunch.getResult().get(i).getMediaUrl())) {

                        //图片
                        if (findLanunch.getResult().get(i).getMediaType() == 1) {
                            SaveUtils.setBoolean(Save_Key.NewImage, true);
                            SaveUtils.setBoolean(Save_Key.NewVideo, false);
                            SaveUtils.setString(Save_Key.NewImageUrl,findLanunch.getResult().get(i).getMediaUrl());
                        }

                        //视频
                        if (findLanunch.getResult().get(i).getMediaType() == 2) {
                            SaveUtils.setBoolean(Save_Key.NewVideo, true);
                            SaveUtils.setBoolean(Save_Key.NewImage, false);
                            SaveUtils.setString(Save_Key.NewVideoUrl, findLanunch.getResult().get(i).getMediaUrl());
                        }
                    }
                }
            }
        } else {
            LogUtil.e(TAG, findLanunch.getErrormsg());
            if (num > 3) {

            } else {
                num++;
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    return;
                }
                //再来
                new FindLanunch_Servlet().execute();
            }
        }
    }
}
