##  FogCloudSDK

FogCloud提供移动端，服务端整体解决方案，为移动应用开发者提供稳定可依赖的后端云服务，包括账号管理、数据收发、云食谱等，以及相关的技术支持和服务。

整体解决方案同时支持三种开发方式，android、ios和apicloud混合开发，对应的开发手册如下
* [android](https://github.com/MXCHIP/Fog2.0/blob/master/docs/Android-Fog2.0.md)
* [ios](https://github.com/MXCHIP/Fog2.0/blob/master/docs/iOS-Fog2.0.md)
* [apicloud](https://github.com/MXCHIP/Fog2.0/blob/master/docs/APICloud-Fog2.0.md)

##  基础功能

如果设备端使用MiCO系统的wifi模组，云平台使用自建云平台的或者第三方的云服务，那么配合wifi模组的使用需要以下三种基础功能库，分别为easylink配网、mDNS发现局域网设备、MQTT数据收发
* [EasyLink2.0(android)](https://github.com/MXCHIP/Fog2.0/blob/master/docs/Android-EasyLink2.0.md)
* [mDNS2.0(android)](https://github.com/MXCHIP/Fog2.0/blob/master/docs/Android-mDNS2.0.md)
* [MQTT2.0(android)](https://github.com/MXCHIP/Fog2.0/blob/master/docs/Android-MQTT2.0.md)
* [EasyLink(ios)](https://github.com/MXCHIP/Fog2.0/blob/master/docs/iOS-Fog2.0.md)

## 状态码
[状态码](https://github.com/MXCHIP/Fog2.0/blob/master/docs/Error-code.md)

## 开发流程
使用庆科的WIFI模块，但是云平台并非庆科云，开发流程如下

1. APP端启用EasyLink发送配网的数据包(SSID, PASSWORD), 发送频率建议为50ms-200ms
2. 设备收到数据包后会自动连接上WIFI路由器，并开启mDNS服务
3. APP在打开EasyLink的同时，打开mDNS，来搜索设备

> 可能会搜索到多个设备，这时候需要解析收到的信息，如果"IsEasylinkOK": "false"，

> 如果IsEasylinkOK==false，说明这台设备没有被用户绑定，那么一般情况下，他就是我们刚刚EasyLink时候新配上路由器的设备

> 断开EasyLink和mDNS

4. 解析出待绑定设备的IP，并通过HTTP方式连接上去（端口为8002），发送绑定请求
5. 以上是基础配网和通信的功能，剩下来的工作视具体云平台而定

## 其他
[Fog文档](http://doc.fogcloud.io)
