## 元素版本: EasyLink3.0

##### Android Studio引入的资源

```js
dependencies {
    // 只发广播的版本
    compile 'io.fogcloud.sdk:easylinkv3:0.1.4'
    // 广播组播同时发的版本
    compile 'io.fogcloud.sdk:easylinkv3:0.1.5'
}
```

一般情况使用只发广播的版本就可以了，特殊需求，比如广播收不到的情况可以使用0.1.5版本，广播组播一起发

##### eclipse版本使用此demo

[传送门](https://github.com/MXCHIP/EasyLinkMin)

##### 开启服务

需要现在manifest.xml中开启服务
```js
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.CHANGE_WIFI_MULTICAST_STATE" />
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
```

## **功能列表**

* [获取SSID](#getSSID)
* [开始配网](#startEasyLink)
* [停止配网](#stopEasyLink)
* [状态码](https://github.com/MXCHIP/Fog2.0/blob/master/docs/Error-code.md)


<div id="getSSID"></div>

## getSSID

    获取当前手机连接的WIFI的名称，即ssid

    String getSSID()

##### callback
ssid
- 类型：String
- 描述：当前WIFI的名称

##### 示例代码
```java
EasyLink elink = new EasyLink(context);
elink.getSSID()
```

<div id="startEasyLink"></div>

## startEasyLink

    发送数据包(包含ssid和password)给设备，连续发10s，再停止3s，再继续发，如此反复

    startEasyLink(EasyLinkParams easylinkPara, EasyLinkCallBack easylinkcb)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
easylinkPara     | EasyLinkParams       | EasyLinkParams包含以下的信息

##### EasyLinkParams
参数名 | 类型 | 默认值 | 描述
:-----------  | :-------------:| -----------:| -----------:
ssid        | String       | 无默认值，不可为空 | 当前wifi的名称
password     | String       | 无默认值，可为空 | 当前wifi的密码(8-64个字节，越长配网速度越慢)
isSendIP     | Boolean       | 默认值为false，可为空|是否发送手机的IP，默认不发送，如果此参数为false，那么extraData也不可用
runSecond         | int       | 默认值60000，可为空 | 发送持续的时间，到点了就停止发送, 单位ms
sleeptime         | int       | 默认值50，可为空 | 每包数据的间隔时间，建议20-200, 单位ms
extraData         | String     | 无默认值，可为空   | 需要发送给设备的额外信息
rc4key     | String       | 无默认值，可为空 | 如果需要RC4加密，这里就输入字符串密钥

##### callback
easylinkcb
- 类型：EasyLinkCallBack
- 描述：接口调用成功后的回调函数

##### 示例代码
```java
EasyLinkParams easylinkPara = new EasyLinkParams();
easylinkPara.ssid = "mxchip";
easylinkPara.password = "12345678";
easylinkPara.runSecond = 60000;
easylinkPara.sleeptime = 20;

elink.startEasyLink(easylinkPara, new EasyLinkCallBack() {
    @Override
    public void onSuccess(int code, String message) {
        Log.d(TAG, code + message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, code + message);
    }
});
```

<div id="stopEasyLink"></div>

## stopEasyLink
    
    停止发送数据包

    stopEasyLink(EasyLinkCallBack easylinkcb)

##### callback
easylinkcb
- 类型：EasyLinkCallBack
- 描述：接口调用成功后的回调函数

##### 示例代码
```java
elink.stopEasyLink(new EasyLinkCallBack() {
    @Override
    public void onSuccess(int code, String message) {
        Log.d(TAG, code + message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, code + message);
    }
});
```

## 实时更新SSID

```java
@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
    ...
    listenwifichange();
}

private void listenwifichange() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
    registerReceiver(broadcastReceiver, intentFilter);
}

BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (info.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                Log.d(TAG, "---heiheihei---");
                wifissid.setText(elink.getSSID());
            }
        }
    }
};

@Override
protected void onDestroy() {
    super.onDestroy();
    unregisterReceiver(broadcastReceiver);
}
```
