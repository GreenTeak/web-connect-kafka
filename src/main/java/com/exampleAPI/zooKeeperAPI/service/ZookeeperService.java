package com.exampleAPI.zooKeeperAPI.service;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ZookeeperService {

    public final static String PATH = "/test";
    public static final String ZOOKEEPER_PATH_TEST = "zookeeper PATH : /test";
    public static final String CONNECT_STRING = "127.0.0.1:2181";
    public static final String TEST = "test";
    public Watcher watcher;
    public ZooKeeper zookeeper;

    private void initService() {
        watcher = event -> System.out.println(ZOOKEEPER_PATH_TEST);
        try {
            zookeeper = new ZooKeeper(CONNECT_STRING, 1000, watcher);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Stat stat = null;
        try {
            stat = zookeeper.exists(PATH, true);
            if (stat == null) {
                addNodeData(PATH, TEST);
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public ZookeeperService(Watcher watcher, ZooKeeper zooKeeper) {
        this.watcher = watcher;
        this.zookeeper = zooKeeper;
        addNodeData(PATH, TEST);
    }

    public ZookeeperService() {
        initService();
    }

    public String listNodeData() {
        List<String> children = new ArrayList<>();
        try {
            children = zookeeper.getChildren(PATH, true);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        if(children.isEmpty()) return "";
        return String.join(",", children);
    }

    public boolean addNodeData(String path, String data) {
        try {
            zookeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean deleteNode(String path) {
        try {
            zookeeper.delete(path, -1);
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean updateNodeData(String path, String data) {
        try {
            zookeeper.setData(path, data.getBytes(), -1);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

}
