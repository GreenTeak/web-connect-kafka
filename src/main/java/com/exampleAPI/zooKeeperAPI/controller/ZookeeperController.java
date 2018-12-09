package com.exampleAPI.zooKeeperAPI.controller;

import com.exampleAPI.zooKeeperAPI.service.ZookeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ZookeeperController {

    public static final String WRONG_PATH_STR_FORMAT = "%s is wrong,please input path:str format";
    public static final String CREATE_SUCCESS = "create success";
    public static final String UPDATE_SUCCESS = "update success";
    public static final String REGEX = ":";
    public static final String ZK_NODE = "/zkNode";
    @Autowired
    public ZookeeperService zookeeperService;

    @GetMapping(value = ZK_NODE)
    public String zkget() {
        return zookeeperService.listNodeData();
    }

    @PostMapping(value = ZK_NODE)
    public String zkCreate(@RequestBody String input) {
        String[] str = input.split(REGEX);
        if (str.length == 2) {
            zookeeperService.addNodeData(str[0], str[1]);
            return CREATE_SUCCESS;
        }
        return String.format(WRONG_PATH_STR_FORMAT, input);
    }

    @DeleteMapping(value = ZK_NODE)
    public void zkDelete(@RequestBody String path) {
        zookeeperService.deleteNode(path);
    }

    @PutMapping(value = ZK_NODE)
    public String zkUpdate(@RequestBody String input) {
        String[] str = input.split(REGEX);
        if (str.length == 2) {
            zookeeperService.updateNodeData(str[0], str[1]);
            return UPDATE_SUCCESS;
        }
        return String.format(WRONG_PATH_STR_FORMAT, input);
    }
}
