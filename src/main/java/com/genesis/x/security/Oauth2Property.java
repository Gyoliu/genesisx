package com.genesis.x.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: liuxing
 * @Date: 2018/11/15 14:53
 * @Description:
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "genesis.oauth2")
@EnableConfigurationProperties
public class Oauth2Property {

    private String clientId;

    private String secret;

    private Integer accessTokenValiditySeconds;

    private Integer refreshTokenValiditySeconds;

}