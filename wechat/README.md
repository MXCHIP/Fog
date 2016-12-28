## wechat
<!-- websoket，可供微信使用

1、微信授权
https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb9838927f21c4ab3&redirect_uri=http://32278f14-8507-4b77-8193-7775d34faff1.app.easylink.io/oauth.php%3Fwxappid%3Dwxb76dd57f05c5922b&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect

2、授权域名：设置-公众号设置-功能设置

3、https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxb9838927f21c4ab3&secret=d45f7431352bdce63ad299b23a4ce35c&code=031nDS890vnydt1PGQ690EmN890nDS8e&grant_type=authorization_code



原始ID: gh_a445481d9bda

AppID(应用ID): wxb9838927f21c4ab3

AppSecret(应用密钥): d45f7431352bdce63ad299b23a4ce35c

Token(令牌): 90af9e672807cb7066a05a3c9d06d8ad -->


部署在fog上的微信的信息


#####获取code

https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb9838927f21c4ab3&redirect_uri=http://wxtest.fogcloud.io/index.html&response_type=code&scope=snsapi_userinfo&state=565#wechat_redirect

#####获取openid

https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxb9838927f21c4ab3&secret=d45f7431352bdce63ad299b23a4ce35c&code=031nDS890vnydt1PGQ690EmN890nDS8e&grant_type=authorization_code

