##元素版本: EasyLink2.0

    2016年06月23日

对应库：easylink2.0.jar

```js
dependencies {
    compile 'io.fogcloud.sdk:easylink:0.1.0'
}
```

##**功能列表**

* [获取SSID](#getSSID)
* [开始配网](#startEasyLink)
* [停止配网](#stopEasyLink)
* [状态码](#errorcode)


<div id="getSSID"></div>
#**getSSID**
    获取当前手机连接的WIFI的名称，即ssid

    String getSSID()

#####callback
ssid
- 类型：String
- 描述：当前WIFI的名称

#####示例代码
```java
EasyLink elink = new EasyLink(context);
elink.getSSID()
```

<div id="startEasyLink"></div>
#**startEasyLink**
    发送数据包(包含ssid和password)给设备，连续发10s，再停止3s，再继续发，如此反复

    startEasyLink(EasyLinkParams easylinkPara, EasyLinkCallBack easylinkcb)

#####params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
easylinkPara     | EasyLinkParams       | EasyLinkParams包含以下的信息

#####EasyLinkParams
参数名 | 类型 | 默认值 | 描述
:-----------  | :-------------:| -----------:| -----------:
ssid        | String       | 无默认值，不可为空 | 当前wifi的名称
password     | String       | 无默认值，可为空 | 当前wifi的密码
runSecond         | int       | 默认值60000，可为空 | 发送持续的时间，到点了就停止发送, 单位ms
sleeptime         | int       | 默认值50，可为空 | 每包数据的间隔时间，建议20-200, 单位ms
extraData         | String     | 无默认值，可为空   | 需要发送给设备的额外信息
rc4key     | String       | 无默认值，可为空 | 如果需要RC4加密，这里就输入字符串密钥

#####callback
easylinkcb
- 类型：EasyLinkCallBack
- 描述：接口调用成功后的回调函数

#####示例代码
```java
EasyLinkParams easylinkPara = new EasyLinkParams();
easylinkPara.ssid = "mxchip-office";
easylinkPara.password = "12345678";
easylinkPara.runSecond = 20000;
easylinkPara.sleeptime = 50;
easylinkPara.extraData = "tom";
// easylinkPara.rc4key = "";

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
#**stopEasyLink**
    停止发送数据包

    stopEasyLink(EasyLinkCallBack easylinkcb)

#####callback
easylinkcb
- 类型：EasyLinkCallBack
- 描述：接口调用成功后的回调函数

#####示例代码
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
<div id="errorcode"></div>
#**状态码**

code | message | 描述
:-----------  | :-------------: | :-------------:
1000     | success       | 打开EasyLink成功
1001     | stop success   |    关闭EasyLink成功
9400     | Parameters can not be empty.   |    参数不能为空
9402     | It is progressing.   |    EasyLink正在运行中
9403     | It is closed.   |    EasyLink已经被关闭了
9405     | Context is null.  |    上下文不能为空