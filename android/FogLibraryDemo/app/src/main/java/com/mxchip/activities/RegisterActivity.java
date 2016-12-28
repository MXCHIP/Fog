package com.mxchip.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mxchip.helper.CheckHelper;
import com.mxchip.helper.CommonPara;
import com.mxchip.helper.JsonHelper;
import com.mxchip.helper.SharePreHelper;

import io.fog.callbacks.MiCOCallBack;
import io.fog.fog2sdk.MiCOUser;

/**
 * Created by Sin on 2016/06/15.
 * Email:88635653@qq.com
 */
public class RegisterActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String username;

    private EditText loginname;
    private EditText smsvercode;
    private TextView getvercode;
    private Button checkcodebtn;

    private MiCOUser micouser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //First translucent status bar.
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //After LOLLIPOP not translucent status bar
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //Then call setStatusBarColor.
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                //设置状态栏颜色
                window.setStatusBarColor(Color.parseColor("#2E7D32"));
            }
        }

        username = getIntent().getStringExtra("loginname");
        micouser = new MiCOUser();

        initView();
        initClick();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.registertoolbar);
        toolbar.setTitle("返回");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        loginname = (EditText) findViewById(R.id.regloginname);
        smsvercode = (EditText) findViewById(R.id.smsvercode);
        getvercode = (TextView) findViewById(R.id.getvercode);
        checkcodebtn = (Button) findViewById(R.id.checkcodebtn);

        if (CheckHelper.checkPara(username)) {
            loginname.setText(username);
        }
    }

    private void initClick() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getvercode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                micouser.getVerifyCode(loginname.getText().toString().trim(), CommonPara._APPID(), new MiCOCallBack() {
                    @Override
                    public void onSuccess(String message) {
                        Toast.makeText(RegisterActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        checkcodebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = loginname.getText().toString().trim();
                micouser.checkVerifyCode(username, smsvercode.getText().toString().trim(), CommonPara._APPID(), new MiCOCallBack() {
                    @Override
                    public void onSuccess(String message) {
                        if (CheckHelper.checkPara(JsonHelper.getFogToken(message))){
                            SharePreHelper sph = new SharePreHelper(RegisterActivity.this);
                            sph.addData(CommonPara.SHARE_USERNAME, username);
                            sph.addData(CommonPara.SHARE_TOKEN, JsonHelper.getFogToken(message));
                            sph.addData(CommonPara.SHARE_USERID, JsonHelper.getFogEndUserid(message));

                            Intent intent = new Intent(RegisterActivity.this, SetPasswordActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
