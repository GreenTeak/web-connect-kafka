package com.exampleAPI.zooKeeperAPI.support;

import com.exampleAPI.zooKeeperAPI.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class JsonAndObject {
    public ObjectMapper mapper = new ObjectMapper();
    public ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    public String ObjectToJson(User user) throws JsonProcessingException {
        String userJson = ow.writeValueAsString(user);
        return userJson;
    }
    public User JsonToObject(String input) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(input, User.class);
        return user;
    }
}
