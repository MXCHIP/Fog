package com.mxchip.helper;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by Rocke on 2016/06/12.
 */
public class MyApp extends Application {
    private String TAG = "---MyApp---application";

    @Override
    public void onCreate() {

        Log.d(TAG, "MyApp");
        String filePath = "";
        if (externalMemoryAvailable()) {
            filePath = Environment.getExternalStorageDirectory() + "/iBakImgs/";
            CommonPara._FILE_ROOT_PATH = Environment.getExternalStorageDirectory() + "";
        } else {
            filePath = Environment.getDataDirectory() + "/iBakImgs/";
            CommonPara._FILE_ROOT_PATH = Environment.getDataDirectory() + "";
        }

        if (CheckHelper.checkPara(filePath)) {
            File f = new File(filePath);
            if (!f.exists()) {
                if (!f.mkdir()) {
                    f.mkdirs();
                }
            }
        }
        super.onCreate();
    }

    /**
     * 手机是否存在SD卡
     *
     * @return
     */
    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }
}