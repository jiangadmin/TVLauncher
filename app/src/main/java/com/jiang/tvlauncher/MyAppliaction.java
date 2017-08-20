package com.jiang.tvlauncher;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import com.jiang.tvlauncher.entity.FindChannelList;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.servlet.TurnOn_servlet;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.SaveUtils;
import com.jiang.tvlauncher.utils.Tools;
import com.xgimi.xgimiapiservice.XgimiApiManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by  jiang
 * on 2017/7/3.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO
 * update：
 */

public class MyAppliaction extends Application {
    private static final String TAG = "MyAppliaction";
    public static boolean LogShow = true;
    public static Context context;

    public static XgimiApiManager apiManager;

    public static String ID = "FFFFFF";
    public static String Temp = "FFFFFF";
    public static String WindSpeed = "FFFFFF";

    @Override
    public void onCreate() {
        super.onCreate();
//        startService(new Intent(this, TimingService.class));
        context = this;

        SaveUtils.setBoolean(Save_Key.FristTurnOn, true);
        LogUtil.e(TAG, "准备连接AIDL");
        ComponentName componentName = new ComponentName("com.xgimi.xgimiapiservice", "com.xgimi.xgimiapiservice.XgimiApiService");
        bindService(new Intent().setComponent(componentName), serviceConnection, Context.BIND_AUTO_CREATE);

    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        //绑定上服务的时候
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogUtil.e(TAG, "连接AIDL成功");
            //得到远程服务
            apiManager = XgimiApiManager.Stub.asInterface(iBinder);

            try {
                ID = apiManager.get("getMachineId", null, null);
                WindSpeed = apiManager.get("getWindSpeed", null, null);
                Temp = apiManager.get("getTemp", null, null);
                LogUtil.e(TAG, " 序列号 ：" + apiManager.get("getMachineId", null, null));

                LogUtil.e(TAG, "全局缩放：" + apiManager.get("getZoomValue", null, null));
                LogUtil.e(TAG, "横向缩放：" + apiManager.get("getHorizentalValue", null, null));
                LogUtil.e(TAG, "纵向缩放：" + apiManager.get("getVerticalValue", null, null));
                LogUtil.e(TAG, "标识数据：" + apiManager.get("getMachineSignal", null, null));
                LogUtil.e(TAG, "设备名称：" + apiManager.get("getDeviceName", null, null));
                LogUtil.e(TAG, "亮度模式：" + apiManager.get("getLedMode", null, null));
                LogUtil.e(TAG, "风   速：" + apiManager.get("getWindSpeed", null, null));
                LogUtil.e(TAG, "投影模式：" + apiManager.get("getProjectionMode", null, null));
                LogUtil.e(TAG, "温   度：" + apiManager.get("getTemp", null, null));
                LogUtil.e(TAG, " 开机源 ：" + apiManager.get("getBootSource", null, null));
                LogUtil.e(TAG, "上电开机：" + apiManager.get("getPowerOnStartValue", null, null));
                LogUtil.e(TAG, "梯形角度：" + apiManager.get("getKeyStoneData", null, null));

                apiManager.set("setKeyStoneByPoint","position0","horizontal","20",null);

                String jsonStr = apiManager.get("getKeyStoneData","position",null);

                apiManager.set("setKeyStoneByPoint","position","horizontal","vertical",null);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(jsonStr);
                    String type = (String) jsonObject.get("version");
                    if(type.equals("angle_keystone")){
                        JSONArray array = jsonObject.getJSONArray("angle");
                        LogUtil.e(TAG,array.toString());
                        //自己的相应读取操作
                    }else if(type.equals("point_keystone")){
                        JSONArray array = jsonObject.getJSONArray("point");
                        LogUtil.e(TAG,array.toString());
//自己的相应读取操作
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                LogUtil.e(TAG, "左上角：" + apiManager.get("getKeyStoneByPoint", "position", "0"));

                if (!TextUtils.isEmpty(ID))
                    SaveUtils.setString(Save_Key.SerialNum, ID);
//                new Register_Servlet(MyAppliaction.this).execute();
//
                do {
                    new TurnOn_servlet(context).execute();
                } while (!Tools.ping());
//                new Update_Servlet().execute();

            } catch (RemoteException e) {
                e.printStackTrace();
                LogUtil.e(TAG, "连接失败" + e.getMessage());
            }
        }

        //断开服务的时候
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtil.e(TAG, "断开AIDL连接");
        }
    };


    public static FindChannelList channelList;
}
