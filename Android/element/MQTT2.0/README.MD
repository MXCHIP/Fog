##元素版本: MQTT2.0

2016年06月28日

对应库：easylink2.0.jar

##**功能列表**

* [开始监听设备](#startListenDevice)
* [停止监听设备](#stopListenDevice)
* [增加订阅](#addDeviceListener)
* [移除订阅](#removeDeviceListener)
* [发送指令](#sendCommand)
* [状态码](#errorcode)

<div id="startListenDevice"></div>
#**startListenDevice**
    开始监听设备，建立MQTT连接，假如断开会自动重连

    startListenDevice(ListenDeviceParams listendevparams, ListenDeviceCallBack ctrldevcb)

#####params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:
listendevparams     | ListenDeviceParams       | ListenDeviceParams包含以下的信息

#####ListenDeviceParams
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:| -----------:
host        | String       | host，域名或者IP
port     | String       | 端口
userName         | int       | 用户名
passWord         | int       | 密码
clientID         | String     | 客户端id
topic     | String       | 监听的主题

#####callback
ctrldevcb
- 类型：ListenDeviceCallBack
- 描述：接口调用成功后的回调函数

#####示例代码
```java
MQTT mqttapi = new MQTT(ctx);

ListenDeviceParams ldp = new ListenDeviceParams();
ldp.host = "api.easylink.io";
ldp.port = "1883";
ldp.userName = "admin";
ldp.passWord = "admin";
ldp.topic = "d64f517c/c8934691313c/out/read";
ldp.clientID = "client-000";

mqttapi.startListenDevice(ldp, new ListenDeviceCallBack() {
    @Override
    public void onSuccess(String message) {
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

<div id="stopListenDevice"></div>
#**stopListenDevice**
    停止监听设备

    stopListenDevice(ListenDeviceCallBack ctrldevcb)

#####callback
ctrldevcb
- 类型：ListenDeviceCallBack
- 描述：接口调用成功后的回调函数

#####示例代码
```java
mqttapi.stopListenDevice(new ListenDeviceCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d("---", message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d("---", code + " - " + message);
    }
});
```

<div id="sendCommand"></div>
#**sendCommand**
    发送指令给设备

    sendCommand(String topic, String command, int qos, boolean retained, ListenDeviceCallBack ctrldevcb)

#####params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:| -----------:
topic     | String       | 发送指令的通道
command        | String       | 指令
qos     | int       | 建议为0(QoS=0：最多一次，有可能重复或丢失。QoS=1：至少一次，有可能重复。QoS=2：只有一次，确保消息只到达一次（用于比较严格的计费系统）。)
retained         | boolean       | 建议为false(设置是否在服务器中保存消息体)

#####callback
ctrldevcb
- 类型：ListenDeviceCallBack
- 描述：接口调用成功后的回调函数

#####示例代码
```java
String sendtopic = "d64f517c/c8934691813c/in/write";
String command = "{\"4\":true}";
mqttapi.sendCommand(sendtopic, command, 0, false,
        new ListenDeviceCallBack() {
            @Override
            public void onSuccess(String message) {
                Log.d("---", message);
            }
            @Override
            public void onFailure(int code, String message) {
                Log.d("---", code + " - " + message);
            }
        });
```

<div id="addDeviceListener"></div>
#**addDeviceListener**
    增加订阅的通道

    addDeviceListener(String topic, int qos, ListenDeviceCallBack ctrldevcb)

#####params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:| -----------:
topic     | String       | 订阅的通道
qos     | int       | 建议为0(QoS=0：最多一次，有可能重复或丢失。QoS=1：至少一次，有可能重复。QoS=2：只有一次，确保消息只到达一次（用于比较严格的计费系统）。)

#####callback
ctrldevcb
- 类型：ListenDeviceCallBack
- 描述：接口调用成功后的回调函数

#####示例代码
```java
String addtopic = "d64f517c/c8934691813c/in/write";
mqttapi.addDeviceListener(addtopic, 0, new ListenDeviceCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d("---", message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d("---", code + " - " + message);
    }
});
```

<div id="removeDeviceListener"></div>
#**removeDeviceListener**
    移除一个订阅的通道

    removeDeviceListener(String topic, ListenDeviceCallBack ctrldevcb)

#####params
参数名 | 类型 | 描述
:-----------  | :-------------:| -----------:| -----------:
topic     | String       | 订阅的通道

#####callback
ctrldevcb
- 类型：ListenDeviceCallBack
- 描述：接口调用成功后的回调函数

#####示例代码
```java
String rmtopic = "d64f517c/c8934691813c/in/write";
mqttapi.removeDeviceListener(rmtopic, new ListenDeviceCallBack() {
    @Override
    public void onSuccess(String message) {
        Log.d("---", message);
    }
    @Override
    public void onFailure(int code, String message) {
        Log.d("---", code + " - " + message);
    }
});
```

<div id="errorcode"></div>
#**状态码**

code | message | 描述
:-----------  | :-------------: | :-------------:
9400     | Parameters can not be empty.   |    参数不能为空
9402     | It is progressing.   |    MQTT正在运行中
9403     | It is closed.   |    MQTT已经被关闭了
9404     | Qos must within (0,1,2)   |    QOS必须是0，1，2
9405     | Context is null.  |    上下文不能为空
||
2000     |    |    现在返回的是payload信息，即设备上报的信息
2001     |  Connected  |    连接上了
2002     |  Stopped  |    连接断开
2003     |  ReSubscribe success  | 订阅成功    
2004     |  Connect Exception  |    连接异常
2005     |  Lost  |    连接丢失
2006     |  Publish success  | 发送成功   
2007     |  Topic is empty  |  topic不可以为空  
2008     |  MQTT is disconnect  |    MQTT未连接
2009     |  Subscribe success  |    订阅成功
2010     |  UnSubscribe success  |    取消订阅成功
