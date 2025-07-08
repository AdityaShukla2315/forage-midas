package com.jpmc.midascore.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@Component
@ConfigurationProperties(prefix = "midas.kafka")
public class KafkaProperties {
    private String transactionTopic;

    public String getTransactionTopic() {
        return transactionTopic;
    }

    public void setTransactionTopic(String transactionTopic) {
        this.transactionTopic = transactionTopic;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
} 