package com.exampleAPI.zooKeeperAPI.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import static com.exampleAPI.zooKeeperAPI.support.UserConstant.AGE;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.EMAIL;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.PASSWORD;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class User {

    @JsonProperty(EMAIL)
    private String email;

    @JsonProperty(PASSWORD)
    private String password;

    @JsonProperty(AGE)
    private Integer age;

}
