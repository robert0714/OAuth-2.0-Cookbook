# Chapter 4  OAuth 2.0 Profiles

## Revoking issued tokens
p.172 ~176
<<失敗>>

Chapter04/​revoke-​server
 
https://tools.ietf.org/html/rfc7009 

 

Start the application by running the mvn spring-boot:run command and go to the following URL:

http://localhost:8080/oauth/authorize?client_id=clientapp&redirect_uri=http://localhost:9000/callback&response_type=code&scope=read_profile

and then it would redirect the following URL:

http://localhost:9000/callback?code=Js7SCt

When you as the Resource Owner had approved the clientapp to client access your profile, a row will be created in the oauth_approvals table. Run the following query in your MySQL console to check for a new row.

select * from oauth_approvals;
Now if you request for a new access token by running the following CURL command, you will receive a new access token generating a new row into the oauth_access_token table:

```
$ >   curl -X POST --user clientapp:123456 http://localhost:8080/oauth/token -H  \
"content-type: application/x-www-form-urlencoded" -d   \
"code=Js7SCtS&grant_type=authorization_code&redirect_uri=http://localhost:9000/callback&scope=read_profile"

{"access_token":"446e4445-3d0d-4e5d-bf36-41d3c2d2ac17","token_type":"bearer","expires_in":2999,"scope":"read_profile"}

$ >   curl -v -X POST --user clientapp:123456 http://localhost:8080/oauth/revoke  \
-H "content-type: application/x-www-form-urlencoded" -d "token=446e4445-3d0d-4e5d-bf36-41d3c2d2ac17&token_type_hint=access_token"

* About to connect() to localhost port 8080 (#0)
*   Trying 127.0.0.1...
* Connected to localhost (127.0.0.1) port 8080 (#0)
* Server auth using Basic with user 'clientapp'
> POST /oauth/revoke HTTP/1.1
> Authorization: Basic Y2xpZW50YXBwOjEyMzQ1Ng==
> User-Agent: curl/7.29.0
> Host: localhost:8080
> Accept: */*
> content-type: application/x-www-form-urlencoded
> Content-Length: 71
> 
* upload completely sent off: 71 out of 71 bytes
< HTTP/1.1 401 
* Authentication problem. Ignoring this.
< WWW-Authenticate: Basic realm="Realm"
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 1; mode=block
< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
< Pragma: no-cache
< Expires: 0
< X-Frame-Options: DENY
< Content-Type: application/json;charset=UTF-8
< Transfer-Encoding: chunked
< Date: Mon, 29 Oct 2018 04:15:04 GMT
< 
* Connection #0 to host localhost left intact
{"timestamp":1540786504421,"status":401,"error":"Unauthorized","message":"Bad credentials","path":"/oauth/revoke"}[robert0714@1204003PC01 revoke-server]$ 

```

OAuth 2.0
specification at https:/​/tools.ietf.org/html/rfc6749#section-4.1.1


## Remote validation using token introspection
p.177 ~ p.183

Chapter04/​remote-validation
Chapter04/​remote-validation/remote-authserver
Chapter04/​remote-validation/remote-resource

https://tools.ietf.org/html/rfc7662

 ```
$> curl -X POST --user clientapp:123456 http://localhost:8080/oauth/token -H   \
"accept: application/json" -H "content-type: application/x-www-form-urlencoded" -d  \
"grant_type=password&username=adolfo&password=123&scope=read_profile"

{"timestamp":1540794289704,"status":401,"error":"Unauthorized","message":"Bad credentials","path":"/oauth/token"}

$> curl -X POST --user resource_server:abc123 http://localhost:8080/oauth/token -H   \
 "accept: application/json" -H "content-type: application/x-www-form-urlencoded" -d  \
 "grant_type=password&username=adolfo&password=123&scope=read_profile"

{"error":"invalid_client","error_description":"Unauthorized grant type: password"}

$> curl -X POST --user clientxpto:123 http://localhost:8080/oauth/token -H   \
 "accept: application/json" -H "content-type: application/x-www-form-urlencoded" -d  \
 "grant_type=password&username=adolfo&password=123&scope=read_profile"

{"access_token":"75bd158c-626f-4bf7-b005-ec33f62ebe8b","token_type":"bearer","expires_in":43199,"scope":"read_profile"}

$> curl -H "Authorization: Bearer 75bd158c-626f-4bf7-b005-ec33f62ebe8b"  \
"http://localhost:8081/api/profile"

{"name":"adolfo","email":"adolfo@mailinator.com"}

```

https://tools.ietf.org/html/rfc7662#section-2.2



## Improving performance using cache for remote validation
p.184 ~ 187
 

Chapter04/​remote-validation/remote-authserver
Chapter04/cache-introspection
 

```
$> curl -X POST --user clientapp:123456 http://localhost:8080/oauth/token -H   \
"accept: application/json" -H "content-type: application/x-www-form-urlencoded" -d  \
"grant_type=password&username=adolfo&password=123&scope=read_profile"

{"timestamp":1540794289704,"status":401,"error":"Unauthorized","message":"Bad credentials","path":"/oauth/token"}

$> curl -X POST --user resource_server:abc123 http://localhost:8080/oauth/token -H   \
 "accept: application/json" -H "content-type: application/x-www-form-urlencoded" -d  \
 "grant_type=password&username=adolfo&password=123&scope=read_profile"

{"error":"invalid_client","error_description":"Unauthorized grant type: password"}

$> curl -X POST --user clientxpto:123 http://localhost:8080/oauth/token -H   \
 "accept: application/json" -H "content-type: application/x-www-form-urlencoded" -d  \
 "grant_type=password&username=adolfo&password=123&scope=read_profile"

{"access_token":"75bd158c-626f-4bf7-b005-ec33f62ebe8b","token_type":"bearer","expires_in":43199,"scope":"read_profile"}

$> curl -H "Authorization: Bearer 75bd158c-626f-4bf7-b005-ec33f62ebe8b"  \
"http://localhost:8081/api/profile"

{"name":"adolfo","email":"adolfo@mailinator.com"}

```

Open th below url in browser
http://localhost:9000

## Using Gatling to load test remote token validation
p.188

Chapter04/​remote-validation/remote-authserver
Chapter04/​remote-validation/remote-resource
Chapter04/cache-introspection 
Chapter04/load-testing-remote

```
load-testing-remote]$ mvn clean scala:compile compile gatling:execute
```

Then change the property server.port from the 8081 to 8082 from  *cache-introspection* project.

