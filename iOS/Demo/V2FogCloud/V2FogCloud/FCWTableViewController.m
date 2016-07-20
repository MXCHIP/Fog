//
//  FCWTableViewController.m
//  V2FogCloud
//
//  Created by WuZhengBin on 16/7/19.
//  Copyright © 2016年 WuZhengBin. All rights reserved.
//

#import "FCWTableViewController.h"
#import <FogCloud/MicoUserManager.h>
#import <FogCloud/MicoDeviceManager.h>
#import <FogCloud/MicoMQTT.h>
#import "SVProgressHUD.h"


static NSString * const APP_ID     = @"a4b56ed0-43f4-11e6-9d95-00163e103941";
static NSString * const LOGIN_NAME = @"15502112361";
static NSString * const DEFAULT_PW = @"12345678";

@interface CellItemObject : NSObject
@property (nonatomic, copy) NSString *itemTitle;
+ (instancetype)itemWithTitle:(NSString *)title;
@end

@implementation CellItemObject
+ (instancetype)itemWithTitle:(NSString *)title {
    CellItemObject *object = [[CellItemObject alloc] init];
    object.itemTitle = title;
    return object;
}
@end

@interface FCWTableViewController () <MicoMQTTDelegate>
@property (nonatomic, strong) NSArray *dataSourceArray;
@end

@implementation FCWTableViewController

- (void)setupDataSource {
    NSMutableArray *array = [NSMutableArray array];
    
    CellItemObject *object0 = [CellItemObject itemWithTitle:@"获取验证码"];
    [array addObject:object0];
    
    CellItemObject *object1 = [CellItemObject itemWithTitle:@"检查验证码"];
    [array addObject:object1];
    
    CellItemObject *object2 = [CellItemObject itemWithTitle:@"重置密码"];
    [array addObject:object2];
    
    CellItemObject *object3 = [CellItemObject itemWithTitle:@"登录"];
    [array addObject:object3];
    
    CellItemObject *object4 = [CellItemObject itemWithTitle:@"刷新验证码"];
    [array addObject:object4];
    
    CellItemObject *object5 = [CellItemObject itemWithTitle:@"获取当前ssid"];
    [array addObject:object5];
    
    CellItemObject *object6 = [CellItemObject itemWithTitle:@"EasyLink配网"];
    [array addObject:object6];
    
    CellItemObject *object7 = [CellItemObject itemWithTitle:@"搜索设备"];
    [array addObject:object7];
    
    CellItemObject *object8 = [CellItemObject itemWithTitle:@"绑定设备"];
    [array addObject:object8];
    
    CellItemObject *object9 = [CellItemObject itemWithTitle:@"获取设备列表"];
    [array addObject:object9];
    
    CellItemObject *object10 = [CellItemObject itemWithTitle:@"获取设备信息"];
    [array addObject:object10];
    
    CellItemObject *object11 = [CellItemObject itemWithTitle:@"更换设备别名"];
    [array addObject:object11];
    
    CellItemObject *object12 = [CellItemObject itemWithTitle:@"解除绑定"];
    [array addObject:object12];
    
    CellItemObject *object13 = [CellItemObject itemWithTitle:@"获取设备分享码"];
    [array addObject:object13];
    
    CellItemObject *object14 = [CellItemObject itemWithTitle:@"获取用户列表"];
    [array addObject:object14];
    
    CellItemObject *object15 = [CellItemObject itemWithTitle:@"监听远程设备"];
    [array addObject:object15];
    
    self.dataSourceArray = [NSArray arrayWithArray:array];
    [self.tableView reloadData];
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setupDataSource];
    
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [self.dataSourceArray count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"Cell" forIndexPath:indexPath];
    
    CellItemObject *object = (CellItemObject *)self.dataSourceArray[indexPath.row];
    cell.textLabel.text = object.itemTitle;
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    if (indexPath.row == 0) {
        [self sdk_getVerifyCode];
    }
    
    if (indexPath.row == 1) {
        [self sdk_checkVerifyCode];
    }
    
    if (indexPath.row == 2) {
        [self sdk_registerWithPassword];
    }
    
    if (indexPath.row == 3) {
        [self sdk_login];
    }
    
    if (indexPath.row == 4) {
        [self sdk_refreshToken];
    }
    
    if (indexPath.row == 5) {
        [self sdk_getSSID];
    }
    
    if (indexPath.row == 6) {
        [self sdk_startEasyLink];
    }
    
    if (indexPath.row == 7) {
        [self sdk_startSearchDevices];
    }
    
    if (indexPath.row == 8) {
        [self sdk_bindDevice];
    }
    
    if (indexPath.row == 9) {
        [self sdk_getDeviceList];
    }
    
    if (indexPath.row == 10) {
        [self sdk_getDeviceInfo];
    }
    
    if (indexPath.row == 11) {
        [self sdk_updateDeviceAlias];
    }
    
    if (indexPath.row == 12) {
        [self sdk_unBindDevice];
    }
    
    if (indexPath.row == 13) {
        [self sdk_getShareVerCode];
    }
    
    if (indexPath.row == 14) {
        [self sdk_getMemberList];
    }
    
    if (indexPath.row == 15) {
        [self sdk_startListenDevice];
    }
}

