# Genesis X

### 参考：
> https://github.com/jeesun/oauthserver/tree/master/src/main/java/com/simon/common/config
> https://blog.csdn.net/lvchengbo/article/details/56486718
> https://segmentfault.com/a/1190000012275317

#### 主要参考做的最简单的列子后续再结合业务深入：
> http://www.cnblogs.com/wookong/p/9244132.html

#### 理解OAuth 2.0：
> http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html

#### 主要调试入口：TokenEndpoint 这里定义了OAuth的访问路径
##### 1、通过用户账号密码获取token（grant_type=password）
```
http://localhost:1111/oauth/token?username=genesis&password=genesisx&grant_type=password&client_id=genesis&client_secret=genesisx
username和password是：UserDetailsService里面定义的
grant_type=password：是固定的写法，模式参考 ClientDetailsServiceConfigurer.authorizedGrantTypes.有（"authorization_code","client_credentials", "password", "refresh_token"）
&client_id=genesis&client_secret=genesisx：是授权服务指定的账号密码
返回值如下：
{
    "access_token": "ae70ab4d-63a5-43c1-8605-58c029142f1b",
    "token_type": "bearer",
    "refresh_token": "c144c8ae-2ea0-4c53-9ac3-62c232bddc4b",
    "expires_in": 1199,
    "scope": "all"
}
```
##### 2、通过授权服务颁发的账号密码获取token（grant_type=client_credentials）
```
http://localhost:1111/oauth/token?grant_type=client_credentials&client_id=genesis&client_secret=genesisx
{
    "access_token": "68f1b360-803b-491d-9696-9997dbed8e3a",
    "token_type": "bearer",
    "expires_in": 1199,
    "scope": "all"
}
```

##### 3、交互式授权模式
```
使用浏览器访问下面这个路径，会引导用户到登入页面，用户输入UserDetailsService里面定义的账号密码，登入成功后获取授权码
http://localhost:1111/oauth/authorize?response_type=code&client_id=genesis&redirect_uri=http://baidu.com
response_type=code：为返回的授权码
授权成功后会转发到：&redirect_uri=http://baidu.com?code=qteU2F

http://localhost:1111/oauth/token?grant_type=authorization_code&code=s7Kdfe&client_id=genesis&client_secret=genesisx&redirect_uri=http://baidu.com
&code=s7Kdfe：是上面获取的, code只能使用一次

{
    "access_token": "5ade2caa-fa20-4b98-bef0-e63f2de54b7f",
    "token_type": "bearer",
    "refresh_token": "c144c8ae-2ea0-4c53-9ac3-62c232bddc4b",
    "expires_in": 1199,
    "scope": "all"
}
```

##### 4、刷新token（grant_type=refresh_token）
```
使用浏览器访问下面这个路径，会引导用户到登入页面，用户输入UserDetailsService里面定义的账号密码，登入成功后获取授权码
http://localhost:1111/oauth/token?grant_type=refresh_token&refresh_token=22f29a7e-b0db-4c80-8385-971792c6d420&client_id=genesis&client_secret=genesisx

```

#### 5、关于WebSecurityConfigurerAdapter和ResourceServerConfigurerAdapter区别
```
https://www.jianshu.com/p/1a0a5c92185e

WebSecurityConfigurerAdapter的配置的拦截要优先于ResourceServerConfigurerAdapter，优先级高的http配置是可以覆盖优先级低的配置的。
某些情况下如果需要ResourceServerConfigurerAdapter的拦截优先于WebSecurityConfigurerAdapter需要在配置文件中添加


```

#### UsernamePasswordAuthenticationFilter、TokenEndpoint、AuthorizationEndpoint

AuthorizationCodeTokenGranter 默认的token生成器
OAuth2AccessToken token = this.getTokenGranter().grant(tokenRequest.getGrantType(), tokenRequest);
OAuth2AccessToken token = AuthorizationCodeTokenGranter.grant(tokenRequest.getGrantType(), tokenRequest);

DefaultOAuth2RequestFactory 
TokenRequest tokenRequest = this.getOAuth2RequestFactory().createTokenRequest(parameters, authenticatedClient);

InMemoryClientDetailsService
ClientDetails authenticatedClient = this.getClientDetailsService().loadClientByClientId(clientId);

AuthenticationProvider
AuthenticationProvider接口是用于认证的，可以通过实现这个接口来定制我们自己的认证逻辑，它的实现类有很多，默认的是JaasAuthenticationProvider

AccessDecisionManager
AccessDecisionManager是用于访问控制的，它决定用户是否可以访问某个资源，实现这个接口可以定制我们自己的授权逻辑。

AccessDecisionVoter
AccessDecisionVoter是投票器，在授权的时通过投票的方式来决定用户是否可以访问，这里涉及到投票规则。