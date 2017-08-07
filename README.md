##  FogCloudSDK

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

##  资源清单

### android
* [EasyLink](https://github.com/MXCHIP/Fog2.0/blob/master/docs/Android-EasyLink3.0.md)
* [mDNS](https://github.com/MXCHIP/Fog2.0/blob/master/docs/Android-mDNS3.0.md)
* [MQTT](https://github.com/MXCHIP/Fog2.0/blob/master/docs/Android-MQTT3.0.md)
* [FogV3](https://github.com/MXCHIP/Fog2.0/blob/master/docs/Android-fogV3.md)

### iOS
* [FogV3](https://github.com/MXCHIP/Fog2.0/blob/master/docs/iOS-fogV3.md)

## 状态码
[状态码](https://github.com/MXCHIP/Fog2.0/blob/master/docs/Error-code.md)

## 其他
[Fog文档](http://doc.fogcloud.io)

[V2.0版本SDK](https://github.com/MXCHIP/Fog2.0/blob/master/docs/V2.0)
