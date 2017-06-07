# `custom-service-self-config`

Refresh the application properties itself.

### Dependencies
``` groovy
dependencies {
	compile 'org.springframework.boot:spring-boot-starter-web'
	compile 'org.springframework.boot:spring-boot-starter-actuator'
	compile 'org.springframework.cloud:spring-cloud-config-client'
}
```

Once the application is running, let's fun:
* GET
```
➜  ~ curl -v http://localhost:8080/            
*   Trying 127.0.0.1...
* Connected to localhost (127.0.0.1) port 8080 (#0)
> GET / HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.50.1
> Accept: */*
> 
< HTTP/1.1 200 
< X-Application-Context: application:8080
< Content-Type: text/plain;charset=UTF-8
< Content-Length: 6
< Date: Wed, 07 Jun 2017 14:19:36 GMT
< 
* Connection #0 to host localhost left intact
try me
```

* Change the application yml
```
➜ sed -ri 's/^(\s*)(foo\s*:\s*try me\s*$)/\1foo: tried/' application.yml
```

* Refresh scope
```
➜  ~ curl -X POST http://localhost:8080/refresh
["cloud.foo"]
```

* GET
```
➜  ~ curl -v http://localhost:8080/            
*   Trying 127.0.0.1...
* Connected to localhost (127.0.0.1) port 8080 (#0)
> GET / HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.50.1
> Accept: */*
> 
< HTTP/1.1 200 
< X-Application-Context: application:8080
< Content-Type: text/plain;charset=UTF-8
< Content-Length: 5
< Date: Wed, 07 Jun 2017 14:30:33 GMT
< 
* Connection #0 to host localhost left intact
tried
```
