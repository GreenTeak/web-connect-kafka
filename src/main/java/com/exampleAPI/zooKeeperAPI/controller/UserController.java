package com.exampleAPI.zooKeeperAPI.controller;

import com.exampleAPI.zooKeeperAPI.model.User;
import com.exampleAPI.zooKeeperAPI.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "/api/user")
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping(value = "/api/user")
    public void addUser(@RequestBody User user) {
        try {
            userService.addUser(user);
        } catch (JsonProcessingException | KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value = "/api/user")
    public void userLogin(@RequestParam String email, @RequestParam String password) {
        try {
            userService.userLogin(email, password);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @PutMapping(value = "/api/user")
    public void updateUser(@RequestBody User user) {
        try {
            userService.updateUser(user);
        } catch (JsonProcessingException | KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping(value = "/api/user")
    public void deleteMapping(@RequestParam String email) {
        try {
            userService.deleteUser(email);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
