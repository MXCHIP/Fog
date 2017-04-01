package com.mxchip.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mxchip.helper.CommonPara;
import com.mxchip.helper.SharePreHelper;

import io.fog.callbacks.ControlDeviceCallBack;
import io.fog.callbacks.MiCOCallBack;
import io.fog.fog2sdk.MiCODevice;
import io.fog.helper.CommandPara;
import io.fog.helper.Configuration;
import io.fog.helper.ListenDevParFog;

/**
 * Created by Rocke on 2016/06/13.
 */
public class DevCtrlActivity extends AppCompatActivity {
    private String TAG = "---DevCtrl---";
    private final int _REFRESHTEXTVIEW = 1;

    String commandtemp = "{\"topic\":\"d2c/d95366fe-06c0-11e6-a739-00163e0204c0/status\"," +
            "\"payload\":{\"deviceid\":\"d95366fe-06c0-11e6-a739-00163e0204c0\",\"attrSet\":" +
            "[\"KG_Start\",\"KG_Fan\",\"KG_Turn\",\"KG_Light\",\"WorkTime\",\"WorkStatus\"," +
            "\"EC\",\"WF\",\"KG_Preheat\",\"Temp_Top\",\"Temp_Bottom\",\"WF_CurrentStep\"," +
            "\"WF_TimeLeft\",\"Cur_TempBottom\",\"Cur_TempTop\"],\"KG_Start\":\"1\",\"KG_Fan\":" +
            "\"0\",\"KG_Turn\":\"0\",\"KG_Light\":\"1\",\"WorkTime\":\"58\",\"WorkStatus\":\"2\"," +
            "\"EC\":\"0\",\"WF\":{\"value\":\"0\",\"extra\":{\"WorkMode\":\"1\",\"WF_ID\":\"0\"," +
            "\"Type\":\"0\",\"StepNum\":\"0\",\"TimeTotal\":\"0\"}},\"KG_Preheat\":\"0\",\"Temp_Top\"" +
            ":\"240\",\"Temp_Bottom\":\"240\",\"WF_CurrentStep\":\"0\",\"WF_TimeLeft\":\"58\"," +
            "\"Cur_TempBottom\":\"27\",\"Cur_TempTop\":\"27\"}}";

    private Toolbar toolbar;
    String deviceid;
    String devicepw;
    MiCODevice micodev;
    SharePreHelper sph;
    String token;

    private Button sendcommand;
    private EditText jsoncommand;
    private TextView showcmdcb;
    private MyHandle myhandle = new MyHandle();

    private Button addsubdev;
    private Button sendcmdsub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ctrldevice_home);

        deviceid = getIntent().getStringExtra("deviceid");
        devicepw = getIntent().getStringExtra("devicepw");
        String devicename = getIntent().getStringExtra("devicename");

        toolbar = (Toolbar) findViewById(R.id.ctrltoolbar);
        toolbar.setTitle(devicename);
        setSupportActionBar(toolbar);

        sph = new SharePreHelper(DevCtrlActivity.this);
        token = sph.getData(CommonPara.SHARE_TOKEN);
        micodev = new MiCODevice(DevCtrlActivity.this);

        initView();
        initOnClick();

        startListenDev();

    }

    private void initView() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        jsoncommand = (EditText) findViewById(R.id.jsoncommandet);
        sendcommand = (Button) findViewById(R.id.sendcommandbtn);
        showcmdcb = (TextView) findViewById(R.id.showcmdcb);

        addsubdev = (Button) findViewById(R.id.addsubdev);
        sendcmdsub = (Button) findViewById(R.id.sendcmdsub);
    }

    private void initOnClick() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sendcommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jsoncommandStr = jsoncommand.getText().toString().trim();
                sendCommand(jsoncommandStr);
            }
        });

        /**
         * 添加子设备
         */
        addsubdev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                micodev.addSubDevice(deviceid, null, 10, "", new MiCOCallBack() {
                    @Override
                    public void onSuccess(String message) {
                        Log.d(TAG + "onSuccess", message);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        Log.d(TAG + "onFailure", code + " " + message);
                    }
                }, token);
            }
        });

        /**
         * 发送指令给子设备
         */
        sendcmdsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommandPara cmdPara = new CommandPara();
                cmdPara.deviceid = deviceid;
                cmdPara.devicepw = devicepw;
                cmdPara.command = "{}";

                micodev.sendCommandSub(cmdPara, new ControlDeviceCallBack() {
                    @Override
                    public void onSuccess(String message) {
                        Log.d(TAG + "onSuccess", message);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        Log.d(TAG + "onFailure", code + " " + message);
                    }
                }, token);
            }
        });
    }

    private void startListenDev() {
        ListenDevParFog listendevparams = new ListenDevParFog();

        String enderuserid = sph.getData(CommonPara.SHARE_USERID);
        listendevparams.deviceid = deviceid;
        listendevparams.host = Configuration.MQTTHOST();
        listendevparams.port = "1883";
        listendevparams.userName = enderuserid;
        listendevparams.passWord = "123456";
        listendevparams.clientID = enderuserid;
        listendevparams.isencrypt = false;
        micodev.startListenDevice(listendevparams, new ControlDeviceCallBack() {
            @Override
            public void onSuccess(String message) {
                Log.d(TAG, message);
                Message msg = new Message();
                msg.what = _REFRESHTEXTVIEW;
                msg.obj = message;
                myhandle.sendMessage(msg);
            }

            @Override
            public void onFailure(int code, String message) {
                Log.d(TAG, code + " " + message);
            }

            @Override
            public void onDeviceStatusReceived(int code, String messages) {
                Log.d(TAG, code + " " + messages);
                Message msg = new Message();
                msg.what = _REFRESHTEXTVIEW;
                msg.obj = code + " " + messages;
                myhandle.sendMessage(msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        micodev.stopListenDevice(new ControlDeviceCallBack() {
            @Override
            public void onSuccess(String message) {
                super.onSuccess(message);
            }

            @Override
            public void onFailure(int code, String message) {
                super.onFailure(code, message);
            }
        });
    }

    private void sendCommand(String command) {
        CommandPara cmdPara = new CommandPara();
        cmdPara.deviceid = deviceid;
        cmdPara.devicepw = devicepw;
        cmdPara.command = command;

        micodev.sendCommand(cmdPara, new ControlDeviceCallBack() {
            @Override
            public void onSuccess(String message) {
                Log.d(TAG + "onSuccess", message);
                Message msg = new Message();
                msg.what = _REFRESHTEXTVIEW;
                msg.obj = message;
                myhandle.sendMessage(msg);
            }

            @Override
            public void onFailure(int code, String message) {
                Log.d(TAG + "onFailure", code + " " + message);
                Message msg = new Message();
                msg.what = _REFRESHTEXTVIEW;
                msg.obj = message;
                myhandle.sendMessage(msg);
            }
        }, token);
    }

    class MyHandle extends Handler {
        int countno = 1;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case _REFRESHTEXTVIEW:
                    if(countno >10){
                        showcmdcb.setText("");
                        countno = 1;
                    }
                    showcmdcb.append(msg.obj.toString().trim() + "\r\n");
                    countno ++;
                    break;
            }
        }
    }
}
