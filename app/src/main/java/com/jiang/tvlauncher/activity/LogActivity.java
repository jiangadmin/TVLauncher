package com.jiang.tvlauncher.activity;

import android.os.Bundle;
import android.os.RemoteException;
import androidx.annotation.Nullable;
import android.widget.TextView;

import com.jiang.tvlauncher.MyApp;
import com.jiang.tvlauncher.R;

/**
 * @author jiangadmin
 * date: 2018/9/6
 * Email: www.fangmu@qq.com
 * Phone: 186 6120 1018
 * TODO:
 */
public class LogActivity extends BaseActivity {
    private static final String TAG = "LogActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        TextView textView = findViewById(R.id.log);

        if (MyApp.apiManager == null) {
            textView.setText("AIDL 连接失败");
        } else {

            String s = "";

            try {
                s += "\n设备序列：" + MyApp.apiManager.get("getMachineId", null, null);
                s += "\n全局缩放：" + MyApp.apiManager.get("getZoomValue", null, null);
                s += "\n横向缩放：" + MyApp.apiManager.get("getHorizentalValue", null, null);
                s += "\n纵向缩放：" + MyApp.apiManager.get("getVerticalValue", null, null);
                s += "\n标识数据：" + MyApp.apiManager.get("getMachineSignal", null, null);
                s += "\n设备名称：" + MyApp.apiManager.get("getDeviceName", null, null);
                s += "\n亮度模式：" + MyApp.apiManager.get("getLedMode", null, null);
                s += "\n当前风速：" + MyApp.apiManager.get("getWindSpeed", null, null);
                s += "\n投影模式：" + MyApp.apiManager.get("getProjectionMode", null, null);
                s += "\n当前温度：" + MyApp.apiManager.get("getTemp", null, null);
                s += "\n开机信源：" + MyApp.apiManager.get("getBootSource", null, null);
                s += "\n上电开机：" + MyApp.apiManager.get("getPowerOnStartValue", null, null);
                s += "\n设备型号：" + MyApp.apiManager.get("getDeviceModel", null, null);
                textView.setText(s);
            } catch (RemoteException e) {
                e.printStackTrace();
                textView.setText(e.getMessage());
            }
        }
    }
}
