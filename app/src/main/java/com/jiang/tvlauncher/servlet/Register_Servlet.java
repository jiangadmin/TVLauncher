package com.jiang.tvlauncher.servlet;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jiang.tvlauncher.MyAppliaction;
import com.jiang.tvlauncher.entity.Const;
import com.jiang.tvlauncher.entity.Register;
import com.jiang.tvlauncher.utils.HttpUtil;
import com.jiang.tvlauncher.utils.LogUtil;
import com.jiang.tvlauncher.utils.PreferencesUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by  jiang
 * on 2017/6/30.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 设备注册
 * update：
 */
public class Register_Servlet extends AsyncTask<Register_Servlet.Info, Integer, Register> {

    private static final String TAG = "Register_Servlet";
    Context context;

    public Register_Servlet(Context context) {
        this.context = context;
    }

    @Override
    protected Register doInBackground(Info... infos) {
        Map map = new HashMap();
        map.put("serialNum", "111111");

        String res = HttpUtil.doPost(Const.URL,map);
        Register register;
        if (res != null) {
            try {
                register = new Gson().fromJson(res,Register.class);
            } catch (Exception e) {
                register =new Register();
                register.setErrorcode(-1);
                register.setErrormsg("数据解析失败");
            }
        } else {
            register =new Register();
            register.setErrorcode(-2);
            register.setErrormsg("连接服务器失败");
        }
        return register;
    }

    @Override
    protected void onPostExecute(Register register) {
        super.onPostExecute(register);
        Toast.makeText(MyAppliaction.context,"发出注册请求",Toast.LENGTH_SHORT).show();

        if (register.getErrorcode()==1000){
            Const.ID =register.getResult().getId();
            PreferencesUtils.putInt(context,"ID",register.getResult().getId());
        }else {

        }
    }

    public static class Info {

    }

}
