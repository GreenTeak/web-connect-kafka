package com.exampleAPI.zooKeeperAPI.service;

import com.exampleAPI.zooKeeperAPI.model.Node;
import com.exampleAPI.zooKeeperAPI.model.User;
import com.exampleAPI.zooKeeperAPI.support.JsonAndObject;
import com.exampleAPI.zooKeeperAPI.support.testConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.zookeeper.KeeperException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;

import static com.exampleAPI.zooKeeperAPI.support.testConstant.TEST;
import static com.exampleAPI.zooKeeperAPI.support.testConstant.TEST_1;
import static com.exampleAPI.zooKeeperAPI.support.testConstant.TEST_PATH;
import static com.exampleAPI.zooKeeperAPI.support.testConstant.TEST_PATH_QQ_COM;
import static com.exampleAPI.zooKeeperAPI.support.testConstant.TEST_QQ_COM;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class UserServiceTest {

    @MockBean
    private ZookeeperService zookeeperService;

    @Autowired
    public JsonAndObject jsonAndObject;

    private UserService userService;

    private User user = new User(TEST_QQ_COM, TEST, 12);

    @Before
    public void setUp() throws KeeperException, InterruptedException, IOException {
        zookeeperService = mock(ZookeeperService.class);

        when(zookeeperService.getData(TEST_PATH_QQ_COM)).thenReturn(user);
        when(zookeeperService.getData(TEST_PATH_QQ_COM)).thenReturn(null);
        when(zookeeperService.getStat(TEST_PATH_QQ_COM)).thenReturn(null);

        userService = new UserService(zookeeperService);
    }

    @Test
    public void shouldBeAddNodeWhenAddUser() throws InterruptedException, KeeperException, JsonProcessingException {
        userService.addUser(user);

        verify(zookeeperService, times(1)).addNodeIfNotExists(any(Node.class));
    }

    @Test
    public void shouldBeReturnNegativeWhenPasswordIsWrong() throws KeeperException, InterruptedException, IOException {
        Integer test1 = userService.userLogin(TEST_QQ_COM, TEST_1);

        assertEquals(test1.intValue(), -1);
    }

    @Test
    public void shouldBeReturnNegativeWhenEmailIsWrong() throws KeeperException, InterruptedException, IOException {
        Integer test1 = userService.userLogin(TEST_QQ_COM, testConstant.TEST);

        assertEquals(test1.intValue(), -1);
    }

    @Test
    public void shouldBeUpdateOnceWhenUpdateUser() throws InterruptedException, KeeperException, JsonProcessingException {
        String userJson = jsonAndObject.ObjectToJson(user);
        Node node = new Node(TEST_PATH + user.getEmail(), userJson);

        userService.updateUser(user);

        verify(zookeeperService, times(1)).updateNodeData(node);
    }

    @Test
    public void shouldBeDeleteOnceWhenDeleteUser() throws KeeperException, InterruptedException {
        userService.deleteUser(TEST_QQ_COM);

        verify(zookeeperService, times(1)).deleteNode(TEST_PATH_QQ_COM);
    }
}
