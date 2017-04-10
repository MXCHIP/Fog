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
import android.widget.Toast;

import com.mxchip.helper.ActivitiesManagerApplication;
import com.mxchip.helper.CommonPara;
import com.mxchip.helper.SharePreHelper;

import io.fog.callbacks.MiCOCallBack;
import io.fog.fog2sdk.MiCOUser;

/**
 * Created by Sin on 2016/06/16.
 * Email:88635653@qq.com
 */
public class SetPasswordActivity extends AppCompatActivity {
    private Toolbar toolbar;
    String token;
    SharePreHelper sph;
    private EditText newpassword;
    private Button setpswbtn;
    private MiCOUser micouser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setpassword);
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

//        username = getIntent().getStringExtra("loginname");
        micouser = new MiCOUser();
        sph = new SharePreHelper(SetPasswordActivity.this);
        token = sph.getData(CommonPara.SHARE_TOKEN);
        initView();
        initClick();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.setpswtoolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.umeng_update_close_bg_normal);

        newpassword = (EditText) findViewById(R.id.newpassword);
        setpswbtn = (Button) findViewById(R.id.setpswbtn);
    }

    private void initClick() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitiesManagerApplication ama = new ActivitiesManagerApplication();
                ama.destoryActivity(CommonPara.LOGIN_PAGE);

                Intent intent = new Intent(SetPasswordActivity.this, IndexFragmentActivity.class);
                startActivity(intent);
                finish();
            }
        });

        setpswbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                micouser.setPassword(newpassword.getText().toString().trim(), new MiCOCallBack() {
                    @Override
                    public void onSuccess(String message) {
                        ActivitiesManagerApplication ama = new ActivitiesManagerApplication();
                        ama.destoryActivity(CommonPara.LOGIN_PAGE);

                        Intent intent = new Intent(SetPasswordActivity.this, IndexFragmentActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        Toast.makeText(SetPasswordActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                }, token);
            }
        });
    }
}
