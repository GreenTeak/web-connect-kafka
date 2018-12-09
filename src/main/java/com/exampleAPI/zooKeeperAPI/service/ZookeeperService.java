package com.exampleAPI.zooKeeperAPI.service;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ZookeeperService {

    public final static String PATH = "/test";
    public final static String SEPERATOR = "/";
    public Watcher watcher;
    public ZooKeeper zookeeper;

    private void initService() {
        watcher = event -> System.out.println("zookeeper PATH : /test");
        try {
            zookeeper = new ZooKeeper("127.0.0.1:2181", 1000, watcher);
        } catch (IOException e) {
            e.printStackTrace();
        }
        addNodeData("test", "test");
    }

    public ZookeeperService(Watcher watcher, ZooKeeper zooKeeper) {
        this.watcher = watcher;
        this.zookeeper = zooKeeper;
        addNodeData("test", "test");
    }

    public ZookeeperService() {
        initService();
    }

    public String listNodeData() {
        List<String> children = null;
        try {
            children = zookeeper.getChildren(PATH, true);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        assert children != null;
        return String.join(",", children);
    }

    public boolean addNodeData(String path, String data) {
        try {
            zookeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
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
