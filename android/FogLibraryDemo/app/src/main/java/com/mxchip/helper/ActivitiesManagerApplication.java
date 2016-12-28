package com.mxchip.helper;

import android.app.Activity;
import android.app.Application;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Sin on 2016/06/16.
 * Email:88635653@qq.com
 */
public class ActivitiesManagerApplication extends Application {
    private static Map<String, Activity> destoryMap = new HashMap<>();


    public static void addDestoryActivity(Activity activity, String activityName) {
        destoryMap.put(activityName, activity);
    }

    public static void destoryActivity(String activityName) {
        Set<String> keySet = destoryMap.keySet();
        for (String key : keySet) {
            if (activityName.equals(key))
                destoryMap.get(key).finish();
        }
    }
}

