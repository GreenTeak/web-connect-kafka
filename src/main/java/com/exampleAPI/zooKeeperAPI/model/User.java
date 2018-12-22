package com.exampleAPI.zooKeeperAPI.model;


import org.springframework.stereotype.Component;

@Component
public class User {
    private String email;
    private String password;
    private Integer age;

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
