## MiCOSDK开发指南

这里有测试Demo，[传送门](https://github.com/MXCHIP/Fog2.0/tree/master/examples/android)

## 概述

想通过APP远程控制一个智能设备，您需要FAE的支持，如果WIFI模块（硬件）已经准备就绪，那么您只需要完成以下几步

1、通过Fogcloud平台注册一个APP，得到appid，因为下面需要用到

2、对于一个新用户而言，首先需要注册用户，获取验证码、验证验证码、注册登录等，这些都在[MiCOUser](#MiCOUser)部分

3、注册完成后，我还没有一个可以控制的设备，我需要绑定一个设备，绑定之前需要先让设备连上WIFI路由器，这些都在[fogice](#fogice)部分
>1)让设备连上路由器(EasyLink)，

>2)连上以后找到这个设备的IP(SearchDevice)，

>3)绑定她(bindDevice)

4、我可以将我名下的设备分享给别人使用，这些在[ManageDevices](#ManageDevices)部分

## 添加授权

```js
    compile 'io.fogcloud.sdk:fog2_sdk:0.1.2'
```

--------------------------------------
<div id="MiCO"></div>

### MiCO 全局设置

* [初始化](#init)

<div id="MiCOUser"></div>

### MiCOUser 用户管理

__基础功能__

* [获取验证码](#getVerifyCode)
* [验证验证码](#checkVerifyCode)
* [设置初始密码](#setPassword)
* [登录](#login)
* [刷新Token](#refreshToken)

<div id="fogice"></div>

### fogice 设备管理

__设备配网__

* [获取SSID](#getSSID)
* [开始配网](#startEasyLink)
* [停止配网](#stopEasyLink)

__搜索设备__

* [开始搜索设备](#startSearchDevices)
* [停止搜索设备](#stopSearchDevices)

__绑定设备__

* [绑定设备](#bindDevice)
* [解绑设备](#unBindDevice)

<div id="ManageDevices"></div>

__设备管理__

* [获取设备列表](#getDeviceList)
* [获取设备详情](#getDeviceInfo)
* [修改设备名称](#updateDeviceAlias)
* [获取设备分享码](#getShareVerCode)
* [通过分享码绑定设备](#addDeviceByVerCode)
* [添加子设备](#addSubDevice)

__权限管理__

* [获取用户列表](#getMemberList)
* [移除用户权限](#removeBindRole)

<div id="ControlRemoteDevice"></div>

__远程控制__

* [监听远程设备](#startListenDevice)
* [发送指令](#sendCommand)
* [发送命令至子设备](#sendCommandSub)
* [增加订阅通道](#addDeviceListener)
* [移除订阅通道](#removeDeviceListener)
* [停止监听设备](#stopListenDevice)



--------------------------------------

<div id="init">初始化</div>

##  获取验证码init
    初始化host，因为连接的服务器分为测试环境和生产环境

    init(String host)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
host     | String       | 服务器的域名(默认为https://v2.fogcloud.io)

##### 示例代码
```java
Fog fog = new Fog(this.getApplicationContext());
fog.init("https://v2.fogcloud.io")
```

<div id="getVerifyCode">获取验证码</div>

### getVerifyCode
    获取验证码，目前仅支持大陆手机号，海外用户请使用邮箱注册

    getVerifyCode(String loginname, String appid, FogCallBack fogcallback)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
loginName     | String       | 登录名，邮箱或者手机号
appid         | String       | 在Fogcloud平台注册的APP的id

##### callback
fogcb
- 类型：FogCallBack
- 描述：接口调用成功后的回调函数
```js
{
    "meta":
        {
            "message":"Send message to phone successfully.",
            "code":0
        },
    "data":{}
}
        

```

##### 示例代码
```java
Fog fog = new Fog(this.getApplicationContext());
String loginname = "13122222222";
String appid = "81d79316-bb5a-11e5-a739-00163e0204c0";
fog.getVerifyCode(loginname, appid, new FogCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG, message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, code + " " + message);
    }
});
```

<div id="checkVerifyCode">验证验证码</div>

### checkVerifyCode
    验证获取到的手机验证码

    checkVerifyCode(String loginname, String vercode, String appid, FogCallBack fogcallback)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
loginName     | String       | 登录名，邮箱或者手机号
vercode         | String       | 手机收到的验证码
appid         | String       | 在Fogcloud平台注册的APP的id

##### callback
fogcb
- 类型：FogCallBack
- 描述：接口调用成功后的回调函数
```js
{
    "meta":
        {
            "message":"sign up ok",
            "code":0
        },
    "data":{
        "token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbmR1c2VyaWQiOiIzODMxNDVlNDc2N2IxMWU3YTU1NGZhMTYzZTg3NjE2NCIsIm9yaWdfaWF0IjoxNTAyMDczMTc5LCJpZGVudGlmaWNhdGlvbiI6IjEzODE2Nzk1MjI1IiwiZXhwIjoxNTAyNjc3OTc5LCJhcHBpZCI6ImYxNmZhMDcyLTc2NzItMTFlNy1hNTU0LWZhMTYzZTg3NjE2NCJ9.ewYMWttucaHHXah3vQhZQs_AjAltGcDAZrgOzss-ksw",
        "clientid":"383145e4767b11e7a554fa163e876164"
    }
    
}
```

##### 示例代码
```java
Fog fog = new Fog(this.getApplicationContext());
String loginname = "13122222222";
String vercode = "556897";
String appid = "81d79316-bb5a-11e5-a739-00163e0204c0";
Fog.checkVerifyCode(loginname, vercode, appid, new FogCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG, message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, code + " " + message);
    }
});
```

<div id="setPassword">设置初始密码</div>

## setPassword
    验证码验证成功后，输入密码注册新用户

    setPassword(String password, FogCallBack fogcallback, String token)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
password     | String       | 登录名，邮箱或者手机号


##### callback
fogcb
- 类型：FogCallBack
- 描述：接口调用成功后的回调函数
```js
{
    "meta":
        {
            "message":"Update password successfully.",
            "code":0
        },
    "data":{}
}
```

##### token
- 类型：String, 不可为空
- 描述：用户登录后获取的token

##### 示例代码
```java
Fog fog = new Fog(this.getApplicationContext());
String password = "123456";
String appid = "81d79316-bb5a-11e5-a739-00163e0204c0";
String token = "xxx81d79316-bb5a-11e5-a739-00163e0204c0xxx";
micoUser.register(password, appid, new FogCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG, message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, code + " " + message);
    }
}, token);
```

<div id="login">登录</div>

## login
    用户登录

    login(String phone, String password, String appid, FogCallBack fogcallback)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
loginName     | String       | 登录名，邮箱或者手机号
password     | String       | 用户密码
appid         | String       | 在Fogcloud平台注册的APP的id

##### callback
fogcb
- 类型：FogCallBack
- 描述：接口调用成功后的回调函数
```js
{"meta":{"message":"ok","code":0},"data":{"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbmR1c2VyaWQiOiIzODMxNDVlNDc2N2IxMWU3YTU1NGZhMTYzZTg3NjE2NCIsIm9yaWdfaWF0IjoxNTAyMDczMzU0LCJpZGVudGlmaWNhdGlvbiI6IjEzODE2Nzk1MjI1IiwiZXhwIjoxNTAyNjc4MTU0LCJhcHBpZCI6ImYxNmZhMDcyLTc2NzItMTFlNy1hNTU0LWZhMTYzZTg3NjE2NCJ9.zIJSbbLd1yt02T5KMgNrSpGWh1ySBZVOzybie6BKe1o","clientid":"383145e4767b11e7a554fa163e876164"}}
```

##### 示例代码
```java
MiCOUser micoUser = new MiCOUser();
String userName = "13122222222";
String password = "123456";
String appid = "81d79316-bb5a-11e5-a739-00163e0204c0";
micoUser.login(userName, password, appid, new FogCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG, message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, code + " " + message);
    }
});
```

<div id="refreshToken">刷新Token</div>

## refreshToken
    刷新用户的token，服务器端默认7天内生效，刷新后可以后延7天，失效了就需要重新登录

    refreshToken(String token, FogCallBack fogcallback)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
token     | String       | 用户登录后服务器端返回的token值，一般保存在localstorege里，以便下一次获取使用

##### callback
fogcb
- 类型：FogCallBack
- 描述：接口调用成功后的回调函数
```js
{"meta":{"message":"ok","code":0},"data":{"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbmR1c2VyaWQiOiIzODMxNDVlNDc2N2IxMWU3YTU1NGZhMTYzZTg3NjE2NCIsIm9yaWdfaWF0IjoxNTAyMDc4MTk3LCJpZGVudGlmaWNhdGlvbiI6IjEzODE2Nzk1MjI1IiwiZXhwIjoxNTAyNjgyOTk3LCJhcHBpZCI6ImYxNmZhMDcyLTc2NzItMTFlNy1hNTU0LWZhMTYzZTg3NjE2NCJ9.dXV5HNjwR5MmhOHV_psV1i7bv_9-eABQGU1wAS7rPbQ","clientid":"383145e4767b11e7a554fa163e876164"}}
```

##### 示例代码
```java
MiCOUser micoUser = new MiCOUser();
String userToken = "XXX...";
micoUser.refreshToken(userToken, new FogCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG, message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, code + " " + message);
    }
});
```

------------------------------
## 以下是设备管理部分 

<div id="getSSID">获取SSID</div>

## getSSID
    获取当前手机连接的WIFI的名称，即ssid

    String getSSID()

##### callback
ssid
- 类型：String
- 描述：当前WIFI的名称

##### 示例代码
```java
Fog fog = new Fog(MainActivity.this);
Log.d(TAG, fog.getSSID());
```

<div id="startEasyLink">开始配网</div>

## startEasyLink
    发送数据包(包含ssid和password)给设备，每10ms发一次，连续发10s，再停止10s，继续发，如此反复

    startEasyLink(String ssid, String password, int runSecond, int sleeptime, EasyLinkCallBack easylinkcb, String extraData)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
ssid     | String       | 准备发送的ssid
password     | String       | SSID对应的WIFI密码
isSendIP     | boolean       | 是否发送手机的ip给设备,默认false不发送
runSecond         | int       | 发送持续的时间，到点了就停止发送, 单位ms
sleeptime         | int       | 每包数据的间隔时间，建议未20, 单位ms
extraData         | String       | 需要发送给设备的额外信息

##### callback
easylinkcb
- 类型：EasyLinkCallBack
- 描述：接口调用成功后的回调函数

##### 示例代码
```java
Fog fog = new Fog(MainActivity.this);
String ssidStr = "mxchip";
String passwordStr = "123456";
int runs = 10000; //发送10秒即关闭
fog.startEasyLink(ssidStr, passwordStr, runs, new EasyLinkCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG, message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, code + " " + message);
    }
});
```

<div id="stopEasyLink">停止配网</div>

## stopEasyLink
    停止发送数据包

    stopEasyLink(EasyLinkCallBack easylinkcb)

##### callback
easylinkcb
- 类型：EasyLinkCallBack
- 描述：接口调用成功后的回调函数

##### 示例代码
```java
Fog fog = new Fog(MainActivity.this);
fog.stopEasyLink(new EasyLinkCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG, message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, code + " " + message);
    }
});
```

<div id="startSearchDevices">开始搜索设备</div>

## startSearchDevices
    设备连上WIFI路由器后，我就可以通过这个接口来发现他，

    当然，前提是手机和设备必须在同一个网段

    startSearchDevices(String serviceName, SearchDeviceCallBack searchdevcb)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
serviceName     | String       | 不可为空, 一般"_easylink._tcp.local."只要你使用的是庆科的模块，这个名字是不会变的

##### callback
searchdevcb
- 类型：SearchDeviceCallBack
- 描述：接口调用成功后的回调函数
```js
[
  {
    "Name":"EMW3080B Module#100318",
    "IP":"192.168.137.204",
    "Port":0,
    "fog_v3_is_need_stop_easylink":"false",
    "fog_v3_productid":"8766f11c712711e78d1700163e03b4d6",
    "fog_v3_devicesn":"B0F893100318",
    "fog_v3_deviceid":"b557aa68713011e78d1700163e03b4d6"
  }
]
```

##### 示例代码
```java
Fog fog = new Fog(MainActivity.this);
String serviceName = "_easylink._tcp.local.";
fog.startSearchDevices(serviceName, new SearchDeviceCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG, message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, message);
    }
    @Override
    public void onDevicesFind(JSONArray deviceStatus) {
        if (!deviceStatus.equals("")) {
            Log.d(TAG, deviceStatus.toString());
        }
    }
});
```

<div id="stopSearchDevices">停止搜索设备</div>

## stopSearchDevices
    停止发现设备，发现了需要激活的设备，主动调用此接口

    stopSearchDevices(SearchDeviceCallBack searchdevcb)

##### callback
searchdevcb
- 类型：SearchDeviceCallBack
- 描述：接口调用成功后的回调函数

##### 示例代码
```java
Fog fog = new Fog(MainActivity.this);
fog.stopSearchDevices(new SearchDeviceCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG, message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, code + " " + message);
    }
});
```

<div id="bindDevice">绑定设备</div>

## bindDevice
    通过startSearchDevices获取准备绑定设备的信息，从中提取出IP地址，和deviceid，再通过此接口绑定设备

    bindDevice(String deviceid, ManageDeviceCallBack managedevcb, String token)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
deviceid     | String       | 设备id

##### callback
managedevcb
- 类型：ManageDeviceCallBack
- 描述：接口调用成功后的回调函数
```js
{
  "meta":
    {
      "message":"Bind superuser to device successfully.",
      "code":0},
  "data":
    {
      "devicename":"rehau",
      "deviceid":"2d7e2d68767311e7a554fa163e876164
    }
}
```

##### token
- 类型：String, 不可为空
- 描述：用户登录后获取的token

##### 示例代码
```java
Fog fog = new Fog(MainActivity.this);
String deviceId = "2d7e2d68767311e7a554fa163e876164";
String port = "8002";
String token = "xxx...";
fog.bindDevice(deviceId, new ManageDeviceCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG, message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, code + " " + message);
    }
}, token);
```

<div id="unBindDevice">解绑设备</div>

## unBindDevice
    用户不准备使用此设备时候，调用此接口解绑设备，

    1）如果是普通用户或者普通管理员，解绑只会解绑自己和设备的绑定关系

    2）如果是超级管理员，那么解绑后，所有人均不能控制这个设备了

    unBindDevice(String deviceid, final ManageDeviceCallBack managedevcb, String token)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
deviceid     | String       | 设备的deviceid

##### callback
managedevcb
- 类型：ManageDeviceCallBack
- 描述：接口调用成功后的回调函数
```js
{
  "meta": {
    "message": "Unbind superuser successfully",
    "code": 0
  },
  "data": {
  }
}
```

##### token
- 类型：String, 不可为空
- 描述：用户登录后获取的token

##### 示例代码
```java
Fog fog = new Fog(MainActivity.this);
String deviceid = "f71246d8-b9db-11e5-a739-00163e0204c0";
String token = "xxx...";
fog.unBindDevice(deviceid, new ManageDeviceCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG, message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, message);
    }
}, token);
```

<div id="getDeviceList">获取设备列表</div>

## getDeviceList
    获取本账号名下的所有相关设备

    getDeviceList(FogCallBack fogcallback, String token)

##### callback
fogcb
- 类型：FogCallBack
- 描述：接口调用成功后的回调函数

##### token
- 类型：String, 不可为空
- 描述：用户登录后获取的token

##### 示例代码
```java
Fog fog = new Fog(MainActivity.this);
String token = "xxx...";
micoUser.getDeviceList(new FogCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG, message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, code + " " + message);
    }
}, token);
```

<div id="getDeviceInfo">获取设备详情</div>

## getDeviceInfo
    获取设备信息

    getDeviceInfo(String deviceid, FogCallBack fogcallback, String token)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
deviceid     | String       | 设备的deviceid

##### callback
fogcb
- 类型：FogCallBack
- 描述：接口调用成功后的回调函数
```js
{
  "meta": {
    "message": "device list by user",
    "code": 0
  },
  "data": {
    "alias": "爱焙客",
    "online": false,
    "devicepw": "7176"
  }
}
```

##### token
- 类型：String, 不可为空
- 描述：用户登录后获取的token

##### 示例代码
```java
Fog fog = new Fog(MainActivity.this);
String deviceid = "f71246d8-b9db-11e5-a739-00163e0204c0";
String token = "xxx...";
fog.getDeviceInfo(deviceid, new FogCallBack() {
    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onFailure(int code, String message) {

    }
}, token);
```

<div id="updateDeviceAlias">修改设备名称</div>

## updateDeviceAlias
    获取设备信息

    updateDeviceAlias(String deviceid, String alias, ManageDeviceCallBack managedevcb, String token)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
deviceid     | String       | 设备的deviceid
alias     | String       | 设备名称

##### callback
managedevcb
- 类型：ManageDeviceCallBack
- 描述：接口调用成功后的回调函数

##### token
- 类型：String, 不可为空
- 描述：用户登录后获取的token

##### 示例代码
```java
Fog fog = new Fog(MainActivity.this);
String deviceid = "f71246d8-b9db-11e5-a739-00163e0204c0";
String alias = "好名字";
String token = "xxx...";
fog.updateDeviceAlias(deviceid, alias, new ManageDeviceCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG, message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, code + " " + message);
    }
}, token);
```

<div id="getShareVerCode">获取设备分享码</div>

## getShareVerCode
    我是超级管理员或者普通管理员，那么我就能把我名下的设备分享给别人，首先需要获取分享码

    getShareVerCode(String deviceid, ManageDeviceCallBack managedevcb, String token)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
deviceid     | String       | 设备的deviceid
role     | int       | 设备的权限
token         | String       | 用户登录后获取的token

##### callback
managedevcb
- 类型：ManageDeviceCallBack
- 描述：接口调用成功后的回调函数

##### 示例代码
```java
Fog fog = new Fog(MainActivity.this);
String deviceid = "f71246d8-b9db-11e5-a739-00163e0204c0";
int role = 3; 
String token = "xxx...";
getShareVerCode(deviceid, new ManageDeviceCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG, message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, code + " " + message);
    }
}, token);
```

<div id="addDeviceByVerCode">通过分享码绑定设备</div>

## addDeviceByVerCode
    解析出二维码里的内容，并通过此接口绑定被授权的设备

    addDeviceByVerCode(ShareDeviceParams sdevp, ManageDeviceCallBack managedevcb, String token)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
sdevp     | ShareDeviceParams       | ShareDeviceParams至少包含以下的信息

##### ShareDeviceParams
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
bindvercode     | String       | getShareVerCode接口获取的sharcode, 不可为空
iscallback         | boolean       | 是否返回绑定状态，此版本请都设置为false, 不可为空

##### callback
managedevcb
- 类型：ManageDeviceCallBack
- 描述：接口调用成功后的回调函数
```js
{
  "meta": {
    "message": "您是超级用户，无法更新授权",
    "code": 23102
  },
  "data": {
    
  }
}
```

##### token
- 类型：String, 不可为空
- 描述：用户登录后获取的token

##### 示例代码
```java
Fog fog = new Fog(MainActivity.this);
ShareDeviceParams sdevp = new ShareDeviceParams();
sdevp.bindvercode = "xxx...";
sdevp.bindingtype = "home";
sdevp.iscallback = false;
String token = "xxx...";
fog.addDeviceByVerCode(sdevp, new ManageDeviceCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG, message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, code + " " + message);
    }
}, token);
```


<div id="getMemberList">获取用户列表</div>

## getMemberList
    获取此设备名下的用户，只能看到自己以外的用户

    getMemberList(String deviceid, FogCallBack fogcallback, String token)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
deviceid     | String       | 设备的deviceid

##### callback
fogcb
- 类型：FogCallBack
- 描述：接口调用成功后的回调函数

```js
{
  "meta": {
    "message": "成功根据设备ID获取用户列表",
    "code": 0
  },
  "data": "[
        {
          "enduserid": "e32bd592-1bf8-11e6-a739-00163e0204c0",
          "phone": "",
          "email": "wzbdroid@126.com",
          "nickname": "",
          "realname": "",
          "is_active": true,
          "app": "db456b4a-17fc-11e6-a739-00163e0204c0"
        }
  ]"
}
```

##### token
- 类型：String, 不可为空
- 描述：用户登录后获取的token

##### 示例代码
```java
fogice fogice = new fogice(MyListViewActivity.this);
String deviceid = "xxx-b9db-11e5-a739-00163e0204c0";
String token = "xxx...";
fogice.getMemberList(deviceid, new FogCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG + "getMemberList", message);
        setAdapter(message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, message);
    }
}, token);
```

<div id="removeBindRole">移除用户权限</div>

## removeBindRole
    删除某人的设备管理权限

    removeBindRole(String deviceid, String enduserid, FogCallBack fogcallback, String token)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
deviceid     | String       | 设备的deviceid
enduserid     | String       | 用户的id

##### callback
fogcb
- 类型：FogCallBack
- 描述：接口调用成功后的回调函数
```js
{
  "meta": {
    "message": "用户解除成功",
    "code": 0
  },
  "data": {
    "enduserid": "xxx-fbc5-11e5-a739-00163e0204c0"
  }
}
```

##### token
- 类型：String, 不可为空
- 描述：用户登录后获取的token

##### 示例代码
```java
fogice fogice = new fogice(MyListViewActivity.this);
String mdeviceid = "xxx-b9db-11e5-a739-00163e0204c0";
String menduserid = "xxx11e5-a739-00163e0204c0";
String token = "xxx...";
fogice.removeBindRole(mdeviceid, menduserid, new FogCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG, message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, message);
    }
},token);
```

<div id="startListenDevice">监听远程设备</div>

## startListenDevice
    远程监听设备，获取设备上报的数据

    startListenDevice(ListenDevParFog listendevparams, ControlDeviceCallBack ctrldevcb)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
listendevparams     | ListenDevParFog       | ListenDevParFog至少包含以下的信息

##### ShareDeviceParams
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
deviceid     | String       | 设备的deviceid
host     | String       | 云端的host地址，默认为"mqtt.fogcloud.io"
port     | String       | 云端的port，默认为"1883"
userName     | String       | enduserid
passWord     | String       | devicepw, 与用户密码相同，或者与注册验证码相同
clientID     | String       | enduserid，即用户登录后获取的enduserid
isencrypt     | boolean       | 是否使用SSL加密

##### callback
ctrldevcb
- 类型：ControlDeviceCallBack
- 描述：接口调用成功后的回调函数

##### 示例代码
```java
Fog fog = new Fog(MainActivity.this);
ListenDevParFog listendevparams = new ListenDevParFog();

listendevparams.deviceid = "f71246d8-b9db-11e5-a739-00163e0204c0";
listendevparams.host = "http://101.201.101.153";
listendevparams.port = "1883";
listendevparams.userName = enderuserid;
listendevparams.passWord = "123456";
listendevparams.clientID = enderuserid;
listendevparams.isencrypt = false;
fog.startListenDevice(listendevparams, new ControlDeviceCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG, message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, code + " " + message);
    }
    @Override
    public void onDeviceStatusReceived(int code, String messages) {
        Log.d("---" + code + "---", messages);
    }
});
```

<div id="sendCommand">发送指令</div>

## sendCommand
    发送指令给设备端
    sendCommand(CommandPara cmdpara,  ControlDeviceCallBack ctrldevcb)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
cmdpara     | CommandPara       | 发送命令 参数结合

endpoint+"/"+cmdpara.productid+"/"+cmdpara.deviceid+"/command/json";


##### callback
ctrldevcb
- 类型：ControlDeviceCallBack
- 描述：接口调用成功后的回调函数

##### token
- 类型：String, 不可为空
- 描述：用户的token

##### 示例代码
```java
CommandPara cmdPara = new CommandPara();
cmdPara.deviceid = deviceid;
cmdPara.devicepw = devicepw;
cmdPara.command = "{}";
fog.sendCommandSub(cmdPara, new ControlDeviceCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG + "onSuccess", message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG + "onFailure", code + " " + message);
    }
}, token);
```

<div id="addDeviceListener">增加订阅通道</div>

## addDeviceListener
    增加订阅的频道

    addDeviceListener(String topic, int qos, ControlDeviceCallBack ctrldevcb)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
topic     | String       | 需要定义的topic
qos     | int       | 0

##### callback
ctrldevcb
- 类型：ControlDeviceCallBack
- 描述：接口调用成功后的回调函数

##### 示例代码
```java
String topic = "xxx...";
int qos = 0;
fog.addDeviceListener(topic, qos, new ControlDeviceCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG + "onSuccess", message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG + "onFailure", code + " " + message);
    }
});
```

<div id="removeDeviceListener">移除订阅通道</div>

## removeDeviceListener
    移除某个监听的topic

    removeDeviceListener(String topic, ControlDeviceCallBack ctrldevcb)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
topic     | String       | 需要定义的topic

##### callback
ctrldevcb
- 类型：ControlDeviceCallBack
- 描述：接口调用成功后的回调函数

##### 示例代码
```java
String topic = "xxx...";
fog.removeDeviceListener(topic, qos, new ControlDeviceCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG + "onSuccess", message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG + "onFailure", code + " " + message);
    }
});
```

<div id="stopListenDevice">停止监听设备</div>

## stopListenDevice
    停止监听设备

    stopListenDevice(ControlDeviceCallBack ctrldevcb)

##### callback
ctrldevcb
- 类型：ControlDeviceCallBack
- 描述：接口调用成功后的回调函数

##### 示例代码
```java
Fog fog = new Fog(MainActivity.this);
fog.stopListenDevice(new ControlDeviceCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG + "onDestroy onSuccess", message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG + "onDestroy onFailure", code + " " + message);
    }
});
```