#pragma mark - Mico API
- (void)sdk_getVerifyCode {
    //获取验证码
    [SVProgressHUD show];
    [[MicoUserManager sharedInstance] getVerifyCodeWithLoginName:LOGIN_NAME andAppid:APP_ID success:^(NSDictionary *result) {
        [SVProgressHUD dismiss];
        NSLog(@"%@", result);
    } failure:^(NSError *error) {
        [SVProgressHUD showErrorWithStatus:error.localizedDescription];
    }];
}

- (void)sdk_checkVerifyCode {
    UIAlertController *ac = [UIAlertController alertControllerWithTitle:@"输入验证码" message:nil preferredStyle:UIAlertControllerStyleAlert];
    [ac addTextFieldWithConfigurationHandler:^(UITextField * _Nonnull textField) {
        textField.placeholder = @"验证码";
        textField.keyboardType = UIKeyboardTypeNumberPad;
        textField.clearButtonMode = UITextFieldViewModeWhileEditing;
    }];
    [ac addAction:[UIAlertAction actionWithTitle:@"检查验证码" style:UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
        NSArray * textfields = ac.textFields;
        UITextField *verifyCodeTextField = textfields[0];
        [SVProgressHUD show];
        [[MicoUserManager sharedInstance] checkVerifyCodeWithLoginName:LOGIN_NAME vercode:verifyCodeTextField.text appid:APP_ID success:^(NSDictionary *result) {
            NSLog(@"%@", result);
            [self p_saveToken:result[@"token"]];
            [SVProgressHUD dismiss];
        } failure:^(NSError *error) {
            [SVProgressHUD showErrorWithStatus:error.localizedDescription];
        }];
        
    }]];
    [ac addAction:[UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleCancel handler:nil]];
    [self presentViewController:ac animated:YES completion:nil];
}

- (void)sdk_startEasyLink {
   UIAlertController *ac = [UIAlertController alertControllerWithTitle:@"输入Wifi密码以启动 EasyLink" message:nil preferredStyle:UIAlertControllerStyleAlert];
    [ac addTextFieldWithConfigurationHandler:^(UITextField * _Nonnull textField) {
        textField.placeholder = @"wifi 密码";
        textField.clearButtonMode = UITextFieldViewModeWhileEditing;
    }];
    [ac addAction:[UIAlertAction actionWithTitle:@"启动 EasyLink" style:UIAlertActionStyleDefault handler:^(UIAlertAction *action) {
        NSArray * textfields = ac.textFields;
        UITextField *passwordTextField = textfields[0];
        
        
        [[MicoDeviceManager sharedInstance] startEasyLinkWithPassword:passwordTextField.text handler:^(BOOL isSuccess) {
            if (isSuccess) {
                //在合适时机关闭 EasyLink
                [SVProgressHUD showSuccessWithStatus:@"现在可以搜索周边的设备了"];
            }
        }];
        
    }]];
    [ac addAction:[UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleCancel handler:nil]];
    [self presentViewController:ac animated:YES completion:nil];
}


