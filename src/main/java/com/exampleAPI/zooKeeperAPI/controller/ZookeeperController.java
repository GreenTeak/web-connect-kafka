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

    @Autowired
    public ZookeeperService zookeeperService;

    @GetMapping(value = "/zkNode")
    public String zkget() {
        return zookeeperService.listNodeData();
    }

    @PostMapping(value = "/zkNode")
    public void zkCreate(@RequestBody String input) {
        String[] str = input.split(":");
        zookeeperService.addNodeData(str[0], str[1]);
    }

    @DeleteMapping(value = "/zkNode")
    public void zkDelete(@RequestBody String path) {
        zookeeperService.deleteNode(path);
    }

    @PutMapping(value = "/zkNode")
    public void zkUpdate(@RequestBody String input) {
        String[] str = input.split(":");
        zookeeperService.updateNodeData(str[0], str[1]);
    }
}
