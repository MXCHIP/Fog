##  FogV3 使用指南

1. 解压 `FogV3.framework.zip` 引入解压后的 `FogV3.framework`文件
2. pod 导入XMNetWorking，MQTTClient
3. `Build Settings`->`Other linker Flags` 添加`-ObjC`
4. 引入头文件，如`#import <FogV3/FogV3.h>`
5. 网络请求全局配置 如：
#### 代码示例
```
[XMCenter setupConfig:^(XMConfig *config) {
config.generalServer = @"https://api.fogcloud.io";
config.callbackQueue=dispatch_get_main_queue();
#ifdef DEBUG
config.consoleLog = YES;
#endif
}];
```

</br>

## 概述
#### 想通过APP远程控制一个智能设备，您需要FAE的支持，如果WIFI模块（硬件）已经准备就绪，那么您只需要完成以下几步

1. 通过Fogcloud平台注册一个APP，得到appid，因为下面需要用到
2. 对于一个新用户而言，首先需要注册用户，获取验证码、验证验证码、注册登录等，这些都在FogUser部分
3. 注册完成后，我还没有一个可以控制的设备，我需要绑定一个设备，绑定之前需要先让设备连上WIFI路由器，

>1)让设备连上路由器(EasyLink)

>2)连上以后找到这个设备的IP(SearchDevice)

>3)绑定它(bindDevice)

