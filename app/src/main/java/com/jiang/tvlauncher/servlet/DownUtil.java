package com.jiang.tvlauncher.servlet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.view.KeyEvent;
import android.widget.Toast;

import com.jiang.tvlauncher.MyAppliaction;
import com.jiang.tvlauncher.dialog.Loading;
import com.jiang.tvlauncher.utils.LogUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jiangyao
 * on 2017/9/7.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 下载
 * update：
 */
public class DownUtil {
    private static final String TAG = "DownUtil";
    Activity activity;

    ProgressDialog pd;

    public static boolean isopen = false;

    public DownUtil(Activity activity) {
        this.activity = activity;
    }

    public void downLoadApk(final String path, final String fileName) {
        // 进度条对话框
        pd = new ProgressDialog(activity);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("下载中...");
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        // 监听返回键--防止下载的时候点击返回
        pd.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    Toast.makeText(activity, "正在下载请稍后", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    return false;
                }
            }
        });
        // Sdcard不可用
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(activity, "SD卡不可用~", Toast.LENGTH_SHORT).show();
            Loading.dismiss();

        } else {
            pd.show();
            //下载的子线程
            new Thread() {
                @Override
                public void run() {
                    try {
                        // 在子线程中下载APK文件
                        File file = getFileFromServer(path, "New"+fileName, pd);
                        sleep(1000);
                        // 安装APK文件
                        LogUtil.e(TAG, "文件下载好了" + file.getPath());
//                        if (isopen){
//                            String result = MyAppliaction.apiManager.set("setInstallApk", file.getPath(), "true", "activity", "com.jiang.tvlauncher/com.jiang.tvlauncher.activity.Home_Activity");
//                            LogUtil.e(TAG,"静默安装并自启动 :"+result);
//                        }
//
//                        else
                        //com.jiang.tvlauncher
                        String result = MyAppliaction.apiManager.set("setInstallApk", "mnt/usb/C8F3-C4EE/app-debug.apk", "true", "activity", "com.jiang.tvlauncher/com.jiang.tvlauncher.activity.Home_Activity");
//                            LogUtil.e(TAG,"静默安装并自启动 :"+result);
                        pd.dismiss(); // 结束掉进度条对话框
                    } catch (Exception e) {
                        LogUtil.e(TAG, "文件下载失败了");
                        Loading.dismiss();
                        pd.dismiss();
                        e.printStackTrace();
                    }
                }

            }.start();
        }
    }

    /**
     * 从服务器下载apk
     */
    public static File getFileFromServer(String path, String fileName, ProgressDialog pd) throws Exception {
        // 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            // 获取到文件的大小
            pd.setMax(conn.getContentLength() / 1024);
            InputStream is = conn.getInputStream();

            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/feekr/Download/", fileName);
            //判断文件夹是否被创建
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                // 获取当前下载量
                pd.setProgress(total / 1024);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            Loading.dismiss();
            return null;
        }
    }
}
