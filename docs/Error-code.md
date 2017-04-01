## 状态码


状态码(code) | 说明(message) |标签(tag)| 描述
:-----------:  | :-------------: | :-------------:| :-------------:
0	|success	||	各种成功
4301	|Parameters can not be empty.||	参数为空
4302	|Context can not be null.	||	上下文有错
4303	|RunSecond should greater than 1.	||	easylink发包频率不能低于1ms
4304	|Role must within (1,2,3).||	用户的权限仅限于1，2，3
4305	|Qos must within (0,1,2).||Qos需要为0，1，2
4306	|Json Exception||收到的数据不是json
**EasyLink**|||
4000	|success	||	stopeasylink成功
4001	|invalid param	|1-99	|各种参数错误（tag用来定位具体的参数）
4002	|invalid context	||	无效的上下文
4003	|easylink busy	||	EasyLink正在工作中
4004	|easylink closed	||	EasyLink已经关闭
4006	|exception	||	异常
**mDNS**|||
4100	|[]	|	|成功搜索到了设备信息，以数组形式返回，有可能为空数组，说明此网络里没有搜索到设备
4101	|invalid param	|1-99	|各种参数错误（tag用来定位具体的参数）
4102	|invalid context	||	无效的上下文
4103	|mdns busy	||	mDNS正在工作中
4104	|mdns closed	||	mDNS已经关闭
4106	|exception	||	异常
**MQTT**|||
4201	|invalid param|	1-99	|各种参数错误（tag用来定位具体的参数）
4202	|invalid context	||	无效的上下文
4203	|mqtt busy	||	MQTT正在工作中
4204	|mqtt closed	||	MQTT已经关闭
4205	|qos must within(0,1,2)		||qos必须是0、1、2之一
4206	|exception	||	异常
|||
4210	|connected	||	MQTT连接成功
4211	|topic missing	||	缺少topic参数
4212	|stopped	||	MQTT断开连接成功
4213	|subscribe success	||	订阅成功
4214	|re-subscribe success	||	再订阅成功
4215	|unsubscribe success	||	取消订阅成功
4216	|connect exception	||	连接异常
4217	|lost	||	连接丢失
4218	|disconnected	||	未连接
4219	|publish success	||	发布成功
