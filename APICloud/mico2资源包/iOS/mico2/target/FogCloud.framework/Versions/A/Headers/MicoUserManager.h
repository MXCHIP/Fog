//
//  MicoUserManager.h
//  Pods
//
//  Created by WuZhengBin on 16/5/24.
//
//

#import <Foundation/Foundation.h>

@interface MicoUserManager : NSObject

+ (instancetype)sharedInstance;

- (void)getVerifyCodeWithLoginName:(NSString *)loginName
                          andAppid:(NSString *)appid
                           success:(void (^)(NSDictionary *result))success
                           failure:(void (^)(NSError *error))failure;

- (void)checkVerifyCodeWithLoginName:(NSString *)loginName
                             vercode:(NSString *)vercode
                               appid:(NSString *)appid
                             success:(void (^)(NSDictionary *result))success
                             failure:(void (^)(NSError *error))failure;

- (void)registerUserWithPassword:(NSString *)password1
                 confirmPassword:(NSString *)password2
                           appid:(NSString *)appid
                           token:(NSString *)token
                         success:(void (^)(NSDictionary *result))success
                         failure:(void (^)(NSError *error))failure;

- (void)loginWithName:(NSString *)loginName
             password:(NSString *)password
                appid:(NSString *)appid
              success:(void (^)(NSDictionary *result))success
              failure:(void (^)(NSError *error))failure;

- (void)refreshTokenWithOldToken:(NSString *)oldToken
                         success:(void (^)(NSDictionary *result))success
                         failure:(void (^)(NSError *error))failure;

- (void)getMemberListWithDeviceID:(NSString *)deviceID
                         andToken:(NSString *)token
                          success:(void (^)(NSDictionary *result))success
                          failure:(void (^)(NSError *error))failure;

- (void)removeBindRoleWithDeviceID:(NSString *)deviceID
                         enduserID:(NSString *)enduserID
                             token:(NSString *)token
                           success:(void (^)(NSDictionary *result))success
                           failure:(void (^)(NSError *error))failure;
@end
