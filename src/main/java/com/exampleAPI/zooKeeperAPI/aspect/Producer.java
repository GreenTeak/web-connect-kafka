package com.exampleAPI.zooKeeperAPI.aspect;

import com.exampleAPI.zooKeeperAPI.model.LogType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    public static final String COMMA = ",";
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic}")
    private String topic;

    public Producer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String message) {
        this.kafkaTemplate.send(topic, message);
    }

    public String combineMessage(LogType logType) {
        return logType.getDate() + COMMA + logType.getUrlType() + COMMA + logType.getType() + COMMA + logType.getParameter() + COMMA + logType.getResponse() + "\t";
    }
}
