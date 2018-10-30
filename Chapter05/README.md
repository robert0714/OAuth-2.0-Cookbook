# Chapter 5  Self Contained Tokens with JWT

## Generating access tokens as JWT
p.207 ~ p.211

Chapter05/symmetric/auth-server-jwt

https://tools.ietf.org/html/rfc7519

https://jwt.io/introduction/

As you might have noticed, the main differences between the declared Authorization Server and the normal version (*that is, Authorization Servers that do not generate JWT access tokens*), is that we are using special implementations of TokenStore and AccessTokenConverter interfaces.


```
$ >   curl -X POST --user clientapp:123456   \
-H  "Content-Type: application/x-www-form-urlencoded"   \
-H "Accept: application/json" "http://localhost:8080/oauth/token"  \
 -d "grant_type=password&username=adolfo&password=123"

{  
   "access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDA5MDg3NjYsInVzZXJfbmFtZSI6ImFkb2xmbyIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiI4OWQyYmFjNS1iY2U4LTQxODEtYjhmNy0xNWNlNDkyYWJhMzUiLCJjbGllbnRfaWQiOiJjbGllbnRhcHAiLCJzY29wZSI6WyJyZWFkX3Byb2ZpbGUiXX0.OHaS58QXJEAqcTFrNEeyq2BDwIXCzIZrgHn2abYYkk0",
   "token_type":"bearer",
   "refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG9sZm8iLCJzY29wZSI6WyJyZWFkX3Byb2ZpbGUiXSwiYXRpIjoiODlkMmJhYzUtYmNlOC00MTgxLWI4ZjctMTVjZTQ5MmFiYTM1IiwiZXhwIjoxNTQzNDU3NTY2LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiOTBjOGZhMWItNmU4MC00MmY3LTk0YzAtNDY2MzFmMjg2YzI0IiwiY2xpZW50X2lkIjoiY2xpZW50YXBwIn0.SNMewcuXSiklskiBooMf7UIOTrFaoEfDc9JkU_c-pss",
   "expires_in":43199,
   "scope":"read_profile",
   "jti":"89d2bac5-bce8-4181-b8f7-15ce492aba35"
}
```
https://medium.facilelogin.com/jwt-jws-and-jwe-for-not-so-dummies-b63310d201a3

https://www.jsonwebtoken.io/


## Validating JWT tokens at the Resource Server side
p.211 ~ p.215

Chapter05/symmetric/auth-server-jwt

Chapter05/symmetric/resource-server-jwt

https://tools.ietf.org/html/rfc7519

 ```
$> curl -X POST --user clientapp:123456   \
-H "Content-Type: application/x-www-form-urlencoded"  \
 -H "Accept: application/json" "http://localhost:8080/oauth/token"  \
  -d  "grant_type=password&username=adolfo&password=123"

{  
   "access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDA5MDkyODEsInVzZXJfbmFtZSI6ImFkb2xmbyIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiIzMGM4MThlYi0zMGQ5LTRmODEtYTEzOS1lYzliODQyNWE1ODciLCJjbGllbnRfaWQiOiJjbGllbnRhcHAiLCJzY29wZSI6WyJyZWFkX3Byb2ZpbGUiXX0.m9AtuW_JVgPrOXquP4pa5-S2jov4WmEY0_68iHkhfXM",
   "token_type":"bearer",
   "refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG9sZm8iLCJzY29wZSI6WyJyZWFkX3Byb2ZpbGUiXSwiYXRpIjoiMzBjODE4ZWItMzBkOS00ZjgxLWExMzktZWM5Yjg0MjVhNTg3IiwiZXhwIjoxNTQzNDU4MDgxLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiM2Q2MmYxMzYtOTU3Yi00MTVhLTkyMzktNTM3MGYyN2QzODU4IiwiY2xpZW50X2lkIjoiY2xpZW50YXBwIn0.QEslJTStn79RcYz7uirseH_fsvNmpURyX6LHj0ad8ko",
   "expires_in":43199,
   "scope":"read_profile",
   "jti":"30c818eb-30d9-4f81-a139-ec9b8425a587"
}
```
https://www.base64decode.org/

https://www.jsonwebtoken.io/

