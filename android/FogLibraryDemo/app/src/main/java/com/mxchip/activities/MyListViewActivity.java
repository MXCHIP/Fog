package com.mxchip.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.mxchip.ListViewAdapter.CommonAdapter;
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
 * Created by Rocke on 2016/06/14.
 */
public class MyListViewActivity extends AppCompatActivity {

    private String TAG = "---MyListView---";

    CommonAdapter adapter;
    ListView mylistview;
    private Toolbar toolbar;
    String deviceid;

    SharePreHelper sph;
    String token;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_listview);

        toolbar = (Toolbar) findViewById(R.id.memlisttoolbar);
        toolbar.setTitle("用户列表");
        setSupportActionBar(toolbar);

        deviceid = getIntent().getStringExtra("deviceid");

        initView();
        initOnClick();
        sph = new SharePreHelper(MyListViewActivity.this);
        token = sph.getData(CommonPara.SHARE_TOKEN);

        adapter = new CommonAdapter(MyListViewActivity.this, token, deviceid);
        mylistview = (ListView) findViewById(R.id.mylistview);
        getData();
    }

    private void initView() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
    }

    private void initOnClick() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData() {
        adapter.clean();

        MiCODevice micodevice = new MiCODevice(MyListViewActivity.this);
        micodevice.getMemberList(deviceid, new MiCOCallBack() {
            @Override
            public void onSuccess(String message) {
                Log.d(TAG, message);
                try {
                    String userlistinfo = JsonHelper.getFogData(message);
                    JSONArray listarr = new JSONArray(userlistinfo);

                    JSONObject temp;
                    for (int jo = 0; jo < listarr.length(); jo++) {
                        try {
                            temp = (JSONObject) listarr.get(jo);
                            String name = CheckHelper.checkPara(temp.getString("phone"))?temp.getString("phone"):temp.getString("email");
                            adapter.addBook(name, temp.getString("enduserid"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mylistview.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String message) {
                Log.d(TAG, message);
            }
        }, token);

//        for (int i = 0; i < 10; i++) {
//            adapter.addBook("G3" + i, "The id" + i);
//        }
    }
}
