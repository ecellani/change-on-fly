package br.com.sample.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@lombok.Data
@Component
@ConfigurationProperties("spring")
public class SpringConfig {

    private Data data = new Data();

    @lombok.Data
    public static class Data {
        private Mongodb mongodb = new Mongodb();
    }

    @lombok.Data
    public static class Mongodb {
        private int port;
        private String host;
        private String database;
    }
}