```
{
 "exp": 1540869816,
 "user_name": "adolfo",
 "authorities": [
  "ROLE_USER"
 ],
 "jti": "30c818eb-30d9-4f81-a139-ec9b8425a587",
 "client_id": "clientapp",
 "scope": [
  "read_profile"
 ],
 "iat": 1540866216
}

$>  curl -X GET -H "Authorization: Bearer  /
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDA5MDkyODEsInVzZXJfbmFtZSI6ImFkb2xmbyIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiIzMGM4MThlYi0zMGQ5LTRmODEtYTEzOS1lYzliODQyNWE1ODciLCJjbGllbnRfaWQiOiJjbGllbnRhcHAiLCJzY29wZSI6WyJyZWFkX3Byb2ZpbGUiXX0.m9AtuW_JVgPrOXquP4pa5-S2jov4WmEY0_68iHkhfXM"   "http://localhost:8081/api/profile"

{"error":"invalid_token","error_description":"Cannot convert access token to JSON"}

$>  curl -X GET -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDA5MDkyODEsInVzZXJfbmFtZSI6ImFkb2xmbyIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiIzMGM4MThlYi0zMGQ5LTRmODEtYTEzOS1lYzliODQyNWE1ODciLCJjbGllbnRfaWQiOiJjbGllbnRhcHAiLCJzY29wZSI6WyJyZWFkX3Byb2ZpbGUiXX0.m9AtuW_JVgPrOXquP4pa5-S2jov4WmEY0_68iHkhfXM"   "http://localhost:8081/api/profile"

{"name":"adolfo","email":"adolfo@mailinator.com"}

```

org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter

org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor (line: 40,48)

## Adding custom claims on JWT
p.216 ~ p.220
 
Chapter05/custom-claims-jwt
 

```
$> curl -X POST --user clientapp:123456   \
-H "Content-Type: application/x-www-form-urlencoded"  \
 -H "Accept: application/json" "http://localhost:8080/oauth/token"  \
  -d  "grant_type=password&username=adolfo&password=123"

{  
   "access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDA5MTA3NzcsInVzZXJfbmFtZSI6ImFkb2xmbyIsImp0aSI6IjU2NDY4NWM3LTJjZmYtNGMxMC1hYzVkLTkxOTExNzRjNjNjMCIsImVtYWlsIjoiYWRvbGZvQG1haWxpbmF0b3IuY29tIiwiY2xpZW50X2lkIjoiY2xpZW50YXBwIiwic2NvcGUiOlsicmVhZF9wcm9maWxlIl19.mLM3IGh8E0UW_0_1XRic08Ttq53xqPwUYrFj9PAcMQ4",
   "token_type":"bearer",
   "refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG9sZm8iLCJzY29wZSI6WyJyZWFkX3Byb2ZpbGUiXSwiYXRpIjoiNTY0Njg1YzctMmNmZi00YzEwLWFjNWQtOTE5MTE3NGM2M2MwIiwiZXhwIjoxNTQzNDU5NTc3LCJqdGkiOiJkZWU1ZTdhOC1lNWNkLTRjNjctOWVjNC1mNGZjNWU3ZGViOGYiLCJlbWFpbCI6ImFkb2xmb0BtYWlsaW5hdG9yLmNvbSIsImNsaWVudF9pZCI6ImNsaWVudGFwcCJ9.A-3xNyJB-Ddn03cl7aTzi2GJnYcT7dlTMNsuYSLN8ag",
   "expires_in":43199,
   "scope":"read_profile",
   "email":"adolfo@mailinator.com",
   "jti":"564685c7-2cff-4c10-ac5d-9191174c63c0"
}

```
https://www.base64decode.org/

https://www.jsonwebtoken.io/

```
{
 "exp": 1540871314,
 "user_name": "adolfo",
 "jti": "564685c7-2cff-4c10-ac5d-9191174c63c0",
 "email": "adolfo@mailinator.com",
 "client_id": "clientapp",
 "scope": [
  "read_profile"
 ],
 "iat": 1540867714
}
```

## Asymmetric signing of a JWT token
p.221 ~ p.225
 
Chapter05/asymmetric/jwt-asymmetric-server

