package com.jiang.tvlauncher.servlet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.TvTicketTool.TvTicketTool;
import com.google.gson.Gson;
import com.jiang.tvlauncher.MyApp;
import com.jiang.tvlauncher.dialog.Loading;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.entity.VIP_Model;
import com.jiang.tvlauncher.utils.HttpUtil;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.SaveUtils;
import com.jiang.tvlauncher.utils.Tools;
import com.ktcp.video.ktsdk.TvTencentSdk;
import com.ktcp.video.thirdagent.JsonUtils;
import com.ktcp.video.thirdagent.KtcpPaySdkProxy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangyao
 * date: 2018/5/14
 * Email: www.fangmu@qq.com
 * Phone: 186 6120 1018
 * TODO: 騰訊登錄接口
 */
public class TencentVuidLoginEventServlet extends AsyncTask<String, Integer, VIP_Model> {
    private static final String TAG = "TencentVuidLoginEventServlet";
    final Context context;

    public TencentVuidLoginEventServlet(final Context context) {
        this.context = context;
    }

    @Override
    protected VIP_Model doInBackground(String... strings) {
        Map<String, String> map = new HashMap<String, String>();
        VIP_Model entity;
        if (!TextUtils.isEmpty(MyApp.SN)) {

            map.put("serialNum", MyApp.SN);
            map.put("mac", Tools.getMacAddress());
        } else {
            entity = new VIP_Model();
            entity.setErrorcode(-3);
            entity.setErrormsg("数据缺失");
        }

        String res = HttpUtil.doPost(Const.URL + "tencent/tencentVideoController/getVuidInfo.do", map);

        //空判断
        if (res != null && res.contains(",\"result\":\"\"")) {
            res = res.replaceAll(",\"result\":\"\"", "");
        }

        if (TextUtils.isEmpty(res)) {
            entity = new VIP_Model();
            entity.setErrorcode(-1);
            entity.setErrormsg("连接服务器失败");
        } else {
            try {
                entity = new Gson().fromJson(res, VIP_Model.class);
            } catch (Exception e) {
                entity = new VIP_Model();
                entity.setErrorcode(-2);
                entity.setErrormsg("数据解析失败");
                LogUtil.e(TAG, e.getMessage());
            }
        }
        return entity;
    }

    @Override
    protected void onPostExecute(VIP_Model entity) {
        super.onPostExecute(entity);
        Loading.dismiss();

        Const.IsGetVip = true;
        if (entity.getErrorcode() == 1000 && this.context != null) {

            final HashMap<String, Object> loginData = new HashMap<>();
            loginData.put("loginType", "vu");//登录类型 vu ,qq,wx,ph
            loginData.put("vuid",entity.getResult().getVuid());
            loginData.put("vtoken", entity.getResult().getVtoken());
            loginData.put("accessToken", entity.getResult().getAccessToken());

            //大票换小票接口
            TvTicketTool.getVirtualTVSKey(this.context, false, entity.getResult().getVuid(), entity.getResult().getVtoken(), entity.getResult().getAccessToken(), new TvTencentSdk.OnTVSKeyListener() {
                @Override
                public void OnTVSKeySuccess(String vusession, int expiredTime) {
                    LogUtil.e(TAG, "vusession=" + vusession + ",expiredTime=" + expiredTime);
                    int status = 0;
                    String msg = "login success";
                    loginData.put("vusession", vusession);
                    //通过onLoginResponse 将数据回传给腾讯
                    KtcpPaySdkProxy.getInstance().onLoginResponse(status, msg, JsonUtils.addJsonValue(loginData));
                }

                @Override
                public void OnTVSKeyFaile(int failedCode, String failedMsg) {
                    LogUtil.e(TAG, "failedCode=" + failedCode + ",msg=" + failedMsg);
                    int status = failedCode;
                    String msg = failedMsg;
                    KtcpPaySdkProxy.getInstance().onLoginResponse(status, msg, JsonUtils.addJsonValue(loginData));
                }
            });
        }
    }
}
