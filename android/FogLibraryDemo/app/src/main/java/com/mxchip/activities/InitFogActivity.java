package com.mxchip.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mxchip.helper.CheckHelper;
import com.mxchip.helper.CommonPara;
import com.mxchip.helper.SharePreHelper;

import io.fog.helper.MiCO;

/**
 * Created by Sin on 2016/06/17.
 * Email:88635653@qq.com
 */
public class InitFogActivity extends AppCompatActivity {
    private Toolbar toolbar;
    AutoCompleteTextView initfoghost;
    private EditText initappid;
    private Button finishinitbtn;
    SharePreHelper sph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initfog);
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
        sph = new SharePreHelper(InitFogActivity.this);
        initView();
        initClick();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.registertoolbar);
        toolbar.setTitle("返回");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        initSpinnaer();
        initappid = (EditText) findViewById(R.id.initappid);
        finishinitbtn = (Button) findViewById(R.id.finishinitbtn);

        if(CheckHelper.checkPara(sph.getData(CommonPara._FOG_APPID))){
            initappid.setText(sph.getData(CommonPara._FOG_APPID));
        }else{
            initappid.setText("db456b4a-17fc-11e6-a739-00163e0204c0");
        }
    }

    private void initSpinnaer() {
        //定义字符串数组，作为提示的文本
        String[] personData = new String[]{
                "https://v2.fogcloud.io",
                "https://sp.fogcloud.io"
        };
        //创建一个ArrayAdapter,封装数组
        ArrayAdapter<String> pp = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, personData);
        initfoghost = (AutoCompleteTextView) findViewById(R.id.initfoghost);
        initfoghost.setAdapter(pp);

        if(CheckHelper.checkPara(sph.getData(CommonPara._FOG_HOST))){
            initfoghost.setText(sph.getData(CommonPara._FOG_HOST));
        }else{
            initfoghost.setText("https://v2.fogcloud.io");
        }
    }

    private void initClick() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        finishinitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String host = initfoghost.getText().toString().trim();
                String appid = initappid.getText().toString().trim();
                if (CheckHelper.checkPara(host) || CheckHelper.checkPara(appid)) {
                    if (CheckHelper.checkPara(host)){
                        MiCO.init(host);
                        sph.addData(CommonPara._FOG_HOST, host);
                    }
                    if (CheckHelper.checkPara(appid)){
                        CommonPara._APPID = appid;
                        sph.addData(CommonPara._FOG_APPID, appid);
                    }
                    Toast.makeText(InitFogActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InitFogActivity.this, "参数不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
