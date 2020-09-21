package com.oauth2.example.authorizationserver.controller;

import java.util.Base64;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oauth2.example.authorizationserver.dto.LoginResponse;
import com.oauth2.example.authorizationserver.dto.LoginUserAuthenticationDetails;
import com.oauth2.example.authorizationserver.dto.UserCredentials;
import com.oauth2.example.authorizationserver.exceptionmapping.InvalidLoginCredentials;
import com.oauth2.example.authorizationserver.service.LoginUserAuthenticationService;

import kong.unirest.Unirest;
import kong.unirest.UnirestException;



@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private LoginUserAuthenticationService userAuthenticationService;

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Value("${security.oauth2.clientID}")
    private String clientId;
   
    @Value("${security.oauth2.clientSecret}")
    private String clientSecret;
    
	@Value("${security.oauth2.accessTokenUri}")
    private String authLink;
	
	@PostMapping
	public LoginUserAuthenticationDetails login(@RequestBody UserCredentials userCredentials) {
		
		logger.info("authetication Login-->" + userCredentials);
		LoginUserAuthenticationDetails details = new LoginUserAuthenticationDetails();
		
		details = userAuthenticationService.authenticate(userCredentials);
		
		if (Objects.isNull(details.getUser())) {
			throw new InvalidLoginCredentials("Wrong Login Credentials");
		}
		
		LoginResponse loginResponse = getauthToken(userCredentials);
		logger.info("Login Response==> "+loginResponse);
		
		//details.setLoginResponse(loginResponse);
		//details.setAccessToken(loginResponse.getAccess_token());
		details.setLoginResponse(loginResponse);
		logger.info("user Login successfull-->");
		//userDao.save(details);
		return details;
		
	}
	
	private LoginResponse getauthToken(UserCredentials userCredentials) {
		String authStr = clientId+":"+clientSecret;
	    String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
		LoginResponse response = new LoginResponse();
		try 
		{
			response = Unirest.post(authLink+"?grant_type=password&username="+userCredentials.getUsername()+"&password="+userCredentials.getPassword())
			  .header("Authorization", "Basic "+base64Creds)
			  .asObject(LoginResponse.class).getBody();
			
			System.out.println("response===================>  "+response);
		} 
		catch (UnirestException e) 
		{
			logger.error(e.getMessage());
		}
		return response;
	}

	@GetMapping("get/call/test")
	public String getStringPrint() {
		return "Hello Shivani";
	}
	
}
