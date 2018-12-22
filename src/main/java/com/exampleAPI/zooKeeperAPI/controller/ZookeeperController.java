package com.exampleAPI.zooKeeperAPI.controller;

import com.exampleAPI.zooKeeperAPI.model.Node;
import com.exampleAPI.zooKeeperAPI.service.ZookeeperService;
import lombok.extern.slf4j.Slf4j;
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
import io.swagger.annotations.Api;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@Api(value = "/api/node")
public class ZookeeperController {

    public final Logger logger = Logger.getLogger(ZookeeperController.class);

    @Autowired
    public ZookeeperService zookeeperService;

    @GetMapping
    public ResponseEntity<String> zkGet() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(zookeeperService.listNodeData());
        } catch (KeeperException | InterruptedException e) {
            logger.error("request is wrong");
        }
        return new ResponseEntity<>("request is wrong", HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public void zkCreate(@RequestBody Node input, HttpServletResponse response) {
        try {
            zookeeperService.addNodeIfNotExists(input);
            response.setStatus(201);
        } catch (KeeperException | InterruptedException e) {
            logger.error(String.format("create %s is failure", input.getPath()));
            response.setStatus(400);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> zkDelete(@RequestBody String path) {
        try {
            zookeeperService.deleteNode(path);
        } catch (KeeperException | InterruptedException e) {
            logger.error(String.format("create %s is failure", path));
            return new ResponseEntity<>(path, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("delete is success", HttpStatus.ACCEPTED);
    }

    @PutMapping
    public ResponseEntity<String> zkUpdate(@RequestBody Node input) {
        try {
            zookeeperService.updateNodeData(input);
        } catch (KeeperException | InterruptedException e) {
            logger.error(String.format("update %s is failure", input.getPath()));
            return new ResponseEntity<>(input.getPath(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("update is success", HttpStatus.ACCEPTED);
    }
}
