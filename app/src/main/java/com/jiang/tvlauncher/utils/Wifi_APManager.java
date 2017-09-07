package com.jiang.tvlauncher.utils;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Wifi_APManager {
    private static final String TAG = "Wifi_APManager";

    private WifiManager mWifiManager;
    private Context mContext;

    public Wifi_APManager(Context context) {
        this.mContext = context;
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 创建热点
     *
     * @param mSSID   热点名称
     * @param mPasswd 热点密码
     * @param isOpen  是否是开放热点
     */
    public void startWifiAp(String mSSID, String mPasswd, boolean isOpen) {
        Method method1 = null;
        try {
            method1 = mWifiManager.getClass().getMethod("setWifiApEnabled",
                    WifiConfiguration.class, boolean.class);
            WifiConfiguration netConfig = new WifiConfiguration();

            netConfig.SSID = mSSID;
            netConfig.preSharedKey = mPasswd;
            netConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            netConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            netConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            if (isOpen) {
                netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            } else {
                netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            }
            netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            method1.invoke(mWifiManager, netConfig, true);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取热点名
     **/
    public String getApSSID() {
        try {
            Method localMethod = this.mWifiManager.getClass().getDeclaredMethod("getWifiApConfiguration", new Class[0]);
            if (localMethod == null) return null;
            Object localObject1 = localMethod.invoke(this.mWifiManager, new Object[0]);
            if (localObject1 == null) return null;
            WifiConfiguration localWifiConfiguration = (WifiConfiguration) localObject1;
            if (localWifiConfiguration.SSID != null) return localWifiConfiguration.SSID;
            Field localField1 = WifiConfiguration.class.getDeclaredField("mWifiApProfile");
            if (localField1 == null) return null;
            localField1.setAccessible(true);
            Object localObject2 = localField1.get(localWifiConfiguration);
            localField1.setAccessible(false);
            if (localObject2 == null) return null;
            Field localField2 = localObject2.getClass().getDeclaredField("SSID");
            localField2.setAccessible(true);
            Object localObject3 = localField2.get(localObject2);
            if (localObject3 == null) return null;
            localField2.setAccessible(false);
            String str = (String) localObject3;
            return str;
        } catch (Exception localException) {
        }
        return null;
    }

    /**
     * 检查是否开启Wifi热点
     *
     * @return
     */
    public boolean isWifiApEnabled() {
        try {
            Method method = mWifiManager.getClass().getMethod("isWifiApEnabled");
            method.setAccessible(true);
            return (boolean) method.invoke(mWifiManager);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 关闭热点
     */
    public void closeWifiAp() {
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        if (isWifiApEnabled()) {
            try {
                Method method = wifiManager.getClass().getMethod("getWifiApConfiguration");
                method.setAccessible(true);
                WifiConfiguration config = (WifiConfiguration) method.invoke(wifiManager);
                Method method2 = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
                method2.invoke(wifiManager, config, false);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 开热点手机获得其他连接手机IP的方法
     *
     * @return 其他手机IP 数组列表
     */
    public ArrayList<String> getConnectedIP() {
        ArrayList<String> connectedIp = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(" +");
                if (splitted != null && splitted.length >= 4) {
                    String ip = splitted[0];
                    if (!ip.equalsIgnoreCase("ip")) {
                        connectedIp.add(ip);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connectedIp;
    }


    /**
     * 创建Wifi热点
     */
    public void createWifiHotspot(String SSID, String PWD) {
        if (mWifiManager.isWifiEnabled()) {
            //如果wifi处于打开状态，则关闭wifi,
            mWifiManager.setWifiEnabled(false);
        }
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = SSID;
        config.preSharedKey = PWD;
        config.hiddenSSID = true;
        config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);//开放系统认证
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        config.status = WifiConfiguration.Status.ENABLED;
        //通过反射调用设置热点
        Method method = null;
        try {
            method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);

            LogUtil.e(TAG, "method:" + method);
            boolean enable = (Boolean) method.invoke(mWifiManager, config, true);
            if (enable) {
                Toast.makeText(mContext, "热点已开启 SSID:" + SSID + " password:" + PWD, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mContext, "热点开启失败", Toast.LENGTH_SHORT).show();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            LogUtil.e(TAG, e.getMessage());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            LogUtil.e(TAG, e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            LogUtil.e(TAG, e.getMessage());
        }

    }
    boolean flag = false;

    public boolean createAp(String SSID, String preSharedKey) {
        //开启热点先关闭Wifi
        if (mWifiManager.isWifiEnabled()) {
            //如果wifi处于打开状态，则关闭wifi,
            mWifiManager.setWifiEnabled(false);
        }
        try {

            Method method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            method.setAccessible(true);
            WifiConfiguration config = new WifiConfiguration();
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.SSID = SSID;
            config.preSharedKey = preSharedKey;
            mWifiManager.setWifiEnabled(false);
            if(checkWifiApStatus()){
                method.invoke(mWifiManager,config,false);
                flag = (boolean) method.invoke(mWifiManager,config,true);

            }else{
                flag = (boolean) method.invoke(mWifiManager,config,true);

            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Rain.tang","changed_error : "+e.getLocalizedMessage());
        }
        return false;

    }

    public boolean checkWifiApStatus(){
        Method method2 = null;
        try {
            method2 = mWifiManager.getClass().getDeclaredMethod("getWifiApState");
            int state = (int) method2.invoke(mWifiManager);
            //通过放射获取 WIFI_AP的开启状态属性
            Field field = mWifiManager.getClass().getDeclaredField("WIFI_AP_STATE_ENABLED");
            //获取属性值
            int value = (int) field.get(mWifiManager);
            //判断是否开启
            if (state == value) {
                Log.d("Rain.tang","AP_ok");
                return true;
            } else {
                Log.d("Rain.tang","AP_false");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}