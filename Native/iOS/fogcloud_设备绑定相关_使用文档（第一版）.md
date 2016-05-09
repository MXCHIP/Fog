#FogCloud 设备绑定相关 使用文档（第一版）

##一、配网
####0.同用户管理
####1.引入头文件
	///
	#import "FogCloud/EasyLink.h"
	
####2.在.h或者.m文件里面加入协议
	///
	@interface YourClass() <EasyLinkFTCDelegate>
####3.注意事项
#####设备只支持2.4G协议，即iPhone只能连接2.4G的WIFI，另不支持模拟器调试
	
</br>

####4.代码相关

######0.连接WIFI，获取相关WIFI信息
	///
	_ssid_textField.text = [EASYLINK ssidForConnectedNetwork];//获取广播SSID
	_ssidData = [EASYLINK ssidDataForConnectedNetwork];//获取当前的SSID信息DATA

######1.初始化EasyLink配置
	///
	easyLink_config = [[EASYLINK alloc] initWithDelegate:self];
######2.配置EasyLink
	///	
	NSMutableDictionary *wlanConfig = [NSMutableDictionary dictionaryWithCapacity:5];
    [wlanConfig setObject:_ssidData forKey:KEY_SSID];//当前的SSID信息
    [wlanConfig setObject:_passwordTextField.text forKey:KEY_PASSWORD];//当前WIFI密码
    [wlanConfig setObject:[NSNumber numberWithBool:YES] forKey:KEY_DHCP];
    [easylink_config prepareEasyLink:wlanConfig info:nil mode:EASYLINK_V2_PLUS];
   
######3.开启服务
	///
	[easyLink_config transmitSettings];
	
######4.代理方法
	///
	//注意：有些庆科模块的固件代码有回连机制，配置成功设备会返回数据给app，新版本设备配上网络以后是不回连的
	#pragma mark - EasyLink delegate - 
	- (void)onFoundByFTC:(NSNumber *)ftcClientTag withConfiguration: (NSDictionary *)configDict {
    
    	NSLog(@"New device found!");
    	[m_easylink_config configFTCClient:ftcClientTag
                   		  withConfiguration: [NSDictionary dictionary]];
	 }
	 
	#pragma mark - EasyLink delegate -
	- (void)onDisconnectFromFTC:(NSNumber *)ftcClientTag
	{
    	NSLog(@"Device disconnected!");
	}

######5.返回主屏幕或者其他情况的时候关闭服务
	///
	[m_easylink_config stopTransmitting];
    [m_easylink_config unInit];

##二、发现设备IP，建立Socket通信
####0.引入头文件
	///
	#import "FogCloud/BrowserBonjour.h"
    #import "FogCloud/AsyncSocket.h"
####1.在.h或者.m文件里面加入协议
	///
	@interface YourClass() <BrowserBonjourDelegate, AsyncSocketDelegate>

</br>

####代码相关
######1.初始化MDNS
	///
	self.m_mdns = [[BrowserBonjour alloc] init];
    _m_mdns.delegate = self;
    [_m_mdns getMdns:@"_easylink._tcp" andDomain:@"local"];
######2.在代理中处理设备发送过来的数据
	///
	- (void)returnMdnsData:(NSArray *)servicesData {
		NSLog(@"**********");
    
    	for(id object in servicesData)
    	{
        	NSString *displayModuleName=[self getdevModuleName:servicesData andObject:object];
        
        	/*detailString显示设备的MCA和IP信息*/
        	NSString *devMAC=[self getdevMAC:servicesData andObject:object];
        	NSString *devIP=[self getdevIP:servicesData andObject:object];
        	NSString *detailString = [[NSString alloc] initWithFormat:
                                  @"MAC:%@\nIP :%@",
                                  devMAC,devIP];
        	NSString *isHaveSuperUser = [self getIsHaveSuperUser:servicesData andObject:object];
        
        	NSLog(@"%@\n%@\nisHaveSuperUser:%@",displayModuleName,detailString, isHaveSuperUser);
        
        device_IPaddress = devIP;
    	}
    	NSLog(@"***********************************");
	}
	
	- (NSString*)getdevIP:(NSArray*)array andObject:(id)object {
	
    	if(array.count==0)
    	{
        	return nil;
    	}
    	
    	NSNetService *service=object[@"BonjourService"];
    	NSData *ipAddress;
    	if(service.addresses.count) {
	        ipAddress = [service.addresses objectAtIndex:0];
    	}
    	
    	NSString *deviceIP = [ipAddress host];
    	return deviceIP;
    	
	}
	
	//添加一个NSData的Category
	@implementation NSData (Additions)
	- (NSString *)host {
    	
    	struct sockaddr *addr = (struct sockaddr *)[self bytes];
    	
    	if(addr->sa_family == AF_INET) {
        char *address = inet_ntoa(((struct sockaddr_in *)addr)->sin_addr);
        
        if (address)
            return [NSString stringWithCString: address encoding: NSASCIIStringEncoding];
    	} else if (addr->sa_family == AF_INET6) {
	        struct sockaddr_in6 *addr6 = (struct sockaddr_in6 *)addr;
    	    char straddr[INET6_ADDRSTRLEN];
        	inet_ntop(AF_INET6, &(addr6->sin6_addr), straddr,
                  sizeof(straddr));
        	return [NSString stringWithCString: straddr encoding: NSASCIIStringEncoding];
    	}
    	return nil;
	}
	@end
	
##三.从手机端给设备发送信息，让设备从云端获取验证码
####代码相关
	///
	- (void)getVercodeButtonDidClicked:(id)sender {
		[self writeString:@"POST / HTTP/1.1\r\n\r\n{\"getvercode\":\"\"}" toHost:device_IPaddress onPort:8002 withTimeout:3];
	}

	/**
 	*  给设备发送信息
 	*
 	*  @param string  发送的具体信息字符串
 	*  @param host    设备的IP地址
 	*  @param port    设备的端口，默认为8002
 	*  @param timeout 超时时间
 	*/
	- (void)writeString:(NSString *)string toHost:(NSString *)host onPort:(NSInteger)port withTimeout:(NSInteger)timeout {
	
    	AsyncSocket *socket = [[AsyncSocket alloc] initWithDelegate:self];
	    [socket connectToHost:host onPort:port error:nil];
    	[socket readDataWithTimeout:timeout tag:1];
	    [socket writeData:[string dataUsingEncoding:NSUTF8StringEncoding] withTimeout:timeout tag:1];
	}

##四.通过代理获取设备发送回来的数据并处理之
####代码相关
	///
	- (void)onSocket:(AsyncSocket *)sock didConnectToHost:(NSString *)host port:(UInt16)port {
		//确定成功
	}

	- (void)onSocket:(AsyncSocket *)sock didReadData:(NSData *)data withTag:(long)tag {
	    
	    NSString *string = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
    	// 处理数据，获取验证码
	}

##五.获取设备发送的验证码到FogCloud上绑定设备
####代码相关
	///
	NSString *deviceId = ;//设备的ID
    NSString *vercode = ;//设备给我们返回的验证码
    NSString *token = ;//注册时候获取的token
    
    [[MXWCloudManager sharedInstance] bindDeviceWithID:deviceId andPassword:@"3325" vercode:vercode token:token success:^(NSDictionary *result) {
    	//
    } error:^(NSError *error) {
        //
    }];
