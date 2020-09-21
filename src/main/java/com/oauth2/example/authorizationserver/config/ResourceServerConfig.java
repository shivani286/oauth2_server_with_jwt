package com.oauth2.example.authorizationserver.config;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.AuthenticationEntryPoint;


@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	private static final Logger logger = LoggerFactory.getLogger(ResourceServerConfig.class);
	
	@Autowired
	private HttpSessionSecurityContextRepository httpSessionSecurityContextRepository;
	
	@Autowired
	private AuthenticationEntryPoint authEntryPoint;
	
	@Bean
	public AuthenticationEntryPoint unauthorizedEntryPoint() {
		return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		logger.info("****************resource configure  login======>> ***************************");
		http.cors().and().csrf().disable();
		http.securityContext().securityContextRepository(httpSessionSecurityContextRepository);
        http.authorizeRequests()
            .antMatchers("/",
            		"/login",
            		"/login/refresh",
    				"/oauth/token",
    				"/oauth/check_token",
    				"/oauth/authorize",
    				"/oauth/confirm_access",
    				"/registration",
    				"/registration/**").permitAll()
            .anyRequest().authenticated()
            .and().httpBasic().authenticationEntryPoint(authEntryPoint)
            .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
}
