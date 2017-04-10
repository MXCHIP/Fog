package com.mxchip.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mxchip.ListViewAdapter.ListViewAdapter;
import com.mxchip.activities.R;
import com.mxchip.helper.CheckHelper;
import com.mxchip.helper.CommonPara;
import com.mxchip.helper.JsonHelper;
import com.mxchip.helper.SharePreHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.fog.callbacks.MiCOCallBack;
import io.fog.fog2sdk.MiCODevice;

/**
 * Created by Rocke on 2016/06/12.
 */
public class DeviceListFragment extends Fragment {

    private String TAG = "---DevLiFrag---";

    private ListView mydevlistlistviewid;
    ListViewAdapter adapter;

    private MiCODevice micodev;
    FloatingActionButton fab;
    Animation anim;
    Handler handler;
    private static final int REFRESH_LIST = 0x10001;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.devicelist_home, container, false);
        micodev = new MiCODevice(view.getContext());
        handler = new MyHandler();
        initView(view);
        initClick();

        initPermission();

        return view;
    }

    private void initView(View view) {
        fab = (FloatingActionButton) view.findViewById(R.id.refreshdevlist);
        mydevlistlistviewid = (ListView) view.findViewById(R.id.mydevlistlistviewid);
        adapter = new ListViewAdapter(getActivity(), mydevlistlistviewid);
        mydevlistlistviewid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        anim = AnimationUtils.loadAnimation(view.getContext(), R.anim.my_rotate);
        LinearInterpolator lir = new LinearInterpolator();
        anim.setInterpolator(lir);
    }

    private void initClick() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                msg.what = 2;
                handler.sendMessage(msg);
//                Snackbar.make(view, "Will refresh this list.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    private void reload() {
        adapter.clean();
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadDate();
                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(REFRESH_LIST);
            }
        }).start();
    }

    public void loadDate() {
        SharePreHelper shareph = new SharePreHelper(getView().getContext());
        String token = shareph.getData(CommonPara.SHARE_TOKEN);
        micodev.getDeviceList(new MiCOCallBack() {
            @Override
            public void onSuccess(String message) {
                Log.d(TAG, message);
                try {
                    JSONArray datas = new JSONArray(JsonHelper.getFogData(message));
                    for (int i = 0; i < datas.length(); i++) {
                        JSONObject temp = (JSONObject) datas.get(i);
                        String name = temp.getString("device_name");
                        String online = Boolean.parseBoolean(temp.getString("online")) ? "online" : "offline";
                        String img = temp.getString("product_icon");
                        String deviceid = temp.getString("device_id");
                        String devicepw = temp.getString("device_pw");

                        // TODO 设备是否在线
                        if (!CheckHelper.checkPara(img)) {
                            img = "http://img0.pchouse.com.cn/pchouse/1403/15/576135_2.jpg";
                        }
                        adapter.addBook(name, online, img, deviceid, devicepw);
                    }
                    mydevlistlistviewid.setAdapter(adapter);

                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String message) {
                Log.d(TAG, message);
            }
        }, token);
    }

    private void initPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);

                // MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }


    //右下角的悬浮刷新按钮
    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {
                fab.clearAnimation();
            } else if (msg.what == 2) {
                reload();
                fab.startAnimation(anim);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
            } else {
                // Permission Denied
                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