```
$> curl -X POST --user clientapp:123456   \
-H "Content-Type: application/x-www-form-urlencoded"  \
 -H "Accept: application/json" "http://localhost:8080/oauth/token"  \
  -d  "grant_type=password&username=adolfo&password=123"

{  
   "access_token":"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDA5MTI2ODQsInVzZXJfbmFtZSI6ImFkb2xmbyIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJiZGE2MzlmOC01YmU4LTQ3MDktOWRhNS00MWEyYmM2NmYxZTUiLCJjbGllbnRfaWQiOiJjbGllbnRhcHAiLCJzY29wZSI6WyJyZWFkX3Byb2ZpbGUiXX0.HYynXZFW6H2-0_tcQtoENJ-gVQBG1zZZzSgucQ0dSZR7VRsY9JRVyahZYF7W3EFOzyIzeZsH1a7KGBIFulcnWE3enHWElcuSnsNVAC8iuvkRSmNgojkWaLlWN6_1WGokO4EujR35QOPDptn2ne2BbXQBED2YjnVKf8v0B_TGzsA",
   "token_type":"bearer",
   "expires_in":43199,
   "scope":"read_profile",
   "jti":"bda639f8-5be8-4709-9da5-41a2bc66f1e5"
}


$>  curl http://localhost:8080/oauth/token_key
{  
   "alg":"SHA256withRSA",
   "value":"-----BEGIN PUBLIC KEY-----\nMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2Ofl+PT+sFmgyqxZGTn8UiT9Y8J0errtho6Y2YGteabQgV6CXn6utn8JlG6uPab4tw6lEu4MwxluI6rlJI8NzoCPWVCjPNLAHUk5bSlyhGIvisRq32Dwjh5VmA110N/QxVMIYafQWdTDRg1ywI7OVk3ffPDWL86dwNFD1ezu2kQIDAQAB\n-----END PUBLIC KEY-----"
}

```
https://www.base64decode.org/

https://www.jsonwebtoken.io/



## Validating asymmetric signed JWT token
p.225 ~ p.227

Chapter05/asymmetric/jwt-asymmetric-resource

Chapter05/asymmetric/jwt-asymmetric-server

```
$> curl -X POST --user clientapp:123456   \
-H "Content-Type: application/x-www-form-urlencoded"  \
 -H "Accept: application/json" "http://localhost:8080/oauth/token"  \
  -d  "grant_type=password&username=adolfo&password=123"

{  
   "access_token":"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDA5MTI2ODQsInVzZXJfbmFtZSI6ImFkb2xmbyIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJiZGE2MzlmOC01YmU4LTQ3MDktOWRhNS00MWEyYmM2NmYxZTUiLCJjbGllbnRfaWQiOiJjbGllbnRhcHAiLCJzY29wZSI6WyJyZWFkX3Byb2ZpbGUiXX0.HYynXZFW6H2-0_tcQtoENJ-gVQBG1zZZzSgucQ0dSZR7VRsY9JRVyahZYF7W3EFOzyIzeZsH1a7KGBIFulcnWE3enHWElcuSnsNVAC8iuvkRSmNgojkWaLlWN6_1WGokO4EujR35QOPDptn2ne2BbXQBED2YjnVKf8v0B_TGzsA",
   "token_type":"bearer",
   "expires_in":43199,
   "scope":"read_profile",
   "jti":"bda639f8-5be8-4709-9da5-41a2bc66f1e5"
}


$>  curl http://localhost:8080/oauth/token_key
{  
   "alg":"SHA256withRSA",
   "value":"-----BEGIN PUBLIC KEY-----\nMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2Ofl+PT+sFmgyqxZGTn8UiT9Y8J0errtho6Y2YGteabQgV6CXn6utn8JlG6uPab4tw6lEu4MwxluI6rlJI8NzoCPWVCjPNLAHUk5bSlyhGIvisRq32Dwjh5VmA110N/QxVMIYafQWdTDRg1ywI7OVk3ffPDWL86dwNFD1ezu2kQIDAQAB\n-----END PUBLIC KEY-----"
}

$> curl -X GET http://localhost:8081/api/profile -H "authorization:Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDA5MTI2ODQsInVzZXJfbmFtZSI6ImFkb2xmbyIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJiZGE2MzlmOC01YmU4LTQ3MDktOWRhNS00MWEyYmM2NmYxZTUiLCJjbGllbnRfaWQiOiJjbGllbnRhcHAiLCJzY29wZSI6WyJyZWFkX3Byb2ZpbGUiXX0.HYynXZFW6H2-0_tcQtoENJ-gVQBG1zZZzSgucQ0dSZR7VRsY9JRVyahZYF7W3EFOzyIzeZsH1a7KGBIFulcnWE3enHWElcuSnsNVAC8iuvkRSmNgojkWaLlWN6_1WGokO4EujR35QOPDptn2ne2BbXQBED2YjnVKf8v0B_TGzsA"

{"name":"adolfo","email":"adolfo@mailinator.com"}
```

## Using JWE to cryptographically protect JWT tokens
p.228 ~ p.234

Chapter05/jwe/jwe-resource

Chapter05/jwe/jwe-server

