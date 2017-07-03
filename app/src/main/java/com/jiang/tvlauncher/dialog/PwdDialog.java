package com.jiang.tvlauncher.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.jiang.tvlauncher.R;

public class PwdDialog extends Dialog {
    private static final String TAG = "PwdDialog";

    ImageView pwd1, pwd2, pwd3, pwd4, pwd5, pwd6;

    Context context;

    String password = "";

    public PwdDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_password);
        initview();
    }

    private void initview() {
        pwd1 = (ImageView) findViewById(R.id.pwd1);
        pwd2 = (ImageView) findViewById(R.id.pwd2);
        pwd3 = (ImageView) findViewById(R.id.pwd3);
        pwd4 = (ImageView) findViewById(R.id.pwd4);
        pwd5 = (ImageView) findViewById(R.id.pwd5);
        pwd6 = (ImageView) findViewById(R.id.pwd6);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                updatepwdshow(R.drawable.ic_up, 8);
                return true;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                updatepwdshow(R.drawable.ic_down, 2);
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                updatepwdshow(R.drawable.ic_left, 4);
                return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                updatepwdshow(R.drawable.ic_right, 6);
                return true;
            default:
                return false;
        }

    }

    public void updatepwdshow(int resid, int npwd) {
        password = password + String.valueOf(npwd);
        switch (password.length()) {
            case 1:
                pwd1.setImageResource(resid);
                break;
            case 2:
                pwd2.setImageResource(resid);
                break;
            case 3:
                pwd3.setImageResource(resid);
                break;
            case 4:
                pwd4.setImageResource(resid);
                break;
            case 5:
                pwd5.setImageResource(resid);
                break;
            case 6:
                pwd6.setImageResource(resid);
                Toast.makeText(context, password, Toast.LENGTH_SHORT).show();
                dismiss();
                break;
        }
    }
}