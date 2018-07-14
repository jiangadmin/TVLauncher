package com.jiang.tvlauncher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.jiang.tvlauncher.MyAppliaction;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.servlet.GetVIP_Servlet;
import com.jiang.tvlauncher.servlet.VIPCallBack_Servlet;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.SaveUtils;
import com.jiang.tvlauncher.utils.Tools;
import com.ktcp.video.thirdagent.JsonUtils;
import com.ktcp.video.thirdagent.ThirdPartyAgent;
import com.ktcp.video.thirdagent.inter.IThirdPartyAgentListener;
import com.ktcp.video.thirdparty.IThirdPartyAuthCallback;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by v_shlicheng on 2018/4/26.
 */

public class ThirdPartyReceiver extends BroadcastReceiver implements IThirdPartyAgentListener {
    private static final String TAG = "ThirdpartyReceiver";
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        LogUtil.e(TAG, "处理登录");

        //处理登录
        if (ThirdPartyAgent.ACTION_SERVER_AGGENT.equals(intent.getAction())) {
            String channel = intent.getStringExtra("channel");
            String data = intent.getStringExtra("data");
            Log.i(TAG, "channel=" + channel + ",data=" + data);

            Toast.makeText(MyAppliaction.context, "channel=" + channel + ",data=" + data, Toast.LENGTH_LONG).show();

            JSONObject dataObj = JsonUtils.getJsonObj(data);

            //启动APP
            if (dataObj.optInt("type") == ThirdPartyAgent.TYPE_LOGIN) {
                ThirdPartyAgent.getInstance().setOnThirdPartyAgentListener(this);
                ThirdPartyAgent.getInstance().doAuthLogin(channel);

            }

            //登录返回
            if (dataObj.optInt("type") == ThirdPartyAgent.TYPE_NOTICE) {

                String extra = dataObj.optString("extra");
                JSONObject extraObj = JsonUtils.getJsonObj(extra);

                int code = extraObj.optInt("code");
                String msg = extraObj.optString("msg");
                String vtoken = extraObj.optString("vtoken");
                String vuid = (String.valueOf(extraObj.optLong("vuid")));

                VIPCallBack_Servlet.TencentVip vip = new VIPCallBack_Servlet.TencentVip();
                vip.setCode(String.valueOf(code));
                vip.setMsg(msg);
                new VIPCallBack_Servlet().execute(vip);

                //登录失败
                //关闭当前应用

            }

            //处理支付订单
            else if (dataObj.optInt("type") == ThirdPartyAgent.TYPE_ORDER) {
                long vuid = dataObj.optLong("vuid", -1);
                if (vuid > 0) {
                    String produceId = dataObj.optString("produeceId");
                    String vuSession = dataObj.optString("vuSession");
                    ThirdPartyAgent.getInstance().setOnThirdPartyAgentListener(this);
                    ThirdPartyAgent.getInstance().doOrder(vuid, produceId, vuSession);
                }
            }
        }
    }

    //退出登录
//                HashMap params = new HashMap<String, Object>();
//                params.put("channel", "");
//                ThirdPartyAgent.getInstance().noticeClient(MyAppliaction.context, ThirdPartyAgent.EVENT_SERVER_LOGOUT, JsonUtils.addJsonValue(params));


    @Override
    public void getAccount(String channel, final IThirdPartyAuthCallback thirdPartyAuthCallback) {
        //fixme 由厂商实现的接口 成功获取到接口vuid,vtoken,accessToken必须通过data回调给视频客户端，需要视频处理的错误定义好提示文案放errTip中
        Toast.makeText(context, "正在为您提供会员服务", Toast.LENGTH_LONG).show();

        try {
            thirdPartyAuthCallback.authInfo(0, "get vuid error", SaveUtils.getString(Save_Key.PARAMS)); //data需要返回vuid,vtoken,accesssToken
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void getOrder(long vuid, String produceId, String vusession, final IThirdPartyAuthCallback thirdPartyAuthCallback) {
        LogUtil.e(TAG, "getOrder由厂商实现的接口");
        //fixme 由厂商实现的接口 成功下单回调{"orderId":"","errTip":""}给视频客户端，需要视频处理的错误定义好提示文案放errTip中
        final HashMap<String, Object> params = new HashMap<>();
        params.put("orderId", "");
        params.put("errTip", "");
        int status = 0;// 0 获取数据成功 非0失败
        String msg = "success";
        try {
            thirdPartyAuthCallback.orderResult(status, msg, params.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
