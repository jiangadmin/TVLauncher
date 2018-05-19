package com.jiang.tvlauncher.servlet;

import android.os.AsyncTask;
import android.os.RemoteException;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jiang.tvlauncher.MyAppliaction;
import com.jiang.tvlauncher.dialog.Loading;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.entity.VIP_Entity;
import com.jiang.tvlauncher.utils.HttpUtil;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.Tools;
import com.ktcp.video.thirdagent.JsonUtils;
import com.ktcp.video.thirdparty.IThirdPartyAuthCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiangyao
 * @date: 2018/5/14
 * @Email: www.fangmu@qq.com
 * @Phone: 186 6120 1018
 * TODO: 获取会员账号
 */
public class GetVIP_Servlet extends AsyncTask<String, Integer, VIP_Entity> {
    private static final String TAG = "GetVIP_Servlet";

    IThirdPartyAuthCallback iThirdPartyAuthCallback;

    public GetVIP_Servlet(IThirdPartyAuthCallback iThirdPartyAuthCallback) {
        this.iThirdPartyAuthCallback = iThirdPartyAuthCallback;
    }

    @Override
    protected VIP_Entity doInBackground(String... strings) {
        Map map = new HashMap();
        VIP_Entity entity;
        if (!TextUtils.isEmpty(MyAppliaction.ID)) {

            map.put("serialNum", MyAppliaction.ID);
        } else {
            entity = new VIP_Entity();
            entity.setErrorcode(-3);
            entity.setErrormsg("数据缺失");
        }
        String res = "";
        res = HttpUtil.doPost(Const.URL + "tencent/tencentVideoController/getVuidInfo.do", map);

        //空判断
        if (res.contains(",\"result\":\"\"")) {
            res = res.replaceAll(",\"result\":\"\"", "");
        }

        if (TextUtils.isEmpty(res)) {
            entity = new VIP_Entity();
            entity.setErrorcode(-1);
            entity.setErrormsg("连接服务器失败");
        } else {
            try {
                entity = new Gson().fromJson(res, VIP_Entity.class);
            } catch (Exception e) {
                entity = new VIP_Entity();
                entity.setErrorcode(-2);
                entity.setErrormsg("数据解析失败");
                LogUtil.e(TAG, e.getMessage());
            }
        }
        return entity;
    }

    @Override
    protected void onPostExecute(VIP_Entity entity) {
        super.onPostExecute(entity);
        Loading.dismiss();

        if (entity.getErrorcode() == 1000) {
            HashMap<String, Object> params = new HashMap<>();
            int status = 0;//0获取数据成功 非0失败
            String msg = "get vuid error";
            long vuid = entity.getResult().getVuid();

            Const.ktcp_vuid = String.valueOf(entity.getResult().getVuid());
            Const.ktcp_vtoken = entity.getResult().getVtoken();

            params.put("vuid", vuid);
            params.put("vtoken", entity.getResult().getVtoken());
            params.put("accessToken", entity.getResult().getAccessToken());
            params.put("errTip", "");
            try {
                iThirdPartyAuthCallback.authInfo(status, msg, JsonUtils.addJsonValue(params)); //data需要返回vuid,vtoken,accesssToken
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {

            //杀死应用
            LogUtil.e(TAG, "");

//            ((ActivityManager) MyAppliaction.context.getSystemService(Context.ACTIVITY_SERVICE)).killBackgroundProcesses("com.ktcp.tvvideo");

//            Tools.getRunningServiceInfo(MyAppliaction.context, "com.ktcp.tvvideo");

            if (Tools.isAppInstalled("com.ktcp.video")) {

//                Process.killProcess();

                //启动应用
                Tools.StartApp(MyAppliaction.activity, "com.ktcp.video");


            }
        }
    }
}
