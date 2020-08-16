package com.halosky.study.config;

import com.halosky.study.util.EncodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import javax.xml.ws.soap.Addressing;
import java.util.concurrent.TimeUnit;

/**
 * 授权配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private EncodeUtil encodeUtil;

    private final AuthenticationManager authenticationManager;

    public AuthorizationConfiguration(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // 需要BASIC Auth -> clientId 和 secret作为获取AccessToken的一部分
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("halosky")
                .secret(encodeUtil.encode("123456"))
                .authorizedGrantTypes("password")  //授权类型密码 - “password”
                .scopes("read","write","trust")
                .accessTokenValiditySeconds((int)TimeUnit.MINUTES.toSeconds(60))  //access token 有效时间
                ;
    }


    @Bean
    public TokenStore tokenStore(){
        return new InMemoryTokenStore();            // 基于内存的单机token存储实现
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
    }
}
