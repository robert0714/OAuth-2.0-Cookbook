# Chapter 3  Using OAuth 2.0 Protected APIs

## Creating an OAuth 2.0 client using the Authorization Code grant type
p.130 ~141

Chapter02/​auth-​code-​server

Chapter03/​client-authorization-code 

To run this project, you must start the application located at
`chapter-2/auth-code-server`

Open th below url in browser

http://localhost:8080/oauth/authorize?client_id=clientapp&redirect_uri=http://localhost:9000/callback&response_type=code&scope=read_profile

not use 
security.user.name=adolfo
security.user.password=123


clientdb.client_user.username=aeloy
clientdb.client_user.password=abc


and then redirect the below url

http://localhost:9000/callback?code=v2zSzU

```
$> curl -X POST --user clientapp:123456 http://localhost:8080/oauth/token -H  \
"content-type: application/x-www-form-urlencoded" -d  \
"code=v2zSzU&grant_type=authorization_code&redirect_uri=http%3A%2F%2Flocalhost%3A9000%2Fcallback&scope=read_profile"

{"access_token":"77de55ce-03dc-4eaf-983b-64028bed1211","token_type":"bearer","expires_in":43199,"scope":"read_profile"}


$> curl -X GET http://localhost:8080/api/profile -H "authorization: Bearer 77de55ce-03dc-4eaf-983b-64028bed1211"

{"name":"adolfo","email":"adolfo@mailinator.com"}

```

OAuth 2.0
specification at https:/​/tools.ietf.org/html/rfc6749#section-4.1.1


## Creating an OAuth 2.0 client using the Implicit grant type
p.142 ~ p.150

Chapter02/​implicit-server

Chapter03/client-​implicit

Open th below url in browser

http://localhost:8080/oauth/authorize?client_id=clientapp&redirect_uri=http://localhost:9000/callback&response_type=token&scope=read_profile&state=xyz

and then redirect the below url

http://localhost:9000/callback#access_token=6c3c75df-cb86-48d7-ace2-f8ef629bb59a&token_type=bearer&state=xyz&expires_in=119

```
$> curl -X GET http://localhost:8080/api/profile -H "authorization: Bearer 6c3c75df-cb86-48d7-ace2-f8ef629bb59a"

{"name":"adolfo","email":"adolfo@mailinator.com"}

```


## Creating an OAuth 2.0 client using the Resource Owner Password Credentials grant type
p.150 - p.155
 
Chapter02/password-server

Chapter03/client-​password

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

Open th below url in browser
http://localhost:9000

## Creating an OAuth 2.0 client using the Client Credentials grant type
p.155 ~ 161

Chapter02/client-credentials-server

Chapter03/client-client-credentials

```
$> curl -X POST "http://localhost:8080/oauth/token" --user clientadmin:123 -d  "grant_type=client_credentials&scope=admin"

{"access_token":"da9da0e2-5c4d-421f-9efc-3713e8acd077","token_type":"bearer","expires_in":43199,"scope":"admin"}

$> curl "http://localhost:8080/api/users" -H "Authorization: Bearer da9da0e2-5c4d-421f-9efc-3713e8acd077"

[{"name":"adolfo","email":"adolfo@mailinator.com"},{"name":"demigreite","email":"demigreite@mailinator.com"},{"name":"jujuba","email":"jujuba@mailinator.com"}]

$> curl "http://localhost:8080/user" --user adolfo:123

{"name":"adolfo","email":"adolfo@mailinator.com"}
```

Open th below url in browser
http://localhost:9000/
 

security.user.name=admin
security.user.password=123

## Managing refresh tokens on the client side
p.161 ~ p. 163
Chapter02/refresh-server

Chapter03/client-refresh-token


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
## Accessing an OAuth 2.0 protected API with RestTemplate
p.164 ~ p.170

Chapter02/auth-code-server

Chapter03/client-rest-template

Open th below url in browser
 http://localhost:9000/dashboard

clientdb.client_user.username=aeloy
clientdb.client_user.password=abc