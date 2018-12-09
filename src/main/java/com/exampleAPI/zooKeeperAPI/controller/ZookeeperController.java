package com.exampleAPI.zooKeeperAPI.controller;

import com.exampleAPI.zooKeeperAPI.service.ZookeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ZookeeperController {

    public static final String WRONG_PATH_STR_FORMAT = "%s is wrong,please input path:str format";
    public static final String CREATE_SUCCESS = "create success";
    public static final String UPDATE_SUCCESS = "update success";
    public static final String REGEX = ":";
    public static final String API = "/api/node";
    @Autowired
    public ZookeeperService zookeeperService;

    @GetMapping(value = API + "/get")
    public String zkGet() {
        return zookeeperService.listNodeData();
    }

    @PostMapping(value = API + "/create")
    public String zkCreate(@RequestBody String input) {
        String[] str = input.split(REGEX);
        if (str.length == 2) {
            zookeeperService.addNodeData(str[0], str[1]);
            return CREATE_SUCCESS;
        }
        return String.format(WRONG_PATH_STR_FORMAT, input);
    }

    @DeleteMapping(value = API + "/delete")
    public boolean zkDelete(@RequestBody String path) {
        return zookeeperService.deleteNode(path);
    }

    @PutMapping(value = API + "/update")
    public String zkUpdate(@RequestBody String input) {
        String[] str = input.split(REGEX);
        if (str.length == 2) {
            zookeeperService.updateNodeData(str[0], str[1]);
            return UPDATE_SUCCESS;
        }
        return String.format(WRONG_PATH_STR_FORMAT, input);
    }
}
