package br.com.sample.bean;

import br.com.sample.config.SpringConfig;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class DataBeans {

    @Autowired
    private SpringConfig springConfig;

    @Bean
    @RefreshScope
    public MongoTemplate mongoTemplate() throws Exception {
        SpringConfig.Mongodb mongodb = springConfig.getData().getMongodb();
        return new MongoTemplate(new MongoClient(mongodb.getHost(), mongodb.getPort()), mongodb.getDatabase());
    }
}
