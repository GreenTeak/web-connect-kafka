package com.exampleAPI.zooKeeperAPI.controller;

import static com.exampleAPI.zooKeeperAPI.support.UserConstant.DELETE_IS_SUCCESS;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.INTERRUPTED_EXCEPTION;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.JSON_PROCESSING_EXCEPTION;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.KEEPER_EXCEPTION;
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
    private UserService userService;

    private final Logger logger = Logger.getLogger(UserController.class);

    @PostMapping(value = "/api/users/register")
    public ResponseEntity<String> addUser(@RequestBody User user) throws InterruptedException, KeeperException, JsonProcessingException {
        if (userService.addUser(user)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(user.getEmail());
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(user.getEmail());
    }

    @PostMapping(value = "/api/users/login")
    public ResponseEntity<Boolean> userLogin(@RequestBody User user) throws InterruptedException, IOException, KeeperException {

        String email = userService.userLogin(user.getEmail(), user.getPassword());

        if (null != email) {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(false);
    }

    @PutMapping(value = "/api/users")
    public ResponseEntity<String> updateUser(@RequestParam User user, @RequestBody String newPassword) throws InterruptedException, KeeperException, JsonProcessingException {
        if (userService.updateUser(user, newPassword)) {
            return ResponseEntity.status(HttpStatus.OK).body(user.getEmail());
        } else return ResponseEntity.status(HttpStatus.NO_CONTENT).body(user.getEmail());
    }

    @DeleteMapping(value = "/api/users")
    public ResponseEntity<String> deleteMapping(@RequestParam String email) throws KeeperException, InterruptedException {
        userService.deleteUser(email);
        return ResponseEntity.status(HttpStatus.OK).body(email);
    }

    @ExceptionHandler(KeeperException.class)
    public @ResponseBody
    Map<String, Object> keeperExceptionHandler(KeeperException keeperException) {
        logger.error(keeperException.getLocalizedMessage());
        Map<String, Object> model = new TreeMap<>();
        model.put(keeperException.code().toString(), keeperException.getMessage());
        return model;
    }

    @ExceptionHandler(InterruptedException.class)
    public @ResponseBody
    Map<String, Object> InterruptedExceptionHandler(InterruptedException interruptedException) {
        logger.error(interruptedException.getMessage());
        Map<String, Object> model = new TreeMap<>();
        model.put(INTERRUPTED_EXCEPTION, interruptedException.getMessage());
        return model;
    }

    @ExceptionHandler(JsonProcessingException.class)
    public @ResponseBody
    Map<String, Object> JsonProcessingExceptionHandler(JsonProcessingException JsonProcessingException) {
        logger.error(JsonProcessingException.getMessage());
        Map<String, Object> model = new TreeMap<>();
        model.put(JSON_PROCESSING_EXCEPTION, JsonProcessingException.getMessage());
        return model;
    }
}
