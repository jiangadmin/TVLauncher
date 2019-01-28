package com.jiang.tvlauncher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;

import com.jiang.tvlauncher.entity.Const;
import com.ktcp.video.thirdagent.JsonUtils;
import com.ktcp.video.thirdagent.KtcpPaySdkProxy;
import com.ktcp.video.thirdagent.ThirdPartyAgent;
import com.ktcp.video.thirdagent.inter.IThirdPartyAgentListener;
import com.ktcp.video.thirdparty.IThirdPartyAuthCallback;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by v_shlicheng on 2018/4/26.
 */
@Deprecated
public class ThirdPartyReceiver extends BroadcastReceiver implements IThirdPartyAgentListener {
    private static final String TAG = "ThirdpartyReceiver";
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
//        //新增代码用来处理三方付费的回调只在一处调用
//        if (KtcpPaySdkProxy.getInstance().getAgentVersion().compareTo("2.0.0") >= 0) {
//            Log.i(TAG, "deal KtcpPaySDK use KtcpPaySDKCallback");
//            return;
//        }
//
//        this.context = context;
//        if (ThirdPartyAgent.ACTION_SERVER_AGGENT.equals(intent.getAction())) {
//            String channel = intent.getStringExtra("channel");
//            String data = intent.getStringExtra("data");
//            Log.i(TAG, "channel=" + channel + ",data=" + data);
//            JSONObject dataObj = JsonUtils.getJsonObj(data);
//            //处理获取帐号
//            if (dataObj.optInt("type") == ThirdPartyAgent.TYPE_LOGIN) {
//                ThirdPartyAgent.getInstance().setOnThirdPartyAgentListener(this);
//                ThirdPartyAgent.getInstance().doAuthLogin(channel);
//            } else if (dataObj.optInt("type") == ThirdPartyAgent.TYPE_NOTICE) {//接收来自客户端的通知回调
//                int eventId = dataObj.optInt("eventId");// 2 帐号登录回调  3 帐号退出回调 4 APP退出回调
//                String extraJson = dataObj.optString("extra");//对应事件的回调数据
//                switch (eventId) {
//                    case 2://帐号登录回调再这里处理
//                        break;
//                    case 3:
//                        break;
//                    case 4:
//                        break;
//                }
//            } else if (dataObj.optInt("type") == ThirdPartyAgent.TYPE_EVENT) {//接收来自客户端的事件
//
//            }
//        }

    }

    @Override
    public void getAccount(String channel, final IThirdPartyAuthCallback thirdPartyAuthCallback) {
        //fixme 由厂商实现的接口 成功获取到接口vuid,vtoken,accessToken必须通过data回调给视频客户端，需要视频处理的错误定义好提示文案放errTip中

//        HashMap<String, Object> params = new HashMap<>();
//        int status = 2;//0获取数据成功 非0失败
//        String msg = "get vuid success";
//        params.put("vuid", Const.ktcp_vuid);
//        params.put("vtoken", Const.ktcp_vtoken);
//        params.put("accessToken", Const.ktcp_accessToken);
//        params.put("errTip", "");
//        try {
//            thirdPartyAuthCallback.authInfo(status, msg, JsonUtils.addJsonValue(params)); //data需要返回vuid,vtoken,accesssToken
//        } catch (Exception e) {
//            Log.i(TAG, " getAccount err=" + e.getMessage());
//            e.printStackTrace();
//        }

    }

    @Override
    public void getOrder(long vuid, String produceId, String vusession, final IThirdPartyAuthCallback thirdPartyAuthCallback) {
//        //fixme 由厂商实现的接口 成功下单回调{"orderId":"","errTip":""}给视频客户端，需要视频处理的错误定义好提示文案放errTip中
//        final HashMap<String, Object> params = new HashMap<>();
//        params.put("orderId", "");
//        params.put("errTip", "");
//        int status = 0;// 0 获取数据成功 非0失败
//        String msg = "success";
//        try {
//            thirdPartyAuthCallback.orderResult(status, msg, params.toString());
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
    }
}
