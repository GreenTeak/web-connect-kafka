package com.exampleAPI.zooKeeperAPI.controller;

import com.exampleAPI.zooKeeperAPI.model.Node;
import com.exampleAPI.zooKeeperAPI.service.ZookeeperService;
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
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static com.exampleAPI.zooKeeperAPI.support.UserConstant.API_NODE;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.CREATE_IS_FAILURE;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.DELETE_IS_SUCCESS;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.DELETE_IS_FAILURE;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.REQUEST_IS_WRONG;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.UPDATE_IS_SUCCESS;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.UPDATE_S_IS_FAILURE;

@RestController
public class ZookeeperController {

    public final Logger logger = Logger.getLogger(ZookeeperController.class);

    @Autowired
    public ZookeeperService zookeeperService;

    @GetMapping(value = API_NODE)
    public ResponseEntity<String> zkGet() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(zookeeperService.listNodeData());
        } catch (KeeperException | InterruptedException e) {
            logger.error(REQUEST_IS_WRONG);
        }
        return new ResponseEntity<>(REQUEST_IS_WRONG, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = API_NODE)
    public void zkCreate(@RequestBody Node input, HttpServletResponse response) {
        try {
            zookeeperService.addNodeIfNotExists(input);
            response.setStatus(201);
        } catch (KeeperException | InterruptedException e) {
            logger.error(String.format(CREATE_IS_FAILURE, input.getPath()));
            response.setStatus(400);
        }
    }

    @DeleteMapping(value = API_NODE)
    public ResponseEntity<String> zkDelete(@RequestBody String path) {
        try {
            zookeeperService.deleteNode(path);
        } catch (KeeperException | InterruptedException e) {
            logger.error(String.format(DELETE_IS_FAILURE, path));
            return new ResponseEntity<>(path, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(DELETE_IS_SUCCESS, HttpStatus.ACCEPTED);
    }

    @PutMapping(value = API_NODE)
    public ResponseEntity<String> zkUpdate(@RequestBody Node input) {
        try {
            zookeeperService.updateNodeData(input);
        } catch (KeeperException | InterruptedException e) {
            logger.error(String.format(UPDATE_S_IS_FAILURE, input.getPath()));
            return new ResponseEntity<>(input.getPath(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(UPDATE_IS_SUCCESS, HttpStatus.ACCEPTED);
    }
}
