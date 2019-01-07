package com.exampleAPI.zooKeeperAPI.controller;

import com.exampleAPI.zooKeeperAPI.model.Node;
import com.exampleAPI.zooKeeperAPI.service.ZookeeperService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

import static com.exampleAPI.zooKeeperAPI.support.UserConstant.CREATE_IS_SUCCESS;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.DELETE_IS_SUCCESS;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.INTERRUPTED_EXCEPTION;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.KEEPER_EXCEPTION;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.UPDATE_IS_SUCCESS;


@RestController
public class ZookeeperController {

    private final Logger logger = Logger.getLogger(ZookeeperController.class);

    @Autowired
    private ZookeeperService zookeeperService;

    @GetMapping(value = "/api/node")
    public ResponseEntity<String> zkGet() throws KeeperException, InterruptedException {
        return ResponseEntity.status(HttpStatus.OK).body(zookeeperService.listNodeData());
    }

    @PostMapping(value = "/api/node")
    public ResponseEntity<String> zkCreate(@RequestBody Node input) throws KeeperException, InterruptedException {
        zookeeperService.addNodeIfNotExists(input);
        return new ResponseEntity<>(CREATE_IS_SUCCESS, HttpStatus.OK);

    }

    @DeleteMapping(value = "/api/node")
    public ResponseEntity<String> zkDelete(@RequestBody String path) throws KeeperException, InterruptedException {
        zookeeperService.deleteNode(path);
        return new ResponseEntity<>(DELETE_IS_SUCCESS, HttpStatus.OK);
    }

    @PutMapping(value = "/api/node")
    public ResponseEntity<String> zkUpdate(@RequestBody Node input) throws KeeperException, InterruptedException {
        zookeeperService.updateNodeData(input);
        return new ResponseEntity<>(UPDATE_IS_SUCCESS, HttpStatus.OK);
    }

    @ExceptionHandler(KeeperException.class)
    public @ResponseBody
    Map<String, Object> keeperExceptionHandler(KeeperException keeperException) {
        logger.error(keeperException.getMessage());
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

}
