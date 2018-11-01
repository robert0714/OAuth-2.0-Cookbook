# Chapter 6 OpenID Connect for Authentication

## Authenticating Google's users through Google OpenID Connect
p.259 ~273

Chapter06/google-connect
 
Open th below url in browser

http://localhost:8080

and http://localhost:8080/google/callback


replace the following attributes within the application.properties file (your credentials will be different from what follows):

```
google.config.clientId=290927198833-2nil12dpbuq2tfmi4u2rmj5ci4fgg3q6.apps.googleusercontent.com
google.config.clientSecret=mP842ZoaGp9f8vO99d6CVzUF
```

Another thing that is important to note is where all the URIs defined within GoogleConfiguration come from. These URIs and other details about the OpenID
Connect provider's configuration can be found at *https://accounts.google.com/.well-known/openid-configuration* .
 
### NOTICE
The configuration would work After the one day I register an application it .


## Obtaining user information from Identity Provider

p.273 ~p.278

Chapter06/google-userinfo 
 
Open th below url in browser

http://localhost:8080

and http://localhost:8080/google/callback

### NOTICE
The configuration would work After the one day I register an application it .

## Using Facebook to authenticate users
<failure>
p.279 ~ p.289

Chapter06/facebook-login-oauth2

I encountered  the same situation as the former case . Maybe I need to wait one day.

## Using Google OpenID Connect with Spring Security 5
 

p.290 ~p.295

Chapter06/google-openid-spring5

Go to the Google Developers Console located at 
https://console.developers.google.com


Then enter the URL settings for Authorized redirect URIs, as presented in the following :

http://localhost:8080/oauth2/authorize/code/google

[[google-redirect-uri]]
=== Setting the redirect URI

The redirect URI is the path in the application that the end-user's user-agent is redirected back to after they have authenticated with Google
and have granted access to the OAuth Client _(created in the previous step)_ on the Consent page.

In the "Set a redirect URI" sub-section, ensure that the *Authorized redirect URIs* field is set to `http://localhost:8080/oauth2/authorize/code/google`.
 


### reference
https://qiita.com/kazuki43zoo/items/53804e18337933a77ad0

https://github.com/spring-projects/spring-security/tree/master/samples/boot/oauth2login


## Using Microsoft and Google OpenID providers together with Spring Security 5

p.296 ~ 302

Chapter06/microsoft-login

You can see how to register an application on Microsoft Azure as well as how to create credentials by reading the official documentation at 
*https://docs.microsoft.​com/​en-us/azure/​active-directory/​develop/active-​directory-​integrating-​applications* .

https://docs.microsoft.com/en-us/azure/active-directory/develop/quickstart-v1-add-azure-ad-app

https://docs.microsoft.com/en-us/azure/active-directory/develop/vs-active-directory-add-connected-service


Microsoft service not free

