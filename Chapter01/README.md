# Chapter 1 OAutj 2.0 Foundations
p.11

Chapter01/simplemvc

curl "http://localhost:8080/message"

The OAuth 2.0 specificatio
https://tools.ietf.org/html/rfc6749


p.13 ~ p.23
Chapter01/client-implicit

https://developers.facebook.com/apps/

you must click on the Set Up button from the Facebook Login box

After choosing the Web platform, enter the Site URL for your application. I am
using a fictitious URL named http://clientimplicit.test  

http://localhost:8080/callback


p.26 ~ p.36

Chapter01/social-authcode

https://developers.facebook.com/apps/

you must click on the Set Up button from the Facebook Login box

After choosing the Web platform, enter the Site URL for your application. I am
using a fictitious URL named http://socialauthcode.test/

After creating the application on Facebook, click on Facebook Login on the left
panel to configure a valid redirect URI, which should be
http://localhost:8080/connect/facebook .

Click on Dashboard on the left panel so you can retrieve the App ID and App Secret 
which map to client_id and client_secret , as you may already know,
and grab the credentials to use later when implementing the client application.



Chapter01/social-authcode/src/resources/applications.properties

```
facebook.app-id=1948923582021549
facebook.app-secret=1b4b0f882b185094a903e76a661c7c7c
facebook.api-version=2.9
```

open url  http://localhost:8080/ in browser .


p.37 ~ p.45

Chapter01/social-linkd

https://www.linkedin.com/developer/apps/

Company Name: OAuth2-cookbook
Application: social-linkd
Application Description: OAuth 2.0 sample application
 
Website URL:  http://localhost:8080/connect/linkedin
 

http://localhost:8080/connect/linkedin


Client ID:	78ncdi9mwmmhfe 
Client Secret:	GRrXApUrsgiIytBS

Chapter01/social-linkd/src/resources/applications.properties

```
facebook.app-id=1948923582021549
facebook.app-secret=1b4b0f882b185094a903e76a661c7c7c
facebook.api-version=2.9
```

open url  http://localhost:8080/ in browser .


p.46 ~ p.61

Chapter01/social-google1

https://console.developers.google.com

Click on the ENABLE API link or on Library at the left side of the dashboard

Then select Google+ API

用戶端 ID	
290927198833-2nil12dpbuq2tfmi4u2rmj5ci4fgg3q6.apps.googleusercontent.com
用戶端密碼	
mP842ZoaGp9f8vO99d6CVzUF
建立日期	
2018年10月24日 上午11:31:04

Chapter01/social-google1/src/resources/applications.properties

```
spring.social.google.appId=290927198833-2nil12dpbuq2tfmi4u2rmj5ci4fgg3q6.apps.googleusercontent.com
spring.social.google.appSecret=mP842ZoaGp9f8vO99d6CVzUF
```

open url  http://localhost:8080/ in browser .

user :   adolf
password :  123

user :   jujuba
password :  123