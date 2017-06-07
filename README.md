# Change Spring on the fly

* `config-service`: spring-cloud server
* `custom-service`: client of `config-service`
* `custom-service-self-config`: spring-cloud client without server

# `config-service`

spring-cloud server with the application properties that can be used by clients.

### Properties / YAML

At service application.yml, define the properties/yml location that will be externalized.
``` yml
# file:// OR git dir
spring.cloud.config.server.git.uri: file:///tmp/externalized-properties
```

### Dependencies
``` groovy
// springBootVersion = 1.5.3.RELEASE
// springCloudVersion = Dalston.SR1
dependencies {
	compile 'org.springframework.boot:spring-boot-starter'
	compile 'org.springframework.cloud:spring-cloud-config-server'
}
```

# `custom-service`

Client of `config-service` using the externalized properties/yml and refresh on the fly.

### Appling changes on the fly

``` java
@RefreshScope
@RestController
class CustomController {

    // cloud.foo must be configured at externalized properties
    // that config-service is looking.
    // Example:
    // /tmp/externalized-properties/application.properties
    // cloud.foo=change_me
    @Value("${cloud.foo}")

    private String value;

    @RequestMapping
    public String sayValue() {
        return value;
    }
}
```

### Dependencies
``` groovy
dependencies {
	compile 'org.springframework.boot:spring-boot-starter-web'
	compile 'org.springframework.boot:spring-boot-starter-actuator'
	compile 'org.springframework.cloud:spring-cloud-config-client'
}
```

Considering both services is running, let's fun:
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
< X-Application-Context: application
< Content-Type: text/plain;charset=UTF-8
< Content-Length: 9
< Date: Wed, 07 Jun 2017 13:52:27 GMT
< 
* Connection #0 to host localhost left intact
change_me
```

* Change the external config
```
➜  ~ echo cloud.foo=changed! > /tmp/externalized-properties/application.properties
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
< X-Application-Context: application
< Content-Type: text/plain;charset=UTF-8
< Content-Length: 8
< Date: Wed, 07 Jun 2017 13:55:58 GMT
< 
* Connection #0 to host localhost left intact
changed!
```

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
