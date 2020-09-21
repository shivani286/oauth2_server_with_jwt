# OAUTH2_SERVER(oauth2_server_with_jwt)

1) Registration First Api call:

Api: http://localhost:8081/registration

Request :

{
 "emailId":"PQR@gmail.com",
 "firstName":"PQR",
 "lastName":"xyz",
 "primaryContactNumber":"9028223620",
 "companyName" :"ORG_TEST_LOGIN_ADMIN_2"
}

2) Confirm Password:

Api : http://localhost:8081/registration/confirm

Request:

{
	 "password": "abc@123",
	 "confirmPassword":"abc@123",
	 "confirmationCode":"amFmdHZxOGY5cWJ2ZWQydDd1MGs=",
	 "emailId":"PQR@gmail.com"
}

3) AuthToken api:

Api : http://localhost:8181/oauth/token

Authentication --->  Type =  Basic Auth
		     userName = ClientId
		     password = ClientSecret

Form Data ----------------------> grant_type = password
Registor email enter in --------> username  = PQR@gmail.com
		                  password = abc@123

Response : 

{
    "access_token": "---------------",
    "token_type": "bearer",
    "refresh_token": "---------------",
    "expires_in": 3599,
    "scope": "READ WRITE"
}

4) User Detail Get Call Api:

Api: http://localhost:8081/user/detail/by/1

Header add ------> Key = Authorization
		   Value = bearer 2b94a2ba-43a9-4563-85cd-533e4ce48a49

Response:

{
    "userId": 1,
    "emailId": "PQR@gmail.com",
    "organization": {
        "organizationId": 1,
        "name": "ORG_TEST_LOGIN_ADMIN_2",
        "emailId": "PQR@gmail.com",
        "primaryContactNumber": "9028223619"
    },
    "firstName": "PQR",
    "lastName": "xyz",
    "companyName": null,
    "contactNumber": "9028223619",
    "createdOn": "2020-06-06T16:37:21.000+0000",
    "updatedOn": "2020-06-06T16:37:21.000+0000",
    "enabled": true,
    "accountNonExpired": true,
    "credentialsNonExpired": true,
    "accountNonLocked": true,
    "roles": [
        {
            "roleId": 1,
            "name": "ROLE_admin",
            "permissions": [
                {
                    "permissionId": 1,
                    "name": "create_profile"
                }
            ]
        }
    ]
}


