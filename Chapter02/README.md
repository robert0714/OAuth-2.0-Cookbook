# Chapter 2  Implementing Your Own OAuth 2.0 Provider

## Protecting resources using the Authorization Code grant type
p.63 ~ p.70

Chapter02/​auth-​code-​server

 

Open th below url in browser

http://localhost:8080/oauth/authorize?client_id=clientapp&redirect_uri=http://localhost:9000/callback&response_type=code&scope=read_profile

security.user.name=adolfo
security.user.password=123

and then redirect the below url

http://localhost:9000/callback?code=l1kgc3

```
$> curl -X POST --user clientapp:123456 http://localhost:8080/oauth/token -H  \
"content-type: application/x-www-form-urlencoded" -d  \
"code=l1kgc3&grant_type=authorization_code&redirect_uri=http%3A%2F%2Flocalhost%3A9000%2Fcallback&scope=read_profile"

{"access_token":"abcd35cb-3b7d-438f-b1e4-2fd699d02f7b","token_type":"bearer","expires_in":43199,"scope":"read_profile"}


$> curl -X GET http://localhost:8080/api/profile -H "authorization: Bearer abcd35cb-3b7d-438f-b1e4-2fd699d02f7b"

{"name":"adolfo","email":"adolfo@mailinator.com"}

```

OAuth 2.0
specification at https:/​/tools.ietf.org/html/rfc6749#section-4.1.1


## Supporting the Implicit grant type
p.71 ~ p.75

Chapter02/​implicit-server

Open th below url in browser

http://localhost:8080/oauth/authorize?client_id=clientapp&redirect_uri=http://localhost:9000/callback&response_type=token&scope=read_profile&state=xyz

and then redirect the below url

http://localhost:9000/callback#access_token=6c3c75df-cb86-48d7-ace2-f8ef629bb59a&token_type=bearer&state=xyz&expires_in=119

```
$> curl -X GET http://localhost:8080/api/profile -H "authorization: Bearer 6c3c75df-cb86-48d7-ace2-f8ef629bb59a"

{"name":"adolfo","email":"adolfo@mailinator.com"}

```


## Using the Resource Owner Password Credentials grant type as an approach for OAuth 2.0 migration
p.76 ~ 80
 
Chapter02/password-server

Open th below url in browser

http://localhost:8080/oauth/authorize?client_id=clientapp&redirect_uri=http://localhost:9000/callback&response_type=token&scope=read_profile&state=xyz

and then redirect the below url

OAuth Error
error="invalid_grant", error_description="A redirect_uri can only be used by implicit or authorization_code grant types."


You could find th method is not get method ,so tou could not use browser


```
$> curl -X POST --user clientapp:123456 http://localhost:8080/oauth/token -H "accept: application/json" -H "content-type: application/x-www-form-urlencoded" -d   "grant_type=password&username=adolfo&password=123&scope=read_profile"

{"access_token":"7a7ea3f8-a207-4a2c-b168-d3814fc5be36","token_type":"bearer","expires_in":43199,"scope":"read_profile"}

$> curl -X GET http://localhost:8080/api/profile -H "authorization: Bearer 7a7ea3f8-a207-4a2c-b168-d3814fc5be36"

{"name":"adolfo","email":"adolfo@mailinator.com"}[
```
## Configuring the Client Credentials grant type
p.81 ~ p.85

Chapter02/client-credentials-server

```
$> curl -X POST "http://localhost:8080/oauth/token" --user clientadmin:123 -d  "grant_type=client_credentials&scope=admin"

{"access_token":"da9da0e2-5c4d-421f-9efc-3713e8acd077","token_type":"bearer","expires_in":43199,"scope":"admin"}

$> curl "http://localhost:8080/api/users" -H "Authorization: Bearer da9da0e2-5c4d-421f-9efc-3713e8acd077"

[{"name":"adolfo","email":"adolfo@mailinator.com"},{"name":"demigreite","email":"demigreite@mailinator.com"},{"name":"jujuba","email":"jujuba@mailinator.com"}]

$> curl "http://localhost:8080/user" --user adolfo:123

{"name":"adolfo","email":"adolfo@mailinator.com"}
```
## Adding support for refresh tokens
p.86 ~ p.91
 
Chapter02/refresh-server

```
$ >  curl -X POST --user clientapp:123456 http://localhost:8080/oauth/token   \
     -H "accept: application/json" -H "content-type: application/x-www-form-urlencoded"   \
     -d   "grant_type=password&username=adolfo&password=123&scope=read_profile"


{"access_token":"c22308a3-16ec-4995-9de3-06a6caa5c033","token_type":"bearer","refresh_token":"c4d68ff3-bf8d-4710-8d22-531e39de22e4","expires_in":119,"scope":"read_profile"}

$ >  curl -X GET http://localhost:8080/api/profile -H "authorization: Bearer c22308a3-16ec-4995-9de3-06a6caa5c033"

{"name":"adolfo","email":"adolfo@mailinator.com"}

//wait for 2 minutes to try a new request

{
"error":"invalid_token",
"error_description":"Access token expired: c22308a3-16ec-4995-9de3-06a6caa5c033"
}

$ >   curl -X POST --user clientapp:123456 http://localhost:8080/oauth/token -H  \
"content-type: application/x-www-form-urlencoded" -d   \
"grant_type=refresh_token&refresh_token=c4d68ff3-bf8d-4710-8d22-531e39de22e4&scope=read_profile"

{"access_token":"ece51d58-fdba-4090-90bc-db327050447d","token_type":"bearer","refresh_token":"c4d68ff3-bf8d-4710-8d22-531e39de22e4","expires_in":119,"scope":"read_profile"}

```
## Using a relational database to store tokens and client details
p.92 ~ 98

