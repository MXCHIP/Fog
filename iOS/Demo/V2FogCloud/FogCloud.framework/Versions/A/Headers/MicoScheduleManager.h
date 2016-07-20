//
//  MicoScheduleManager.h
//  Pods
//
//  Created by WuZhengBin on 16/6/30.
//
//

#import <Foundation/Foundation.h>
#import "MXWCloudManager.h"

@interface MicoScheduleManager : NSObject
+ (instancetype)sharedInstance;
- (void)getTaskListForType:(TaskType)type
                  toDevice:(NSString *)deviceID
                 withToken:(NSString *)token
                   success:(void (^)(NSDictionary *result))success
                   failure:(void (^)(NSError *error))failure;

- (void)deleteTask:(NSString *)taskID
          inDevice:(NSString *)deviceID
         withToken:(NSString *)token
           success:(void (^)(NSDictionary *result))success
           failure:(void (^)(NSError *error))failure;

- (void)createDelayTaskWithCommand:(NSString *)command
                             after:(NSInteger)seconds
                          toDevice:(NSString *)device
                         withToken:(NSString *)token
                            enable:(BOOL)enable
                           success:(void (^)(NSDictionary *result))success
                           failure:(void (^)(NSError *error))failure;

- (void)updateDelayTaskWithTaskID:(NSString *)taskID
                       andCommand:(NSString *)command
                            after:(NSInteger)seconds
                         toDevice:(NSString *)device
                        withToken:(NSString *)token
                           enable:(BOOL)enable
                          success:(void (^)(NSDictionary *result))success
                          failure:(void (^)(NSError *error))failure;

- (void)createScheduleTaskWithCommand:(NSString *)command toDevice:(NSString *)deviceID atMonth:(NSString *)month dayOfMonth:(NSString *)dom dayOfWeek:(NSString *)dow onHour:(NSString *)hour minute:(NSString *)minute second:(NSString *)second enable:(BOOL)enable withToken:(NSString *)token success:(void (^)(NSDictionary *result))success failure:(void (^)(NSError *error))failure;

- (void)updateScheduleTaskWithTaskID:(NSString *)taskID andCommand:(NSString *)command toDevice:(NSString *)deviceID atMonth:(NSString *)month dayOfMonth:(NSString *)dom dayOfWeek:(NSString *)dow onHour:(NSString *)hour minute:(NSString *)minute second:(NSString *)second enable:(BOOL)enable withToken:(NSString *)token success:(void (^)(NSDictionary *result))success failure:(void (^)(NSError *error))failure;
@end
