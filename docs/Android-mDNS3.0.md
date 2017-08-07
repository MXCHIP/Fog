## 元素版本: mDNS3.0

##### Android Studio引入的资源

```js

dependencies {
    compile 'io.fogcloud.sdk:mdns:0.0.3'
}
```

##### eclipse版本使用此demo

[传送门](https://github.com/MXCHIP/mDNSmin)

##### 开启服务

需要现在manifest.xml中开启服务
```js
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
<uses-permission android:name="android.permission.CHANGE_WIFI_MULTICAST_STATE" />
```

## **功能列表**

* [搜索设备](#startSearchDevices)
* [停止搜索](#stopSearchDevices)
* [状态码](https://github.com/MXCHIP/Fog2.0/blob/master/docs/Error-code.md)


<div id="startSearchDevices"></div>

## **startSearchDevices**
    开始搜索设备，每3秒返回一次搜索结果，以json数组形式返回

    startSearchDevices(String serviceName, SearchDeviceCallBack searchdevcb)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
serviceName     | 字符串       | mdns服务的名称, 如果使用庆科模块，那么此信息为“_easylink._tcp.local.”

##### callback
searchdevcb
- 类型：SearchDeviceCallBack
- 描述：接口调用成功后的回调函数
```js
[
  {
    "Name": "MiCOKit 3165#0762C6",
    "IP": "192.168.18.119",
    "Port": 8002,
    "MAC": "D0:BA:E4:07:62:C6",
    "Firmware Rev": "fog_3165_Michael@001",
    "FogProductId": "0b3270da-393d-11e6-a739-00163e0204c0",
    "IsEasylinkOK": "true",
    "IsHaveSuperUser": "false",
    "RemainingUserNumber": "3",
    "Hardware Rev": "MK3165_1",
    "MICO OS Rev": "31620002.046",
    "Model": "MiCOKit-3165",
    "Protocol": "com.mxchip.basic",
    "Manufacturer": "MXCHIP Inc.",
    "Seed": "8"
  },
  {
    "Name": "MiCOKit 3288#91813C",
    "IP": "192.168.18.121",
    "Port": 8080,
    "MAC": "C8:93:46:91:81:3C",
    "Binding": "true",
    "Firmware Rev": "MK3288_1@1507211945",
    "Hardware Rev": "MK3288_1",
    "MICO OS Rev": "10880002.035-0709",
    "Model": "MiCOKit-3288",
    "Protocol": "com.mxchip.micokit",
    "Manufacturer": "MXCHIP Inc.",
    "Seed": "708"
  }
]
```

##### 示例代码
```java
MDNS mdns = new MDNS(context);
String serviceInfo = "_easylink._tcp.local.";
mdns.startSearchDevices(serviceInfo, new SearchDeviceCallBack() {
        @Override
        public void onDevicesFind(JSONArray deviceStatus) {
            if (!deviceStatus.equals("")) {
                Log.d("---mdns---", deviceStatus.toString());
            }
        }
        @Override
        public void onFailure(int code, String message) {
            Log.d("---mdns---", message);
        }
        @Override
        public void onSuccess(int code, String message) {
            Log.d("---mdns---", message);
        }
    });
}
```

<div id="stopSearchDevices"></div>

## **stopSearchDevices**
    停止搜索设备

    stopSearchDevices(SearchDeviceCallBack searchdevcb)

##### callback
searchdevcb
- 类型：SearchDeviceCallBack
- 描述：接口调用成功后的回调函数

##### 示例代码
```java
mdns.stopSearchDevices(new SearchDeviceCallBack() {
    public void onSuccess(int code, String message) {
        Log.d("---mdns---", message);
    };
    @Override
    public void onFailure(int code, String message) {
        Log.d("---mdns---", message);
    }
});
```
