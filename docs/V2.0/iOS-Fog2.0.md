##  FogCloud For iOS Native 使用指南

1. 解压 `FogCloud.zip` 引入解压后的 `FogCloud.framework`, `Others.Bundle`, `EasyLink.a`文件
2. Pod 安装 `AFNetworking`
3. 点击项目文件，添加 `CoreData` 库
4. 引入头文件，如`#import <FogCloud/MicoUserManager.h>`

</br>
## 概述
#### 想通过APP远程控制一个智能设备，您需要FAE的支持，如果WIFI模块（硬件）已经准备就绪，那么您只需要完成以下几步

1. 通过Fogcloud平台注册一个APP，得到appid，因为下面需要用到
2. 对于一个新用户而言，首先需要注册用户，获取验证码、验证验证码、注册登录等，这些都在MiCOUser部分
3. 注册完成后，我还没有一个可以控制的设备，我需要绑定一个设备，绑定之前需要先让设备连上WIFI路由器，

>1)让设备连上路由器(EasyLink)

>2)连上以后找到这个设备的IP(SearchDevice)

>3)绑定它(bindDevice)

## #MiCOUser 用户管理
#### 基础功能
* [获取验证码](#getVerifyCode)
* [检查验证码](#checkVerifyCode)
* [注册](#register)
* [登录](#login)
* [刷新Token](#refreshToken)

#### 权限管理
* [获取用户列表](#fetchMemberList)
* [移除用户权限](#removeBindRole)


<div id='getVerifyCode'>

### *getVerifyCode*
#### params
| 参数名       |    类型    |                   描述 |
| :-------- | :------: | -------------------: |
| loginName | NSString |          登录名，邮箱或者手机号 |
| appid     | NSString | 在Fogcloud平台注册的APP的id |

#### 代码示例
```
    NSString *loginName = @"wzbdroid@126.com";//也可以为手机号码
    NSString *appid = @"appidappidappidappid";
    [[MicoUserManager sharedInstance] getVerifyCodeWithLoginName:loginName andAppid:appid success:^(NSDictionary *result) {
        //成功回调
    } failure:^(NSError *error) {
        //失败回调
    }];
```

<div id='checkVerifyCode'>

### *checkVerifyCode*
#### params
| 参数名       |    类型    |                   描述 |
| :-------- | :------: | -------------------: |
| loginName | NSString |          登录名，邮箱或者手机号 |
| appid     | NSString | 在Fogcloud平台注册的APP的id |
| vercode   | NSString |         邮箱或者手机收到的验证码 |

#### 代码示例 
```
	NSString *loginName = @"wzbdroid@126.com";//也可以为手机号码
    NSString *appid = @"appidappidappidappid";
    NSString *vercode = @"478966";
    [[MicoUserManager sharedInstance] checkVerifyCodeWithLoginName:loginName vercode:vercode appid:appid success:^(NSDictionary *result) {
        //处理成功回调，例如
        self.tokenLabel.text = result[@"token"];
    } failure:^(NSError *error) {
        //处理失败回调
    }];
```

<div id='register'>

### *register*

#### params
| 参数名       |    类型    |                   描述 |
| :-------- | :------: | -------------------: |
| password1 | NSString |                 用户密码 |
| password2 | NSString |             用户密码，确认用 |
| appid     | NSString | 在Fogcloud平台注册的APP的id |
| token     | NSString |       检查验证码返回的 token |

#### 代码示例
```
	NSString *token = @"xxx...";
    [[MicoUserManager sharedInstance] registerUserWithPassword:@"123456" confirmPassword:@"123456" appid:APP_ID token:token success:^(NSDictionary *result) {
        //处理成功回调
    } failure:^(NSError *error) {
        //处理失败回调
    }];
```

<div id='login'>

### *login*

#### params
| 参数名       |    类型    |                        描述 |
| :-------- | :------: | ------------------------: |
| loginName | NSString |            登录名，可以是邮箱或者手机号 |
| password  | NSString |                      用户密码 |
| appid     | NSString | 在 Fogcloud 平台注册的 app 的 id |

#### 代码示例
```
    [[MicoUserManager sharedInstance] loginWithName:@"wzbdroid@126.com" password:@"123456" appid:APP_ID success:^(NSDictionary *result) {
        //处理成功回调
    } failure:^(NSError *error) {
        //处理失败回调
    }];
```

<div id='refreshToken'>

### *refreshToken*

#### params
| 参数名   |    类型    |           描述 |
| :---- | :------: | -----------: |
| token | NSString | 本地持久化的 token |
#### 代码示例
```
    NSString *token = [TokenManager token];//老的 token
    [[MicoUserManager sharedInstance] refreshTokenWithOldToken:@"" success:^(NSDictionary *result) {
        //处理成功回调
        //持久化新的 token
    } failure:^(NSError *error) {
        //处理失败回调
    }];
```

<div id='fetchMemberList'>

### *getMemberList*
#### params
| 参数名      |    类型    |           描述 |
| :------- | :------: | -----------: |
| token    | NSString | 本地持久化的 token |
| deviceid | NSString |       设备的 id |
#### 代码示例
```
    NSString *token = [TokenManager token];
    NSString *deviceid = @"xxx-asdfasdf-asdfasdfas";
    [[MicoUserManager sharedInstance] getMemberListWithDeviceID:deviceid andToken:token success:^(NSDictionary *result) {
        //处理成功回调
    } failure:^(NSError *error) {
        //处理失败的回调
    }];
```

<div id='removeBindRole'>

### *removeBindRole*
#### params
| 参数名      |    类型    |           描述 |
| :------- | :------: | -----------: |
| token    | NSString | 本地持久化的 token |
| deviceid | NSString |       设备的 id |
| userid   | NSString | 欲移除权限的用户的 id |

#### 代码示例
```
    [[MicoUserManager sharedInstance] removeBindRoleWithDeviceID:deviceId enduserID:enduserId token:token success:^(NSDictionary *result) {
        //处理成功回调
    } failure:^(NSError *error) {
        //处理失败回调
    }];
```

## #MiCODevice 设备管理
#### EasyLink
* [获取SSID](#fetchCurrentSSID)
* [开始配网](#startEasyLink)
* [停止配网](#stopEasyLink)

#### 搜索设备
* [开始搜索设备](#startSearchDevices)
* [停止搜索设备](#stopSearchDevices)

#### 绑定设备
* [绑定设备](#bindDevice)
* [解绑设备](#unBindDevice)

#### 设备管理
* [获取设备列表](#getDeviceList)
* [获取设备信息](#getDeviceInfo)
* [修改设备名称](#updateDeviceAlias)


<!--#### 权限管理
* [获取用户列表](#getMemberList)
* [移除用户权限](#)-->




<div id='fetchCurrentSSID'>

### *fetchCurrentSSID*
#### 代码示例
```
    [[MicoDeviceManager sharedInstance] fetchCurrentSSIDWithBlock:^(NSString *ssid) {
        NSLog(@"%@", ssid);
    }];
```

<div id='startEasyLink'>

### *startEasyLink*
| 参数名      |    类型    |             描述 |
| :------- | :------: | -------------: |
| password | NSString | 当前连接的 wifi 的密码 |
#### 代码示例
```
	[[MicoDeviceManager sharedInstance] startEasyLinkWithPassword:@"12345678" handler:^(BOOL isSuccess) {
    	//其实好像不需要这个 block    
    }];
```

<div id='stopEasyLink'>

### *stopEasyLink*
#### 代码示例
```
    [[MicoDeviceManager sharedInstance] stopEasyLinkWithBlock:^(NSString *message) {
        //这里开个 HUD 好了
    }];
```

<div id='startSearchDevices'>

### *startSearchDevices*

**注意，此处有较大更新。**

- 首先引入头文件 `#import <FogCloud/ZBBonjourService.h>`
- 其次遵从协议 `@interface YourCustomClass () <ZBBonjourServiceDelegate>`
- 再次请看下面代码示例

#### 代码示例
```
- (void)searchDevices {
	[[ZBBonjourService sharedInstance] stopSerachDevice];//为保险起见，先停止搜索
	[ZBBonjourService sharedInstance].delegate = self;
	[[ZBBonjourService sharedInstance] startSearchDevice];
}

#pragma mark - ZBBonjourServiceDelegate
- (void)bonjourService:(ZBBonjourService *)service didReturnDevicesArray:(NSArray *)array {
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
[[ZBBonjourService sharedInstance] stopSerachDevice];
```

<div id='bindDevice'>

### *bindDevice*
| 参数名      |    类型    |                   描述 |
| :------- | :------: | -------------------: |
| deviceIP | NSString | 从 mDNS 中发现的设备的 IP 地址 |
| token    | NSString |    登录 app 后获取的 token |
#### 代码示例
```
	[[MicoDeviceManager sharedInstance] bindDeviceWithDeviceIP:@"192.168.1.102" andToken:token success:^(NSDictionary *result) {
        NSLog(@"%@", result);
    } failure:^(NSError *error) {
        NSLog(@"%@", error.localizedDescription);
    }];
```

<div id='unBindDevice'>

### *unBindDevice*
| 参数名      |    类型    |                             描述 |
| :------- | :------: | -----------------------------: |
| deviceID | NSString | 想要解除绑定的设备 ID（在上面的绑定设备接口成功后会返回） |
| token    | NSString |              登录 app 后获取的 token |
#### 代码示例
```
	[[MicoDeviceManager sharedInstance] unBindDeviceWithDeviceID:deviceID andToken:token success:^(NSDictionary *result) {
        NSLog(@"%@", result);
    } failure:^(NSError *error) {
        NSLog(@"%@", error.localizedDescription);
    }];
```

<div id='getDeviceList'>

### *getDeviceList*
#### 示例代码
```
	[[MicoDeviceManager sharedInstance] fetchDeviceListWithToken:token 		success:^(NSDictionary *result) {
		NSLog(@"%@", result);    
    } failure:^(NSError *error) {
        NSLog(@"%@", error.localizedDescription)
    }];
```

<div id='getDeviceInfo'>

### *getDeviceInfo*
| 参数名      |    类型    |                描述 |
| :------- | :------: | ----------------: |
| deviceID | NSString |             设备 ID |
| token    | NSString | 登录 app 后获取的 token |
#### 示例代码
```
    [[MicoDeviceManager sharedInstance] fetchDeviceInfoWithDeviceID:deviceID andToken:token success:^(NSDictionary *result) {
        NSLog(@"%@", result);
    } failure:^(NSError *error) {
        NSLog(@"%@", error.localizedDescription);
    }];
```

<div id='updateDeviceAlias'>

### *updateDeviceAlias*
| 参数名      |    类型    |                描述 |
| :------- | :------: | ----------------: |
| alias    | NSString |              设备别名 |
| deviceID | NSString |             设备 ID |
| token    | NSString | 登录 app 后获取的 token |
#### 示例代码
```
	[[MicoDeviceManager sharedInstance] updateDeviceAlias:@"9412B" withDeviceID:@"" token:token success:^(NSDictionary *result) {
        NSLog(@"%@", result);
    } failure:^(NSError *error) {
        NSLog(@"%@", error.localizedDescription);
    }];
```