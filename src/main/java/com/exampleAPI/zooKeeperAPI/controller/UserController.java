package com.exampleAPI.zooKeeperAPI.controller;

import static com.exampleAPI.zooKeeperAPI.support.UserConstant.API_USER_DELETE;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.API_USER_LOGIN;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.API_USER_REGISTER;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.API_USER_UPDATE;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.DELETE_IS_SUCCESS;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.EMAIL;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.LOGIN_IS_WRONG;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.PASSWORD;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.REQUEST_IS_WRONG;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.UPDATE_IS_SUCCESS;

import com.exampleAPI.zooKeeperAPI.model.User;
import com.exampleAPI.zooKeeperAPI.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
public class UserController {

    @Autowired
    public UserService userService;

    public final Logger logger = Logger.getLogger(UserController.class);

    @PostMapping(value = API_USER_REGISTER)
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            userService.addUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user.getEmail());
        } catch (JsonProcessingException | KeeperException | InterruptedException e) {
            logger.error(REQUEST_IS_WRONG);
        }
        return new ResponseEntity<>(REQUEST_IS_WRONG, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = API_USER_LOGIN)
    public ResponseEntity<String> userLogin(@RequestParam(value = EMAIL) String email,
                                            @RequestParam(value = PASSWORD) String password) {
        try {
            Integer age = userService.userLogin(email, password);
            if (age != -1) {
                return ResponseEntity.status(HttpStatus.OK).body(age.toString());
            }
        } catch (KeeperException | InterruptedException | IOException e) {
            logger.error(LOGIN_IS_WRONG);
        }
        return new ResponseEntity<>(REQUEST_IS_WRONG, HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = API_USER_UPDATE)
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        try {
            userService.updateUser(user);
        } catch (JsonProcessingException | KeeperException | InterruptedException e) {
            logger.error(String.format(UPDATE_IS_SUCCESS, user.getEmail()));
            return new ResponseEntity<>(user.getEmail(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(UPDATE_IS_SUCCESS, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = API_USER_DELETE)
    public ResponseEntity<String> deleteMapping(@RequestParam String email) {
        try {
            userService.deleteUser(email);
        } catch (KeeperException | InterruptedException e) {
            logger.error(String.format(DELETE_IS_SUCCESS, email));
            return new ResponseEntity<>(email, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(DELETE_IS_SUCCESS, HttpStatus.ACCEPTED);
    }

}
