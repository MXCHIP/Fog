//
//  GCDAsyncSocketManager.h
//  MiCO2
//
//  Created by WuZhengBin on 16/5/20.
//  Copyright © 2016年 WuZhengBin. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GCDAsyncSocket.h"

typedef void (^GCDSendDeviceVercodeHandler) (NSString *deviceId, NSString *devicePassword, NSString *vercode);

@interface GCDAsyncSocketManager : NSObject

+ (instancetype)sharedInstance;
- (void)fetchVercodeFromHost:(NSString *)host onPort:(NSInteger)port andHandler:(GCDSendDeviceVercodeHandler)handler;

@end