Chapter02/rdbm-server

About oauth2 database schema , you can refer

https://projects.spring.io/spring-security-oauth/docs/oauth2.html

https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/test/resources/schema.sql

https://piotrminkowski.wordpress.com/tag/mysql/


Start the application by running the mvn spring-boot:run command and go to the following URL:

http://localhost:8080/oauth/authorize?client_id=clientapp&redirect_uri=http://localhost:9000/callback&response_type=code&scope=read_profile

and then it would redirect the following URL:

http://localhost:9000/callback?code=Js7SCt

When you as the Resource Owner had approved the clientapp to client access your profile, a row will be created in the oauth_approvals table. Run the following query in your MySQL console to check for a new row.

```
select * from oauth_approvals;
```

Now if you request for a new access token by running the following CURL command, you will receive a new access token generating a new row into the oauth_access_token table:

```
$ >   curl -X POST --user clientapp:123456 http://localhost:8080/oauth/token -H  \
"content-type: application/x-www-form-urlencoded" -d   \
"code=Js7SCtS&grant_type=authorization_code&redirect_uri=http://localhost:9000/callback&scope=read_profile"

{"access_token":"d85a5cfb-f7ce-4fb9-bd13-aa8206635a9b","token_type":"bearer","expires_in":2999,"scope":"read_profile"}

```
You notice JdbcTokenStore, oauth_client_details.client_secret ( it need to be encoded in BCrypt )

online tools 
https://www.browserling.com/tools/bcrypt

## Using Redis as a token store
p.99 ~ 103

Chapter02/​redis-​server

To check how *RedisTokenStore* persists the access tokens and related data, start the redis-server application and try to request for an access token using one of the authorized grant types declared, which are Authorization Code and Password Credentials. For practical reasons, I will make the access token request through the Password Credentials, as you can see in the following CURL command:

```
 $ >  curl -X POST --user clientapp:123456 http://localhost:8080/oauth/token -H    \
"accept: application/json" -H "content-type: application/x-www-form-urlencoded" -d   \
"grant_type=password&username=adolfo&password=123&scope=read_profile"

{"access_token":"589964cd-c9c1-4d96-ae2f-5b8974c0da50","token_type":"bearer","expires_in":43199,"scope":"read_profile"}

```
## Implementing client registration
p.104 ~ p.115

Chapter02/​oauth2provider


To see the client registration process in action, start the application and go to *http://localhost:8080/client/dashboard* so you can see the following page (the application will try to authenticate you, so enter the user credentials that you have set up within the application.properties file):

security.user.name=adolfo
security.user.password=123

##  Breaking the OAuth 2.0 Provider in the middle
p.116 ~ p.120

Chapter02/​separated-oauthprovider

Same as authoriaztion_code ,password grandtypes

### authoriaztion_code
Open th below url in browser

http://localhost:8080/oauth/authorize?client_id=clientapp&redirect_uri=http://localhost:9000/callback&response_type=code&scope=read_profile

security.user.name=adolfo
security.user.password=123

and then redirect the below url

http://localhost:9000/callback?code=l1kgc3

```
$> curl -X POST --user clientapp:123456 http://localhost:8080/oauth/token -H  \
"content-type: application/x-www-form-urlencoded" -d  \
"code=l1kgc3&grant_type=authorization_code&redirect_uri=http%3A%2F%2Flocalhost%3A9000%2Fcallback&scope=read_profile"

{"access_token":"abcd35cb-3b7d-438f-b1e4-2fd699d02f7b","token_type":"bearer","expires_in":43199,"scope":"read_profile"}

//by sending a request using port 8081
$> curl -X GET http://localhost:8081/api/profile -H "authorization: Bearer abcd35cb-3b7d-438f-b1e4-2fd699d02f7b"

{"name":"adolfo","email":"adolfo@mailinator.com"}

```
### password
Open th below url in browser

http://localhost:8080/oauth/authorize?client_id=clientapp&redirect_uri=http://localhost:9000/callback&response_type=token&scope=read_profile&state=xyz

and then redirect the below url

OAuth Error
error="invalid_grant", error_description="A redirect_uri can only be used by implicit or authorization_code grant types."


You could find th method is not get method ,so tou could not use browser


```
$> curl -X POST --user clientapp:123456 http://localhost:8080/oauth/token -H "accept: application/json" -H "content-type: application/x-www-form-urlencoded" -d   "grant_type=password&username=adolfo&password=123&scope=read_profile"

{"access_token":"7a7ea3f8-a207-4a2c-b168-d3814fc5be36","token_type":"bearer","expires_in":43199,"scope":"read_profile"}

//by sending a request using port 8081
$> curl -X GET http://localhost:8081/api/profile -H "authorization: Bearer 7a7ea3f8-a207-4a2c-b168-d3814fc5be36"

{"name":"adolfo","email":"adolfo@mailinator.com"}[
```


## Using Gatling to load test the token validation process using shared databases
p.121 ~ p.129

Chapter02/load-testing

```
load-testing]$ mvn clean scala:compile compile gatling:execute
```

