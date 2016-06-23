//
//  MicoDeviceManager.h
//  Pods
//
//  Created by WuZhengBin on 16/5/24.
//
//

#import <Foundation/Foundation.h>

@interface MicoDeviceManager : NSObject

+ (instancetype)sharedInstance;

- (void)fetchCurrentSSIDWithBlock:(void (^)(NSString *ssid))handler;
- (void)startEasyLinkWithPassword:(NSString *)password
                          handler:(void (^)(BOOL isSuccess))handler;
- (void)stopEasyLinkWithBlock:(void (^)(NSString *message))handler;


- (void)startSearchDevicesWithBlock:(void (^)(NSArray *devicesArray))handler;
- (void)stopSearchDevicesWithBlock:(void (^)(NSString *message))handler;

- (void)bindDeviceWithDeviceIP:(NSString *)deviceIP
                      andToken:(NSString *)token
                       success:(void (^)(NSDictionary *result))success
                       failure:(void (^)(NSError *error))failure;

- (void)unBindDeviceWithDeviceID:(NSString *)deviceID
                        andToken:(NSString *)token
                         success:(void (^)(NSDictionary *result))success
                         failure:(void (^)(NSError *error))failure;

- (void)fetchDeviceListWithToken:(NSString *)token
                         success:(void (^)(NSDictionary *result))success
                         failure:(void (^)(NSError *error))failure;

- (void)fetchDeviceInfoWithDeviceID:(NSString *)deviceID
                           andToken:(NSString *)token
                            success:(void (^)(NSDictionary *result))success
                            failure:(void (^)(NSError *error))failure;

- (void)updateDeviceAlias:(NSString *)alias
             withDeviceID:(NSString *)deviceID
                    token:(NSString *)token
                  success:(void (^)(NSDictionary *result))success
                  failure:(void (^)(NSError *error))failure;

- (void)fetchShareVercodeWithDeviceID:(NSString *)deviceID
                             andToken:(NSString *)token
                              success:(void (^)(NSDictionary *result))success
                              failure:(void (^)(NSError *error))failure;

- (void)addDeviceByVerCodeByVercode:(NSString *)vercode
                           deviceID:(NSString *)deviceID
                           password:(NSString *)password
                        bindingType:(NSString *)bindingType
                               role:(NSString *)role
                              token:(NSString *)token
                            success:(void (^)(NSDictionary *result))success
                            failure:(void (^)(NSError *error))failure;

- (void)startListenDeviceWithDeviceID:(NSString *)deviceID
                             clientID:(NSString *)clientID
                          andUserName:(NSString *)username
                             password:(NSString *)password
                               toHost:(NSString *)host
                               onPort:(NSInteger)port
                              success:(void (^)(NSDictionary *result))success;

- (void)stopListenDevice;

- (void)addDeviceListenerWithTopic:(NSString *)topic
                            andQOS:(NSInteger)qos
                           success:(void (^)(NSString *message))success;

- (void)removeDeviceListenerWithTopic:(NSString *)topic
                              success:(void (^)(NSString *message))success;

- (void)sendCommandToDevice:(NSString *)deviceID
               withPassword:(NSString *)devicePassword
                    command:(NSString *)command
                      token:(NSString *)token
                    success:(void (^)(NSDictionary *result))success
                    failure:(void (^)(NSError *error))failure;

@end