- (void)sdk_registerWithPassword {
    //此处仅供 demo 参考，所以只输入一次密码，并预设密码为12345678
    UIAlertController *ac = [UIAlertController alertControllerWithTitle:@"输入密码" message:nil preferredStyle:UIAlertControllerStyleAlert];
    [ac addTextFieldWithConfigurationHandler:^(UITextField * _Nonnull textField) {
        textField.placeholder = @"输入密码";
        textField.secureTextEntry = YES;
        textField.text = DEFAULT_PW;
        textField.clearButtonMode = UITextFieldViewModeWhileEditing;
    }];
    [ac addAction:[UIAlertAction actionWithTitle:@"重置密码" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        UITextField *tf = ac.textFields[0];
        [SVProgressHUD show];
        [[MicoUserManager sharedInstance] registerUserWithPassword:tf.text confirmPassword:tf.text appid:APP_ID token:[self p_loadToken] success:^(NSDictionary *result) {
            NSLog(@"%@", result);
            [SVProgressHUD dismiss];
        } failure:^(NSError *error) {
            [SVProgressHUD showErrorWithStatus:error.localizedDescription];
        }];
    }]];
    [ac addAction:[UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleCancel handler:nil]];
    [self presentViewController:ac animated:YES completion:nil];
}

- (void)sdk_login {
    //此处只是为了方便，直接使用默认的登录
    [SVProgressHUD show];
    [[MicoUserManager sharedInstance] loginWithName:LOGIN_NAME password:DEFAULT_PW appid:APP_ID success:^(NSDictionary *result) {
        NSLog(@"%@", result);
        [self p_saveToken:result[@"token"]];
        [SVProgressHUD dismiss];
    } failure:^(NSError *error) {
        [SVProgressHUD showErrorWithStatus:error.localizedDescription];
    }];
}

- (void)sdk_refreshToken {
    [SVProgressHUD show];
    [[MicoUserManager sharedInstance] refreshTokenWithOldToken:[self p_loadToken] success:^(NSDictionary *result) {
        NSLog(@"%@", result);
        [self p_saveToken:result[@"token"]];
        [SVProgressHUD dismiss];
    } failure:^(NSError *error) {
        [SVProgressHUD showErrorWithStatus:error.localizedDescription];
    }];
    
}

- (void)sdk_getSSID {
    [[MicoDeviceManager sharedInstance] fetchCurrentSSIDWithBlock:^(NSString *ssid) {
        [SVProgressHUD showSuccessWithStatus:ssid];
    }];
}

- (void)sdk_startSearchDevices {
    [[MicoDeviceManager sharedInstance] startSearchDevicesWithBlock:^(NSArray *devicesArray) {
        
        //这个服务会持续开着，请在合适时机调用[MicoDeviceManager stopSearchDevicesWithBlock:]
        NSLog(@"%@", devicesArray);
        
    }];
}

- (void)sdk_bindDevice {
    //此处为了方便，直接写死了设备 IP
    [SVProgressHUD show];
    [[MicoDeviceManager sharedInstance] bindDeviceWithDeviceIP:@"192.168.18.205" andToken:[self p_loadToken] success:^(NSDictionary *result) {
        NSLog(@"%@", result);//->此处会收到 deviceid,devicename, devicepw 等信息
        [SVProgressHUD showSuccessWithStatus:@"绑定成功，可以通过获取用户名下设备列表来验证"];
        
        
    } failure:^(NSError *error) {
        
        [SVProgressHUD showErrorWithStatus:error.localizedDescription];
    }];
}

- (void)sdk_unBindDevice {
    [SVProgressHUD show];
    
    [[MicoDeviceManager sharedInstance] unBindDeviceWithDeviceID:@"aa2dde14-0b8d-11e6-a739-00163e0204c0" andToken:[self p_loadToken] success:^(NSDictionary *result) {
        [SVProgressHUD showSuccessWithStatus:@"成功解绑, 可以通过获取用户名下设备列表来验证"];
    } failure:^(NSError *error) {
        [SVProgressHUD showErrorWithStatus:error.localizedDescription];
    }];
}

