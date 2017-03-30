## 可以直接参考APICloud模块手册[Fog2](http://docs.apicloud.com/Client-API/Open-SDK/fog2)

## **概述**

想通过APP远程控制一个智能设备，您需要FAE的支持，如果WIFI模块（硬件）已经准备就绪，那么您只需要完成以下几步

1、通过[Fogcloud平台](https://v2.fogcloud.io)注册一个APP，得到appid，因为下面需要用到

2、对于一个新用户而言，首先需要注册用户，步骤：获取验证码、验证验证码、完成注册

3、注册完成后，还没有一个可以控制的设备，需要绑定一个设备，绑定之前需要先让设备连上WIFI路由器

>1)让设备连上路由器(EasyLink)，

>2)连上以后找到这个设备的IP(SearchDevice)，

>3)绑定设备(bindDevice)

4、可以将自己名下的设备分享给别人使用，这些在(ManageDevices)部分

5、远程控制设备(ControlRemoteDevice)

<br/>
<div id="MiCO"></div>

## **MiCO** 全局设置

* [初始化](#init)

<div id="MiCOUser"></div>

## **MiCOUser** 用户管理

#### 基础功能

* [获取验证码](#getVerifyCode)
* [验证验证码](#checkVerifyCode)
* [设置初始密码](#setPassword)
* [登录](#login)
* [刷新Token](#refreshToken)

<div id="MiCODevice"></div>

## **MiCODevice** 设备管理

#### 设备配网

* [获取SSID](#getSSID)
* [开始配网](#startEasyLink)
* [停止配网](#stopEasyLink)

#### 搜索设备

* [开始搜索设备](#startSearchDevices)
* [停止搜索设备](#stopSearchDevices)

#### 绑定设备

* [绑定设备](#bindDevice)
* [解绑设备](#unBindDevice)

<div id="ManageDevices"></div>

#### 设备管理

* [获取设备列表](#getDeviceList)
* [获取设备详情](#getDeviceInfo)
* [修改设备名称](#updateDeviceAlias)
* [获取设备分享码](#getShareVerCode)
* [通过分享码绑定设备](#addDeviceByVerCode)

#### 权限管理

* [获取用户列表](#getMemberList)
* [移除用户权限](#removeBindRole)

<div id="ControlRemoteDevice"></div>

#### 远程控制

* [监听远程设备](#startListenDevice)
* [发送指令](#sendCommand)
* [增加订阅通道](#addDeviceListener)
* [移除订阅通道](#removeDeviceListener)
* [停止监听设备](#stopListenDevice)

<div id="CommandTask"></div>

#### 任务管理

* [创建定时任务](#createScheduleTask)
* [创建延时任务](#creatDelayTask)
* [获取任务列表](#getTaskList)
* [移除任务](#deleteTask)
* [更新定时任务](#updateScheduleTask)
* [更新延时任务](#updateDelayTask)

<br/>
<br/>
<div id="init"></div>

## **init**

    初始化，设置服务器域名

    init({params})

## params

host
- 类型：字符串, 不可为空
- 描述：服务器域名

## 示例代码

```java
const host = {host:"https://v2.fogcloud.io"};
var fog2 = api.require('fog2');
fog2.init(host);
```

## 可用性

    Android系统4.0+

<div id="getVerifyCode"></div>

## **getVerifyCode**

    获取验证码，填入的内容需要为手机号码或者邮箱

    getVerifyCode({params}, callback(ret, err))

## params

loginname
- 类型：字符串, 不可为空
- 描述：手机号码或邮箱

appid
- 类型：字符串, 不可为空
- 描述：在Fogcloud平台注册的APP的id

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{
  "meta": {
    "message": "Send E-mail successfully.",
    "code": 0
  },
  "data": {
    
  }
}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

```js
{
  "meta": {
    "message": "Params error",
    "code": 23013
  },
  "data": {
    
  }
}
```

## 示例代码

```java
var param = {
    loginname: "8800000@qq.com",
    appid: "d8cdf9c6-de8c-11e5-a7-xxxx"
};
fog2.getVerifyCode(param, function(ret, err) {
    if (ret) {
        alert(JSON.stringify(ret));
    } else {
        alert(JSON.stringify(err));
    }
});
```

## 可用性

    Android系统4.0+

<div id="checkVerifyCode"></div>

## **checkVerifyCode**

    验证获取到的手机验证码

    checkVerifyCode({params}, callback(ret, err))

## params

loginname
- 类型：字符串, 不可为空
- 描述：手机号码或邮箱

vercode
- 类型：字符串, 不可为空
- 描述：收到的验证码

appid
- 类型：字符串, 不可为空
- 描述：在Fogcloud平台注册的APP的id

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "clientid": "a015e6d6-1d6e-11e6-a739-00163e0204c0"
}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

```js
{
  "meta": {
    "message": "Please use correct phone number or email address",
    "code": 23013
  },
  "data": {
    
  }
}
```

## 示例代码

```java
var param = {
    loginname: "8800000@qq.com",
    vercode: "886526",
    appid: "d8cdf9c6-de8c-11e5-a7-xxxx"
};
fog2.checkVerifyCode(param, function(ret, err) {
    if (ret) {
        alert(JSON.stringify(ret));
    } else {
        alert(JSON.stringify(err));
    }
});
```

## 可用性

    Android系统4.0+

<div id="setPassword"></div>

## **setPassword**

    验证码验证成功后，设置初始密码，这一步可以跳过

    setPassword({params}, callback(ret, err))

## params

password
- 类型：字符串, 不可为空
- 描述：用户密码

token
- 类型：字符串, 不可为空
- 描述：验证验证码后返回的token

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{
  "meta": {
    "message": "Update password successfully.",
    "code": 0
  },
  "data": {
    
  }
}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

```js
{
  "meta": {
    "message": "Params error",
    "code": 23013
  },
  "data": {
    
  }
}
```

## 示例代码

```java
var param = {
    password: "123456",
    token: "eyJhVlSV8I..."
};
fog2.setPassword(param, function(ret, err) {
    if (ret) {
        alert(JSON.stringify(ret));
    } else {
        alert(JSON.stringify(err));
    }
});
```

## 可用性

    Android系统4.0+

<div id="login"></div>

## **login**

    用户登录

    login({params}, callback(ret, err))

## params

loginname
- 类型：字符串, 不可为空
- 描述：手机号码/邮箱

password
- 类型：字符串, 不可为空
- 描述：用户密码

appid
- 类型：字符串, 不可为空
- 描述：在Fogcloud平台注册的APP的id

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "clientid": "a015e6d6-1d6e-11e6-a739-00163e0204c0"
}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

```js
{
  "meta": {
    "message": {
      "non_field_errors": [
        "Password error."
      ]
    },
    "code": 23010
  },
  "data": {
    
  }
}
```

## 示例代码

```java
var fog2 = api.require('fog2');
var param = {
    loginname: "8800000@qq.com",
    password: "123456",
    appid: "d8cdf9c6-de8c-11e5-a7-xxxx"
};
fog2.login(param, function(ret, err) {
    if (ret) {
        alert(JSON.stringify(ret));
    } else {
        alert(JSON.stringify(err));
    }
});
```

## 可用性

    Android系统4.0+

<div id="refreshToken"></div>

## **refreshToken**

    刷新用户的token，服务器端默认7天内生效，刷新后可以后延7天，失效了就需要重新登录

    refreshToken({params}, callback(ret, err))

## params

token
- 类型：字符串, 不可为空
- 描述：用户登录后服务器端返回的token值，一般保存在localstorege里，以便下一次获取使用

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "clientid": "a015e6d6-1d6e-11e6-a739-00163e0204c0"
}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

```js
{
  "meta": {
    "message": {
      "non_field_errors": [
        "Parser signature error."
      ]
    },
    "code": 23010
  },
  "data": {
    
  }
}
```

## 示例代码

```java
var param = {
    token: "eyJhVlSV8I..."
};
fog2.refreshToken(param, function(ret, err) {
    if (ret) {
        alert(JSON.stringify(ret));
    } else {
        alert(JSON.stringify(err));
    }
});
```

## 可用性

    Android系统4.0+

<div id="getMemberList"></div>

## **getMemberList**

    获取此设备名下的用户，只能看到自己以外的用户

    getMemberList({params}, callback(ret, err))

## params

deviceid
- 类型：字符串, 不可为空
- 描述：设备的deviceid

token
- 类型：字符串, 不可为空
- 描述：用户登录后获取的token

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{
  "meta": {
    "message": "成功根据设备ID获取用户列表",
    "code": 0
  },
  "data": [
    {
      "realname": "",
      "enduserid": "e32bd592-1bf8-11e6-a739-00163e0204c0",
      "app": "db456b4a-17fc-11e6-a739-00163e0204c0",
      "is_active": true,
      "phone": "",
      "role": 2,
      "nickname": "",
      "email": "wzbdroid@126.com"
    }
  ]
}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

```js

```

## 示例代码

```java
var param = {
    deviceid: "db456b4a-17fc-xxxx",
    token: "eyJhVlSV8I..."
};
fog2.getMemberList(param, function(ret, err) {
    if (ret) {
        console.log(JSON.stringify(ret));
    } else {
        console.log(JSON.stringify(err));
    }
}); 
```

## 可用性

    Android系统4.0+

<div id="removeBindRole"></div>

## **removeBindRole**

    删除某人的设备管理权限

    removeBindRole({params}, callback(ret, err))

## params

deviceid
- 类型：字符串, 不可为空
- 描述：设备的deviceid

enduserid
- 类型：字符串, 不可为空
- 描述：用户的id

token
- 类型：字符串, 不可为空
- 描述：用户登录后获取的token

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{
  "meta": {
    "message": "用户解除成功",
    "code": 0
  },
  "data": {
    "enduserid": "e32bd592-1bf8-11e6-a739-00163e0204c0"
  }
}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

```js

```

## 示例代码

```java
var enduserid = "";
var param = {
    deviceid: "db456b4a-17fc-11e6-a739-00163e0204",
    enduserid: "e32bd592-1bf8-11e6-a739-00163e0204c0",
    token: "eyJhVlSV8I..."
};
fog2.removeBindRole(param, function(ret, err) {
    if (ret) {
        console.log(JSON.stringify(ret));
    } else {
        console.log(JSON.stringify(err));
    }
}); 
```

## 可用性

    Android系统4.0+


## **以下是设备管理部分** 


<div id="getSSID"></div>

## **getSSID**

    获取当前手机连接的WIFI的名称，即ssid

    getSSID(callback(ret, err))

## callback

ssid
- 类型：字符串
- 描述：当前WIFI的名称

```js
{ssid: "fog2"}
```

## 示例代码

```java
fog2.getSSID(function(ret, err){
    alert(ret.ssid);
});
```

## 可用性

    Android系统4.0+

<div id="startEasyLink"></div>

## **startEasyLink**

    发送数据包(包含ssid和password)给设备，每10ms发一次，连续发10s，再停止10s，继续发，如此反复

    startEasyLink({params}, callback(ret, err))

## params

ssid
- 类型：字符串, 不可为空
- 描述：准备发送的ssid

password
- 类型：字符串, 不可为空
- 描述：ssid对应的WIFI密码

runSecond
- 类型：int, 不可为空，单位ms
- 描述：发送持续的时间，到点了就停止发送

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{message: "success"}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

```js
{code: 9403, message: "It is closed."}
```

## 示例代码

```java
var param = {
    ssid: "fog2",
    password: "12345678",
    runSecond: 20000 //20秒后停止发包
};
fog2.startEasyLink(param, function(ret, err) {
    if(ret)
        alert(ret);
    else
        alert(err);
});
```

## 可用性

    Android系统4.0+

<div id="stopEasyLink"></div>

## **stopEasyLink**

    停止发送数据包

    stopEasyLink(callback(ret, err))

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{message: "stop success"}
```

## 示例代码

```java
fog2.stopEasyLink(function(ret, err) {
    if(ret)
        alert(ret);
    else
        alert(err);
}); 
```

## 可用性

    Android系统4.0+

<div id="startSearchDevices"></div>

## **startSearchDevices**

    设备连上WIFI路由器后，我就可以通过这个接口来发现他，

    当然，前提是手机和设备必须在同一个网段

    startSearchDevices(callback(ret, err))

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{
  "devices": [
    {
      "Name": "MiCOKit 3165#040A39",
      "IP": "192.168.19.81",
      "Port": 8002,
      "MAC": "D0:BA:E4:04:0A:39",
      "Firmware Rev": "fog_3165_aca@002",
      "FogProductId": "9f062218-31fc-11e6-a739-00163e0204c0",
      "IsEasylinkOK": "true",
      "IsHaveSuperUser": "false",
      "RemainingUserNumber": "3",
      "Hardware Rev": "MK3165_1",
      "MICO OS Rev": "31620002.046",
      "Model": "MiCOKit-3165",
      "Protocol": "com.mxchip.basic",
      "Manufacturer": "MXCHIP Inc.",
      "Seed": "89203"
    },
    {
      "Name": "MiCOKit 3165#075AEE",
      "IP": "192.168.18.182",
      "Port": 8002,
      "MAC": "D0:BA:E4:07:5A:EE",
      "Firmware Rev": "fog_3165_aca@002",
      "FogProductId": "6486b2d1-0ee9-4647-baa3-78b9cbc778f7",
      "IsEasylinkOK": "true",
      "IsHaveSuperUser": "false",
      "RemainingUserNumber": "3",
      "Hardware Rev": "MK3165_1",
      "MICO OS Rev": "31620002.046",
      "Model": "MiCOKit-3165",
      "Protocol": "com.mxchip.fogcloud",
      "Manufacturer": "MXCHIP Inc.",
      "Seed": "22"
    }
  ]
}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

```js
{code: 9403, message: "It is closed."}
```

## 示例代码

```java
fog2.startSearchDevices(function(ret, err) {
    if (ret)
        alert(JSON.stringify(ret));
    else
        alert(err);
});
```

## 可用性

    Android系统4.0+

<div id="stopSearchDevices"></div>

## **stopSearchDevices**

    停止发现设备，发现了需要激活的设备，主动调用此接口

    stopSearchDevices(callback(ret, err))

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{message: "stop success"}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数


## 示例代码

```java
fog2.stopSearchDevices(function(ret, err) {
    if (ret)
        alert(ret);
    else
        alert(err);
});
```

## 可用性

    Android系统4.0+

<div id="bindDevice"></div>

## **bindDevice**

    通过startSearchDevices获取准备绑定设备的信息，从中提取出IP地址，和deviceid，再通过此接口绑定设备

    bindDevice({params}, callback(ret, err))

## params

ip
- 类型：字符串, 不可为空
- 描述：即将绑定的设备的IP

port
- 类型：String, 不可为空
- 描述：设备服务的端口

token
- 类型：字符串, 不可为空
- 描述：用户登录后获取的token

## callback

ret
- 类型：JSON 对象
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

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

```js
{
  "meta": {
    "message": "请求参数错误",
    "code": 23010
  },
  "data": {
  }
}
```

## 示例代码

```java
var param = {
    ip:"192.168.1.10",
    port: "8002",
    token:"eyJhVlSV8I..."
};
fog2.bindDevice(param, function(ret, err) {
    if (ret)
        alert(ret);
    else
        alert(err);
});
```

## 可用性

    Android系统4.0+

<div id="unBindDevice"></div>

## **unBindDevice**

    用户不准备使用此设备时候，调用此接口解绑设备，

    1）如果是普通用户或者普通管理员，解绑只会解绑自己和设备的绑定关系

    2）如果是超级管理员，那么解绑后，所有人均不能控制这个设备了

    unBindDevice({params}, callback(ret, err))

## params

deviceid
- 类型：字符串, 不可为空
- 描述：设备的deviceid


token
- 类型：字符串, 不可为空
- 描述：用户token

## callback

ret
- 类型：JSON 对象
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

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

```js
{
  "meta": {
    "message": {
      "81d79316-bb5a-11e5-a739-00163e0204c0": [
        "Ensure this field has no more than 36 characters."
      ]
    },
    "code": 23010
  },
  "data": {
  }
}
```

## 示例代码

```java
var param = {
    deviceid: "81d79316-bb5a-11e5-a739-00163e0204c0",
    token: "eyJhVlSV8I..."
};
fog2.unBindDevice(param, function(ret, err) {
    if (ret)
        alert(ret);
    else
        alert(err);
});
```

## 可用性

    Android系统4.0+

<div id="getDeviceList"></div>

## **getDeviceList**

    获取本账号名下的所有相关设备

    getDeviceList({params}, callback(ret, err))

## params

token
- 类型：字符串, 不可为空
- 描述：用户登录后获取的token

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{
  "meta": {
    "message": "device list by user",
    "code": 0
  },
  "data": [
    {
      "device_pw": "1486",
      "product_icon": "",
      "device_name": "mxchip-3031",
      "mac": "d0bae45001e5",
      "role": 1,
      "online": false,
      "product_name": "mxchip-3031",
      "device_id": "e5dc25a4-700c-11e6-9baf-00163e120d98"
    },
    {
      "device_pw": "5600",
      "product_icon": "",
      "device_name": "MiCOKit-3165",
      "mac": "D0BAE4077AA4",
      "role": 1,
      "online": true,
      "product_name": "MiCOKit-3165",
      "device_id": "501370ec-86b7-11e6-9baf-00163e120d98"
    }
  ]
}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

```js
{"code":401,"message":{"detail":"签名解码错误"}}
```

## 示例代码

```java
var param = {
    token: "eyJhVlSV8I..."
};
fog2.getDeviceList(param, function(ret, err) {
    if (ret){
        alert(JSON.stringify(ret));
    }else{
        alert(JSON.stringify(err));
    }
});
```

## 可用性

    Android系统4.0+

<div id="getDeviceInfo"></div>

## **getDeviceInfo**

    获取设备信息

    getDeviceInfo({params}, callback(ret, err))

## params

deviceid
- 类型：字符串, 不可为空
- 描述：即将绑定的设备的deviceid

token
- 类型：字符串, 不可为空
- 描述：用户登录后获取的token

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{
  "meta": {
    "message": "设备信息。",
    "code": 0
  },
  "data": {
    "alias": "iBake",
    "online": false,
    "devicepw": "7176"
  }
}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

```js
{
  "code": 400,
  "message": {
    "meta": {
      "message": "用户跟设备没有绑定。",
      "code": 23100
    },
    "data": {
    }
  }
}
```

## 示例代码

```java
var param = {
    deviceid: "81d79316-bb5a-11e5-a739-00163e0204c0",
    token: "eyJhVlSV8I..."
};
fog2.getDeviceInfo(param, function (ret, err) {
    if (ret){
        alert(JSON.stringify(ret));
    }else{
        alert(JSON.stringify(err));
    }
});
```

## 可用性

    Android系统4.0+

<div id="updateDeviceAlias"></div>

## **updateDeviceAlias**

    修改设备名称

    updateDeviceAlias({params}, callback(ret, err))

## params

deviceid
- 类型：字符串, 不可为空
- 描述：即将绑定的设备的deviceid

devicename
- 类型：字符串, 不可为空
- 描述：设备名称

token
- 类型：字符串, 不可为空
- 描述：用户登录后获取的token

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{
}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

```js
{
}
```

## 示例代码

```java
var param = {
    deviceid: "81d79316-bb5a-11e5-a739-00163e0204c0",
    devicename: "好风尚",
    token: "eyJhVlSV8I..."
};
fog2.updateDeviceAlias(param, function (ret, err) {
    if (ret){
        alert(JSON.stringify(ret));
    }else{
        alert(JSON.stringify(err));
    }
});
```

## 可用性

    Android系统4.0+

<div id="getShareVerCode"></div>

## **getShareVerCode**

    我是超级管理员或者普通管理员，那么我就能把我名下的设备分享给别人，首先需要获取分享码

    getShareVerCode({params}, callback(ret, err))

## params

deviceid
- 类型：字符串, 不可为空
- 描述：即将绑定的设备的deviceid

token
- 类型：字符串, 不可为空
- 描述：用户登录后获取的token

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{
  "vercode": "3c642f24-1b18-11e6-a739-00163e0204c0"
}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

```js
{
  "code": 400,
  "message": {
    "meta": {
      "message": "用户跟设备没有绑定。",
      "code": 23100
    },
    "data": {
    }
  }
}

{
  "code": 9500,
  "message": {
    "meta": {
      "message": "用户对该设备不具有管理员权限",
      "code": 23201
    },
    "data": {
    }
  }
}
```

## 示例代码

```java
var param = {
    deviceid: "81d79316-bb5a-11e5-a739-00163e0204c0",
    token: "eyJhVlSV8I..."
};
fog2.getShareVerCode(param, function (ret, err) {
    if (ret){
        alert(JSON.stringify(ret));
    }else{
        alert(JSON.stringify(err));
    }
});
```

## 可用性

    Android系统4.0+

<div id="addDeviceByVerCode"></div>

## **addDeviceByVerCode**

    解析出二维码里的内容，并通过此接口绑定被授权的设备

    addDeviceByVerCode({params}, callback(ret, err))

## params

deviceid
- 类型：字符串, 不可为空
- 描述：设备的id

devicepw
- 类型：字符串, 不可为空
- 描述：设备的PW

bindvercode
- 类型：int, 不可为空
- 描述：vercode

role
- 类型：int, 不可为空
- 描述：3普通用户 2管理员

bindingtype
- 类型：字符串, 不可为空
- 描述：绑定类型 sa 超级用户 home 家庭用户 guest 访客 other 其他

iscallback
- boolean, 不可为空
- 描述：是否返回绑定状态，此版本请都设置为false

token
- 类型：字符串, 不可为空
- 描述：用户登录后获取的token

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{
  "meta": {
    "message": "用户已重新绑定",
    "code": 0
  },
  "data": {
  }
}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

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

## 示例代码

```java
var param = {
    deviceid: "81d79316-bb5a-11e5-a739-00163e0204c0",
    devicepw: "1234",
    bindvercode: "7a661792-215d-11e6-a739-00163e0204c0",
    role: 3,
    bindingtype: "home",
    token: "eyJhVlSV8I..."
};
fog2.addDeviceByVerCode(param, function (ret, err) {
    if (ret){
        alert(JSON.stringify(ret));
    }else{
        alert(JSON.stringify(err));
    }
});
```

## 可用性

    Android系统4.0+

<div id="startListenDevice"></div>

## **startListenDevice**

    远程监听设备，获取设备上报的数据

    startListenDevice({params}, callback(ret, err))

## params

host
- 类型：字符串, 不可为空
- 描述：云端的host地址

port
- 类型：字符串, 不可为空
- 描述：云端的port

deviceid
- 类型：字符串, 不可为空
- 描述：设备的deviceid

username
- 类型：字符串, 不可为空
- 描述：enduserid

password
- 类型：字符串, 不可为空
- 描述：如果设置过密码，password与用户密码相同，如果是用验证码登录，那么password与注册验证码相同

clientid
- 类型：字符串, 不可为空
- 描述：enduserid，即用户登录后获取的enduserid

isencrypt
- 类型：boolean, 可为空
- 描述：是否通过SSL通道发送数据

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{"message":"success"}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

## 示例代码

```java
var param = {
    host: "iot.mxchip.com",
    port: "1883",
    username: "enduserid",
    password: "password",
    deviceid: "81d79316-bb5a-11e5-a739-00163e0204c0",
    clientid: "enduserid",
    isencrypt:false
};
fog2.startListenDevice(param, function (ret, err) {
    if (ret){
        alert(JSON.stringify(ret));
    }else{
        alert(JSON.stringify(err));
    }
});  
```

## 可用性

    Android系统4.0+

<div id="sendCommand"></div>

## **sendCommand**

	发送指令给设备端

    sendCommand({params}, callback(ret, err))

## params

deviceid
- 类型：字符串, 不可为空
- 描述：设备的deviceid

devicepw
- 类型：字符串, 不可为空
- 描述：设备的devicepw

command
- 类型：字符串, 不可为空
- 描述：发送给设备的指令"json"格式的字符串

token
- 类型：字符串, 不可为空
- 描述：用户的token


## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{
  "meta": {
    "message": "命令发送成功",
    "code": 0
  },
  "data": {
  }
}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

```js
{"message":"Json Exception"}
```

## 示例代码

```java
var param = {
    deviceid: "81d79316-bb5a-11e5-a739-00163e0204c0",
    devicepw: "1234",
    command: '{"KG_Bottom":"1"}',
    token: "eyJhVlSV8I..."
};
fog2.sendCommand(param, function (ret, err) {
    if (ret){
        alert(JSON.stringify(ret));
    }else{
        alert(JSON.stringify(err));
    }
});  
```

## 可用性

    Android系统4.0+

<div id="addDeviceListener"></div>

## **addDeviceListener**

	增加订阅的频道

    addDeviceListener({params}, callback(ret, err))

## params

topic
- 类型：字符串, 不可为空
- 描述：需要定义的topic，例"d2c/" + deviceid + "/status"

qos
- 类型：int, 不可为空
- 描述：0

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{"message":"success"}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

```js
{"message":"success"}
```

## 示例代码

```java
var param = {
    topic: "d2c/" + deviceid + "/status",
    qos: 0
};
fog2.addDeviceListener(param, function (ret, err) {
    if (ret){
        alert(JSON.stringify(ret));
    }else{
        alert(JSON.stringify(err));
    }
});   
```

## 可用性

    Android系统4.0+

<div id="removeDeviceListener"></div>

## **removeDeviceListener**

    移除某个监听的topic

    removeDeviceListener({params}, callback(ret, err))

## params

topic
- 类型：字符串, 不可为空
- 描述：需要定义的topic

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{"message":"success"}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

```js
```

## 示例代码

```java
var param = {
    topic: "d2c/" + deviceid + "/status"
};
fog2.removeDeviceListener(param, function (ret, err) {
    if (ret){
        alert(JSON.stringify(ret));
    }else{
        alert(JSON.stringify(err));
    }
});   
```

## 可用性

    Android系统4.0+

<div id="stopListenDevice"></div>

## **stopListenDevice**

	停止监听设备

    stopListenDevice(callback(ret, err))

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{"message":"success"}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

```js
```

## 示例代码

```java
fog2.stopListenDevice(function(ret, err){
    if (ret){
        alert(JSON.stringify(ret));
    }else{
        alert(JSON.stringify(err));
    }
});
```

## 可用性

    Android系统4.0+

<div id="createScheduleTask"></div>

## **createScheduleTask**

    创建定时任务

    createScheduleTask({params}, callback(ret, err))
## params

deviceid
- 类型：字符串, 不可为空
- 描述：设备的device id

command
- 类型：字符串, 不可为空
- 描述：控制指令

enable
- 类型：boolean, 可为空，默认为true
- 描述：当前task，True 启用 False 暂停

```js
时间格式：

星期，取值：
周日：0
周一：1
周二：2
周三：3
周四：4
周五：5
周六：6
"*"：每天
不传：单次任务
(例如“0,1,2”表示 周日 周一 周二)
```

month
- 类型：字符串, 可为空
- 描述：月

dayofmonth
- 类型：字符串, 可为空
- 描述：日

dayofweek
- 类型：字符串, 可为空
- 描述：

hour
- 类型：字符串, 可为空
- 描述：小时

minute
- 类型：字符串, 可为空
- 描述：分

token
- 类型：字符串, 不可为空
- 描述：用户登录后服务器端返回的token值，一般保存在localstorege里，以便下一次获取使用

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{
  "meta": {
    "msg": "创建任务成功",
    "code": 0
  },
  "data": "8b95b17c-20bc-11e6-a739-00163e0204c0"
}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

## 示例代码

```java
var command = '{"KG_Start":"1","WorkMode":"1"}';

var param = {
    deviceid: "81d79316-bb5a-11e5-a739-00163e0204c0",
    command: command,
    enable: true,
    month: "*",
    dayofmonth: "*",
    dayofweek: "*",
    hour: "*",
    minute: "*",
    token: "eyJhVlSV8I..."
};
fog2.createScheduleTask(param, function (ret, err) {
    if (ret){
        alert(JSON.stringify(ret));
    }else{
        alert(JSON.stringify(err));
    }
}); 
```

## 可用性

    Android系统4.0+

<div id="creatDelayTask"></div>

## **creatDelayTask**

    创建延时任务

    creatDelayTask({params}, callback(ret, err))
## params

deviceid
- 类型：字符串, 不可为空
- 描述：设备的device id

command
- 类型：字符串, 不可为空
- 描述：控制指令

enable
- 类型：boolean, 可为空，默认为true
- 描述：当前task，True 启用 False 暂停

second
- 类型：int, 可为空
- 描述：秒

token
- 类型：字符串, 不可为空
- 描述：用户登录后服务器端返回的token

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{
  "meta": {
    "msg": "创建任务成功",
    "code": 0
  },
  "data": "8b95b17c-20bc-11e6-a739-00163e0204c0"
}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

## 示例代码

```java
var command = '{"KG_Start":"1","WorkMode":"1"}'
var param = {
    deviceid: "81d79316-bb5a-11e5-a739-00163e0204c0"
    command: command,
    enable: true,
    second: 61,
    token: "eyJhVlSV8I..."
};
fog2.createDelayTask(param, function (ret, err) {
    if (ret){
        alert(JSON.stringify(ret));
    }else{
        alert(JSON.stringify(err));
    }
});  
```

## 可用性

    Android系统4.0+


<div id="getTaskList"></div>

## **creatDelayTask**

    获取任务列表

    getTaskList({params}, callback(ret, err))
## params

deviceid
- 类型：字符串, 不可为空
- 描述：设备的device id

tasktype
- 类型：字符串, 不可为空
- 描述：任务类型 0 定时任务， 1 延时任务

token
- 类型：字符串, 不可为空
- 描述：用户登录后服务器端返回的token

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{
  "meta": {
    "msg": "任务列表",
    "code": 0
  },
  "data": [
    {
      "commands": "{\"KG_Start\":\"1\",\"WorkMode\":\"1\"}",
      "second": 60,
      "enable": true,
      "name": "426d19f4-20b7-11e6-a739-00163e0204c0"
    },
    {
      "commands": "{\"KG_Start\":\"1\",\"WorkMode\":\"1\"}",
      "second": 60,
      "enable": true,
      "name": "0c14e3ac-20ba-11e6-a739-00163e0204c0"
    }
  ]
}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数


## 示例代码

```java
var param = {
  deviceid: "81d79316-bb5a-11e5-a739-00163e0204c0",
  tasktype: 1,
  token: "eyJhVlSV8I..."
};
fog2.getTaskList(param, function (ret, err) {
  if (ret){
      alert(JSON.stringify(ret));
  }else{
      alert(JSON.stringify(err));
  }
});  
```

## 可用性

    Android系统4.0+

<div id="deleteTask"></div>

## **deleteTask**

    删除任务

    deleteTask({params}, callback(ret, err))
## params

deviceid
- 类型：字符串, 不可为空
- 描述：设备的device id

taskid
- 类型：字符串, 不可为空
- 描述：任务的id，通过getTaskList可以获取

token
- 类型：字符串, 不可为空
- 描述：用户登录后服务器端返回的token

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{
  "meta": {
    "msg": "成功删除task：8b95b17c-20bc-11e6-a739-00163e0204c0",
    "code": 0
  },
  "data": "8b95b17c-20bc-11e6-a739-00163e0204c0"
}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

```js
{
  "code": 400,
  "message": {
    "meta": {
      "msg": "没有找到对应任务,请检查task name是否正确。",
      "code": 21110
    },
    "data": {
    }
  }
}
```

## 示例代码

```java
var param = {
    deviceid: "81d79316-bb5a-11e5-a739-00163e0204c0",
    taskid: "5af8bb16-20b4-11e6-a739-00163e0204c0",
    token: "eyJhVlSV8I..."
};
fog2.deleteTask(param, function (ret, err) {
    if (ret){
        alert(JSON.stringify(ret));
    }else{
        alert(JSON.stringify(err));
    }
});   
```

## 可用性

    Android系统4.0+

<div id="updateScheduleTask"></div>

## **updateScheduleTask**

    更新定时任务

    updateScheduleTask({params}, callback(ret, err))
## params


deviceid
- 类型：字符串, 不可为空
- 描述：设备的device id

taskid
- 类型：字符串, 不可为空
- 描述：任务的id，通过getTaskList可以获取

command
- 类型：字符串, 不可为空
- 描述：控制指令

enable
- 类型：boolean, 可为空，默认为true
- 描述：当前task，True 启用 False 暂停

```js
时间格式：

星期，取值：
周日：0
周一：1
周二：2
周三：3
周四：4
周五：5
周六：6
"*"：每天
不传：单次任务
(例如“0,1,2”表示 周日 周一 周二)
```

month
- 类型：字符串, 可为空
- 描述：月

dayofmonth
- 类型：字符串, 可为空
- 描述：日

dayofweek
- 类型：字符串, 可为空
- 描述：

hour
- 类型：字符串, 可为空
- 描述：小时

minute
- 类型：字符串, 可为空
- 描述：分

token
- 类型：字符串, 不可为空
- 描述：用户登录后服务器端返回的token值，一般保存在localstorege里，以便下一次获取使用

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{
  "meta": {
    "msg": "修改任务成功",
    "code": 0
  },
  "data": "5af8bb16-20b4-11e6-a739-00163e0204c0"
}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数


## 示例代码

```java
var command = "{\"KG_Start\":\"44\",\"WorkMode\":\"55\"}";
var taskid = "412ab16e-20b7-11e6-a739-00163e0204c0";

var param = {
    deviceid: "81d79316-bb5a-11e5-a739-00163e0204c0",
    taskid: taskid,
    command: command,
    enable: false,
    month: "*",
    dayofmonth: "*",
    dayofweek: "*",
    hour: "*",
    minute: "*",
    token: "eyJhVlSV8I..."
};
fog2.updateScheduleTask(param, function (ret, err) {
    if (ret){
        alert(JSON.stringify(ret));
    }else{
        alert(JSON.stringify(err));
    }
});  
```

## 可用性

    Android系统4.0+

<div id="updateDelayTask"></div>

## **updateDelayTask**

    更新延时任务

    updateDelayTask({params}, callback(ret, err))
## params

deviceid
- 类型：字符串, 不可为空
- 描述：设备的device id

taskid
- 类型：字符串, 不可为空
- 描述：任务的id，通过getTaskList可以获取

command
- 类型：字符串, 不可为空
- 描述：控制指令

enable
- 类型：boolean, 可为空，默认为true
- 描述：当前task，True 启用 False 暂停

second
- 类型：int, 可为空
- 描述：秒

token
- 类型：字符串, 不可为空
- 描述：用户登录后服务器端返回的token

## callback

ret
- 类型：JSON 对象
- 描述：接口调用成功后的回调函数

```js
{
  "meta": {
    "msg": "修改任务成功",
    "code": 0
  },
  "data": "5af8bb16-20b4-11e6-a739-00163e0204c0"
}
```

err
- 类型：JSON 对象
- 描述：接口调用失败后的回调函数

## 示例代码

```java
var command = "{\"KG_Start\":\"44\",\"WorkMode\":\"55\"}";
var taskid = "5af8bb16-20b4-11e6-a739-00163e0204c0";

var param = {
    deviceid: "81d79316-bb5a-11e5-a739-00163e0204c0",
    taskid: taskid,
    command: command,
    enable: false,
    second: 65,
    token: "eyJhVlSV8I..."
};
fog2.updateDelayTask(param, function (ret, err) {
    if (ret){
        alert(JSON.stringify(ret));
    }else{
        alert(JSON.stringify(err));
    }
});   
```

## 可用性

    Android系统4.0+


## 错误码摘要

- 0,     success
- 1003,  取消
- 9000,  未登陆
- 9001,  请求参数错误
- 9003,  Json Exception