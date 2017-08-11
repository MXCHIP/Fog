## 元素版本: MQTT3.0

2016年06月28日

对应库：mqtt2.0.jar

```js
dependencies {
    compile 'io.fogcloud.sdk:mqtt:0.0.3'
}
```

##### eclipse版本使用此demo

[传送门](https://github.com/MXCHIP/mqttmin)

##### 开启服务

需要现在manifest.xml中开启服务

```js
<uses-permission android:name="android.permission.INTERNET" />

<service android:name="io.fogcloud.fog_mqtt.service.MqttService"></service>
```

## **功能列表**

* [开始监听设备](#startMqtt)
* [停止监听设备](#stopMqtt)
* [增加订阅](#subscribe)
* [移除订阅](#unsubscribe)
* [发送指令](#publish)
* [状态码](https://github.com/MXCHIP/Fog2.0/blob/master/docs/Error-code.md)
* [附录](#appendixes)

<div id="startMqtt"></div>

## **startMqtt**
    开始监听设备，建立MQTT连接，假如断开会自动重连

    startMqtt(ListenDeviceParams listendevparams, ListenDeviceCallBack ctrldevcb)

##### params

参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
listendevparams     | ListenDeviceParams       | ListenDeviceParams包含以下的信息

##### ListenDeviceParams

参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
host        | String       | host，域名或者IP
port     | String       | 端口(非必填)
userName         | int       | 用户名
passWord         | int       | 密码
clientID         | String     | 客户端id
topic     | String       | 监听的主题
isencrypt     | boolean       | 是否SSL加密(默认为false)

##### callback
ctrldevcb
- 类型：ListenDeviceCallBack
- 描述：接口调用成功后的回调函数

##### 示例代码
```java
MQTT mqtt = new MQTT(ctx);

ListenDeviceParams ldp = new ListenDeviceParams();
ldp.host = "api.easylink.io";
ldp.port = "1883";
ldp.userName = "admin";
ldp.passWord = "admin";
ldp.topic = "d64f517c/c8934691313c/out/read";
ldp.clientID = "client-000";
ldp.isencrypt = false;

mqtt.startMqtt(ldp, new ListenDeviceCallBack() {
    @Override
    public void onSuccess(int code, String message) {
        Log.d("---", message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d("---", code + " - " + message);
    }
    @Override
    public void onDeviceStatusReceived(int code, String messages) {
        Log.d("---" + code + "---", messages);
    }
});
```

<div id="stopMqtt"></div>

## **stopMqtt**
    停止监听设备

    stopMqtt(ListenDeviceCallBack ctrldevcb)

##### callback
ctrldevcb
- 类型：ListenDeviceCallBack
- 描述：接口调用成功后的回调函数

##### 示例代码
```java
mqtt.stopMqtt(new ListenDeviceCallBack() {
    @Override
    public void onSuccess(int code, String message) {
        Log.d("---", message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d("---", code + " - " + message);
    }
});
```

<div id="publish"></div>

## **publish**
    发送指令给设备

    publish(String topic, String command, int qos, boolean retained, ListenDeviceCallBack ctrldevcb)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
topic     | String       | 发送指令的通道
command        | String       | 指令
qos     | int       | 建议为0(描述见[附录](#appendixes))
retained         | boolean       | 建议为false(设置是否在服务器中保存消息体)

##### callback
ctrldevcb
- 类型：ListenDeviceCallBack
- 描述：接口调用成功后的回调函数

##### 示例代码
```java
String sendtopic = "d64f517c/c8934691813c/in/write";
String command = "{\"4\":true}";
mqtt.publish(sendtopic, command, 0, false,
        new ListenDeviceCallBack() {
            @Override
            public void onSuccess(int code, String message) {
                Log.d("---", message);
            }
            @Override
            public void onFailure(int code, String message) {
                Log.d("---", code + " - " + message);
            }
        });
```

<div id="subscribe"></div>

## **subscribe**
    增加订阅的通道

    subscribe(String topic, int qos, ListenDeviceCallBack ctrldevcb)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
topic     | String       | 订阅的通道
qos     | int       | 建议为0(描述见[附录](#appendixes))


##### callback
ctrldevcb
- 类型：ListenDeviceCallBack
- 描述：接口调用成功后的回调函数

##### 示例代码
```java
String addtopic = "d64f517c/c8934691813c/in/write";
mqtt.subscribe(addtopic, 0, new ListenDeviceCallBack() {
    @Override
    public void onSuccess(int code, String message) {
        Log.d("---", message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d("---", code + " - " + message);
    }
});
```

<div id="unsubscribe"></div>

## **unsubscribe**
    移除一个订阅的通道

    unsubscribe(String topic, ListenDeviceCallBack ctrldevcb)

##### params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
topic     | String       | 订阅的通道

##### callback
ctrldevcb
- 类型：ListenDeviceCallBack
- 描述：接口调用成功后的回调函数

##### 示例代码
```java
String rmtopic = "d64f517c/c8934691813c/in/write";
mqtt.unsubscribe(rmtopic, new ListenDeviceCallBack() {
    @Override
    public void onSuccess(int code, String message) {
        Log.d("---", message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d("---", code + " - " + message);
    }
});
```

<div id="appendixes"></div>

## **附录**

>QoS=0：最多一次，有可能重复或丢失

>QoS=1：至少一次，有可能重复

>QoS=2：只有一次，确保消息只到达一次（用于比较严格的计费系统）