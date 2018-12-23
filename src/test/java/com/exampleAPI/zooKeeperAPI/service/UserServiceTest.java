package com.exampleAPI.zooKeeperAPI.service;

import com.exampleAPI.zooKeeperAPI.model.Node;
import com.exampleAPI.zooKeeperAPI.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
    @MockBean
    private ZookeeperService zookeeperService;

    private UserService userService;

    private User user = new User("test@qq.com", "test", 12);

    @Before
    public void setUp() throws KeeperException, InterruptedException, IOException {
        zookeeperService = mock(ZookeeperService.class);
        when(zookeeperService.getData("/test/test@qq.com")).thenReturn(user);
        when(zookeeperService.getData("/test/test1@qq.com")).thenReturn(null);
        when(zookeeperService.getStat("/test/test1@qq.com")).thenReturn(null);
        userService = new UserService(zookeeperService);
    }

    @Test
    public void shouldBeAddNodeWhenAddUser() throws InterruptedException, KeeperException, JsonProcessingException {
        userService.addUser(user);
        verify(zookeeperService, times(1)).addNodeIfNotExists(any(Node.class));
    }

    @Test
    public void shouldBeReturnNegativeWhenPasswordIsWrong() throws KeeperException, InterruptedException, IOException {
        Integer test1 = userService.userLogin("test@qq.com", "test1");
        assertEquals(test1.intValue(), -1);
    }

    @Test
    public void shouldBeReturnAgeWhenPasswordIsRight() throws KeeperException, InterruptedException, IOException {
        Integer test1 = userService.userLogin("test@qq.com", "test");
        assertEquals(test1.intValue(), 12);
    }

    @Test
    public void shouldBeReturnNegativeWhenEmailIsWrong() throws KeeperException, InterruptedException, IOException {
        Integer test1 = userService.userLogin("test1@qq.com", "test");
        assertEquals(test1.intValue(), -1);
    }

    @Test
    public void shouldBeUpdateOnceWhenUpdateUser() throws InterruptedException, KeeperException, JsonProcessingException {
        userService.updateUser(user);
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String userJson = ow.writeValueAsString(user);
        Node node = new Node("/test/" + user.getEmail(), userJson);
        verify(zookeeperService, times(1)).updateNodeData(node);
    }

    @Test
    public void shouldBeDeleteOnceWhenDeleteUser() throws KeeperException, InterruptedException {
        userService.deleteUser("test@qq.com");
        verify(zookeeperService,times(1)).deleteNode("/test/test@qq.com");
    }
}
