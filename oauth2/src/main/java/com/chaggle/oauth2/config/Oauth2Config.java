package com.chaggle.oauth2.config;


import com.chaggle.oauth2.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class Oauth2Config extends AuthorizationServerConfigurerAdapter {

    @Resource
    private DataSource dataSource; //引入持久层

    @Resource
    private UserService userService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    @Primary
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setTokenStore(tokenStore());
        services.setAccessTokenValiditySeconds(30 * 24 * 3600); //过期登录 30 日
        return services;
    }

    @Bean
    public ClientDetailsService clientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.userDetailsService(userService)
                .tokenServices(defaultTokenServices())
                .authenticationManager(authenticationManager);
    }
}
