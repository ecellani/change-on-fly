# `config-service`: spring-cloud server

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
