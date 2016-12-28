##FogCloudSDK

FogCloud提供移动端，服务端整体解决方案，为移动应用开发者提供稳定可依赖的后端云服务，包括账号管理、数据收发、云食谱等，以及相关的技术支持和服务。

整体解决方案同时支持三种开发方式，android、ios和apicloud混合开发，对应的开发手册如下
* [android](https://github.com/MXCHIP/Fog2.0/wiki/(Android)-Fog2.0(%E4%BA%91)-&-MiCO(%E6%A8%A1%E5%9D%97)-%E5%BC%80%E5%8F%91%E6%8C%87%E5%8D%97)
* [ios](https://github.com/MXCHIP/Fog2.0/wiki/(iOS)-Fog2.0(%E4%BA%91)-&-MiCO(%E6%A8%A1%E5%9D%97)-%E5%BC%80%E5%8F%91%E6%8C%87%E5%8D%97)
* [apicloud](https://github.com/MXCHIP/Fog2.0/wiki/(APICloud)-Fog2.0(%E4%BA%91)-&-MiCO(%E6%A8%A1%E5%9D%97)-%E5%BC%80%E5%8F%91%E6%8C%87%E5%8D%97)

##基础功能

如果设备端使用MiCO系统的wifi模组，云平台使用自建云平台的或者第三方的云服务，那么配合wifi模组的使用需要以下三种基础功能库，分别为easylink配网、mDNS发现局域网设备、MQTT数据收发
* [EasyLink2.0(android)](https://github.com/MXCHIP/Fog2.0/wiki/(Android)--EasyLink2.0%E5%BC%80%E5%8F%91%E6%8C%87%E5%8D%97)
* [mDNS2.0(android)](https://github.com/MXCHIP/Fog2.0/wiki/(Android)-mDNS2.0-%E5%BC%80%E5%8F%91%E6%8C%87%E5%8D%97)
* [MQTT2.0(android)](https://github.com/MXCHIP/Fog2.0/wiki/(Android)-MQTT2.0-%E5%BC%80%E5%8F%91%E6%8C%87%E5%8D%97)
* [EasyLink(ios)](https://github.com/MXCHIP/EasylinkDemo)

##状态码
[状态码](https://github.com/MXCHIP/Fog2.0/wiki/(Android)-%E7%8A%B6%E6%80%81%E7%A0%81)

##开发流程
使用庆科的WIFI模块，但是云平台并非庆科云，开发流程如下

1. APP端启用EasyLink发送配网的数据包(SSID, PASSWORD), 发送频率建议为50ms-200ms
2. 设备收到数据包后会自动连接上WIFI路由器，并开启mDNS服务
3. APP在打开EasyLink的同时，打开mDNS，来搜索设备
* 可能会搜索到多个设备，这时候需要解析收到的信息，如果"IsEasylinkOK": "false"，
* 如果IsEasylinkOK==false，说明这台设备没有被用户绑定，那么一般情况下，他就是我们刚刚EasyLink时候新配上路由器的设备
* 断开EasyLink和mDNS
4. 解析出待绑定设备的IP，并通过HTTP方式连接上去（端口为8002），发送绑定请求
5. 以上是基础配网和通信的功能，剩下来的工作视具体云平台而定

更多相关文档参考[FogCloud文档首页](http://doc.fogcloud.io)