package com.x.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * @Author: liuxing
 * @Date: 2018/11/12 10:55
 * @Description:
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    public static final String resourceId = "oauth2-resource";

    @Autowired @Qualifier("authenticationManagerBean")//认证方式
    private AuthenticationManager authenticationManager ;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private Oauth2Property oauth2Property;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                //允许 GET、POST 请求获取 token，即访问端点：oauth/token
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
        ;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("isAuthenticated()")
                .tokenKeyAccess("permitAll()")
                .realm(resourceId)
        ;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(oauth2Property.getClientId())
                .secret(oauth2Property.getSecret())
                .authorizedGrantTypes("authorization_code","client_credentials", "password", "refresh_token")
                .scopes("all")
                .autoApprove(true)
                .autoApprove("all")
                .resourceIds(resourceId)
                .accessTokenValiditySeconds(oauth2Property.getAccessTokenValiditySeconds())
                .refreshTokenValiditySeconds(oauth2Property.getRefreshTokenValiditySeconds())
        ;
    }

}