package com.packt.example.openidspring5.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/callback").permitAll()
            .anyRequest().authenticated().and()
            .oauth2Login().and();
    }

//    @Autowired
//    private GoogleRegistrationProperties properties;

//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        ClientRegistration registration = new ClientRegistration.Builder(properties.getClientId())
//            .authorizationUri(properties.getAuthorizationUri())
//            .clientSecret(properties.getClientSecret())
//            .tokenUri(properties.getTokenUri())
//            .redirectUri(properties.getRedirectUri())
//            .scope(properties.getScopes().split(","))
//            .clientName(properties.getClientName())
//            .clientAlias(properties.getClientAlias())
//            .jwkSetUri(properties.getJwkSetUri())
//            .authorizationGrantType(properties.getAuthorizedGrantType())
//            .userInfoUri(properties.getUserInfoUri())
//            .build();
//
//        return new InMemoryClientRegistrationRepository(Arrays.asList(registration));
//    }
    
//  @Bean
//  public ClientRegistrationRepository clientRegistrationRepository(@Autowired GoogleRegistrationProperties properties) {
//      ClientRegistration registration =  ClientRegistration.withRegistrationId("google")
//    	  .clientId(properties.getClientId())
//          .authorizationUri(properties.getAuthorizationUri())
//          .clientSecret(properties.getClientSecret())
//          .tokenUri(properties.getTokenUri())
//          .redirectUriTemplate(properties.getRedirectUri())
//          .scope(properties.getScopes().split(","))
//          .clientName(properties.getClientName())
////          .clientAlias(properties.getClientAlias())
//          .jwkSetUri(properties.getJwkSetUri())
//          .authorizationGrantType(properties.getAuthorizedGrantType())
//          .userInfoUri(properties.getUserInfoUri())
//          .build();
//
//      return new InMemoryClientRegistrationRepository(Arrays.asList(registration));
//  }
    
}
