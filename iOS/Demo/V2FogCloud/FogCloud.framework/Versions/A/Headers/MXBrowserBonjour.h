//
//  MXBrowserBonjour.h
//  MiCO2
//
//  Created by WuZhengBin on 16/5/18.
//  Copyright © 2016年 WuZhengBin. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef void (^MXBrowserBonjourDataHandler)(NSArray *array);

@protocol MXBrowserBonjourDelegate <NSObject>
- (void)returnMdnsData:(NSArray *)servicesData;
@end

@interface MXBrowserBonjour : NSObject <NSNetServiceBrowserDelegate, NSNetServiceDelegate>
//- (void)startMdnsForType:(NSString *)serviceType andDomain:(NSString *)domain;
- (void)stopMdns;
- (void)startMdnsForType:(NSString *)serviceType andDomain:(NSString *)domain andHandler:(MXBrowserBonjourDataHandler)handler;
@property (nonatomic, copy) id<MXBrowserBonjourDelegate> delegate;
@end
