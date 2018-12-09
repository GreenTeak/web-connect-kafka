package com.exampleAPI.zooKeeperAPI.service;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static com.exampleAPI.zooKeeperAPI.service.ZookeeperService.PATH;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ZookeeperServiceTest {

    public static final String TEST_TEST_3 = "/test/test3";
    public static final String TEST_3 = "test3";

    public Watcher watcher;

    public ZooKeeper zk;

    public ZookeeperService zookeeperService;

    @Before
    public void setUp() {
        watcher = mock(Watcher.class);
        zk = mock(ZooKeeper.class);
        zookeeperService = new ZookeeperService(watcher, zk);
    }

    @Test
    public void zkCreate() throws KeeperException, InterruptedException {
        zookeeperService.addNodeIfNotExists(TEST_TEST_3, TEST_3);
        verify(zk).create(TEST_TEST_3,
                TEST_3.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Test
    public void zkUpdate() throws KeeperException, InterruptedException {
        zookeeperService.updateNodeData(TEST_TEST_3, TEST_3);
        verify(zk, times(1)).setData(TEST_TEST_3, TEST_3.getBytes(), -1);
    }

    @Test
    public void zkGet() throws KeeperException, InterruptedException {
        zookeeperService.listNodeData();
        verify(zk, times(1)).getChildren(PATH, true);
    }

    @Test
    public void zkDelete() throws KeeperException, InterruptedException {
        zookeeperService.deleteNode(TEST_TEST_3);
        verify(zk,times(1)).delete(TEST_TEST_3,-1);
    }
}