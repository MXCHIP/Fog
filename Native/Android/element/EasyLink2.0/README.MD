##Description: EasyLink接口规范

    2015年06月29日

* [getCurrentSSID](#1)

* [startEasyLink](#2)

* [stopEasyLink](#3)

##**概述**

配网流程：

    1、通过“startEasyLink”发送配网数据包，同时开启mDNS（接口startMdnsService），发现局域网设备，发现后关闭EasyLink
    
    注意：发现设备时候，如果局域网有多台设备同时在配网，那么会发现多台设备，一般解决方案，开启mDNS之前，
    通过扫描二维码或者其他方式得到需要配网设备的mac地址，mDNS搜索到设备时候进行mac匹配，如果一致则说明之
    前的EasyLink工作完成，关闭EasyLink

首先需要导入一个库：easylink_only.jar


#**getCurrentSSID**<div id="1"></div>

    获取WIFI的名称SSID

    public  String getCurrentSSID(){}

##return

return：

- 类型：String

##示例代码

```js
private EasyLinkWifiManager mWifiManager = null;
private Context ctx = null;

ctx = MainActivity.this;
mWifiManager = new EasyLinkWifiManager(ctx);
String Ssid = mWifiManager.getCurrentSSID();
```

#**startEasyLink**<div id="2"></div>

    开启EasyLink，发送配网数据包给设备
    
    public void startEasyLink(Context context, String ssid, String password) {}

##示例代码

```js
public EasyLinkAPI elapi;
private Context ctx = null;

ctx = MainActivity.this;
elapi.startEasyLink_FTC(context,wifissid,  wifipsw);
```

#**stopEasyLink**<div id="3"></div>

    停止EasyLink，一般情况会等mDNS搜到需要配网的设备时候停止或者添加一个60s超时来停止
    
    public void stopEasyLink() {}

##示例代码

```js
elapi.stopEasyLink();
```
