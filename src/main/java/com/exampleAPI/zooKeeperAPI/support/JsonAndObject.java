package com.exampleAPI.zooKeeperAPI.support;

import com.exampleAPI.zooKeeperAPI.model.LogItem;
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
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String userJson = ow.writeValueAsString(user);
        return userJson;
    }
    public static User JsonToObject(String input) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(input, User.class);
        return user;
    }
    public static String LogTypeToJson(LogItem logItem) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String userJson = ow.writeValueAsString(logItem);
        return userJson;
    }
}
