package com.mxchip.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mxchip.helper.CommonPara;
import com.mxchip.helper.SharePreHelper;

import io.fog.callbacks.ManageDeviceCallBack;
import io.fog.callbacks.MiCOCallBack;
import io.fog.fog2sdk.MiCODevice;

/**
 * Created by Rocke on 2016/06/13.
 */
public class DeviceEditActivity extends AppCompatActivity {

    private String TAG = "---DeviceEdit---";
    private final int _UPDATEVIEW = 1;

    private Toolbar toolbar;
    String deviceid;
    String devicepw;
    SharePreHelper sph;
    String token;

    private TextView showdevinfo;
    private Button findmemberlist;
    private Button getsharecode;

    private MiCODevice micodev;
    private MyHandler myhandle = new MyHandler();
    private AlertDialog mAlertDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deviceedit_home);
        deviceid = getIntent().getStringExtra("deviceid");
        devicepw = getIntent().getStringExtra("devicepw");
        String devicename = getIntent().getStringExtra("devicename");
        micodev = new MiCODevice(DeviceEditActivity.this);

        toolbar = (Toolbar) findViewById(R.id.devedittoolbar);
        toolbar.setTitle(devicename);
        setSupportActionBar(toolbar);
        initView();
        initOnClick();

        getDeviceInfo();

//        getOnlineStatus();
    }

    private void initView() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        showdevinfo = (TextView) findViewById(R.id.showdevinfo);
        findmemberlist = (Button) findViewById(R.id.findmemberlist);
        getsharecode = (Button) findViewById(R.id.getsharecode);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editmenu, menu);
        return true;
    }

    private void initOnClick() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_editname:
                        editNameI();
                        break;
                    case R.id.menu_del:
                        removeThisDevice();
                        break;
                }
                Log.d(TAG, item.toString());
                return true;
            }
        });

        findmemberlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeviceEditActivity.this, MyListViewActivity.class);
                intent.putExtra("deviceid", deviceid);
                startActivity(intent);
            }
        });

        getsharecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                micodev.getShareVerCode(deviceid, new ManageDeviceCallBack() {
                    @Override
                    public void onSuccess(String message) {
                        Log.d(TAG, message);
                        Message msg = new Message();
                        msg.what = _UPDATEVIEW;
                        msg.obj = "code:" + message + " deviceid:" + deviceid + " devicepw:" + devicepw;
                        myhandle.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(int code, String message) {
                        Log.d(TAG, message);
                        Message msg = new Message();
                        msg.what = _UPDATEVIEW;
                        msg.obj = message;
                        myhandle.sendMessage(msg);
                    }
                }, token);
            }
        });

    }

    private void getOnlineStatus(){
        micodev.getOnlineStatus(deviceid, "2016-10-20", "2016-10-21", new MiCOCallBack() {
            @Override
            public void onSuccess(String message) {
                Log.d(TAG, message);
            }

            @Override
            public void onFailure(int code, String message) {
                Log.d(TAG, code + message);
            }
        },token);
    }

    /**
     * 打开修改设备名称的dialog
     */
    private void editNameI() {
        LayoutInflater inflater = LayoutInflater.from(DeviceEditActivity.this);
        View view = inflater.inflate(R.layout.alertdialog, null);
        mAlertDialog = new AlertDialog.Builder(this).create();
        mAlertDialog.setView((this).getLayoutInflater().inflate(R.layout.alertdialog, null));//这里的R.layout.alertdialog即为你自定义的布局文件
        mAlertDialog.show();
        mAlertDialog.getWindow().setContentView(view);

        final EditText devnameet = (EditText) view.findViewById(R.id.devnameet);
        Button submit_devname = (Button) view.findViewById(R.id.submit_devnamebtn);
        submit_devname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String devname = devnameet.getText().toString().trim();
                Log.d(TAG, devname);
                updateDeviceAlias(devname);
            }
        });
    }

    /**
     * 更新设备名称
     *
     * @param devname
     */
    private void updateDeviceAlias(String devname) {
        micodev.updateDeviceAlias(deviceid, devname, new ManageDeviceCallBack() {
            @Override
            public void onSuccess(String message) {
                Log.d(TAG, message);

                mAlertDialog.dismiss();

                Message msg = new Message();
                msg.what = _UPDATEVIEW;
                msg.obj = message;
                myhandle.sendMessage(msg);
            }

            @Override
            public void onFailure(int code, String message) {
                Log.d(TAG, code + " " + message);
                Message msg = new Message();
                msg.what = _UPDATEVIEW;
                msg.obj = message;
                myhandle.sendMessage(msg);
            }
        }, token);
    }

    /**
     * 打开即获取当前设备的详细信息
     */
    private void getDeviceInfo() {
        sph = new SharePreHelper(DeviceEditActivity.this);
        token = sph.getData(CommonPara.SHARE_TOKEN);

        micodev.getDeviceInfo(deviceid, new MiCOCallBack() {
            @Override
            public void onSuccess(String message) {
                Log.d(TAG, message);
                Message msg = new Message();
                msg.what = _UPDATEVIEW;
                msg.obj = message;
                myhandle.sendMessage(msg);
            }

            @Override
            public void onFailure(int code, String message) {
                Log.d(TAG, code + message);
                Message msg = new Message();
                msg.what = _UPDATEVIEW;
                msg.obj = message;
                myhandle.sendMessage(msg);
            }
        }, token);
    }

    /**
     * 打开解绑我的设备名称的dialog
     */
    private void removeThisDevice() {
        Dialog alertDialog = new AlertDialog.Builder(this).
                setTitle("解绑设备").
                setMessage("因为您是管理员，所以解绑后所有人都用不了").
                setPositiveButton("残忍解绑", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "好残忍");
                        unBindMydevice();
                    }
                }).
                setNegativeButton("我再想想", null).create();
        alertDialog.show();
    }

    /**
     * 解绑我的设备
     */
    private void unBindMydevice() {
        micodev.unBindDevice(deviceid, new ManageDeviceCallBack() {
            @Override
            public void onSuccess(String message) {
                Log.d(TAG, message);
                finish();
            }

            @Override
            public void onFailure(int code, String message) {
                Log.d(TAG, code + message);
                Message msg = new Message();
                msg.what = _UPDATEVIEW;
                msg.obj = message;
                myhandle.sendMessage(msg);
            }
        }, token);
    }

    /**
     * 控制界面TextView显示
     */
    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case _UPDATEVIEW:
                    showdevinfo.setText(msg.obj.toString().trim() + "\r\n");
                    break;
            }
        }
    }
}
