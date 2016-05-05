##MiCOSDK开发指南

##**概述**

想通过APP远程控制一个智能设备，您需要FAE的支持，如果WIFI模块（硬件）已经准备就绪，那么您只需要完成以下几步

1、通过Fogcloud平台注册一个APP，得到appid，因为下面需要用到

2、对于一个新用户而言，首先需要注册用户，获取验证码、验证验证码、注册登录等，这些都在[MiCOUser](#MiCOUser)部分

3、注册完成后，我还没有一个可以控制的设备，我需要绑定一个设备，绑定之前需要先让设备连上WIFI路由器，

1)让设备连上路由器(EasyLink)，

2)连上以后找到这个设备的IP(SearchDevice)，

3)绑定她(bindDevice)

这些都在[MiCODevice](#MiCODevice)部分

4、我可以将我名下的设备分享给别人使用，这些在[ManageDevices](#ManageDevices)部分

5、控制设备分云端远程控制[ControlRemoteDevice](#ControlRemoteDevice)和局域网内本地控制[ControlLocalDevice](#ControlLocalDevice)

6、如果是烤箱或者电饭煲等智能设备，也许需要用到云菜谱[ClodRecipe](#ClodRecipe)

<br/>
<br/>
<div id="MiCOUser"></div>
##**MiCOUser** 用户管理

__基础功能__

* [获取验证码](#getVerifyCode)

* [验证验证码](#checkVerifyCode)

* [注册](#register)

* [登录](#login)

* [刷新Token](#refreshToken)

__权限管理__

* [获取用户列表](#getMemberList)

* [移除用户权限](#removeBindRole)

<div id="MiCODevice"></div>
##**MiCODevice** 设备管理

__EasyLink__

* [获取SSID](#getSSID)

* [开始配网](#startEasyLink)

* [停止配网](#stopEasyLink)

__SearchDevice__

* [开始搜索设备](#startSearchDevices)

* [停止搜索设备](#stopSearchDevices)

__BindDevice__

* [绑定设备](#bindDevice)

* [解绑设备](#unBindDevice)

<div id="ManageDevices"></div>
__ManageDevices__

* [获取设备列表](#getDeviceList)

* [获取设备详情](#getDeviceInfo)

* [获取设备分享码](#getShareVerCode)

* [生成二维码](#creatQrCode)

* [通过分享码绑定设备](#addDeviceByVerCode)

<div id="ControlRemoteDevice"></div>
__ControlRemoteDevice__

* [监听远程设备](#startListenDevice)

* [发送指令](#sendCommand)

* [增加订阅通道](#addDeviceListener)

* [移除订阅通道](#removeDeviceListener)

* [停止监听设备](#stopListenDevice)

<div id="ControlLocalDevice"></div>
__ControlLocalDevice__

* [连接本地设备](#connectLocalDevice)

* [发送本地控制指令](#sendLocalCommand)

* [断开与本地设备的连接](#disconnectLocalDevice)

<div id="ClodRecipe"></div>
__ClodRecipe__

* [通过类型获取菜谱列表](#getCookBookByType)

* [通过名称获取菜谱列表](#getCookBookByName)

* [获取菜谱详情](#getCookBookInfo)

* [给菜谱点赞](#addCookBookLikeNo)

* [取消点赞](#delCookBookLikeNo)

* [创建定时任务](#createScheduleTask)

* [创建延时任务](#creatDelayTask)

<br/>
<br/>
<div id="getVerifyCode"></div>
#**getVerifyCode**

    获取手机验证码，填入的内容需要为手机号码

    getVerifyCode(String loginname, String appid, UserCallBack usercb)

##params

loginname
- 类型：String, 不可为空
- 描述：手机号码或邮箱

appid
- 类型：String, 不可为空
- 描述：在Fogcloud平台注册的APP的id

##callback

usercb
- 类型：UserCallBack
- 描述：接口调用成功后的回调函数

```js
{
  "meta": {
    "message": "验证邮件已发送",
    "code": 23000
  },
  "data": {
  }
}
```

##示例代码

```java
MiCOUser micoUser = new MiCOUser();
String loginname = "13122222222";
String appid = "81d79316-bb5a-11e5-a739-00163e0204c0";
micoUser.getVerifyCode(loginname, appid, new UserCallBack() {

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

##可用性

    Android系统4.0+

<div id="checkVerifyCode"></div>
#**checkVerifyCode**

    验证获取到的手机验证码

    checkVerifyCode(String loginname, String vercode, String appid, UserCallBack usercb)

##params

loginname
- 类型：String, 不可为空
- 描述：手机号码或邮箱

vercode
- 类型：String, 不可为空
- 描述：手机收到的验证码

appid
- 类型：String, 不可为空
- 描述：在Fogcloud平台注册的APP的id

##callback

usercb
- 类型：UserCallBack
- 描述：接口调用成功后的回调函数

```js
{
  "token": "eyJhVlSV8I...",
  "clientid": "xxx-deaa-11e5-a739-00163e0204c0"
}
```

##示例代码

```java
MiCOUser micoUser = new MiCOUser();
String loginname = "13122222222";
String vercode = "556897";
String appid = "81d79316-bb5a-11e5-a739-00163e0204c0";
micoUser.checkVerifyCode(loginname, vercode, appid, new UserCallBack() {

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

##可用性

    Android系统4.0+

<div id="register"></div>
#**register**

    验证码验证成功后，输入密码注册新用户

    register(String password1, String password2, String appid, UserCallBack usercb, String token)

##params

password1
- 类型：String, 不可为空
- 描述：用户密码

password2
- 类型：String, 不可为空
- 描述：用户密码

appid
- 类型：String, 不可为空
- 描述：在Fogcloud平台注册的APP的id

token
- 类型：String, 不可为空
- 描述：验证验证码后返回的token

##callback

usercb
- 类型：UserCallBack
- 描述：接口调用成功后的回调函数

```js
{
  "meta": {
    "message": "用户密码修改成功",
    "code": 0
  },
  "data": {
  }
}
```

##示例代码

```java
MiCOUser micoUser = new MiCOUser();
String password1 = "123456";
String password2 = "123456";
String appid = "81d79316-bb5a-11e5-a739-00163e0204c0";
String token = "xxx81d79316-bb5a-11e5-a739-00163e0204c0xxx";
micoUser.register(password1, password2, appid, new UserCallBack() {
                        
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

##可用性

    Android系统4.0+

<div id="login"></div>
#**login**

    用户登录

    login(String phone, String password, String appid, UserCallBack usercb)

##params

phone
- 类型：String, 不可为空
- 描述：手机号码

password
- 类型：String, 不可为空
- 描述：用户密码

appid
- 类型：String, 不可为空
- 描述：在Fogcloud平台注册的APP的id

##callback

usercb
- 类型：UserCallBack
- 描述：接口调用成功后的回调函数

```js
{
  "token": "eyJhbGcMCPNKJI...",
  "clientid": "xxx-deaa-11e5-a739-00163e0204c0"
}
```

##示例代码

```java
MiCOUser micoUser = new MiCOUser();
String userName = "13122222222";
String password = "123456";
String appid = "81d79316-bb5a-11e5-a739-00163e0204c0";
micoUser.login(userName, password, appid, new UserCallBack() {

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

##可用性

    Android系统4.0+

<div id="refreshToken"></div>
#**refreshToken**

    刷新用户的token，服务器端默认7天内生效，刷新后可以后延7天，失效了就需要重新登录

    refreshToken(String token, UserCallBack usercb)

##params

token
- 类型：String, 不可为空
- 描述：用户登录后服务器端返回的token值，一般保存在localstorege里，以便下一次获取使用

##callback

usercb
- 类型：UserCallBack
- 描述：接口调用成功后的回调函数

```js
{
  "token": "eyJhbGcMCPNKJI...",
  "clientid": "xxx-deaa-11e5-a739-00163e0204c0"
}
```

##示例代码

```java
MiCOUser micoUser = new MiCOUser();
String userToken = "XXX...";
micoUser.refreshToken(userToken, new UserCallBack() {

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

##可用性

    Android系统4.0+

<div id="getMemberList"></div>
#**getMemberList**

    获取此设备名下的用户，只能看到自己意外的用户

    getMemberList(String deviceid, UserCallBack usercb, String token)

##params

deviceid
- 类型：String, 不可为空
- 描述：设备的deviceid

token
- 类型：String, 不可为空
- 描述：用户登录后获取的token

##callback

usercb
- 类型：UserCallBack
- 描述：接口调用成功后的回调函数

##示例代码

```java
MiCOUser micoUser = new MiCOUser();
String deviceid = "xxx-b9db-11e5-a739-00163e0204c0";
String token = "xxx...";

micoUser.getMemberList(deviceid, new UserCallBack() {

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

##可用性

    Android系统4.0+

<div id="removeBindRole"></div>
#**removeBindRole**

    删除某人的设备管理权限

    removeBindRole(String deviceid, String enduserid, UserCallBack usercb, String token)

##params

deviceid
- 类型：String, 不可为空
- 描述：设备的deviceid

enduserid
- 类型：String, 不可为空
- 描述：用户的id

token
- 类型：String, 不可为空
- 描述：用户登录后获取的token

##callback

usercb
- 类型：UserCallBack
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

##示例代码

```java
MiCOUser micoUser = new MiCOUser();
String mdeviceid = "xxx-b9db-11e5-a739-00163e0204c0";
String menduserid = "xxx11e5-a739-00163e0204c0";
String token = "xxx...";

micoUser.removeBindRole(mdeviceid, menduserid, new UserCallBack() {

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

##可用性

    Android系统4.0+


##**以下是设备管理部分** 


<div id="getSSID"></div>
#**getSSID**

    获取当前手机连接的WIFI的名称，即ssid

    String getSSID()

##callback

ssid
- 类型：String
- 描述：当前WIFI的名称

##示例代码

```java
MiCODevice micodev = new MiCODevice(MainActivity.this);
Log.d(TAG, micodev.getSSID());
```

##可用性

    Android系统4.0+

<div id="startEasyLink"></div>
#**startEasyLink**

    发送数据包(包含ssid和password)给设备，每10ms发一次，连续发10s，再停止10s，继续发，如此反复

    startEasyLink(String ssid, String password, int runSecond, EasyLinkCallBack easylinkcb)

##params

ssid
- 类型：String, 不可为空
- 描述：准备发送的ssid

password
- 类型：String, 不可为空
- 描述：ssid对应的WIFI密码

runSecond
- 类型：int, 不可为空，单位ms
- 描述：发送持续的时间，到点了就停止发送

##callback

easylinkcb
- 类型：EasyLinkCallBack
- 描述：接口调用成功后的回调函数

##示例代码

```java
MiCODevice micodev = new MiCODevice(MainActivity.this);
String ssidStr = "mxchip";
String passwordStr = "123456";
int runs = 10000; //发送10秒即关闭
micodev.startEasyLink(ssidStr, passwordStr, runs, new EasyLinkCallBack() {

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

##可用性

    Android系统4.0+

<div id="stopEasyLink"></div>
#**stopEasyLink**

    停止发送数据包

    stopEasyLink(EasyLinkCallBack easylinkcb)

##callback

easylinkcb
- 类型：EasyLinkCallBack
- 描述：接口调用成功后的回调函数

##示例代码

```java
MiCODevice micodev = new MiCODevice(MainActivity.this);
micodev.stopEasyLink(new EasyLinkCallBack() {

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

##可用性

    Android系统4.0+

<div id="startSearchDevices"></div>
#**startSearchDevices**

    设备连上WIFI路由器后，我就可以通过这个接口来发现他，

    当然，前提是手机和设备必须在同一个网段

    startSearchDevices(String serviceName, SearchDeviceCallBack searchdevcb)

##params

serviceName
- 类型：String, 不可为空, "_easylink._tcp.local."
- 描述：只要你使用的是庆科的模块，这个名字是不会变的

##callback

searchdevcb
- 类型：SearchDeviceCallBack
- 描述：接口调用成功后的回调函数

```js
[
  {
    "deviceName": "MiCOKit 3165#0C2EB6",
    "deviceMac": "D0:BA:E4:0C:2E:B6",
    "deviceIP": "172.16.112.6",
    "deviceMacbind": "0",
    "hardwareID": "0",
    "fogProductID": "6486b2d1-0ee9-4647-baa3-78b9cbc778f7",
    "isEasyLinkOK": "false",
    "isHaveSuperUser": "true",
    "remainingUserNumber": "0",
    "allInfo": "MAC=D0:BA:E4:0C:2E:B6Firmware ...",
    "devicePort": "8002"
  }
]
```

##示例代码

```java
MiCODevice micodev = new MiCODevice(MainActivity.this);
String serviceName = "_easylink._tcp.local.";
micodev.startSearchDevices(serviceName, new SearchDeviceCallBack() {

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

##可用性

    Android系统4.0+

<div id="stopSearchDevices"></div>
#**stopSearchDevices**

    停止发现设备，发现了需要激活的设备，主动调用此接口

    stopSearchDevices(SearchDeviceCallBack searchdevcb)

##callback

searchdevcb
- 类型：SearchDeviceCallBack
- 描述：接口调用成功后的回调函数

##示例代码

```java
MiCODevice micodev = new MiCODevice(MainActivity.this);
micodev.stopSearchDevices(new SearchDeviceCallBack() {

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

##可用性

    Android系统4.0+

<div id="bindDevice"></div>
#**bindDevice**

    通过startSearchDevices获取准备绑定设备的信息，从中提取出IP地址，和deviceid，再通过此接口绑定设备

    bindDevice(String ip, ManageDeviceCallBack managedevcb, String token)

##params

ip
- 类型：String, 不可为空
- 描述：即将绑定的设备的IP

token
- 类型：String, 不可为空
- 描述：用户登录后获取的token

##callback

managedevcb
- 类型：ManageDeviceCallBack
- 描述：接口调用成功后的回调函数

```js
{
  "meta": {
    "message": "设备重新被绑定",
    "code": 0
  },
  "data": {
    "devicepw": "2665",
    "deviceid": "e79f0250-ea5e-11e5-a739-00163e0204c0",
    "devicename": "开心手环"
  }
}
```

##示例代码

```java
MiCODevice micodev = new MiCODevice(MainActivity.this);
String ip = "192.168.1.123";
String token = "xxx...";
micodev.bindDevice(ip, deviceid, new ManageDeviceCallBack() {

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

##可用性

    Android系统4.0+

<div id="unBindDevice"></div>
#**unBindDevice**

    用户不准备使用此设备时候，调用此接口解绑设备，

    1）如果是普通用户或者普通管理员，解绑只会解绑自己和设备的绑定关系

    2）如果是超级管理员，那么解绑后，所有人均不能控制这个设备了

    unBindDevice(String deviceid, final ManageDeviceCallBack managedevcb, String token)

##params

deviceid
- 类型：String, 不可为空
- 描述：设备的deviceid


token
- 类型：String, 不可为空
- 描述：用户token

##callback

managedevcb
- 类型：ManageDeviceCallBack
- 描述：接口调用成功后的回调函数

```js
{
  "meta": {
    "message": "超级用户解除成功",
    "code": 0
  },
  "data": {
  }
}
```

##示例代码

```java
MiCODevice micodev = new MiCODevice(MainActivity.this);
String deviceid = "f71246d8-b9db-11e5-a739-00163e0204c0";
String token = "xxx...";
micoDev.unBindDevice(deviceid, new ManageDeviceCallBack() {

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

##可用性

    Android系统4.0+

<div id="getDeviceList"></div>
#**getDeviceList**

    获取本账号名下的所有相关设备

    getDeviceList(UserCallBack usercb, String token)

##callback

usercb
- 类型：UserCallBack
- 描述：接口调用成功后的回调函数

##token

token
- 类型：String, 不可为空
- 描述：用户登录后获取的token

##示例代码

```java
MiCOUser micoUser = new MiCOUser();
String token = "xxx...";
micoUser.getDeviceList(new UserCallBack() {

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

##可用性

    Android系统4.0+

<div id="getDeviceInfo"></div>
#**getDeviceInfo**

    获取设备信息

    getDeviceInfo(String deviceid, UserCallBack usercb, String token)

##params

deviceid
- 类型：String, 不可为空
- 描述：即将绑定的设备的deviceid

##callback

usercb
- 类型：UserCallBack
- 描述：接口调用成功后的回调函数

##token

token
- 类型：String, 不可为空
- 描述：用户登录后获取的token

##示例代码

```java
MiCOUser micoUser = new MiCOUser();
String deviceid = "f71246d8-b9db-11e5-a739-00163e0204c0";
String token = "xxx...";
micoUser.getDeviceInfo(deviceid, new UserCallBack() {

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

##可用性

    Android系统4.0+

<div id="getShareVerCode"></div>
#**getShareVerCode**

    我是超级管理员或者普通管理员，那么我就能把我名下的设备分享给别人，首先需要获取分享码

    getShareVerCode(String deviceid, ManageDeviceCallBack managedevcb, String token)

##params

deviceid
- 类型：String, 不可为空
- 描述：即将绑定的设备的deviceid

token
- 类型：String, 不可为空
- 描述：用户登录后获取的token

##callback

managedevcb
- 类型：ManageDeviceCallBack
- 描述：接口调用成功后的回调函数

##示例代码

```java
MiCODevice micodev = new MiCODevice(MainActivity.this);
String deviceid = "f71246d8-b9db-11e5-a739-00163e0204c0";
String token = "xxx...";
getShareVerCode(deviceid, new ManageDeviceCallBack() {

    @Override
    public void onSuccess(String message) {
        Log.d(TAG, message);
        String sharcode = new JSONObject(message).getString("data");
        sharcode = new JSONObject(sharcode).getString("vercode");
    }

    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, code + " " + message);
    }
}, token);
```

##可用性

    Android系统4.0+

<div id="creatQrCode"></div>
#**creatQrCode**

    将分享码和绑定的关系转成二维码，让别人通过手机扫描二维码绑定

    Bitmap creatQrCode(String message, int height, int width)

##params

message
- 类型：String, 不可为空
- 描述：需要生成二维码的信息

height
- 类型：int, 不可为空
- 描述：二维码的高度

width
- 类型：int, 不可为空
- 描述：二维码的宽度

vercode
- String, 不可为空
- 描述：getShareVerCode接口获取的sharcode

role
- 类型：int, 不可为空
- 描述：1超级用户 3普通用户 2管理员

bindingtype
- 类型：String, 不可为空
- 描述：绑定类型 sa 超级用户 home 家庭用户 guest 访客 other 其他

iscallback
- boolean, 不可为空
- 描述：是否返回绑定状态，此版本请都设置为false

##callback

Bitmap
- 类型：Bitmap
- 描述：可以直接将BitMap放入ImageView里，如下

##示例代码

```java
MiCODevice micodev = new MiCODevice(MainActivity.this);
ImageView qrcodeimg = (ImageView) findViewById(R.id.qrcodeimg);

String vercode = "xxx...";
int role = 3;
String bindingtype = "home";
boolean iscallback = false;

String message = "{\"vercode\":\""+ vercode +"\",\"role\":"+ role +",\"bindingtype\":\""+ bindingtype +"\",\"iscallback\":"+ iscallback + "}";
qrcodeimg.setImageBitmap(micoDev.creatQrCode(message, 220, 220));
```

##可用性

    Android系统4.0+

<div id="addDeviceByVerCode"></div>
#**addDeviceByVerCode**

    解析出二维码里的内容，并通过此接口绑定被授权的设备

    addDeviceByVerCode(ShareDeviceParams sdevp, ManageDeviceCallBack managedevcb, String token)

##params

sdevp
- 类型：ShareDeviceParams, 不可为空
- 描述：ShareDeviceParams至少包含以下的信息

bindvercode
- 类型：int, 不可为空
- 描述：二维码的高度

role
- 类型：int, 不可为空
- 描述：1超级用户 3普通用户 2管理员

bindingtype
- 类型：String, 不可为空
- 描述：绑定类型 sa 超级用户 home 家庭用户 guest 访客 other 其他

iscallback
- boolean, 不可为空
- 描述：是否返回绑定状态，此版本请都设置为false

token
- 类型：String, 不可为空
- 描述：用户登录后获取的token

##callback

managedevcb
- 类型：ManageDeviceCallBack
- 描述：接口调用成功后的回调函数

##示例代码

```java
MiCODevice micodev = new MiCODevice(MainActivity.this);

ShareDeviceParams sdevp = new ShareDeviceParams();
sdevp.bindvercode = "xxx...";
sdevp.role = 3;
sdevp.bindingtype = "home";
sdevp.iscallback = false;

String token = "xxx...";

micoDev.addDeviceByVerCode(sdevp, new ManageDeviceCallBack() {
    
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

##可用性

    Android系统4.0+

<div id="startListenDevice"></div>
#**startListenDevice**

    远程监听设备，获取设备上报的数据

    startListenDevice(ListenDeviceParams listendevparams, ControlDeviceCallBack ctrldevcb)

##params

listendevparams
- 类型：ListenDeviceParams, 不可为空
- 描述：ListenDeviceParams至少包含以下的信息

deviceid
- 类型：String, 不可为空
- 描述：设备的deviceid

host
- 类型：String, 不可为空
- 描述：云端的host地址，默认为"iot.mxchip.com"

port
- 类型：String, 不可为空
- 描述：云端的port，默认为"1883"

userName
- 类型：String, 不可为空
- 描述：enduserid

passWord
- 类型：String, 不可为空
- 描述：devicepw, 与用户密码相同，或者与注册验证码相同

clientID
- 类型：String, 不可为空
- 描述：enduserid，即用户登录后获取的enduserid

##callback

ctrldevcb
- 类型：ControlDeviceCallBack
- 描述：接口调用成功后的回调函数

##示例代码

```java
MiCODevice micodev = new MiCODevice(MainActivity.this);

ListenDeviceParams listendevparams = new ListenDeviceParams();
listendevparams.deviceid = "f71246d8-b9db-11e5-a739-00163e0204c0";
listendevparams.host = "1883";
listendevparams.port = "home";
listendevparams.userName = "admin";
listendevparams.passWord = "admin";
listendevparams.clientID = "xxx...";

micoDev.startListenDevice(listendevparams, new ControlDeviceCallBack() {
    
    @Override
    public void onSuccess(String message) {
        Log.d(TAG, message);
    }
    
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, code + " " + message);
    }    

    @Override
    public void onDeviceStatusReceived(String msgType, String messages) {
        Log.d(TAG, msgType + " " + message);
    }
});
```

##可用性

    Android系统4.0+

<div id="sendCommand"></div>
#**sendCommand**

	发送指令给设备端

    sendCommand(String deviceid, String devicepw, String command, String commandType,  ControlDeviceCallBack ctrldevcb, String token)

##params

deviceid
- 类型：String, 不可为空
- 描述：设备的deviceid

devicepw
- 类型：String, 不可为空
- 描述：设备的devicepw

command
- 类型：String, 不可为空
- 描述：发送给设备的指令"json"格式的字符串

commandType
- 类型：String, 不可为空
- 描述："json", 默认

token
- 类型：String, 不可为空
- 描述：用户的token


##callback

ctrldevcb
- 类型：ControlDeviceCallBack
- 描述：接口调用成功后的回调函数

##示例代码

```java
String devicepw = "xxx...";
String commandType = "json";
String token = "xxx...";
micoDev.sendCommand(deviceid, devicepw, command, commandType, new ControlDeviceCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d(TAG + "onSuccess", message);
    }

    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG + "onFailure", code + " " + message);
    }

    @Override
    public void onDeviceStatusReceived(String msgType, String messages) {
        Log.d(TAG + "onDeviceStatusReceived", msgType + " " + messages);
    }
}, token);
```

##可用性

    Android系统4.0+

<div id="addDeviceListener"></div>
#**addDeviceListener**

	增加订阅的频道

    addDeviceListener(String topic, int qos, ControlDeviceCallBack ctrldevcb)

##params

topic
- 类型：String, 不可为空
- 描述：需要定义的topic

qos
- 类型：int, 不可为空
- 描述：0

##callback

ctrldevcb
- 类型：ControlDeviceCallBack
- 描述：接口调用成功后的回调函数

##示例代码

```java
String topic = "xxx...";
int qos = 0;
micoDev.addDeviceListener(topic, qos, new ControlDeviceCallBack() {

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

##可用性

    Android系统4.0+

<div id="removeDeviceListener"></div>
#**removeDeviceListener**

    移除某个监听的topic

    removeDeviceListener(String topic, ControlDeviceCallBack ctrldevcb)

##params

topic
- 类型：String, 不可为空
- 描述：需要定义的topic

##callback

ctrldevcb
- 类型：ControlDeviceCallBack
- 描述：接口调用成功后的回调函数

##示例代码

```java
String topic = "xxx...";
micoDev.removeDeviceListener(topic, qos, new ControlDeviceCallBack() {

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

##可用性

    Android系统4.0+

<div id="stopListenDevice"></div>
#**stopListenDevice**

	停止监听设备

    stopListenDevice(ControlDeviceCallBack ctrldevcb)

##callback

ctrldevcb
- 类型：ControlDeviceCallBack
- 描述：接口调用成功后的回调函数

##示例代码

```java
MiCODevice micodev = new MiCODevice(MainActivity.this);
micoDev.stopListenDevice(new ControlDeviceCallBack() {

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

##可用性

    Android系统4.0+

<div id="connectLocalDevice"></div>
#**connectLocalDevice**

    连接本地局域网的设备

    connectLocalDevice(SinSocketParams sspara, SinSocketCallBack sscb)

##params

sspara
- 类型：SinSocketParams, 不可为空
- 描述：SinSocketParams至少包含以下的信息

ip
- 类型：String, 不可为空
- 描述：设备的ip，通过startSearchDevices发现设备

port
- 类型：int, 可为空
- 描述：本地设备的port，默认为8002

overTime
- 类型：int, 可为空, 单位ms
- 描述：连接设备时候的超时时间，默认为60秒

heartBeatTime
- 类型：int, 可为空, 单位ms
- 描述：每个心跳包的间隔时间，默认为20秒

autoConnectNo
- 类型：int, 可为空
- 描述：socket连接丢失后，自动重连的次数，默认1000次

##callback

sscb
- 类型：SinSocketCallBack
- 描述：接口调用成功后的回调函数

##示例代码

```java
MiCODevice micodev = new MiCODevice(MainActivity.this);

SinSocketParams sspara = new SinSocketParams();
sspara.ip = "192.168.1.20";
sspara.port = 8002;
sspara.heartBeatTime = 5000;
sspara.overTime = 10000;
sspara.autoConnectNo = 5;

SinSocketCallBack sscb = new SinSocketCallBack() {
    @Override
    public void onMessageRead(String message) { //消息到达后会执行此方法
        Log.d(TAG, "connect-->"+message);
    }
    @Override
    public void onLost() {  //连接丢失后会执行此方法
        Log.d(TAG, "connect-->"+"lost");
    }
    
    @Override
    public void onSuccess(String message) { //连接成功后会执行此方法
        Log.d(TAG, "connect-->"+"success");
        
        //连接成功后，需要立即发送登录指令，否则设备会在5秒内把你踢掉，sendLocalCommand的指令下面会介绍
        micodev.sendLocalCommand("{\"applocallogin\":\"admin\"}", null);
    }
    
    @Override
    public void onFailure(int code, String message) { //连接失败后会执行此方法
        Log.d(TAG, "connect-->"+code+" "+message);
    }
};

micodev.connectLocalDevice(sspara, sscb);
```

##可用性

    Android系统4.0+

<div id="sendLocalCommand"></div>
#**sendLocalCommand**

    发送本地的控制指令

    sendLocalCommand(String command, SinSocketCallBack sscb)

##params

command
- 类型：String, 不可为空，虽然是String型，但是必须是json的样子
- 描述：否则模块会死掉，格式如下"{\"applocallogin\":\"admin\"}"

##callback

sscb
- 类型：SinSocketCallBack
- 描述：接口调用成功后的回调函数

##示例代码

```java
MiCODevice micodev = new MiCODevice(MainActivity.this);

String command = "{\"applocallogin\":\"admin\"}";

micodev.sendLocalCommand(command, new SinSocketCallBack() {

    @Override
    public void onSuccess(String message) { //这里的发送成功只是write成功，并不能保证设备已经接受到了
        Log.d(TAG, "Command-->"+message);
    }
    
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, "Command-->"+code + " " + message);
    }
});
```

##可用性

    Android系统4.0+

<div id="disconnectLocalDevice"></div>
#**disconnectLocalDevice**

    断开与本地设备的连接

    disconnectLocalDevice(SinSocketCallBack sscb)

##callback

sscb
- 类型：SinSocketCallBack
- 描述：接口调用成功后的回调函数

##示例代码

```java
MiCODevice micodev = new MiCODevice(MainActivity.this);

micodev.disconnectLocalDevice(new SinSocketCallBack() {

    @Override
    public void onSuccess(String message) {
        Log.d(TAG, "disconnect-->"+message);
    }
    
    @Override
    public void onFailure(int code, String message) {
        Log.d(TAG, "disconnect-->"+code + " " + message);
    }
});
```

##可用性

    Android系统4.0+

<div id="getCookBookByType"></div>
#**getCookBookByType**

    通过食谱类型查询食谱

    getCookBookByType(int type, String productid, UserCallBack usercb, String token)
##params

type
- 类型：int, 不可为空
- 描述：云菜谱的类型，自己定义(与云端一致)

productid
- 类型：String, 不可为空
- 描述：产品ID

##callback

usercb
- 类型：UserCallBack
- 描述：接口调用成功后的回调函数

##token

token
- 类型：String, 不可为空
- 描述：用户登录后服务器端返回的token

##示例代码

```java
MiCOUser micoUser = new MiCOUser();
int type = 1;
String productid = "6486b2d1-0ee9-4647-XXXX-78b9cbc778f7";
micoUser.getCookBookByType(type, productid, new UserCallBack() {

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

##可用性

    Android系统4.0+

<div id="getCookBookByName"></div>
#**getCookBookByName**

    通过食谱名称查询食谱

    getCookBookByName(String cookbookname, UserCallBack usercb, String token)
##params

cookbookname
- 类型：String, 不可为空
- 描述：云菜谱的名字，支持模糊查询

##callback

usercb
- 类型：UserCallBack
- 描述：接口调用成功后的回调函数

##token

token
- 类型：String, 不可为空
- 描述：用户登录后服务器端返回的token

##示例代码

```java
MiCOUser micoUser = new MiCOUser();
String recipename = "cake";
miCOUser.getCookBookByName(recipename, new UserCallBack() {

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

<div id="getCookBookInfo"></div>
#**getCookBookInfo**

    获取食谱详情

    getCookBookInfo(int cookbookid, UserCallBack usercb, String token)
##params

cookbookid
- 类型：int, 不可为空
- 描述：云菜谱的id，可以通过获取食谱列表获得

##callback

usercb
- 类型：UserCallBack
- 描述：接口调用成功后的回调函数

##token

token
- 类型：String, 不可为空
- 描述：用户登录后服务器端返回的token

##示例代码

```java
MiCOUser micoUser = new MiCOUser();
int recipeid = 86;
micouser.getCookBookInfo(recipeid, new UserCallBack() {

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

##可用性

    Android系统4.0+

<div id="addCookBookLikeNo"></div>
#**addCookBookLikeNo**

    给食谱点赞

    addCookBookLikeNo(int cookbookid, UserCallBack usercb, String token)
##params

cookbookid
- 类型：int, 不可为空
- 描述：云菜谱的id，可以通过获取食谱列表获得

##callback

usercb
- 类型：UserCallBack
- 描述：接口调用成功后的回调函数

##token

token
- 类型：String, 不可为空
- 描述：用户登录后服务器端返回的token

##示例代码

```java
MiCOUser micoUser = new MiCOUser();
int recipeid = 86;
micouser.addCookBookLikeNo(recipeid, new UserCallBack() {

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

##可用性

    Android系统4.0+

<div id="createScheduleTask"></div>
#**createScheduleTask**

    创建定时任务

    createScheduleTask(ScheduleTaskParam stp, ControlDeviceCallBack ctrldevcb, String token)
##params

stp
- 类型：ScheduleTaskParam, 不可为空
- 描述：ScheduleTaskParam至少包含以下的信息

device_id
- 类型：String, 不可为空
- 描述：设备的device id

order
- 类型：String, 不可为空
- 描述：控制指令

enable
- 类型：boolean, 可为空，默认为true
- 描述：当前task，True 启用 False 暂停

month
- 类型：String, 可为空
- 描述：月

day_of_month
- 类型：String, 可为空
- 描述：日

day_of_week
- 类型：String, 可为空
- 描述：周

hour
- 类型：String, 可为空
- 描述：小时

minute
- 类型：String, 可为空
- 描述：分

##callback

ctrldevcb
- 类型：ControlDeviceCallBack
- 描述：接口调用成功后的回调函数

```js
{"data":"ec49e83a-1103-11e6-a739-00163e0204c0"}
```

##token

token
- 类型：String, 不可为空
- 描述：用户登录后服务器端返回的token值，一般保存在localstorege里，以便下一次获取使用

##示例代码

```java
MiCODevice micodev = new MiCODevice(MainActivity.this);
ScheduleTaskParam stp = new ScheduleTaskParam();

stp.device_id = "d95366fe-06c0-11e6-a739-00163e0204c0";
stp.order = "{\"KG_Start\":\"1\",\"WorkMode\":\"1\"}";
stp.enable = true;
stp.month = "*";
stp.day_of_month = "*";
stp.day_of_week = "*";
stp.hour = "*";
stp.minute = "*";

micoDev.createScheduleTask(stp, new ControlDeviceCallBack() {

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

##可用性

    Android系统4.0+

<div id="creatDelayTask"></div>
#**creatDelayTask**

    创建延时任务

    creatDelayTask(ScheduleTaskParam stp, ControlDeviceCallBack ctrldevcb, String token)
##params

stp
- 类型：ScheduleTaskParam, 不可为空
- 描述：ScheduleTaskParam至少包含以下的信息

device_id
- 类型：String, 不可为空
- 描述：设备的device id

order
- 类型：String, 不可为空
- 描述：控制指令

enable
- 类型：boolean, 可为空，默认为true
- 描述：当前task，True 启用 False 暂停

second
- 类型：int, 可为空
- 描述：秒

##callback

ctrldevcb
- 类型：ControlDeviceCallBack
- 描述：接口调用成功后的回调函数

```js
{"data":"ec49e83a-1103-11e6-a739-00163e0204c0"}
```

##token

token
- 类型：String, 不可为空
- 描述：用户登录后服务器端返回的token

##示例代码

```java
MiCODevice micodev = new MiCODevice(MainActivity.this);
ScheduleTaskParam stp = new ScheduleTaskParam();

stp.device_id = "d95366fe-06c0-11e6-a739-00163e0204c0";
stp.order = "{\"KG_Start\":\"1\",\"WorkMode\":\"1\"}";
stp.enable = true;
stp.second = 100;

micoDev.creatDelayTask(stp, new ControlDeviceCallBack() {

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

##可用性

    Android系统4.0+


(完)