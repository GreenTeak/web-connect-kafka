package com.exampleAPI.zooKeeperAPI.service;

import com.exampleAPI.zooKeeperAPI.model.Node;
import com.exampleAPI.zooKeeperAPI.model.User;
import com.exampleAPI.zooKeeperAPI.support.JsonAndObject;
import lombok.Data;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.exampleAPI.zooKeeperAPI.support.UserConstant.CONNECT_STRING;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.DELIMITER;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.PATH;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.TEST;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.ZOOKEEPER_PATH_TEST;


@Service
@Data
public class ZookeeperService {

    public static final String DEFAULT_TEST = "/test";
    @Autowired
    public JsonAndObject jsonAndObject;

    public ZooKeeper zookeeper;
    public Watcher watcher;

    public ZookeeperService(Watcher watcher, ZooKeeper zooKeeper) {
        this.watcher = watcher;
        this.zookeeper = zooKeeper;
    }

    public ZookeeperService() throws InterruptedException, IOException, KeeperException {
        watcher = event -> System.out.println(ZOOKEEPER_PATH_TEST);
        zookeeper = new ZooKeeper(CONNECT_STRING, 1000, watcher);
        addNodeIfNotExists(new Node(PATH, TEST));
    }


    public String listNodeData() throws KeeperException, InterruptedException {
        List<String> children = zookeeper.getChildren(DEFAULT_TEST, true);
        if (children.isEmpty()) return "";
        return String.join(DELIMITER, children);
    }

    public void deleteNode(String path) throws KeeperException, InterruptedException {
        zookeeper.delete(path, -1);
    }

    public void updateNodeData(Node node) throws KeeperException, InterruptedException {
        zookeeper.setData(node.getPath(), node.getContent().getBytes(), -1);
    }

    public boolean addNodeIfNotExists(Node node) throws KeeperException, InterruptedException {
        Stat stat = getStat(node.getPath());
        if (stat == null) {
            addNodeData(node.getPath(), node.getContent());
            return true;
        }
        return false;
    }

    public Stat getStat(String path) throws KeeperException, InterruptedException {
        return zookeeper.exists(path, true);
    }

    public User getData(String path) throws KeeperException, InterruptedException, IOException {
        byte[] data = zookeeper.getData(path, false, null);
        User user = jsonAndObject.JsonToObject(new String(data));
        return user;
    }

    private void addNodeData(String path, String data) throws KeeperException, InterruptedException {
        zookeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }
}
