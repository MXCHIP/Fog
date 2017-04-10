package com.mxchip.activities;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mxchip.helper.CommonHandler;
import com.mxchip.helper.CommonPara;
import com.mxchip.helper.SharePreHelper;

import io.fog.callbacks.ManageDeviceCallBack;
import io.fog.fog2sdk.MiCODevice;
import io.fog.helper.ShareDeviceParams;

public class AddDeviceActivity extends AppCompatActivity {
    private String TAG = "---AddDev---";

    private Toolbar toolbar;
    private Button adddevbycode;
    private EditText sharecodeet;
    private EditText deviceidet;
    private EditText devicepwet;
    private TextView showaddinfo;
    SharePreHelper sph;
    String token;

    CommonHandler myhandle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adddevice_home);
        sph = new SharePreHelper(AddDeviceActivity.this);
        token = sph.getData(CommonPara.SHARE_TOKEN);
        initView();
        initOnClick();
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.adddevtoolbar);
        toolbar.setTitle("添加设备");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        adddevbycode = (Button) findViewById(R.id.adddevbycode);
        sharecodeet = (EditText) findViewById(R.id.sharecodeet);
        deviceidet = (EditText) findViewById(R.id.deviceidet);
        devicepwet = (EditText) findViewById(R.id.devicepwet);
        showaddinfo = (TextView) findViewById(R.id.showaddinfo);
        myhandle = new CommonHandler(showaddinfo);
    }

    private void initOnClick(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adddevbycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareDeviceParams sdevp = new ShareDeviceParams();
                sdevp.bindvercode = sharecodeet.getText().toString().trim();
                sdevp.role = 2;
                sdevp.bindingtype = "home";
                sdevp.iscallback = false;
                sdevp.deviceid = deviceidet.getText().toString().trim();
                sdevp.devicepw = devicepwet.getText().toString().trim();
                MiCODevice micodev = new MiCODevice(AddDeviceActivity.this);
                micodev.addDeviceByVerCode(sdevp, new ManageDeviceCallBack() {
                    @Override
                    public void onSuccess(String message) {
                        Log.d(TAG, message);
                        Message msg = new Message();
                        msg.what = CommonPara._UPDATEVIEW;
                        msg.obj = message;
                        myhandle.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        Log.d(TAG, code + " " + message);
                        Message msg = new Message();
                        msg.what = CommonPara._UPDATEVIEW;
                        msg.obj = message;
                        myhandle.sendMessage(msg);
                    }
                }, token);
            }
        });
    }
}
