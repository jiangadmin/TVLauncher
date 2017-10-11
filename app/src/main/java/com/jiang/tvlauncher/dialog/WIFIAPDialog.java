package com.jiang.tvlauncher.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiang.tvlauncher.R;
import com.jiang.tvlauncher.entity.Save_Key;
import com.jiang.tvlauncher.utils.ImageUtils;
import com.jiang.tvlauncher.utils.SaveUtils;

/**
 * Created by  jiang
 * on 2017/9/29.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO Wifi热点
 * update：
 */

public class WIFIAPDialog extends Dialog {

    ImageView imageView;
    TextView ssid, pwd;

    public WIFIAPDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_wifiap);

        imageView = (ImageView) findViewById(R.id.qrcode);
        ssid = (TextView) findViewById(R.id.ssid);
        pwd = (TextView) findViewById(R.id.wifipwd);

        imageView.setImageBitmap(ImageUtils.getQRcode("WIFI:T:WPA;P:\""+SaveUtils.getString(Save_Key.WiFiPwd)+"\";S:"+SaveUtils.getString(Save_Key.WiFiName)+";"));
        ssid.setText(SaveUtils.getString(Save_Key.WiFiName));
        pwd.setText(SaveUtils.getString(Save_Key.WiFiPwd));
    }
}
