package com.exampleAPI.zooKeeperAPI.controller;

import static com.exampleAPI.zooKeeperAPI.support.UserConstant.API_USER_DELETE;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.API_USER_LOGIN;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.API_USER_REGISTER;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.API_USER_UPDATE;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.DELETE_IS_SUCCESS;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.EMAIL;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.INTERRUPTED_EXCEPTION;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.JSON_PROCESSING_EXCEPTION;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.KEEPER_EXCEPTION;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;


@RestController
public class UserController {

    @Autowired
    public UserService userService;

    public final Logger logger = Logger.getLogger(UserController.class);

    @PostMapping(value = API_USER_REGISTER)
    public ResponseEntity<String> addUser(@RequestBody User user) throws InterruptedException, KeeperException, JsonProcessingException {
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user.getEmail());
    }

    @GetMapping(value = API_USER_LOGIN)
    public ResponseEntity<String> userLogin(@RequestParam(value = EMAIL) String email,
                                            @RequestParam(value = PASSWORD) String password) throws InterruptedException, IOException, KeeperException {

        Integer age = userService.userLogin(email, password);

        if (age != -1) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(age.toString());
        }
        return new ResponseEntity<>(REQUEST_IS_WRONG, HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = API_USER_UPDATE)
    public ResponseEntity<String> updateUser(@RequestBody User user) throws InterruptedException, KeeperException, JsonProcessingException {
        userService.updateUser(user);
        return new ResponseEntity<>(UPDATE_IS_SUCCESS, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = API_USER_DELETE)
    public ResponseEntity<String> deleteMapping(@RequestParam String email) throws KeeperException, InterruptedException {
        userService.deleteUser(email);
        return new ResponseEntity<>(DELETE_IS_SUCCESS, HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody
    Map<String, Object> keeperExceptionHandler(KeeperException keeperException) {
        logger.error(keeperException.getLocalizedMessage());
        Map<String, Object> model = new TreeMap<>();
        model.put(KEEPER_EXCEPTION, false);
        return model;
    }

    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody
    Map<String, Object> InterruptedExceptionHandler(InterruptedException interruptedException) {
        logger.error(interruptedException.getLocalizedMessage());
        Map<String, Object> model = new TreeMap<>();
        model.put(INTERRUPTED_EXCEPTION, false);
        return model;
    }

    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody
    Map<String, Object> JsonProcessingExceptionHandler(JsonProcessingException JsonProcessingException) {
        logger.error(JsonProcessingException.getLocalizedMessage());
        Map<String, Object> model = new TreeMap<>();
        model.put(JSON_PROCESSING_EXCEPTION, false);
        return model;
    }

}