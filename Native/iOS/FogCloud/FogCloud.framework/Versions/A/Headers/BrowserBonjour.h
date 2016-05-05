//
//  BrowserBonjour.h
//  MiCO
//  mdns发现协议，设备端的固件代码内置有MiCO的mdns Bonjour发现服务协议
//  固件端发送_easylink._tcp服务，携带了一些设备的参数，app端可以调用
//  高级接口去发现设备的信息，这就是苹果的零配置Bonjour协议。
//  Created by zfw on 15/6/5.
//  Copyright (c) 2015年 mxchip. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <sys/socket.h>
#import <netinet/in.h>
#import <arpa/inet.h>
#define LOOPTIMER 5 //5秒查一次路由器
//#define kWebServiceType @"_easylink._tcp"
//#define kInitialDomain  @"local"

typedef void (^BrowserBonjourBlock)(NSArray *servicesData);

@protocol BrowserBonjourDelegate <NSObject>
-(void)returnMndsData:(NSArray *)servicesData;//返回给调用者的结果
@end


@interface BrowserBonjour : NSObject<NSNetServiceBrowserDelegate,NSNetServiceDelegate>
//提供2个函数供使用者调用，另外有个代理需要指定回调地址
-(void)getMdns:(NSString*)serviceType  andDomain:(NSString*)domain ;
-(void)stopMdns;
@property(strong,nonatomic)id<BrowserBonjourDelegate>delegate;
@property (nonatomic, copy) BrowserBonjourBlock block;
- (instancetype)initWithBlock:(BrowserBonjourBlock)block;

@end
