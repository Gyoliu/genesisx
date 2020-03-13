package com.genesis.x.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * @Author liuxing
 * @Date 2020/3/11 17:38
 * @Version 1.0
 * @Description:
 */
@Data
@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "receive.mail")
@EnableConfigurationProperties
public class ReceiveMailProperty {

    private String protocol;

    private String host;

    private String port;

    private String username;

    private String password;

    private String sslEnable = "true";
    private String auth = "true";
    private String debug = "false";

}
