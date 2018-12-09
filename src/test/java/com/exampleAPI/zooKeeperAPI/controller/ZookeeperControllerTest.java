package com.exampleAPI.zooKeeperAPI.controller;

import com.exampleAPI.zooKeeperAPI.service.ZookeeperService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class ZookeeperControllerTest {
    @Autowired
    private ZookeeperController zookeeperController;

    @Test
    public void zkCreate() {
        String input = "path";
        String info = zookeeperController.zkCreate("path");
        String expect = String.format("%s is wrong,please input path:str format", input);
        assertEquals(expect, info);
    }

    @Test
    public void zkUpdate() {
        String input = "path";
        String info = zookeeperController.zkUpdate("path");
        String expect = String.format("%s is wrong,please input path:str format", input);
        assertEquals(expect, info);
    }
}