package com.exampleAPI.zooKeeperAPI.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.exampleAPI.zooKeeperAPI.support.UserConstant.AGE;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.EMAIL;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.PASSWORD;

@Component
public class User {

    @JsonProperty(EMAIL)
    private String email;

    @JsonProperty(PASSWORD)
    private String password;

    @JsonProperty(AGE)
    private Integer age;

    public User(String email, String password, Integer age) {
        this.email = email;
        this.password = password;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getAge(), user.getAge());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPassword(), getAge());
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getAge() {
        return age;
    }

    public User() {

    }

}
