package com.mxchip.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by Sin on 2016/06/16.
 * Email:88635653@qq.com
 */
public class DeviceInfoActivity extends AppCompatActivity {

    private String TAG = "---DeviceInfo---";

    private String deviceinfo;
    private Toolbar toolbar;
    private LinearLayout showdevinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deviceinfo);
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

        deviceinfo = getIntent().getStringExtra("deviceinfo");
        initView();
        initClick();
        showDeviceInfo();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.devinfotoolbar);
        toolbar.setTitle("设备详情");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        showdevinfo = (LinearLayout)findViewById(R.id.showdevinfo);
    }

    private void initClick() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showDeviceInfo(){
        try {
            JSONObject deviceinfojson = new JSONObject(deviceinfo);
            Iterator it = deviceinfojson.keys();
            String key = "";
            while(it.hasNext()){
                key = it.next().toString();

                View viewOne1 = getLayoutInflater().inflate(R.layout.mdnsinfo, null);
                TextView mdnskey = (TextView) viewOne1.findViewById(R.id.mdnskey);
                TextView mdnsvalue = (TextView) viewOne1.findViewById(R.id.mdnsvalue);

                mdnskey.setText(key);
                mdnsvalue.setText(deviceinfojson.getString(key));

                showdevinfo.addView(viewOne1, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
