package com.vn.configurations;

import com.vn.config.DefaultUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private String GRANT_TYPE_PASSWORD = "password";
    private String AUTHORIZATION_CODE = "authorization_code";
    private String REFRESH_TOKEN = "refresh_token";
    private String SCOPE_READ = "read";
    private String SCOPE_WRITE = "write";
    private int VALID_FOREVER = 7200;

    @Value("${basic.auth.client.id}")
    private String clientId;
    @Value("${basic.auth.client.pass}")
    private String clientPass;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("key");
        converter.setVerifierKey("key");
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .jdbc(dataSource);
////                     .inMemory()
//                .withClient(clientId)
//                .secret(passwordEncoder.encode(clientPass))
//                .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN)
//                .scopes(SCOPE_READ, SCOPE_WRITE)
//                .accessTokenValiditySeconds(VALID_FOREVER)
//                .refreshTokenValiditySeconds(VALID_FOREVER)
//                .and().build();

    }

    @Autowired
    private DefaultUserService userService;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain)
                // userService for refresh_token
                .userDetailsService(userService);


        endpoints.authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")
                .passwordEncoder(passwordEncoder)
                .allowFormAuthenticationForClients();
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        //public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        return ((accessToken, authentication) -> {
            final Map<String, Object> additionalInfo = new HashMap<>();
            additionalInfo.put("organization", authentication.getName());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        });
    }
}