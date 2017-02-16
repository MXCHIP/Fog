package com.mxchip.fragments;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mxchip.activities.DeviceInfoActivity;
import com.mxchip.activities.R;
import com.mxchip.helper.CommonPara;
import com.mxchip.helper.SharePreHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.fog.callbacks.ManageDeviceCallBack;
import io.fog.fog2sdk.MiCODevice;
import io.fogcloud.easylink.helper.EasyLinkCallBack;
import io.fogcloud.fog_mdns.helper.SearchDeviceCallBack;

/**
 * Created by Rocke on 2016/05/25.
 */
public class EasyLinkFragment extends Fragment {

    private String TAG = "---EasyLinkFragment---";

    private EditText ssid_et;
    private EditText password_et;
    private Button easylink_btn;
    private Button mdns_btn;
    private TextView showeasylink;
    private LinearLayout showmdns;

    private MiCODevice micodev;
    EasyHandler myhandle;
    LayoutInflater inflater = null;
    SharePreHelper sph;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        this.inflater = inflater;
        initView(view);
        initClick();

        //实时更新ssid
        listenwifichange();

        return view;
    }

    private void initView(View view) {
        ssid_et = (EditText) view.findViewById(R.id.ssid_et);
        password_et = (EditText) view.findViewById(R.id.password_et);
        easylink_btn = (Button) view.findViewById(R.id.easylink_btn);
        mdns_btn = (Button) view.findViewById(R.id.mdns_btn);
        showeasylink = (TextView) view.findViewById(R.id.showeasylink);
        showmdns = (LinearLayout) view.findViewById(R.id.showmdns);

        micodev = new MiCODevice(view.getContext());
        myhandle = new EasyHandler(showeasylink);

        ssid_et.setText(micodev.getSSID());
    }

    private void initClick() {
        easylink_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("start".equals(easylink_btn.getTag().toString().trim())) {
                    easylink_btn.setTag("stop");
                    easylink_btn.setText("停止配网");
                    easylink_btn.setBackgroundColor(Color.parseColor("#FF4081"));

                    String ssid = ssid_et.getText().toString().trim();
                    String password = password_et.getText().toString().trim();
                    micodev.startEasyLink(ssid, password, true, 40000, 20, "", "", new EasyLinkCallBack() {
                        @Override
                        public void onSuccess(int code,String message) {
                            Log.d(TAG, message);
                            Message msg = new Message();
                            msg.what = EasyHandler._UPDATEVIEW;
                            if ("stop success".equals(message)) {
                                msg.what = EasyHandler._STOPEASY;
                            }
                            msg.obj = message;
                            myhandle.sendMessage(msg);
                        }

                        @Override
                        public void onFailure(int code, String message) {
                            Log.d(TAG, message);
                            Message msg = new Message();
                            msg.what = CommonPara._UPDATEVIEW;
                            msg.obj = message;
                            myhandle.sendMessage(msg);
                        }
                    });
                } else {
                    easylink_btn.setTag("start");
                    easylink_btn.setText("开始配网");
                    easylink_btn.setBackgroundColor(Color.parseColor("#81C784"));
                    micodev.stopEasyLink(new EasyLinkCallBack() {
                        @Override
                        public void onSuccess(int code, String message) {
                            Log.d(TAG, message);
                            Message msg = new Message();
                            msg.what = CommonPara._UPDATEVIEW;
                            msg.obj = message;
                            myhandle.sendMessage(msg);
                        }

                        @Override
                        public void onFailure(int code, String message) {
                            Log.d(TAG, message);
                            Message msg = new Message();
                            msg.what = CommonPara._UPDATEVIEW;
                            msg.obj = message;
                            myhandle.sendMessage(msg);
                        }
                    });
                }



                if ("start".equals(mdns_btn.getTag().toString().trim())) {
                    mdns_btn.setTag("stop");
                    mdns_btn.setText("停止搜索");
                    mdns_btn.setBackgroundColor(Color.parseColor("#FF4081"));

                    String serviceName = "_easylink._tcp.local.";
                    micodev.startSearchDevices(serviceName, new SearchDeviceCallBack() {
                        @Override
                        public void onSuccess(int code, String message) {
                            Log.d(TAG, message);
                            Message msg = new Message();
                            msg.what = CommonPara._UPDATEVIEW;
                            msg.obj = message;
                            myhandle.sendMessage(msg);
                        }

                        @Override
                        public void onFailure(int code, String message) {
                            Log.d(TAG, message);
                            Message msg = new Message();
                            msg.what = CommonPara._UPDATEVIEW;
                            msg.obj = message;
                            myhandle.sendMessage(msg);
                        }

                        @Override
                        public void onDevicesFind(int code, JSONArray deviceStatus) {
                            Log.d(TAG, deviceStatus.toString());
//                            String macs = "";
//                            for (int i = 0; i < deviceStatus.length(); i++) {
//                                try {
//                                    macs += ((JSONObject)deviceStatus.get(i)).getString("MAC")+" ";
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            Log.d(TAG, deviceStatus.length() + " -- " + macs);

//                            Message msg = new Message();
//                            msg.what = CommonPara._UPDATEVIEW;
//                            msg.obj = deviceStatus.toString();
//                            myhandle.sendMessage(msg);

                            Message msg = new Message();
                            msg.what = EasyHandler._DEVLIST;
                            msg.obj = deviceStatus.toString();
                            myhandle.sendMessage(msg);
                        }
                    });

                } else {
                    mdns_btn.setTag("start");
                    mdns_btn.setText("搜索设备");
                    mdns_btn.setBackgroundColor(Color.parseColor("#81C784"));

                    micodev.stopSearchDevices(new SearchDeviceCallBack() {
                        @Override
                        public void onSuccess(int code, String message) {
                            Log.d(TAG, message);
                            Message msg = new Message();
                            msg.what = CommonPara._UPDATEVIEW;
                            msg.obj = message;
                            myhandle.sendMessage(msg);
                        }

                        @Override
                        public void onFailure(int code, String message) {
                            Log.d(TAG, message);
                            Message msg = new Message();
                            msg.what = CommonPara._UPDATEVIEW;
                            msg.obj = message;
                            myhandle.sendMessage(msg);
                        }
                    });
                }
            }
        });
        mdns_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ("start".equals(mdns_btn.getTag().toString().trim())) {
                    mdns_btn.setTag("stop");
                    mdns_btn.setText("停止搜索");
                    mdns_btn.setBackgroundColor(Color.parseColor("#FF4081"));

                    String serviceName = "_easylink._tcp.local.";
                    micodev.startSearchDevices(serviceName, new SearchDeviceCallBack() {
                        @Override
                        public void onSuccess(int code, String message) {
                            Log.d(TAG, message);
                            Message msg = new Message();
                            msg.what = CommonPara._UPDATEVIEW;
                            msg.obj = message;
                            myhandle.sendMessage(msg);
                        }

                        @Override
                        public void onFailure(int code, String message) {
                            Log.d(TAG, message);
                            Message msg = new Message();
                            msg.what = CommonPara._UPDATEVIEW;
                            msg.obj = message;
                            myhandle.sendMessage(msg);
                        }

                        @Override
                        public void onDevicesFind(int code, JSONArray deviceStatus) {
                            Log.d(TAG, deviceStatus.toString());
//                            String macs = "";
//                            for (int i = 0; i < deviceStatus.length(); i++) {
//                                try {
//                                    macs += ((JSONObject)deviceStatus.get(i)).getString("MAC")+" ";
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            Log.d(TAG, deviceStatus.length() + " -- " + macs);

//                            Message msg = new Message();
//                            msg.what = CommonPara._UPDATEVIEW;
//                            msg.obj = deviceStatus.toString();
//                            myhandle.sendMessage(msg);

                            Message msg = new Message();
                            msg.what = EasyHandler._DEVLIST;
                            msg.obj = deviceStatus.toString();
                            myhandle.sendMessage(msg);
                        }
                    });

                } else {
                    mdns_btn.setTag("start");
                    mdns_btn.setText("搜索设备");
                    mdns_btn.setBackgroundColor(Color.parseColor("#81C784"));

                    micodev.stopSearchDevices(new SearchDeviceCallBack() {
                        @Override
                        public void onSuccess(int code, String message) {
                            Log.d(TAG, message);
                            Message msg = new Message();
                            msg.what = CommonPara._UPDATEVIEW;
                            msg.obj = message;
                            myhandle.sendMessage(msg);
                        }

                        @Override
                        public void onFailure(int code, String message) {
                            Log.d(TAG, message);
                            Message msg = new Message();
                            msg.what = CommonPara._UPDATEVIEW;
                            msg.obj = message;
                            myhandle.sendMessage(msg);
                        }
                    });
                }
            }
        });
    }

    class EasyHandler extends Handler {

        private final static int _UPDATEVIEW = 2;
        private final static int _STOPEASY = 3;
        private final static int _DEVLIST = 4;
        private TextView showet;
        private int index = 1;

        public EasyHandler(TextView showet) {
            this.showet = showet;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case _UPDATEVIEW:
                    if (index > 10) {
                        showet.setText("");
                        index = 1;
                    }
                    showet.append(index + " " + msg.obj.toString().trim() + "\r\n");
                    index++;
                    break;
                case _STOPEASY:
                    easylink_btn.setTag("start");
                    easylink_btn.setText("开始配网");
                    easylink_btn.setBackgroundColor(Color.parseColor("#81C784"));
                    break;
                case _DEVLIST:
                    updateDeviceList(msg.obj.toString());
                    break;
            }
        }
    }

    private void updateDeviceList(String message) {
        showmdns.removeAllViews();
        if (null != inflater) {

            try {
                JSONArray jsonArray = new JSONArray(message);
                JSONObject temp;
                for (int i = 0; i < jsonArray.length(); i++) {
                    View viewOne1 = inflater.inflate(R.layout.deviceitem, null);
                    temp = (JSONObject) jsonArray.get(i);

                    //更新页面内容
                    TextView mdnsname = (TextView) viewOne1.findViewById(R.id.mdnsname);
                    TextView mdnsmac = (TextView) viewOne1.findViewById(R.id.mdnsmac);
                    TextView mdnsip = (TextView) viewOne1.findViewById(R.id.mdnsip);
                    ImageView mdnsbtn = (ImageView) viewOne1.findViewById(R.id.mdnsbtn);
                    LinearLayout mdnsinfo = (LinearLayout) viewOne1.findViewById(R.id.mdnsinfo);

                    final String devinfo = temp.toString();
                    final String devip = temp.getString("IP");
                    final String devmac = temp.getString("MAC");

                    mdnsname.setText(temp.getString("Name"));
                    mdnsmac.setText("MAC:" + temp.getString("MAC"));
                    mdnsip.setText("IP:" + temp.getString("IP"));

                    mdnsinfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), DeviceInfoActivity.class);
                            intent.putExtra("deviceinfo", devinfo);
                            startActivity(intent);
                        }
                    });

                    if (temp.toString().indexOf("IsHaveSuperUser") > -1) {
                        String isHaveSuperUser = temp.getString("IsHaveSuperUser");
                        if ("false".equals(isHaveSuperUser)) {
                            mdnsbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Dialog alertDialog = new AlertDialog.Builder(getContext()).
                                            setMessage("绑定设备:" + devmac).
                                            setPositiveButton("没错", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    bindDevice(devip);
                                                }
                                            }).
                                            setNegativeButton("我再想想", null).create();
                                    alertDialog.show();
                                }
                            });
                        }else {
                            mdnsbtn.setVisibility(View.GONE);
                        }
                    } else {
                        mdnsbtn.setVisibility(View.GONE);
                    }

                    showmdns.addView(viewOne1, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void bindDevice(String ip) {
        sph = new SharePreHelper(getContext());
        micodev.bindDevice(ip, "8002", new ManageDeviceCallBack() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int code, String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }, sph.getData(CommonPara.SHARE_TOKEN));
    }

    private void listenwifichange() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        getContext().registerReceiver(broadcastReceiver, intentFilter);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (info.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                    Log.d(TAG, "---heiheihei---");
                    ssid_et.setText(micodev.getSSID());
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        micodev.stopSearchDevices(new SearchDeviceCallBack() {
            @Override
            public void onSuccess(int code, String message) {
            }

            @Override
            public void onFailure(int code, String message) {
                super.onFailure(code, message);
            }
        });
        micodev.stopEasyLink(new EasyLinkCallBack() {
            @Override
            public void onSuccess(int code, String message) {
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
        getContext().unregisterReceiver(broadcastReceiver);
    }
}
