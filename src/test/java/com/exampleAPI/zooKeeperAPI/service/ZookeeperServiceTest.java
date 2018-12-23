package com.exampleAPI.zooKeeperAPI.service;

import com.exampleAPI.zooKeeperAPI.model.Node;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.exampleAPI.zooKeeperAPI.support.UserConstant.PATH;
import static com.exampleAPI.zooKeeperAPI.support.testConstant.TEST3;
import static com.exampleAPI.zooKeeperAPI.support.testConstant.TEST3_PATH;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ZookeeperServiceTest {

    public Watcher watcher;

    public ZooKeeper zk;

    public Node node = new Node(TEST3_PATH, TEST3);

    public ZookeeperService zookeeperService;

    @Before
    public void setUp() throws KeeperException, InterruptedException {
        watcher = mock(Watcher.class);
        zk = mock(ZooKeeper.class);
        when(zk.exists(TEST3_PATH,true)).thenReturn(null);
        zookeeperService = new ZookeeperService(watcher, zk);
    }

    @Test
    public void shouldCreateOnceWhenAddNode() throws KeeperException, InterruptedException {
        zookeeperService.addNodeIfNotExists(node);
        verify(zk).create(TEST3_PATH,
                TEST3.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Test
    public void shouldUpdateOnceWhenUpdateNode() throws KeeperException, InterruptedException {
        zookeeperService.updateNodeData(node);
        verify(zk, times(1)).setData(node.getPath(), node.getContent().getBytes(), -1);
    }

    @Test
    public void shouldListNodeOnceWhenListNode() throws KeeperException, InterruptedException {
        zookeeperService.listNodeData();
        verify(zk, times(1)).getChildren(PATH, true);
    }

    @Test
    public void shouldDeleteOnceWhenDeleteOnce() throws KeeperException, InterruptedException {
        zookeeperService.deleteNode(TEST3_PATH);
        verify(zk,times(1)).delete(TEST3_PATH,-1);
    }
}