package com.mxchip.helper;

import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.mxchip.activities.R;

/**
 * Created by Rocke on 2016/06/12.
 */
public class ThisViewControl {

    public static void titleControl(Window window){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
