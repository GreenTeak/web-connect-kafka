package com.exampleAPI.zooKeeperAPI.controller;

import com.exampleAPI.zooKeeperAPI.model.Node;
import com.exampleAPI.zooKeeperAPI.model.User;
import com.exampleAPI.zooKeeperAPI.service.UserService;
import com.exampleAPI.zooKeeperAPI.service.ZookeeperService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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

import java.net.PortUnreachableException;

import static com.exampleAPI.zooKeeperAPI.controller.ZookeeperControllerTest.TEST2;
import static com.exampleAPI.zooKeeperAPI.controller.ZookeeperControllerTest.TEST2_PATH;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UserController {

    private UserService userService;

    @MockBean
    private ZookeeperService zookeeperService;

    @Autowired
    protected MockMvc mvc;

    private String requestJson;

    private User user;

    @Before
    public void setUp() throws JsonProcessingException {
        userService = new UserService(zookeeperService);
        userService = mock(UserService.class);
        user = new User("test@qq.com", "test", 12);
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        requestJson = ow.writeValueAsString(user);
    }

    @Test
    public void shouldBeReturnStatusIsOkWhenRegisterUser() throws Exception {
        mvc.perform(post("/api/user/register")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldBeReturnAgeWhenUserLogin() throws Exception {
        mvc.perform(get("/api/user/login")
                .param("email", "test@qq.com")
                .param("password", "test")
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
