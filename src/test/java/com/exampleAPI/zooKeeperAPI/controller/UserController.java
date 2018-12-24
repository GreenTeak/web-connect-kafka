package com.exampleAPI.zooKeeperAPI.controller;

import com.exampleAPI.zooKeeperAPI.model.User;
import com.exampleAPI.zooKeeperAPI.service.UserService;
import com.exampleAPI.zooKeeperAPI.service.ZookeeperService;
import com.exampleAPI.zooKeeperAPI.support.JsonAndObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.zookeeper.KeeperException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static com.exampleAPI.zooKeeperAPI.support.JsonAndObject.ObjectToJson;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.EMAIL;
import static com.exampleAPI.zooKeeperAPI.support.UserConstant.PASSWORD;
import static com.exampleAPI.zooKeeperAPI.support.testConstant.TEST;
import static com.exampleAPI.zooKeeperAPI.support.testConstant.TEST_QQ_COM;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UserController {

    @Autowired
    protected MockMvc mvc;

    @MockBean
    public ZookeeperService zookeeperService;

    private UserService userService;

    private String requestJson;

    private User user;

    @Before
    public void setUp() throws IOException {
        userService = new UserService(zookeeperService);
        userService = mock(UserService.class);
        user = new User(TEST_QQ_COM, TEST, 12);
        requestJson = ObjectToJson(user);
    }

    @Test
    public void shouldBeReturnStatusIsOkWhenRegisterUser() throws Exception {
        mvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldBeReturnAgeWhenUserLogin() throws Exception {
        mvc.perform(get("/api/user/login")
                .param(EMAIL, TEST_QQ_COM)
                .param(PASSWORD, TEST)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldBeReturnStatusIsAcceptedWhenUserUpdate() throws Exception {
        mvc.perform(put("/api/user/update")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void shouldBeReturnStatusIsAcceptedWhenUserDelete() throws Exception {
        String request = "{\"email\":\"test@qq.com\"}";
        mvc.perform(delete("/api/user/delete")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }
}
