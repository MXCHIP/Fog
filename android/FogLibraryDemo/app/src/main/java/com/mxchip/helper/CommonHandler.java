package com.mxchip.helper;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

/**
 * Created by Rocke on 2016/06/15.
 */
public class CommonHandler extends Handler {

    private final int _UPDATEVIEW = 2;
    private TextView showet;
    private int index = 1;

    public CommonHandler(TextView showet) {
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
        }
    }
}
