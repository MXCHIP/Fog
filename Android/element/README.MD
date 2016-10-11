###element ：使用庆科的WIFI模块，但是云平台并非庆科云时使用element
**以下是单个功能模块*
>[EasyLink2.0：给设备配网](https://github.com/mxchipSDK/Fog2.0/blob/master/Android/element/EasyLink.MD)

>[mDNS2.0：发现局域网设备](https://github.com/mxchipSDK/Fog2.0/blob/master/Android/element/EasyLink.MD)

>[MQTT2.0：通过MQTT与云通信](https://github.com/mxchipSDK/Fog2.0/blob/master/Android/element/EasyLink.MD)

#####演示地址
[EasyLink2.0](http://fir.im/easylink2)

[mDNS2.0](http://fir.im/mDNS2)

[MQTT2.0](http://fir.im/MQTT2)


##开发流程

1、APP端启用EasyLink发送配网的数据包(SSID, PASSWORD), 发送频率建议为50ms-200ms

2、设备收到数据包后会自动连接上WIFI路由器，并开启mDNS服务

3、APP在打开EasyLink的同时，打开mDNS，来搜索设备，

>1、可能会搜索到多个设备，这时候需要解析收到的信息，如果"IsEasylinkOK": "false"，

>2、说明这台设备没有被用户绑定，那么一般情况下他就是我们刚刚EasyLink时候新配上路由器的设备

>3、断开EasyLink和mDNS

4、解析出那台设备的IP，并通过HTTP方式连接上去，发送绑定请求

5、以下操作视具体云平台而定


##MQTT使用场景

1、如果APP和云平台之间通过MQTT的方式来建立连接和通信，那么就可以使用MQTT2.0

2、使用方式和标准的MQTT类似