package com.mxchip.ListViewAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mxchip.activities.R;

import java.util.Vector;

import io.fog.callbacks.MiCOCallBack;
import io.fog.fog2sdk.MiCODevice;

/**
 * Created by Rocke on 2016/06/14.
 */
public class CommonAdapter extends BaseAdapter {
    private String TAG = "---CommonAdapter---";

    private LayoutInflater mInflater;
    private Vector<ComAdapBook> mModels = new Vector<ComAdapBook>();
    private Context mContext;
    private String token;
    private String deviceid;

    public CommonAdapter(Context context, String token, String deviceid) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.token = token;
        this.deviceid = deviceid;
    }

    int i = 1;

    public void addBook(String name, String enduserid) {
        Log.d(TAG + "addbook", i++ + "");
        ComAdapBook model = new ComAdapBook();
        model.name = name;
        model.enduserid = enduserid;
        mModels.add(model);
    }

    public void clean() {
        mModels.clear();
    }

    @Override
    public int getCount() {
        return mModels.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ComAdapBook model = mModels.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.test_mylist, null);

            TextView endusername = (TextView) convertView.findViewById(R.id.endusername);
            final TextView enduserid = (TextView) convertView.findViewById(R.id.enduserid);
            ImageView removeuserid = (ImageView) convertView.findViewById(R.id.removeuserid);

            endusername.setText(model.name);
            enduserid.setText("");
//            enduserid.setText(model.enduserid);

            removeuserid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, model.enduserid);
                    MiCODevice micodev = new MiCODevice(mContext);
                    micodev.removeBindRole(deviceid, model.enduserid, new MiCOCallBack() {
                        @Override
                        public void onSuccess(String message) {
                            Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(int code, String message) {
                            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                        }
                    }, token);
                }
            });
        }
        return convertView;
    }

    /**
     *  * listview中点击按键弹出对话框
     *  
     */
    public void showInfo() {
        new AlertDialog.Builder(mContext)
                .setTitle("我的listview")
                .setMessage("介绍...")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}
