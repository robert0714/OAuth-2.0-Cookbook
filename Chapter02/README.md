# Chapter 2  Implementing Your Own OAuth 2.0 Provider
p.63 ~ p.70
<異常>
Chapter02/​auth-​code-​server

curl "http://localhost:8080/api/profile"


http://localhost:8080/oauth/authorize?client_id=clientapp&redirect_uri=http://localhost:9000/callback&response_type=code&scope=read_profile

security.user.name=adolfo
security.user.password=123


clientapp
123456


OAuth 2.0
specification at https:/​/tools.ietf.org/html/rfc6749#section-4.1.1


p.71 ~ p.75
<異常>
Chapter02/​implicit-server

p.76 ~ 80
<異常>
Chapter02/password-server

```
$> curl -X POST --user clientapp:123456 http://localhost:8080/oauth/token -H "accept: application/json" -H "content-type: application/x-www-form-urlencoded" -d   "grant_type=password&username=adolfo&password=123&scope=read_profile"

{"access_token":"da747938-fd6e-49b6-8cf8-9425bcc9a40a","token_type":"bearer","expires_in":43199,"scope":"read_profile"}

$> curl -X GET http://localhost:8080/api/profile -H "authorization: Bearer da747938-fd6e-49b6-8cf8-9425bcc9a40a"

{"name":"adolfo","email":"adolfo@mailinator.com"}[
```

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

p.86
<異常>
Chapter02/refresh-server

```
$ >  curl -X POST --user clientapp:123456 http://localhost:8080/oauth/token   \
     -H "accept: application/json" -H "content-type: application/x-www-form-urlencoded"   \
     -d   "grant_type=password&username=adolfo&password=123&scope=read_profile"

```