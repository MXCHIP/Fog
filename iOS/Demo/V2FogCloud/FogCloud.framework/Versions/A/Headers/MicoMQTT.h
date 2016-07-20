//
//  MicoMQTT.h
//  Pods
//
//  Created by WuZhengBin on 16/7/18.
//
//

#import <Foundation/Foundation.h>
#import "MQTTClient.h"

@class MicoMQTT;

@protocol MicoMQTTDelegate <NSObject>
- (void)mqtt:(MicoMQTT *)mqtt didRecieveData:(NSDictionary *)result;
@end

@interface MicoMQTT : NSObject

@property (nonatomic, weak) id<MicoMQTTDelegate> delegate;
- (void)startMQTTService:(NSString *)host password:(NSString *)password port:(UInt32)port clientID:(NSString *)clientID deviceID:(NSString *)deviceID usingSSL:(BOOL)usingSSL success:(void (^)(NSString *message))success;
- (void)addTopic:(NSString *)topic;
- (void)removeTopic:(NSString *)topic;
- (void)stopMQTTService;
@end
