package com.oauth2.example.authorizationserver.config;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
    
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
			throws IOException, ServletException 
	{
		System.out.println("---------REQUEST-----------");
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) 
		{
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			System.out.println(key+" : "+value);
		}
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getOutputStream().println("error Messsage: "+authEx.getMessage());
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		setRealmName("Developer");
		super.afterPropertiesSet();
	}
}
