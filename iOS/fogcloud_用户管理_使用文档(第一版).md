#FogCloud 用户管理 使用文档(第一版)
##准备工作
####0.官网注册App获得AppID
####1.引入FogCloud.framework
####2.为项目添加Pod支持，导入AFNetworking
####3.引入头文件 `#import <FogCloud/MXWCloudManager.h>`
</br>

##MICOUser 管理
* [getVerifyCode](#getVerifyCode)
* [checkVerifyCode](#checkVerifyCode)
* [resetPassword](#resetPassword)
* [login](#login)
* [refreshToken](#refreshToken)

</br>


<div id='getVerifyCode'></div>
#**getVerifyCode**
####邮箱或者手机获取验证码代码
	///发送验证短信邮件
    - (void)signupWithAppID:(NSString *)appid 
             identification:(NSString *)identification 
                    success:(SuccessCallback)successCallback 
                      error:(errorCallback)errorCallback;


####**params**:
######appid:在Fogcloud平台注册的APP的id
######identification:手机号码或者邮箱
######successCallback:接口调用成功后返回的Block，一般为NSDictionary结构
######errorCallback:服务器错误等异常情况返回的Block

</br>

<div id='checkVerifyCode'></div>
#**checkVerifyCode**
####验证获取到的手机验证码代码
	///检验验证码
    - (void)checkVerCode:(NSString *)vercode
                   appID:(NSString *)appid
          identification:(NSString *)identification
                 success:(SuccessCallback)successCallback
                   error:(errorCallback)errorCallback;

####**params**:
######appid:在Fogcloud平台注册的APP的id
######identification:手机号码或者邮箱
######vercode:收到的验证码
######successCallback:接口调用成功后返回的Block，一般为NSDictionary结构
######errorCallback:服务器错误等异常情况返回的Block

</br>

<div id='resetPassword'></div>
#**resetPassword**
####重置密码
	///
	- (void)resetPassword:(NSString *)password 
	      confirmPassword:(NSString *)comfirmPassword 
	            withToken:(NSString *)token 
	              success:(SuccessCallback)successCallback
	                error:(errorCallback)errorCallback;

####**params**:
######password:密码
######confirmPassword:确认密码
######token:在[checkVerifyCode](#checkVerifyCode)接口里返回的token
######successCallback:接口调用成功后返回的Block，一般为NSDictionary结构
######errorCallback:服务器错误等异常情况返回的Block

</br>
<div id='login'></div>
#**login**
####登录
	///
	- (void)loginWithAppID:(NSString *)appid 
	       identificatiion:(NSString *)identification 
	              password:(NSString *)password 
	               success:(SuccessCallback)successCallback
	                 error:(errorCallback)errorCallback;

####**params**:
######appid:在Fogcloud平台注册的APP的id
######identification:手机号码或者邮箱
######password:用户密码
######successCallback:接口调用成功后返回的Block，一般为NSDictionary结构
######errorCallback:服务器错误等异常情况返回的Block

</br>
<div id='refreshToken'></div>
#**refreshToken**
####刷新Token
	///
	- (void)refreshToken:(NSString *)token 
	             success:(SuccessCallback)successCallback
	               error:(errorCallback)errorCallback;
######token:原来的token
######successCallback:接口调用成功后返回的Block，一般为NSDictionary结构
######errorCallback:服务器错误等异常情况返回的Block