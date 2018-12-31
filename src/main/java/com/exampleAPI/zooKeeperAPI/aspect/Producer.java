package com.exampleAPI.zooKeeperAPI.aspect;

import com.exampleAPI.zooKeeperAPI.model.LogType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

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
        return String.format("%s,%s,%s,%s,%s%n",logType.getDate(),logType.getUrl(),logType.getType(),logType.getParameter(),logType.getResponse());
    }
}
