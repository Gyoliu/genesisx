package com.genesis.x.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.header.writers.CacheControlHeadersWriter;
import org.springframework.security.web.header.writers.HstsHeaderWriter;
import org.springframework.security.web.header.writers.XContentTypeOptionsHeaderWriter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.web.cors.CorsUtils;

import java.util.ArrayList;

/**
 * @Author: liuxing
 * @Date: 2018/11/12 11:07
 * @Description:
 */
@Slf4j
@Order(6)
@Configuration
@EnableWebSecurity
//@EnableOAuth2Sso
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${login.page:/login}")
    private String loginPage;
    @Value("${home.page:/}")
    private String homePage;

    @Value("${cors.host:http://localhost:8080}")
    private String host;

    @Value("${oauth.cookie.name}")
    private String cookieName;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private Oauth2Property oauth2Property;

    @Autowired
    private AuthorizationServerConfiguration authorizationServerConfiguration;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        //return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ArrayList<HeaderWriter> list = new ArrayList<>();
        list.add(new XContentTypeOptionsHeaderWriter());
        list.add(new XXssProtectionHeaderWriter());
        list.add(new CacheControlHeadersWriter());
        list.add(new HstsHeaderWriter());
        list.add(new XFrameOptionsHeaderWriter());
        http.addFilter(new CustomHeaderWriterFilter(list));

        String loginUrl = host + loginPage;
        String homeUrl = host + homePage;

        http.addFilter(new LoginAuthenticationFilter(super.authenticationManager()
                , clientDetailsService, authorizationServerConfiguration.getTokenGranter()
                , oauth2Property.getClientId(), oauth2Property.getSecret(), homeUrl));

        http.httpBasic()
                .and().csrf().disable().anonymous().and()
                .formLogin()
                .successHandler(new AuthenticationSuccessHandler(homeUrl)).failureHandler(new AuthenticationFailureHandler())
                .loginPage(loginUrl).permitAll()
                .and().logout().addLogoutHandler(new CustomLogoutHandler()).deleteCookies(cookieName, "JSESSIONID")
                .invalidateHttpSession(true).clearAuthentication(true)
                .logoutSuccessHandler(new CustomLogoutSuccessHandler(loginUrl)).permitAll()
                .and().authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .and().authorizeRequests().anyRequest().authenticated()
                //.and().exceptionHandling().accessDeniedHandler(new CustomAccessDeineHandler()).authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                //用户只能存在一个
                .and().sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry()).expiredUrl(loginUrl)
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider)
                .userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
        ;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.debug(true);
    }

}