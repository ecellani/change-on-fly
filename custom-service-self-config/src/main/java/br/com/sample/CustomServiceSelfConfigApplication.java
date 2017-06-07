package br.com.sample;

import br.com.sample.config.ApplicationConfig;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.mongodb.util.JSON.parse;
import static java.lang.String.format;
import static org.springframework.data.mongodb.core.query.Update.fromDBObject;

@SpringBootApplication
public class CustomServiceSelfConfigApplication {

    @Autowired
    private ApplicationConfig config;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CustomServiceSelfConfigApplication.class, args);
	}

	@RefreshScope
	@RestController
    class CustomController {

        @Autowired
        private MongoTemplate mongoTemplate;

        @RequestMapping
        public String sayValue() {
            DBObject dbObject = (DBObject) parse(format("{teste: '%s'}", config.getFoo()));
            mongoTemplate.save(fromDBObject(dbObject), "collection_1");
            return config.getFoo();
        }
    }
}