## FogUser 用户管理
#### 基础功能
* [获取验证码](#getVerifyCode)
* [检查验证码](#checkVerifyCode)
* [注册](#register)
* [登录](#login)
* [刷新Token](#refreshToken)

#### 权限管理
* [获取用户列表](#getMemberList)
* [移除用户权限](#removeBindRole)


<div id='getVerifyCode'>

### *getVerifyCode*
#### params
| 参数名       |    类型    |     描述 |
| :-------- | :------: | :-------------------|
| loginName | NSString |登录名，邮箱或者手机号 |
| appid     | NSString | 在Fogcloud平台注册的APP的id |

#### 代码示例
```
NSString *loginName = @"hj@mxchip.com";//也可以为手机号码
NSString *appid = @"appidappidappidappid";
[[FogUserManager sharedInstance]getVerifyCodeWithLoginName:loginName andAppid:appid success:^(id responseObject) {


} failure:^(NSError *error) {

}];
```

<div id='checkVerifyCode'>

### *checkVerifyCode*
#### params
| 参数名       |    类型    |       描述 |
| :-------- | :------: | :------------------- |
| loginName | NSString |          登录名，邮箱或者手机号 |
| appid     | NSString | 在Fogcloud平台注册的APP的id |
| vercode   | NSString |         邮箱或者手机收到的验证码 |

#### 代码示例 
```
NSString *loginName = @"hj@mxchip.com";//也可以为手机号码
NSString *appid = @"appidappidappidappid";
NSString *vercode = @"478966";
[[FogUserManager sharedInstance]checkVerifyCodeWithLoginName:loginName vercode:vercode appid:appid success:^(id responseObject) {


} failure:^(NSError *error) {

}];
```

<div id='register'>

### *register*

#### params
| 参数名       |    类型    |       描述 |
| :-------- | :------: | :-------------------|
| password  | NSString |           用户密码 |
| token     | NSString |检查验证码返回的 token |

#### 代码示例
```
NSString *token = @"xxx...";
[[FogUserManager sharedInstance]setPassword:password token:token success:^(id responseObject) {
    
} failure:^(NSError *error) {

}];
```

<div id='login'>

### *login*

#### params
| 参数名       |    类型    |     描述 |
| :-------- | :------: | :------------------------ |
| loginName | NSString |登录名，可以是邮箱或者手机号 |
| password  | NSString |                    用户密码 |
| appid     | NSString | 在 Fogcloud 平台注册的 app 的 id |
| extend | NSString |扩展参数（没有传nil） |

#### 代码示例
```
[[FogUserManager sharedInstance]loginWithName:loginName password:password appid:appid extend:nil success:^(id responseObject) {
        
} failure:^(NSError *error) {
        
}];
```

<div id='refreshToken'>

### *refreshToken*

#### params
| 参数名   |    类型    |     描述 |
| :---- | :------: | :----------- |
| token | NSString | 本地持久化的旧的 token |
#### 代码示例
```
[[FogUserManager sharedInstance] refreshTokenWithOldToken:token success:^(id responseObject) {

} failure:^(NSError *error) {

}];
```

<div id='getMemberList'>

### *getMemberList*
#### params
| 参数名      |    类型    |     描述 |
| :------- | :------: | :----------- |
| token    | NSString | 本地持久化的 token |
| deviceid | NSString |       设备的 id |
#### 代码示例
```
NSString *token = [TokenManager token];
NSString *deviceid = @"xxx-asdfasdf-asdfasdfas";
[[FogDeviceManager sharedInstance] getMemberListWithDeviceId:deviceid token:token success:^(id responseObject) {

} failure:^(NSError *error) {

}];
```

<div id='removeBindRole'>

### *removeBindRole*
#### params
| 参数名      |    类型    |   描述 |
| :------- | :------: | :----------- |
| token    | NSString | 本地持久化的 token |
| deviceid | NSString |       设备的 id |
| enduserid   | NSString | 欲移除权限的用户的 id |

#### 代码示例
```
[[FogDeviceManager sharedInstance]removeBindRoleWithDeviceId:deviceid enduserid:enduserid token:token success:^(id responseObject) {

} failure:^(NSError *error) {

}];
```

## FogDevice 设备管理
#### EasyLink
* [获取SSID](#getSSID)
* [开始配网](#startEasyLink)
* [停止配网](#stopEasyLink)

#### 搜索设备
* [开始搜索设备](#startSearchDevices)
* [停止搜索设备](#stopSearchDevices)

#### 绑定设备
* [绑定设备](#bindDevice)
* [解绑设备](#unBindDevice)
* [获取设备分享码](#getShareVerCode)
* [通过分享码绑定设备](#addDeviceByVerCode)

#### 设备管理
* [获取设备列表](#getDeviceList)
* [获取设备信息](#getDeviceInfo)
* [修改设备名称](#updateDeviceAlias)




<div id='getSSID'>

### *getSSID*
#### 代码示例
```
NSString *ssid= [[FogEasyLinkManager sharedInstance] getSSID];
```

<div id='startEasyLink'>

### *startEasyLink*
| 参数名      |    类型    |   描述 |
| :------- | :------: | :------------- |
| password | NSString | 当前连接的 wifi 的密码 |
#### 代码示例
```
 [[FogEasyLinkManager sharedInstance]startEasyLinkWithPassword:password];

```

<div id='stopEasyLink'>

### *stopEasyLink*
#### 代码示例
```
[[FogEasyLinkManager sharedInstance]stopEasyLink];
```

<div id='startSearchDevices'>

### *startSearchDevices*

#### 代码示例
```
[FogDeviceManager sharedInstance].delegate=self;
[[FogDeviceManager sharedInstance]startSearchDevices];


#pragma mark - FogDeviceDelegate
-(void)didSearchDeviceReturnArray:(NSArray *)array
{
NSLog(@"%@", devicesArray);
//简单讲解一下，假设第一次搜索我搜索到两个设备，这时候会返回两个设备的数组
//当有一个新设备进来了，这个回调会再次调用，此时会返回三个设备的数组
//若有设备离线了，这个回调会再次调用，此时返回两个设备的数组
//也就是这个回调会在 搜索到的设备 数量变化的时候调用
//为方便起见，设备信息作为 NSDictionary 返回。一个 Key 是这个 NSNetService，方便开发者进行二次解析，另一个 Key 是返回service 的全部 RecordData，与之前版本返回值相同
}
```

<div id='stopSearchDevices'>

### *stopSearchDevices*
#### 代码示例
```
[[FogDeviceManager sharedInstance]stopSearchDevices];
```

<div id='bindDevice'>

### *bindDevice*
| 参数名      |    类型    |       描述 |
| :------- | :------: | :------------------- |
| deviceid | NSString |     设备id |
| token    | NSString |    登录 app 后获取的 token |
| extend | NSString |     扩展参数（没有传nil） |
#### 代码示例
```
[[FogDeviceManager sharedInstance]bindDeviceWithDeviceId:deviceId token:[HJUserInfo shareUserInfo].token extend:nil success:^(id responseObject) {
        
 } failure:^(NSError *error) {
        
     
 }];
```

<div id='unBindDevice'>

### *unBindDevice*
| 参数名      |    类型    |    描述 |
| :------- | :------: | :----------------------------- |
| deviceid | NSString | 想要解除绑定的设备 ID（在上面的绑定设备接口成功后会返回） |
| token    | NSString |     登录 app 后获取的 token |
#### 代码示例
```
[[FogDeviceManager sharedInstance]unBindDeviceWithDeviceId:deviceid token:token success:^(id responseObject) {
        
} failure:^(NSError *error) {
        
}];
```
<div id='getShareVerCode'>

### *getShareVerCode*
| 参数名      |    类型    |       描述 |
| :------- | :------: | :---------------- |
| deviceid | NSString |     设备id |
| token    | NSString |    登录 app 后获取的 token |
| role | NSInteger |     授权级别：2→管理员 3→普通用户|
| granttimes    | NSInteger |    验证码是否一次有效，0为一次有效，非0为十分钟有效|
#### 代码示例
```
[[FogDeviceManager sharedInstance]getShareVerCodeWithDeviceId:deviceid role:role granttimes:granttimes token:token success:^(id responseObject) {
        
} failure:^(NSError *error) {
        
}];
```

<div id='addDeviceByVerCode'>

### *addDeviceByVerCode*
| 参数名      |    类型    |           描述 |
| :------- | :------: | :------------------- |
| deviceid | NSString |     设备id |
| token    | NSString |    登录 app 后获取的 token |
| vercode | NSString |     授权分享码|
| bindingtype    | enum |    用户分组|
| extend | Json String |     扩展参数(没有传nil)|
| iscallback    | NSInteger |    是否需要发送mqtt消息通知设备|

#### 代码示例
```
[[FogDeviceManager sharedInstance]addDeviceByVerCodeWithDeviceId:deviceid vercode:vercode bindingtype:bindingtype extend:extend iscallback:iscallback token:token success:^(id responseObject) {

  } failure:^(NSError *error) {
        
 }];
```
<div id='getDeviceList'>


### *getDeviceList*
| 参数名      |    类型    |       描述 |
| :------- | :------: | :---------------- |
| token    | NSString | 登录 app 后获取的 token |
#### 示例代码
```
[[FogDeviceManager sharedInstance] getDeviceListWithToken:token success:^(id responseObject) {
        
} failure:^(NSError *error) {
        
}];
```

<div id='getDeviceInfo'>

### *getDeviceInfo*
| 参数名      |    类型    |       描述 |
| :------- | :------: | :---------------- |
| deviceid | NSString |             设备 id |
| token    | NSString | 登录 app 后获取的 token |
#### 示例代码
```
 [[FogDeviceManager sharedInstance]getDeviceInfoWithDeviceId:deviceid token:token success:^(id responseObject) {
       
  } failure:^(NSError *error) {
       
  }];
```

<div id='updateDeviceAlias'>

### *updateDeviceAlias*
| 参数名      |    类型    |    描述 |
| :------- | :------: | :---------------- |
| alias    | NSString |              设备别名 |
| deviceid | NSString |             设备 id |
| token    | NSString | 登录 app 后获取的 token |
#### 示例代码
```
[[FogDeviceManager sharedInstance]updateDeviceAliasWithDeviceId:deviceid alias:alias token:token success:^(id responseObject) {
        
} failure:^(NSError *error) {
        
}];
```

## FogMQTT 管理
#### MQTT

* [获取mqtt信息](#getMqttInfo)
* [监听远程设备](#startListenDevice)
* [增加订阅通道](#addDeviceListener)
* [发送指令](#sendCommand)
* [移除订阅通道](#removeDeviceListener)
* [停止监听](#stopListenDevice)

<div id='getMqttInfo'>

### *getMqttInfo*
| 参数名      |    类型    |          描述 |
| :------- | :------: | :---------------- |
| token    | NSString | 登录 app 后获取的 token |
#### 示例代码
```
[[FogMQTTManager sharedInstance]getMqttInfoWithToken:token success:^(id responseObject) {
        
 } failure:^(NSError *error) {
 
 }];
```

<div id='startListenDevice'>

### *startListenDevice*
| 参数名      |    类型    |      描述 |
| :------- | :------: | :---------------- |
| mqttInfo    | MqttInfo |              mqttInfo模型 |
| usingSSL    | BOOL |              是否使用SSL |
#### 示例代码
```
[[FogMQTTManager sharedInstance]startListenDeviceWithMqttInfo:mqttInfo usingSSL:usingSSL connectHandler:^(NSError *error) {
        
}];
```

<div id='addDeviceListener'>

### *addDeviceListener*
| 参数名      |    类型    |          描述 |
| :------- | :------: | :---------------- |
| topic    | NSString |              主题 |
| qoslevel | enum |             消息质量|
#### 示例代码
```
[[FogMQTTManager sharedInstance]addDeviceListenerWithTopic:topic atLevel:qoslevel mqttReturn:^(NSError *error, NSArray<NSNumber *> *gQoss) {
        
}];
```

<div id='sendCommand'>

### *sendCommand*
| 参数名      |    类型    |     描述 |
| :------- | :------: | :---------------- |
| data  | NSData |              指令
| topic | NSString |             主题 |
| retain| Bool | if YES, data is stored on the MQTT broker until overwritten by the next publish with retainFlag = YES |
| qoslevel | enum |             消息质量 |
#### 示例代码
```
[[FogMQTTManager sharedInstance]sendCommandWithData:data onTopic:topic retain:retain qos:qoslevel sendReturn:^(NSError *error) {
        
}];
```

<div id='removeDeviceListener'>

### *removeDeviceListener*
| 参数名      |    类型    |    描述 |
| :------- | :------: | :---------------- |
| topic    | NSString |              要移除的主题

#### 示例代码
```
[[FogMQTTManager sharedInstance]removeDeviceListenerWithTopic:topic];
```

<div id='stopListenDevice'>

### *stopListenDevice*

#### 示例代码
```
[[FogMQTTManager sharedInstance]stopListenDevice];
```