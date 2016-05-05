//
//  MXWCloudManager.h
//  Pods
//
//  Created by WuZhengBin on 16/3/4.
//
//

#import <Foundation/Foundation.h>
#import "BrowserBonjour.h"


typedef void (^Callback)(BOOL isSuccess);
typedef void (^SuccessCallback)(NSDictionary *result);
//typedef void (^FailureCallback)(NSDictionary *result);
typedef void (^errorCallback)(NSError *error);

@interface MXWCloudManager : NSObject

+ (instancetype)sharedInstance;

//发送验证码
- (void)signupWithAppID:(NSString *)appid identification:(NSString *)identification success:(SuccessCallback)successCallback error:(errorCallback)errorCallback;

//检验验证码
- (void)checkVerCode:(NSString *)vercode
               appID:(NSString *)appid
      identification:(NSString *)identification
             success:(SuccessCallback)successCallback;

//重置密码
- (void)resetPassword:(NSString *)password confirmPassword:(NSString *)comfirmPassword withToken:(NSString *)token success:(SuccessCallback)successCallback;

//登录
- (void)loginWithAppID:(NSString *)appid identificatiion:(NSString *)identification password:(NSString *)password success:(SuccessCallback)successCallback;

//刷新token
- (void)refreshToken:(NSString *)token success:(SuccessCallback)successCallback;

//发送指令
- (void)sendCommand:(NSString *)command toDeviceID:(NSString *)deviceID withPassword:(NSString *)password andToken:(NSString *)token success:(SuccessCallback)successCallback error:(errorCallback)errorCallback;

//云端获取绑定的验证码
- (void)fetchVerifyCodeWithDeviceID:(NSString *)deviceID withToken:(NSString *)token success:(SuccessCallback)successCallback error:(errorCallback)errorCallback;

//自解绑
- (void)removeBindingDevice:(NSString *)deviceID withToken:(NSString *)token success:(SuccessCallback)successCallback error:(errorCallback)errorCallback;

//具有管理员权限移除指定人
- (void)removeBindingDevice:(NSString *)deviceID withUserID:(NSString *)userID andToken:(NSString *)token success:(SuccessCallback)successCallback error:(errorCallback)errorCallback;

//超级用户授权转移
- (void)transferBindingDevice:(NSString *)deviceID toUser:(NSString *)userID withToken:(NSString *)token success:(SuccessCallback)successCallback error:(errorCallback)errorCallback;

//更新授权级别
- (void)updateBindingRole:(NSString *)role forUser:(NSString *)userID withDevice:(NSString *)deviceID andToken:(NSString *)token success:(SuccessCallback)successCallback error:(errorCallback)errorCallback;

//设备授权更新
- (void)updateGrantOfDevice:(NSString *)deviceID withPassword:(NSString *)devicepw vercode:(NSString *)vercode token:(NSString *)token success:(SuccessCallback)successCallback error:(errorCallback)errorCallback;

//设备绑定(超级用户)
- (void)bindDeviceWithID:(NSString *)deviceID andPassword:(NSString *)password vercode:(NSString *)vercode token:(NSString *)token success:(SuccessCallback)successCallback error:(errorCallback)errorCallback;

//验证应用端授权
- (void)verifyToken:(NSString *)token success:(SuccessCallback)successCallback error:(errorCallback)errorCallback;

//设备授权
- (void)grantDevice:(NSString *)device vercode:(NSString *)vercode role:(NSString *)role token:(NSString *)token bindingType:(NSString *)bindingType needCallback:(BOOL)isCallback success:(SuccessCallback)successCallback error:(errorCallback)errorCallback;

//更新设备别名
- (void)updateAlias:(NSString *)alias withDeviceID:(NSString *)deviceID token:(NSString *)token success:(SuccessCallback)successCallback error:(errorCallback)errorCallback;
@end