```
$> curl -X POST --user clientapp:123456   \
-H "Content-Type: application/x-www-form-urlencoded"  \
 -H "Accept: application/json" "http://localhost:8080/oauth/token"  \
  -d  "grant_type=password&username=adolfo&password=123"

{  
   "access_token":"eyJlbmMiOiJBMTI4R0NNIiwiYWxnIjoiQTEyOEtXIn0.O0pRlUocNRqwrECeG_zuulNLkEhgcb-i.HLW5-XgCC7NJmTLq.e684Tf21oTcqXGTsP_GqdAXA6iGL6-1LqRivSx11xgGos7BNlRffv4SA_4jTK3LYi8gteIcfHCIk8x59VHsQ7-jyCpLuFVhOS0Bilgcaes_l1aR0wLShcYBBEoIpYl_gQXMUa5K8JTojYKm45XoZQqjN6kgRCV1J6cps9pWungsVfdvWlCZ3U8vFm-zFO2JItFP8jszAy8Sun0z1dmSFlcw.A5t151t7nlkIVT7tyj8r_w",
   "token_type":"bearer",
   "expires_in":43199,
   "scope":"read_profile",
   "jti":"446fc1b3-09e8-4662-b85c-93ae08354e1e"
}

```

use https://www.base64decode.org/
```
{"enc":"A128GCM","alg":"A128KW"}ҔeR
F+'4rxI2{8M7*\d<jp:bԺ\`;Q}H#Lb/ |pyQC
XNK@bzui0-(\`DX1F+S6
W*ޤԞiZWݽiBgu<Y"O;3/}3ْW0u[{YU>?+
```


use  https://www.jsonwebtoken.io/
{
 "jti": "70c93f81-0f4b-4e68-9340-24078b794cb3",
 "iat": 1540870561,
 "exp": 1540874161
}

## Using JWE at the Resource Server side
p.234 ~ p.239

Chapter05/jwe/jwe-resource

Chapter05/jwe/jwe-server

```
$> curl -X POST --user clientapp:123456   \
-H "Content-Type: application/x-www-form-urlencoded"  \
 -H "Accept: application/json" "http://localhost:8080/oauth/token"  \
  -d  "grant_type=password&username=adolfo&password=123"

{  
   "access_token":"eyJlbmMiOiJBMTI4R0NNIiwiYWxnIjoiQTEyOEtXIn0.O0pRlUocNRqwrECeG_zuulNLkEhgcb-i.HLW5-XgCC7NJmTLq.e684Tf21oTcqXGTsP_GqdAXA6iGL6-1LqRivSx11xgGos7BNlRffv4SA_4jTK3LYi8gteIcfHCIk8x59VHsQ7-jyCpLuFVhOS0Bilgcaes_l1aR0wLShcYBBEoIpYl_gQXMUa5K8JTojYKm45XoZQqjN6kgRCV1J6cps9pWungsVfdvWlCZ3U8vFm-zFO2JItFP8jszAy8Sun0z1dmSFlcw.A5t151t7nlkIVT7tyj8r_w",
   "token_type":"bearer",
   "expires_in":43199,
   "scope":"read_profile",
   "jti":"446fc1b3-09e8-4662-b85c-93ae08354e1e"
}


$> curl -X GET http://localhost:8081/api/profile -H "authorization:Bearer eyJlbmMiOiJBMTI4R0NNIiwiYWxnIjoiQTEyOEtXIn0.O0pRlUocNRqwrECeG_zuulNLkEhgcb-i.HLW5-XgCC7NJmTLq.e684Tf21oTcqXGTsP_GqdAXA6iGL6-1LqRivSx11xgGos7BNlRffv4SA_4jTK3LYi8gteIcfHCIk8x59VHsQ7-jyCpLuFVhOS0Bilgcaes_l1aR0wLShcYBBEoIpYl_gQXMUa5K8JTojYKm45XoZQqjN6kgRCV1J6cps9pWungsVfdvWlCZ3U8vFm-zFO2JItFP8jszAy8Sun0z1dmSFlcw.A5t151t7nlkIVT7tyj8r_w"

{"name":"adolfo","email":"adolfo@mailinator.com"}

```

## Using proof-of-possession key semantics on OAuth 2.0 Provider
<驗證未完成>
p.240 ~ p.248

Chapter05/pop/pop-server 

https://tools.ietf.org/html/rfc7800






## Using proof-of-possession key semantics on OAuth 2.0 Provider
<驗證未完成>

p.249 ~ p.257

Chapter05/pop/pop-server

Chapter05/pop/pop-client

Open th below url in browser

http://localhost:9000