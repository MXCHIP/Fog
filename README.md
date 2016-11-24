##欢迎使用Fogcloud2.0云服务开发app

####APP开发分三种类型
* [Android](https://github.com/MXCHIP/Fog2.0/wiki/(Android)-Fog2.0(%E4%BA%91)-&-MiCO(%E6%A8%A1%E5%9D%97)-%E5%BC%80%E5%8F%91%E6%8C%87%E5%8D%97)

* [iOS](https://github.com/MXCHIP/Fog2.0/wiki/(iOS)-Fog2.0(%E4%BA%91)-&-MiCO(%E6%A8%A1%E5%9D%97)-%E5%BC%80%E5%8F%91%E6%8C%87%E5%8D%97)

* [APICloud](https://github.com/MXCHIP/Fog2.0/wiki/(APICloud)-Fog2.0(%E4%BA%91)-&-MiCO(%E6%A8%A1%E5%9D%97)-%E5%BC%80%E5%8F%91%E6%8C%87%E5%8D%97)

以上三种库均使用Fogcloud和MiCO系统的wifi模组而如果仅仅使用MiCO系统的wifi模组，而云平台使用自己的或者第三方的，那么配合wifi模组的三种基础功能库如下。

####基础功能库

* [EasyLink 2.0](https://github.com/MXCHIP/Fog2.0/wiki/(Android)--EasyLink2.0%E5%BC%80%E5%8F%91%E6%8C%87%E5%8D%97)

* [mDNS 2.0](https://github.com/MXCHIP/Fog2.0/wiki/(Android)-mDNS2.0-%E5%BC%80%E5%8F%91%E6%8C%87%E5%8D%97)

* [MQTT 2.0](https://github.com/MXCHIP/Fog2.0/wiki/(Android)-MQTT2.0-%E5%BC%80%E5%8F%91%E6%8C%87%E5%8D%97)

####APP端全局状态码

* [状态码](https://github.com/MXCHIP/Fog2.0/wiki/(Android)-%E7%8A%B6%E6%80%81%E7%A0%81)

#####使用庆科的WIFI模块，但是云平台并非庆科云开发流程

1. APP端启用EasyLink发送配网的数据包(SSID, PASSWORD), 发送频率建议为50ms-200ms
2. 设备收到数据包后会自动连接上WIFI路由器，并开启mDNS服务
3. APP在打开EasyLink的同时，打开mDNS，来搜索设备，
>1、可能会搜索到多个设备，这时候需要解析收到的信息，如果"IsEasylinkOK": "false"，
>2、说明这台设备没有被用户绑定，那么一般情况下他就是我们刚刚EasyLink时候新配上路由器的设备
>3、断开EasyLink和mDNS
4. 解析出待绑定设备的IP，并通过HTTP方式连接上去，发送绑定请求
5. 以上是基础配网和通信的功能，剩下来的工作视具体云平台而定

##MQTT使用场景
1. 如果APP和云平台之间通过MQTT的方式来建立连接和通信，那么就可以使用MQTT2.0
2. 使用方式和标准的MQTT类似

更多相关文档参考[FogCloud文档首页](http://doc.fogcloud.io)