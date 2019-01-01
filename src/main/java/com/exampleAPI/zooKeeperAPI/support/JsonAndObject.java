package com.exampleAPI.zooKeeperAPI.support;

import com.exampleAPI.zooKeeperAPI.model.LogType;
import com.exampleAPI.zooKeeperAPI.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public  class JsonAndObject {
    private static ObjectMapper mapper = new ObjectMapper();
    private static ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    public static String ObjectToJson(User user) throws JsonProcessingException {
        return ow.writeValueAsString(user);
    }
    public static User JsonToObject(String input) throws IOException {
        return mapper.readValue(input, User.class);
    }
    public static String LogTypeToJson(LogType logType) throws JsonProcessingException {
        return ow.writeValueAsString(logType);
    }
}