- (void)sdk_getDeviceList {
    [SVProgressHUD show];
    [[MicoDeviceManager sharedInstance] fetchDeviceListWithToken:[self p_loadToken] success:^(NSDictionary *result) {
        NSLog(@"%@", result); //->此处能获取到 device_id, device_name
        [SVProgressHUD dismiss];
    } failure:^(NSError *error) {
        [SVProgressHUD showErrorWithStatus:error.localizedDescription];
    }];
}

- (void)sdk_getDeviceInfo {
    [SVProgressHUD show];
    [[MicoDeviceManager sharedInstance] fetchDeviceInfoWithDeviceID:@"aa2dde14-0b8d-11e6-a739-00163e0204c0" andToken:[self p_loadToken] success:^(NSDictionary *result) {
        NSLog(@"获取设备信息: %@", result[@"data"]);
        [SVProgressHUD showSuccessWithStatus:@"获取设备信息成功"];
    } failure:^(NSError *error) {
        [SVProgressHUD showErrorWithStatus:error.localizedDescription];
    }];
}

- (void)sdk_updateDeviceAlias {
    [SVProgressHUD show];
    [[MicoDeviceManager sharedInstance] updateDeviceAlias:@"我爱烤面包" withDeviceID:@"aa2dde14-0b8d-11e6-a739-00163e0204c0" token:[self p_loadToken] success:^(NSDictionary *result) {
        
        NSLog(@"%@", result);
        [SVProgressHUD showSuccessWithStatus:@"更名成功，点击获取设备信息验证一下"];
        
    } failure:^(NSError *error) {
        [SVProgressHUD showErrorWithStatus:error.localizedDescription];
    }];
}

- (void)sdk_getShareVerCode {
    [SVProgressHUD show];
    
    [[MicoDeviceManager sharedInstance] fetchShareVercodeWithDeviceID:@"aa2dde14-0b8d-11e6-a739-00163e0204c0" andToken:[self p_loadToken] success:^(NSDictionary *result) {
       
        NSLog(@"%@", result);
        [SVProgressHUD dismiss];
        
    } failure:^(NSError *error) {
        [SVProgressHUD showErrorWithStatus:error.localizedDescription];
    }];
}

- (void)sdk_getMemberList {
    [SVProgressHUD show];
    
    [[MicoUserManager sharedInstance] getMemberListWithDeviceID:@"aa2dde14-0b8d-11e6-a739-00163e0204c0" andToken:[self p_loadToken] success:^(NSDictionary *result) {
        NSLog(@"%@", result);//只能看到除自己以外的用户
        [SVProgressHUD dismiss];
    } failure:^(NSError *error) {
        [SVProgressHUD showErrorWithStatus:error.localizedDescription];
    }];
}

- (void)sdk_startListenDevice {
    [SVProgressHUD show];
    
    MicoMQTT *mqtt = [[MicoMQTT alloc] init];
    mqtt.delegate = self;
    [mqtt startMQTTService:@"v2.fogcloud.io" password:DEFAULT_PW port:8443 clientID:@"5f1238c6-4d5f-11e6-9d95-00163e103941" deviceID:@"aa2dde14-0b8d-11e6-a739-00163e0204c0" usingSSL:YES success:^(NSString *message) {
        NSLog(@"%@", message);
        [SVProgressHUD dismiss];
    }];
    
}

#pragma mark - mqtt
- (void)mqtt:(MicoMQTT *)mqtt didRecieveData:(NSDictionary *)result {
    
}

#pragma mark - private methods
- (void)p_saveToken:(NSString *)token {
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    [userDefaults setObject:token forKey:@"token"];
    [userDefaults synchronize];
}

- (NSString *)p_loadToken {
    NSUserDefaults *df = [NSUserDefaults standardUserDefaults];
    return [df objectForKey:@"token"];
}


@end
