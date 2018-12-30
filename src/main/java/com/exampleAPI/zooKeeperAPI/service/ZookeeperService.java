package com.exampleAPI.zooKeeperAPI.service;

import com.exampleAPI.zooKeeperAPI.model.Node;
import com.exampleAPI.zooKeeperAPI.model.User;
import lombok.Data;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.exampleAPI.zooKeeperAPI.support.JsonAndObject.JsonToObject;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.CONNECT_STRING;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.DEFAULT_TEST;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.DELIMITER;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.TEST;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.ZOOKEEPER_PATH_TEST;


@Service
@Data
public class ZookeeperService {

    public ZooKeeper zookeeper;
    public Watcher watcher;

    public ZookeeperService(Watcher watcher, ZooKeeper zooKeeper) {
        this.watcher = watcher;
        this.zookeeper = zooKeeper;
    }

    public ZookeeperService() throws InterruptedException, IOException, KeeperException {
        watcher = event -> System.out.println(ZOOKEEPER_PATH_TEST);
        zookeeper = new ZooKeeper(CONNECT_STRING, 10000, watcher);
        addNodeIfNotExists(new Node(DEFAULT_TEST, TEST));
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

    public void addNodeIfNotExists(Node node) throws KeeperException, InterruptedException {
        Stat stat = getStat(node.getPath());
        if (stat == null) {
            addNodeData(node.getPath(), node.getContent());
        }
    }

    public Stat getStat(String path) throws KeeperException, InterruptedException {
        return zookeeper.exists(path, true);
    }

    public User getData(String path) throws KeeperException, InterruptedException, IOException {
        byte[] data = zookeeper.getData(path, false, null);
        return JsonToObject(new String(data));
    }

    private void addNodeData(String path, String data) throws KeeperException, InterruptedException {
        zookeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }
}
