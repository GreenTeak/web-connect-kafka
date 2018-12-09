package com.exampleAPI.zooKeeperAPI.controller;

import com.exampleAPI.zooKeeperAPI.service.ZookeeperService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.websocket.server.PathParam;

@Controller
public class ZookeeperController {

    @Autowired
    public ZookeeperService zookeeperService;

    @GetMapping(value = "/zkNode")
    public String zkget() {
        return zookeeperService.listNodeData();
    }

    @PostMapping(value = "/zkNode")
    public String zkCreate(@RequestBody String input) {
        String[] str = input.split(":");
        if (str.length == 2) {
            zookeeperService.addNodeData(str[0], str[1]);
            return "create success";
        }
        return String.format("%s is wrong,please input path:str format", input);
    }

    @DeleteMapping(value = "/zkNode")
    public void zkDelete(@RequestBody String path) {
        zookeeperService.deleteNode(path);
    }

    @PutMapping(value = "/zkNode")
    public String zkUpdate(@RequestBody String input) {
        String[] str = input.split(":");
        if(str.length ==2) {
            zookeeperService.updateNodeData(str[0], str[1]);
            return "update success";
        }
        return String.format("%s is wrong,please input path:str format", input);
    }
}
