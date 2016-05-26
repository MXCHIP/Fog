//
//  micoMqtt.h
//  micoMqtt
//
//  Created by zfw on 15/9/8.
//  Copyright (c) 2015年 mxchip. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MQTTKit.h"//调用开源mqtt库


typedef void(^MQTTMessageCB)(NSString *str_message);

@interface micoMqtt : NSObject

//声明mqtt函数和变量
@property (nonatomic, strong) MQTTClient *m_mqttClient;
/*
 @开启mqtt服务
 @host：mqtt服务器域名
 @port：tcp端口，mqtt是一种tcp长连接，填0，一般不用，mqtt端口默认是1883
 @username：登录名，暂时不用，可以nil
 @password：密码，暂时不用，可以nil
 @clientId：客户端id，实际使用的时候需要保证每个id都是唯一的，用户自己做唯一性保证，否则客户端会被挤掉
 @cb：异步成功返回回调，不成功不返回
*/
- (void) startMqttService:(NSString *)host
                     port:(NSInteger)port
                 username:(NSString *)username
                 password:(NSString *)password
                 clientId:(NSString *)clientId
                 deviceId:(NSString *)deviceId
                 callBack:(MQTTMessageCB)cb;
/*
 @关闭mqtt服务
 @cb：返回关闭成功与否
 */
- (void) stopMqttService:(MQTTMessageCB)cb;

/*
 @接受mqtt消息
 @cb：返回接收到的消息
 */
- (void) recvMessage:(MQTTMessageCB)cb;

/*
 @停止接收mqtt消息，将接收回调置为nil
 */
- (void) stopRecvMessage;

/*
 @发送控制指令
 @command：发送控制指令
 @topic：指令发往通道名字
 @qos：消息服务质量，至多一次，至少一次，只有一次
 @retained：保持，表示发送的消息是否需要在服务器持久保存，可以选为不保持，只发给当前订阅者
 @cb：异步成功返回回调，不成功不返回
 */
- (void) publishCommand:(NSString *)command
                  topic:(NSString *)topic
                    qos:(NSInteger)qos
               retained:(Boolean)retained
               callBack:(MQTTMessageCB)cb;


/* 
 @订阅一个通道
 @topic 通道名字
 */
- (void) subscribe:(NSString *)topic
          callBack:(MQTTMessageCB)cb;

/*
 @取消订阅一个通道
 @topic 通道名字
 */
- (void) unsubscribe:(NSString *)topic
            callBack:(MQTTMessageCB)cb;

@end
