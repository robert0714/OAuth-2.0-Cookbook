# Chapter 8 Avoiding Common Vulnerabilities

## Validating the Resource Server audience
p.367  ~ p.371

Chapter08/validate-audience/authorization-server

Chapter08/validate-audience/resource-server-a

Chapter08/validate-audience/resource-server-b

test endpoint: /res-a ,res-b


```
]$  curl -X POST --user client-a:123   \
-d "grant_type=password&username=adolfo&password=123&scope=read_profile"  \
"http://localhost:8080/oauth/token"

{"access_token":"92fde7aa-4793-4edc-89ca-9a4a5a646392","token_type":"bearer","expires_in":43174,"scope":"read_profile"}

]$ curl -X GET --user client-a:123    \
"http://localhost:8080/oauth/check_token?token=92fde7aa-4793-4edc-89ca-9a4a5a646392"

{"aud":["resource-a"],"user_name":"adolfo","scope":["read_profile"],"active":true,"exp":1541170602,"authorities":["ROLE_USER"],"client_id":"client-a"}


]$ curl -X GET http://localhost:9000/res-a -H 'authorization: Bearer 92fde7aa-4793-4edc-89ca-9a4a5a646392'

resource A with success


]$ curl -X GET http://localhost:9001/res-b -H 'authorization: Bearer 92fde7aa-4793-4edc-89ca-9a4a5a646392'

{"error":"access_denied","error_description":"Invalid token does not contain resource id (resource-b)"}

```


## Protecting Resource Server with scope validation
p.372  ~ p.375

Chapter08/scope-validation

To better understand what's going on here, start the application, open your browser and go to the following authorization URL:

*http://localhost:8080/oauth/authorize?client_id=clientapp&redirect_uri=http://localhost:9000/callback&response_type=code&scope=read_x+read_y*



Then click on Authorize and retrieve the *Authorization* Code that will be present on the browser address bar (the parameter is *code* as you might already know). Request an access token using this Authorization Code as follows:

```
]$   curl -X POST "http://localhost:8080/oauth/token" --user clientapp:123   \
-H "content-type: application/x-www-form-urlencoded"  \
-d  "code=ZODTbc&grant_type=authorization_code&redirect_uri=http://localhost:9000/callback&scope=read_x"

{"access_token":"41b8dff9-e542-4be7-8895-f3a52bb745a6","token_type":"bearer","expires_in":43199,"scope":"read_x"}

]$  curl -X GET http://localhost:8080/api/x -H "authorization: Bearer 41b8dff9-e542-4be7-8895-f3a52bb745a6 "

resource X

]$  curl -X GET http://localhost:8080/api/y -H "authorization: Bearer 41b8dff9-e542-4be7-8895-f3a52bb745a6 "

{"error":"access_denied","error_description":"Access is denied"}


```
## Binding scopes with user roles to protect user's resources

p.376 ~ p.380

Chapter08/scope-binding

https://stackoverflow.com/questions/22417780/using-scopes-as-roles-in-spring-security-oauth2-provider

To better understand what's going on here, start the application, open your browser and go to the following authorization URL:

http://localhost:8080/oauth/authorize?client_id=clientapp&redirect_uri=http://localhost:9000/callback&response_type=code&scope=read_x+read_y



```
]$   curl -X POST "http://localhost:8080/oauth/token" --user clientapp:123   \
-H "content-type: application/x-www-form-urlencoded"  \
-d  "code=RAdFKB&grant_type=authorization_code&redirect_uri=http://localhost:9000/callback&scope=read_x"

{"access_token":"ec1da1e2-a809-49fb-8f0a-ef84d06590d1","token_type":"bearer","expires_in":43199,"scope":"read_x"}

]$  curl -X GET http://localhost:8080/api/x -H "authorization: Bearer ec1da1e2-a809-49fb-8f0a-ef84d06590d1 "

resource X

]$  curl -X GET http://localhost:8080/api/y -H "authorization: Bearer ec1da1e2-a809-49fb-8f0a-ef84d06590d1 "

{"error":"access_denied","error_description":"Access is denied"}


```

## Protecting the client against Authorization Code injection

p.381 ~ p.389

Chapter08/state-param/oauth2-provider-state

Chapter08/state-param/client-state

To better understand what's going on here, start the application, open your browser and go to the following authorization URL:

http://localhost:8080/oauth/authorize?client_id=clientapp&redirect_uri=http://localhost:9000/resource&response_type=code&scope=read+write


http://localhost:8080

'victim', '123'
'attacker', '123'

http://localhost:9000

security.user.name=adolfo
security.user.password=123


```
]$   curl -X POST "http://localhost:8080/oauth/token" --user attacker:123   \
-H "content-type: application/x-www-form-urlencoded"  \
-d  "code=m5wcA8&grant_type=authorization_code&redirect_uri=http://localhost:9000/resource&response_type=code&scope=read+write"

{"access_token":"ec1da1e2-a809-49fb-8f0a-ef84d06590d1","token_type":"bearer","expires_in":43199,"scope":"read_x"}

]$  curl -X GET http://localhost:8080/api/x -H "authorization: Bearer ec1da1e2-a809-49fb-8f0a-ef84d06590d1 "

resource X

]$  curl -X GET http://localhost:8080/api/y -H "authorization: Bearer ec1da1e2-a809-49fb-8f0a-ef84d06590d1 "

{"error":"access_denied","error_description":"Access is denied"}


```

## Protecting the Authorization Server from invalid redirection

p.389

Chapter08/state-param/uri-validation

Try to access the following URL and authenticate it using the default user credentials ( adolfo and 123 as username and password):

*http://localhost:8080/oauth/authorize?client_id=clientapp&redirect_uri=http://localhost:9000/malicious&response_type=token&scope=read+write*