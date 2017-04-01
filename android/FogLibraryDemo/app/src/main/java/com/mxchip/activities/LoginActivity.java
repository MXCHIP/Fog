package com.mxchip.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.mxchip.helper.ActivitiesManagerApplication;
import com.mxchip.helper.CheckHelper;
import com.mxchip.helper.CommonPara;
import com.mxchip.helper.JsonHelper;
import com.mxchip.helper.SharePreHelper;

import io.fog.callbacks.MiCOCallBack;
import io.fog.fog2sdk.MiCOUser;

/**
 * Created by Rocke on 2016/06/12.
 */
public class LoginActivity extends AppCompatActivity {
    private String TAG = "---Login---";

    String username;
    private EditText loginnameet;
    private EditText passwordet;

    private Button loginbutton;
    private TextView setinitfog;
    private TextView toregister;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
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
        sph = new SharePreHelper(LoginActivity.this);

        initView();
        initClick();
    }

    private void initView() {
        loginnameet = (EditText) findViewById(R.id.loginname);
        passwordet = (EditText) findViewById(R.id.password);
        loginbutton = (Button) findViewById(R.id.loginbutton);
        setinitfog = (TextView) findViewById(R.id.setinitfog);
        toregister = (TextView) findViewById(R.id.toregister);
    }

    SharePreHelper sph;

    private void initClick() {
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = loginnameet.getText().toString().trim();
                String password = passwordet.getText().toString().trim();

                MiCOUser micouser = new MiCOUser();
                micouser.login(username, password, CommonPara._APPID(), new MiCOCallBack() {
                    @Override
                    public void onSuccess(String message) {
                        if (CheckHelper.checkPara(JsonHelper.getFogToken(message))) {
                            sph.addData(CommonPara.SHARE_USERNAME, username);
                            sph.addData(CommonPara.SHARE_TOKEN, JsonHelper.getFogToken(message));
                            sph.addData(CommonPara.SHARE_USERID, JsonHelper.getFogEndUserid(message));
                            Log.d(TAG, sph.getData(CommonPara.SHARE_TOKEN));
                            Intent intent = new Intent(LoginActivity.this, IndexFragmentActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            sph.addData(CommonPara._FOG_HOST, "");
                            sph.addData(CommonPara._FOG_APPID, "");
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        sph.addData(CommonPara._FOG_HOST, "");
                        sph.addData(CommonPara._FOG_APPID, "");

                        Log.d(TAG, message);
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        toregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                username = loginnameet.getText().toString().trim();
                intent.putExtra("loginname", CheckHelper.checkPara(username) ? username : "");
                startActivity(intent);
                ActivitiesManagerApplication ama = new ActivitiesManagerApplication();
                ama.addDestoryActivity(LoginActivity.this, CommonPara.LOGIN_PAGE);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        setinitfog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, InitFogActivity.class);
                startActivity(intent);
            }
        });
    }
}
