package com.jiang.tvlauncher.servlet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.KeyEvent;
import android.widget.Toast;

import com.jiang.tvlauncher.MyApp;
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

    ProgressDialog pd;

    public void downLoad(final String path, final String fileName, final boolean showpd) {
        // 进度条对话框
        pd = new ProgressDialog(MyApp.context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("下载中，精彩马上呈现，请稍后...");
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        // 监听返回键--防止下载的时候点击返回
        pd.setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//                    Toast.makeText(activity, "正在下载请稍后", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                return false;
            }
        });
        // Sdcard不可用
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(MyApp.context, "SD卡不可用~", Toast.LENGTH_SHORT).show();
            Loading.dismiss();

        } else {
            if (showpd)
                try {
                    pd.show();
                } catch (Exception e) {

                }
            //下载的子线程
            new Thread() {
                @Override
                public void run() {
                    try {
                        // 在子线程中下载APK文件
                        File file = getFileFromServer(path, fileName, pd);
                        sleep(1000);
                        // 安装APK文件
                        LogUtil.e(TAG, "文件下载完成" + fileName);
                        if (showpd)
                            pd.dismiss(); // 结束掉进度条对话框
                        //如果是安装包
                        if (fileName.contains(".apk")) {
                            LogUtil.e(TAG, "安装包");

                            //是极米设备
                            if (MyApp.isxgimi) {
                                //调用极米静默安装
                                MyApp.apiManager.set("setInstallApk", file.getPath(), null, null, null);

                            } else {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                                MyApp.currentActivity().startActivity(intent);
                            }

                        }
                        //如果是资源文件
                        if (fileName.contains(".zip")) {
                            LogUtil.e(TAG, "资源文件");

                            MyApp.apiManager.set("setBootStartPlayer", file.getPath(), null, null, null);
                        }
                    } catch (Exception e) {
                        LogUtil.e(TAG, "文件下载失败了" + e.getMessage());

                        Loading.dismiss();
                        if (showpd)
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
            if (pd != null)
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
                if (pd != null)
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
