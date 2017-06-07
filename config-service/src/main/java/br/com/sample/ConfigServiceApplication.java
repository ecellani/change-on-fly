package br.com.sample;

import org.springframework.boot.SpringApplication;
//import org.springframework.boot.actuate.health.Health;
//import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigServiceApplication /*implements HealthIndicator*/ {
/*
	@Override
	public Health health() {
		return Health.up().withDetail("hello", "world").build();
	}
*/
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ConfigServiceApplication.class, args);
	}

}
