package br.com.sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class CustomServiceSelfConfigApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CustomServiceSelfConfigApplication.class, args);
	}

	@RefreshScope
	@RestController
    class CustomController {

        @Value("${cloud.foo}")
        private String value;

        @RequestMapping
        public String sayValue() {
            return value;
        }
    }
}
